package org.inceptez.scalaprograms

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Seq
import Array._

object collections7 {
// array  / list, Map, Tuple
  /*
   * Scala has rich set of collection library
   * Collection are the containers that holds elements 		
	   
   * 1. Seq - linear collection of element may be indexed or linear list
   * Array/List  - Mutable/Immutable (non resizable).
   * ArrayBuffer/ListBuffer - mutable, resizable
   * 
   * 2. Map - contains a collection of key value pairs
   * 3. Set - collection that contains no duplicate values
  	
   */
  def main(args:Array[String]):Unit =
  {
    /*
     * Seq -
     * 			- mutable Array
     * 			- immutable List
     *      
     * */
            
       
       // Also we can define as
       var s2 = Seq(10,20,30,40,50)
       //s2(4) =40 
       println("This shows Seq is mutable " + s2(4))
       
       //An array is sequential and is of a fixed size.
       //Array - Array in scala is sequential, fixed in size and mutable 
       
      
       //Declare Array with 1 element with type of Int
       
       //val ar = Array(Array(5,"hi"),Array(6,"Hello"))
       val ar = Array[Float](5);
       println("result of Array[Int](5) is : " + ar(0))
       
       //or
       
       val ar1 = Array(1,2,3,4,5)
       
       println("Fourth element of Array(1,2,3,4,5) is : " + ar1(3))
       
       //Creating array with range
       
       val ar2= range(2,15,2)
       println("Second element of range(2,15,2) is : " + ar2(1))
       val ar3 = range(15,2,-2)
      println("Second element of range(15,2,-2) " + ar3(1))
       println(ar2(1));
       println(ar1.length)
        
       ar1.sorted.take(3).foreach(println)
        ar1.contains(1)
        println(ar1.length)
        println(ar1.isEmpty)
        ar1.sorted
        ar1(2)=10;
       
        ar1(3)=100;         
       
  /*
   * A list of Scala Collections is much like a Scala array. 
   * Except that, it is immutable and represents a linked list. 
   * An Array, on the other hand, is flat and mutable.
  */
  
       val lst = List(10,20,30,40)
       
       val fruits = List("apples", "oranges", "mangoes")
      
      val g = fruits(0)
      
      var list = List(1,8,5,6,9,58,23,15)
      //var list11 = List(List(1,"a",100000),List(2,"b",200000))
      
      //Access value from the list
      //list(2) = 10
      val lstval = list(0)
      
      //Merging 2 list
      var list3 = list ++ lst 
      //or
      var list4 = list ::: lst 
            
      println(list3)
      println(list4)

      println(list.contains(2))
      
      println(list4.head)
      list4.tail.foreach(println)
      println(list4.length)
      println(list4.isEmpty)
      list4.sorted.foreach(println)
      list4.reverse.foreach(println)
      
      
/*
   Tuples are immutable, contains fixed length of different type elements
  */
      
      val emp = (101,"Karthik",200.00)
      val empid = emp._1
      val empname = emp._2
      val empsal = emp._3
      
      val emp1 = (101,"Karthik",200.00,("New Street","Chennai","TN"))
      
      val empcity = emp1._4._2
      
      
      //// ARRAY PROGRAMS
      
      val ar222=Array[Int]();
        if (ar222.isEmpty)
        {
          println("Array is empty");
        }
      
       
        /*
   * A Map in Scala is a collection of key-value pairs, and is also called a hash table. 
   * We can use a key to access a value. 
   * Keys are unique and values may be in common
   * Map is by default immutable   	 
   * */
       
      var m = Map("Mani" -> 10000,"Karthik" -> 20000)
      
      //Add an element
      m += ("Raj" -> 30000) 
      
      //Remove an element
      m -= ("Mani")
      
      //println(m)
      var m2 = Map.empty[String,String]

        //Add multiple Map elements
        
        m2 += ("1"->"A","2"->"B")
      println(m2)
      
      
      //Immutable map, doesn't allow to modify but allows to add or remove by recreating
      
      var immutablemap = scala.collection.immutable.Map(1 -> "Alto",2 -> "Swift")  
            
      immutablemap -= (2)
      
      //Below update is not possible
      //immutablemap(1)="Alto k10"
      
      var mutablemap = scala.collection.mutable.Map(1 -> "Alto",2 -> "Swift")
      mutablemap(1)="Alto k10"
      
      m.keys
      m.values
      m.isEmpty
      
      val lst1 = m.toList
      val arr = m.toArray

      
 /* A Scala Set is a collection that won’t take duplicates.
  * By default, Scala uses immutable sets.   	 
 * */
 
     var mutablegames = scala.collection.mutable.Set("Cricket","VollyBall","BaseBall","Hockey")
     
     println("Due to mutable, I am able to modify")
     mutablegames.add("Chess");
     mutablegames.add("Hockey");
     mutablegames.remove("Cricket");
     
     println(mutablegames)
     
     var games = scala.collection.immutable.Set("Cricket","Football","Hockey","Golf","Cricket","Football") 
     println(games)
     
     println("Due to Immutable, I am not able to modify, uncomment the below code and check")
     //games.add("Tennis");
     //games.remove("Cricket");
     
     println("Either mutable or immutable, I can reassign if its of type var");
     //val games = scala.collection.immutable.Set("Cricket","Football","Hockey","Golf","Cricket","Football")
     
     games += "Tennis"
     games -= "Cricket"
     
     println(games);
     println(games.head)             // Returns first element present in the set  
     println(games.tail)         // Returns all elements except first element.  
     println(games.isEmpty)          // Returns either true or false  
           
     println(games.max)
     println(games.min)
     println(games.contains("Tennis"))
     println(games ++ mutablegames);
     println(games.intersect(mutablegames))
     println(games.union(mutablegames))
     println("Set difference");
     println(games.diff(mutablegames))
     
          
       
     
  }
}
