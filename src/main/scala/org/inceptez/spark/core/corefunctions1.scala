package org.inceptez.spark.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.storage._
// spend (350) -> 200+150 (400hrs) 8months-> 20l -> 40l  - (12+ -> 10%, 7-> 10000 hrs)

object corefunctions1  // instantiated class corefunctions1
{
  
  def main(args:Array[String])
  {
   //define spark configuration object
    val conf = new SparkConf().setAppName("Local-sparkcore").setMaster("local[*]")
   //define spark context object
    val sc = new SparkContext(conf)
   //Set the logger level to error
   sc.setLogLevel("ERROR")

   // Create a file rdd with 4 partitions

   val rdd = sc.textFile("file:/home/hduser/hive/data/txns",4)
   // create a hadoop rdd with 4 partitions 
   // create the below dir in hadoop and place the file txns in the below dir
   //hadoop fs -mkdir -p /user/hduser/hive/data/
   //hadoop fs -put -f ~/hive/data/txns hive/data/
   
   val hadooprdd = sc.textFile("hdfs://localhost:54310/user/hduser/hive/data/txns",4)
   println("total number of lines in hadoop - " + hadooprdd.count)
   println("print a sample of 20 rows with seed of 100 for dynamic data")
   rdd.takeSample(true, 10, 110).foreach(println); // a,b,x, y,z,b
   println("print only the first line of the dataset " );
   println(rdd.first());
   println("Number of partition of the base file is " + rdd.getNumPartitions);
   
   //Create a splitted rdd to split the line of string to line of array
   val rddsplit=rdd.map(x=>x.split(","))

////////////////////////////////////////////////////////////////////////////////////////////////////   
   // filter only the category contains exercise or product contains jumping.
   val rddexerjump = rddsplit.filter(x => x(4).toUpperCase.contains("EXERCISE") || x(5).toUpperCase.startsWith("JUMP"))
   val valexerjumpcnt= rddexerjump.count()

   
   println("Dynamically increase or decrease the number of partitions based on the volume of data")
  println("Partition handling in Spark")
  
  //Try to convert this as a function
 // volume of data varies on a daily basis, how do u improve the performance 

  var rddexerjumpnew=sc.emptyRDD[Array[String]];
  
   println(rddexerjumpnew.isEmpty())
   
  if (valexerjumpcnt > 0 && valexerjumpcnt <= 10000)
  {
    rddexerjumpnew=rddexerjump.coalesce(2);
    println(s"Number of partition of the filtered data with $valexerjumpcnt count " + rddexerjumpnew.getNumPartitions);    
  }
  else if (valexerjumpcnt > 10001 && valexerjumpcnt < 20000)
  {
    rddexerjumpnew=rddexerjump.coalesce(3);
    println(s"Number of partition of the filtered data with $valexerjumpcnt count " + rddexerjumpnew.getNumPartitions); 
  }
  else 
  {
    rddexerjumpnew=rddexerjump.repartition(6);  
    println(s"Number of partition of the filtered data with $valexerjumpcnt count " + rddexerjumpnew.getNumPartitions); 
  }
  
   val rddexerjumpcnt1=rddexerjumpnew.count();      
   println("Count the filtered rdd with new partition " + rddexerjumpcnt1)
   println("Display only first row ")
   rddexerjumpnew.take(1)
   println(" Check whether the newrdd is empty true/false " + rddexerjumpnew.isEmpty())
   println(" New RDD partition count " + rddexerjumpnew.getNumPartitions)
   println(s"No of lines are $rddexerjumpcnt1 with exercise or jumping")
//////////////////////////////////////////////////////////////////////////////////////////////   
   
   // Identify the non credit transactions alone
   
   val rddcredit = rddsplit.filter(x => !x.contains("credit"))
   
   val rddcreditexact = rddsplit.filter(x => !x(8).equals("credit"))
   
   val cnt = rddcreditexact.count()
  println(s"No of lines that does not contain Credit: " + cnt)
   
  println(" Transform the RDD with only California data who did cash transactions ")   
   val rdd2 = rddsplit.filter(x => x(7) == "California" && x(8) == "cash")
  println(" Transform the RDD with taking only amount column ")
   val rdd3 = rdd2.map(x => x(3).toDouble)
   //rdd3=Array(32.65,10.01)
   
   
   /*
    val rddtup3 = rdd2.map(x => (x(3).toDouble,x(3).toDouble)) 
   
   val rddtup4 = rdd2.map(x => ((x(4),x(5)),(x(3).toDouble,x(3).toDouble))) 
 select category,sum(amt),count(category),min(amt) from tbl group by category
   //1 -> 10.1,12.1 -> 22.2
   //2 -> 24.2,22.3 -> 46.5
   //rddtup3.reduce(_+_)

   println("two columns aggregation :")
//   rddtup4.reduceByKey((x,y)=>((x._1+y._1),(x._2+y._2))).take(10).foreach(println)
   */
   //rdd3=Array(32.65,10.01,5.0)   
  println(" Caching of RDDs ")
     rdd3.cache()
     rdd3.unpersist()
     
     
     rdd3.persist(StorageLevel.MEMORY_AND_DISK)
     
     val sumofsales = rdd3.sum()
     println("Sum of Sales in california with cash: " + sumofsales)
     
     val sumofsalesreduce= rdd3.reduce((x,y)=>x+y);
     //62.66
     println("Sum of total Sales in california with cash using reduce : " + sumofsalesreduce)
     //rdd3=Array(32.65)
     // x=0, y=32.65 x=32.65+y=10.01 -> x=42.66  , y =5.0
     val sumofsalesreduce1= rdd3.reduce((x,y)=>  if (y > 10.0) x+y else x  );
     val sumofsalesreduce2= rdd3.reduce((x,y)=>  if (y > 20.0) x+y else x  );
     println("Sum of Sales where trans amount is > 10 dollar: " + sumofsalesreduce1)
     
     println(" Lets try to achieve the same above functionality using filter")
     val rddfilter3 = rdd2.filter(x=>x(3).toDouble>10.0).map(x => x(3).toDouble)

     val sumofsalesfilterreduce= rddfilter3.reduce((x,y)=>x+y);
     println("Sum of sales (using filter) where minimum trans amount is > 10 dollar: " + sumofsalesfilterreduce)
     
     val maxofsales = rdd3.max()
     println("Max sales value : " + maxofsales)
     
     
     val totalsales = rdd3.count()     
     println("Total no fo sales: " + totalsales)
     
     val minofsales1 = rdd3.min

     val minmaxsumofsales = rddmin(rdd3)//(rdd3.min,rdd3.max,rdd3.sum)
     println(" sales value computed using rddmin method which takes rdd as input and returns the min sales: " + minmaxsumofsales._1)
     println("sales value computed using rddmin method which takes rdd as input and returns the max sales: " + minmaxsumofsales._2)
     println("sales value computed using rddmin method which takes rdd as input and returns the sum sales: " + minmaxsumofsales._3)
     
     val avgofsales = sumofsales/rdd3.count()
     println("Avg sales value : " + avgofsales)
        
     rdd3.unpersist();
     
 /////////////////////////////////////////////////////////////////////////////
     
   val rddtrimupper=rddsplit.map(x=>(x(0),x(1),x(2),x(3),cleanup(x(4))))
   println(" Printing the sample data of the rddtrimupper applied trim and upper case on category using trimupcase udf")
   val x=rddtrimupper.take(10)
   x.foreach(println)

   //val rddexerjump = rddsplit.filter(x => x(4).toUpperCase.contains("EXERCISE") || x(5).toUpperCase.startsWith("JUMP"))
     val rddsubtract = rddsplit.subtract(rddexerjumpnew)
     .map(x=>(x(0),x(1),x(2),x(4),x(6)))
  rddsubtract.take(10).foreach(println)
  
   //val rddunion2cols = rddunion.map(x=>(x._3,x._4))
     val rddsubtractdistinct = rddsubtract.distinct
     
  println("Deduplicated count of subtracted rdd contains non exercise and jumping data  " + rddsubtractdistinct.count);
  
  println("City wise count : ")
  
  val rddkvpair=rddsplit.map(x=>((x(6)),(x(3).toDouble))) 
  rddkvpair.countByKey().take(10).foreach(println)
  
  println("Transction wise count : ")
  val rddcntbyval=rddsplit.map(x=>(x(8))) 
  rddcntbyval.countByValue.take(10).foreach(println)
  
  println("City wise sum of amount : ")
  rddkvpair.reduceByKey(_+_).take(10).foreach(println)
  
  println("City wise maximum amount of sales: ")
  rddkvpair.reduceByKey((a,b)=> (if (a > b) a else b)).take(10).foreach(println)
  
  println("City wise minimum amount of sales calling methods: ")
  val rddcity=rddkvpair.reduceByKey((a,b)=> a+b)
  //phidelphia,(10.1,10,20),baltimore,(10,20,30) -> (10.1,10,20).reduce(x,y=>x+y) -> (phidelphia,40.1)
  //phidelphia -> 
  rddcity.take(5).foreach(println)
  
   println("Brodcast real example")

   println("Create a broadcast variable manually ")   
   //driver
   val kvpair: Map[String,Int] = Map("credit" -> 2, "cash" -> 1)

   // kvpair - > ganaa.com (US)
   // broadcastkvpair -> fm (AIR)
   // workers
   val broadcastkvpair=sc.broadcast(kvpair)
   
   val custrdd=sc.textFile("file:/home/hduser/hive/data/custs");
  
   custrdd.cache;
   println("""Pass the key of the trans data spentby to the broadcast variable and get the 
              respective value and sum with amount """)   
   // for every row the kvpair in driver will be called
   //val broadcastrdd=rddsplit.map(x=>(x(0),x(1),x(3).toDouble-kvpair(x(8))))
   
  //worker
     val broadcastrdd=rddsplit.map(x=>(x(0),x(1),x(3).toDouble-broadcastkvpair.value(x(8))))
   
   broadcastrdd.take(4).foreach(println);
   
   
   val custkvpair = custrdd.map(x => x.split(",")).filter(x=>x.length==5).map(x => (x(0),x(4))).collectAsMap
   
   val broadcastcustkvpair=sc.broadcast(custkvpair)
   
   println("""Load the custid,profession in a broadcast var and join with the transaction rdd to add the 
            profession in the output""")
   val broadcastcustrdd=rddsplit.map(x=>(x(0),x(8),broadcastcustkvpair.value(x(2))))
   //broadcastcustkvpair.destroy()
   broadcastcustrdd.take(4).foreach(println);
  
  println("Save the output to hadoop ")
  
  
  
  val fs = org.apache.hadoop.fs.FileSystem.get(new java.net.URI("hdfs://localhost:54310"),sc.hadoopConfiguration)
  fs.delete(new org.apache.hadoop.fs.Path("/user/hduser/broadcastrdd"),true)
  
  broadcastrdd.saveAsTextFile("hdfs://localhost:54310/user/hduser/broadcastrdd")
  
  /// Join Scenario
  
  println("Join Scenario (join is not advisable in spark core)")
  println("Create paired rdd to perform join from trans and cust data ")  
  val transkvpair = rddsplit.map(x => (x(2),(x(0),x(1),x(3),x(5))))
  transkvpair.cache;
  
  val custrddkvpair = custrdd.map(x => x.split(",")).map(x => (x(0),(x(2),x(3))))
  println("Perform inner join ")    
  val custtransjoin = transkvpair.join(custrddkvpair)
  println("Map the required fields after joining ")    
  val finaljoinrdd=custtransjoin.map(x=>(x._1,x._2._1._3))  
  println("Print the required fields after joining ")
  finaljoinrdd.take(10).foreach(println)
  println("Writing the output to hdfs with the ~ delimiter using productiterator and mkstring")

  fs.delete(new org.apache.hadoop.fs.Path("/user/hduser/joinedout/"),true)  
  custtransjoin.map(x=>(x._1,x._2._1._3).productIterator.mkString("~")).saveAsTextFile("hdfs://localhost:54310//user/hduser/joinedout/")
//custtransjoin.toDF().write.option("delimiter","|").csv("location")
  }
  
  def mintrans(a:Double,b:Double):Double=
  {
    if (a < b) a else b    
  }
  
  
  def cleanup(a:String):String=
  {
    return(a.trim().toUpperCase())
  }
  
  def rddmin(a:org.apache.spark.rdd.RDD[Double]):(Double,Double,Double)={return (a.min(),a.max(),a.sum())}

   
}





