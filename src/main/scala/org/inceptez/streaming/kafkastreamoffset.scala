package org.inceptez.streaming
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming._
import org.apache.spark.sql._
import StreamingContext._
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.json4s.{DefaultFormats,jackson}
object kafkastreamoffset {
def main(args:Array[String])
{
val sparkConf = new SparkConf().setAppName("kafkastreamoffset").setMaster("local[*]")
val sparkcontext = new SparkContext(sparkConf)
sparkcontext.setLogLevel("ERROR")
val sqlctx=new org.apache.spark.sql.SQLContext(sparkcontext);
val ssc = new StreamingContext(sparkcontext, Seconds(10))
//ssc.checkpoint("/tmp/checkpointdir1")
val kafkaParams = Map[String, Object](
"bootstrap.servers" -> "localhost:9092",
"key.deserializer" -> classOf[StringDeserializer],
"value.deserializer" -> classOf[StringDeserializer],
"group.id" -> "group1",
"auto.offset.reset" -> "latest",
//"auto.commit.offset" -> (false:java.lang.Boolean),
"enable.auto.commit" -> (false:java.lang.Boolean) 
// fire and forget message (dont bother whether the ss have consumed it or not), next message
// async mode -> after ss consumed it fully after writing to the target, the offset will be commited
//stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetranges)
)

val topics = Array("tk1")
val stream = KafkaUtils.createDirectStream[String, String](ssc,
PreferConsistent,
Subscribe[String, String](topics, kafkaParams)
)
//__consumer_offsets dont do the read time commit

import sqlctx.implicits._

stream.foreachRDD{
  a =>
    a.foreach(println)
    val offsetranges=a.asInstanceOf[HasOffsetRanges].offsetRanges //95->98 (3 messages)
    offsetranges.foreach(println)
    
    val rdd1=a.flatMap(x => x.value().split(" ")).map(x => (x, 1)).reduceByKey(_ + _)
    rdd1.foreach(println)
    
    val df1=rdd1.toDF("course","cnt")
    df1.createOrReplaceTempView("view1")
    val df2=sqlctx.sql("select distinct * from view1 order by cnt desc");
    
    //batch accuracy, batch reports
    //realtime streaming for performance not for 100% accuracy

println("Writing to mysql")
println(java.time.LocalTime.now)

val prop=new java.util.Properties();
prop.put("user", "root")
prop.put("password", "root")

df1.write.mode("append").jdbc("jdbc:mysql://localhost/custdb","df1spark",prop)
println("completed mysql1 write")

df2.write.mode("append").jdbc("jdbc:mysql://localhost/custdb","df2spark",prop)
println("completed mysql2 write")

//95%

//__consumer_offsets do the commit after i stored the data successfully, dont do the commit when i just read the data from kafka

stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetranges)
println("commiting offset")
}
ssc.start()
ssc.awaitTermination()
}
}