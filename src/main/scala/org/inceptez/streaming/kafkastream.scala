package org.inceptez.streaming
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.{ Seconds, StreamingContext }
import StreamingContext._
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
object kafkastream {
  def main( args : Array[ String ] ) {
    val sparkConf = new SparkConf().setAppName( "kafkastream" ).setMaster( "local[*]" )
    val sparkcontext = new SparkContext( sparkConf )
    sparkcontext.setLogLevel( "ERROR" )
    val ssc = new StreamingContext( sparkcontext, Seconds( 10 ) )
    //ssc.checkpoint("checkpointdir")

    val kafkaParams = Map[ String, Object ](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[ StringDeserializer ],
      "value.deserializer" -> classOf[ StringDeserializer ],
      "group.id" -> "we33",
      "auto.offset.reset" -> "earliest"
    //,"auto.commit.offset" -> (true:java.lang.Boolean)
    )

    val topics = Array( "tk1" )

    //3 executors (containers) equally distributed, 3 executors -> node where brokers running
    val stream = KafkaUtils.createDirectStream[ String, String ]( ssc,
      PreferConsistent, // PreferBroker / PreferFixed
      // PreferConsistent -> Equal distribution across all executors -> exe1 (100), 2(100), 3(100) (JVM -CONTAINERS)
      // PreferBroker -> 10 node cluster (7 nodes spark executors/container can run) -> (3 nodes where kafka brokers (socket/hdd) running) ->
      // (6 exec will be started in the kafka broker nodes) ->
      // PreferFixed -> define your own location strategy (where you want to run the executors) - small clusters
      Subscribe[ String, String ]( topics, kafkaParams ) )

    stream.foreachRDD{
      x ⇒

        x.foreach( println )

        //0,hi
        // 1,hello
        val offsetranges = x.asInstanceOf[ HasOffsetRanges ].offsetRanges
        offsetranges.foreach( println )

        //fault tolerance, decouples the systems (spark streaming sleep,  kafka (24/7) will capture the messages with any spike)
        //read completed from kafka (msg1,1)

        val rdd1 = x.flatMap( x ⇒ x.value().split( " " ) ).map( x ⇒ ( x, 1 ) ).reduceByKey( _ + _ )
        rdd1.foreach( println )

        // whether spark wrote this (msg1,1) into his storage or not
        rdd1.saveAsTextFile( "hdfs://localhost:54310/user/hduser/kafkastreamout/outdir" )
      //commit to kafka (commit)
    }

    ssc.start()
    ssc.awaitTermination()
  }
}
