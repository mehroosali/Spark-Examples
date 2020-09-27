package org.inceptez.streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.dstream.DStream.toPairDStreamFunctions
object socketstream {
def main(args:Array[String])
{
val sparkSession = SparkSession.builder.appName("auctionsocketstream").master("local[*]")
    .config("spark.history.fs.logDirectory", "file:///tmp/sparklog")
    .config("spark.eventLog.dir", "file:////tmp/sparklog")
    .config("spark.eventLog.enabled", "true")
.getOrCreate();
val sparkcontext = sparkSession.sparkContext;
sparkcontext.setLogLevel("ERROR")
// Create the context
val ssc = new StreamingContext(sparkcontext, Seconds(10))

val lines = ssc.socketTextStream("localhost", 9999);

val auctionRDD = lines.map(line => line.split("~")).filter { x=>x.length>=8 }
val items_auctionRDD = auctionRDD.map(x => (x(7), 1)).reduceByKey((x, y) => x + y)
//Identify which item has more auction response
items_auctionRDD.print()
//total number of items (auctions)
val totalitems = auctionRDD.map(line => line(0)).count()
totalitems.print()
items_auctionRDD.saveAsTextFiles("hdfs://localhost:54310/user/hduser/auctionout/auctiondata")
ssc.start()
ssc.awaitTermination()
//ssc.awaitTerminationOrTimeout(50000)
}
}