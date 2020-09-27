package org.inceptez.scalaprograms
// 4 part, basics, functions, collections (arr, list, map, tuple), oops 
object fewscalamethods9 {
  def main (args:Array[String])
{
       //Map - Evaluates a function over each element in the list, returning a list with the same number of elements.

    val lst = List(1,2,3,4,5,6,7,8,9,10)
    lst.map( x => x*2 )
    println(lst)
    
    //Filter - Removes any elements where the function you pass in evaluates to false. 
    lst.filter((i: Int) => i % 2 == 0)
    
    //write a program to find prime numbers in the list
    
    //foreach is like map but returns nothing. 
    lst.foreach(x => println(x))
   
    val Icecreamprice = List(25,13,45,34,25)
    val total = Icecreamprice.reduce((a,b) => (a + b))
    
    val donutPrices: List[Double] = List(1.5, 2.0, 2.5)
    val sum: Double = donutPrices.reduce(_ + _)
    
    val prices: Seq[Double] = Seq(1.5, 2.0, 2.5)
    println(s"Icecream prices = $prices")

    println("\nHow to sum all the icecream prices using fold function")
    val sum1 = prices.fold(0.0)(_ + _)
    println(s"Sum = $sum1")
   
  
     //flattern
    val lst1 = List(List(1,2,3,4),List(5,6),List(7,8)).flatten
    lst1.foreach(println)

    }
  
}