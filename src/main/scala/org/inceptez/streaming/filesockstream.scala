package org.inceptez.streaming
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.sql.SparkSession

object filesockstream {

//  cd /home/hduser/sparkdata
    //head -300 auctiondata > ~/sparkdata/streaming/data18

def main(args:Array[String])
{
val spark =
SparkSession.builder.appName("Dynamic Pricing").master("local[*]")
.getOrCreate();

val sc = spark.sparkContext;
sc.setLogLevel("ERROR")
val ssc = new StreamingContext(sc, Seconds(60))
//val ssc1 = new StreamingContext(sc, Seconds(60))

println("streaming context defined")

val lines = ssc.textFileStream("file:///home/hduser/sparkdata/streaming/")
println("textfilestream initialized")
//tk1

val socketdata = ssc.socketTextStream("localhost", 9999)
println("socket stream initialized")
//tk2

import spark.implicits._
println("Socket Data processing")
println(java.time.LocalTime.now)

socketdata.foreachRDD(x => {
if(!x.isEmpty)
{
println(java.time.LocalTime.now)

//cartier,.05
//xbox,.02

val df1=x.map(_.split(",")).filter(x=>x.length==2).map(x=>(x(0),x(1).toFloat)).map(x=>(x._1,x._2)).toDF("item","amtpercent")
  println("printing socket data")
  df1.createOrReplaceTempView("socketview")
  val sockdf=spark.sql("select distinct item,amtpercent from socketview ")
  sockdf.createOrReplaceTempView("sockviewdeduplicated")
  sockdf.show(10,false);
  //cartier,5
  //xbox,6
  //palm,10
  println(java.time.LocalTime.now)

}
else
{
  println("No socket data available")
}
}
)

// socket stream completed

import spark.implicits._
lines.foreachRDD(x => {
if(!x.isEmpty)
{
println("How to convert an rdd to DS using case class")
println("file data processing")
println(java.time.LocalTime.now)
print("Creating Schema RDD\n")
val ebay = x.map(_.split("~")).filter(x=>x.length==9).map(p => org.inceptez.spark.sql.reusablefw.Auction(p(0), p(1).toFloat, p(2).toFloat,p(3),
p(4).toInt, p(5).toFloat, p(6).toFloat, p(7), p(8).toInt))

print("Converting RDD to dataset\n")
val ebayds = ebay.toDS();
ebayds.createOrReplaceTempView("ebayview");

val auctioncnt = spark.sql("select count(1) from ebayview");
print("total auctions are\n")
auctioncnt.show(1,false);
println("total distinct auctions\n")
val count = ebayds.select("auctionid").distinct.count
print(count);
print("Max bid, sum of price of items are\n")
import org.apache.spark.sql.functions._
val grpbid =
ebayds.groupBy("item").agg(max("bid").alias("max_bid"),sum("price").alias("sp")).sort($"sp".desc)
grpbid.show(10,false);

//or

println(" Creating Dataframe from RDD and map column names to apply the structure further\n")

println("How to convert an rdd to DF using Structure Type")

val ebaydf = x.map(x=>x.split("~")).filter(x=>x.length==9).map(p=>(p(0), p(1).toFloat, p(2).toFloat,p(3),
    p(4).toInt, p(5).toFloat, p(6).toFloat, p(7), p(8).toInt))
.toDF("auctionid","bid","bidtime","bidder","bidderrate","openbid","price","item","daystolive")

//ebaydf.show(5,false)

println(" Creating Dataframe using Structure Type\n")

import org.apache.spark.sql.types._;
val struct1=StructType(Array(StructField("auctionid",StringType,false),StructField("bid",FloatType,true),
    StructField("bidtime",FloatType,true),StructField("bidder",StringType,true)
    ,StructField("bidderrate",IntegerType,true),StructField("openbid",FloatType,true),
    StructField("price",FloatType,true),StructField("item",StringType,true),StructField("daystolive",IntegerType,true)))

val structdf=spark.createDataFrame(ebaydf.rdd,struct1)
structdf.printSchema()
//val ebayrowrdd=ebaydf.rdds

val structdf1=structdf.withColumn("loadts", current_timestamp())

//val rowrdd=ebaydf.rdd

structdf1.createOrReplaceTempView("ebayviewdf")

// file stream of 1 minute is arrived

val sockviewexists=spark.sqlContext.tableNames().contains("sockviewdeduplicated")
val fileviewexists=spark.sqlContext.tableNames().contains("ebayviewdf")

println(spark.sqlContext.tableNames())

println(sockviewexists);

if(sockviewexists && fileviewexists)
{
println(java.time.LocalTime.now)
val datatodb=spark.sql("""select a.auctionid,a.bid,a.bidtime,a.bidder,a.bidderrate,a.openbid,a.price,a.item,a.daystolive,a.price+(a.price*b.amtpercent) as newprice,loadts
               from ebayviewdf a left outer join sockviewdeduplicated b on a.item=b.item""")
print("Executing dataframe\n")
datatodb.show

println("Writing to mysql")
println(java.time.LocalTime.now)
val prop=new java.util.Properties();
prop.put("user", "root")
prop.put("password", "root")

datatodb.write.mode("append").jdbc("jdbc:mysql://localhost/custdb","ebayspark",prop)

println("completed mysql write")
println(java.time.LocalTime.now)
}
else
{
  println("No socket or file table in this iteration")
}

}
else
{
  println("No file data available")
}
})

ssc.start()
ssc.awaitTermination()
}
}
