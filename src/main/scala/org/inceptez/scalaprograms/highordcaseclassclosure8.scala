package org.inceptez.scalaprograms

object highordcaseclassclosure8 {
case class emp(id: Int, name: String, address: String)
  def main(args:Array[String])
  {
/*   Higher-Order Methods - scala treats functions/methods as a variable, hence it can be passed 
 *   as a param for another function/method
A method that takes a function as an input parameter is called a higher-order method. Similarly, a
highorder function is a function that takes another function as input. Higher-order methods and
functions help reduce code duplication. In addition, they help you write concise code. The following
example shows a simple higher-order function.*/

   
def bonus(a:Int):Double = ((a*1.5))
val salaries = Array(20000, 70000, 40000)
val normalmethod = salaries.map (a=>(a*1.5))
val higherordermethod = salaries.map(bonus)
println(higherordermethod(0));

    
/*Case Classes
A case class is a class with a case modifier. An example is shown next.
All input parameters defined implicitly treated as Val, Useful for immutable objects and pattern
matching , Creates Factory method automatically*/
    

val request = emp(1, "Sam", "1, castle point blvd, nj")
println(request.name)
//request.address
    

/*Closures
A closure is a function, whose return value depends on the value of one or 
more variables declared outside this function.*/

var bonuspercent = .10

def bonus1(i:Int) = 
{i+(i * bonuspercent)}

println( "Bonus value is = " + bonus1(100) )

    
  }
}












