package org.inceptez.streaming
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.streaming._;

object filestream {
  def main( args : Array[ String ] ) =
    {
      val spark = SparkSession.builder().appName( "spark streaming" ).master( "local[*]" )
        .getOrCreate()

      val sc = spark.sparkContext
      val sqlc = spark.sqlContext
      sc.setLogLevel( "Error" )

      val ssc = new StreamingContext( sc, Seconds( 10 ) );

      val lines = ssc.textFileStream( "/home/hduser/sparkdata/streaming/" );

      import spark.implicits._;
      /*
val courses = lines.flatMap(_.split(" "))
val coursesCounts = courses.map(x => (x, 1)).reduceByKey(_ + _)
coursesCounts.print()
*/

      lines.foreachRDD( x ⇒
        {
          val courses = x.flatMap( _.split( " " ) )
          val df1 = courses.toDF( "coursename" )
          df1.createOrReplaceTempView( "fileview" )
          spark.sql( "select coursename,count(1) from fileview group by coursename" ).show( 50, false )
        }
      )

      ssc.start();
      ssc.awaitTermination();

    }
}
