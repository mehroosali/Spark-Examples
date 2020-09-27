package org.inceptez.scalaprograms
// Scala basics (important)
// collections (important)
// functions/methods (important)
/* OOPS
 * SPARK DEPENDENT which must be known for Spark Develpment 
 * PACKAGE - important
 * CLASS - important
 * SCALA OBJECT - singleton obj/class, (important) 
 * main or custom METHODs/FUNCTIONs (important)
 * CONSTRUCTOR (primary/auxilary) (important)
 * OBJECT with new keyword which will instantiate the class, (important) 
 * CASE CLASS (important)
 * HIGHER ORDER METHOD/FUNC (important)
 * CLOSURE (important)
 * 
 * OOPS Concepts made simple with Scala program implementation:
 * 
 * All these are Classes -> 
 * Class, singleton object (instantiated class),case class, 
 * abstract class, traits
 * 
 * Polymorphism - Polymorphism means that a function type comes 
 * "in many forms". 
 * the type can have instances of many types.
 * Example - Method with different type of arguments
 * 
 * Overloading - Scala Method overloading is when one class has more than one method with the 
 * same name but different signature. 
 * This means that they may differ in the number of parameters, data types, or both. 
 * Example : Method with different number of arguments
 * 
 * Abstraction - Abstraction is the process to hide the internal details 
 * and showing only the functionality. 
 * abstraction is achieved by using an abstract class.
 * Example : Abstract class and Traits
 * 
 * Inheritance -
 * Inheritance is the process of inheriting the feature of the parent class
 * Multiple Inheritance: In Multiple inheritance ,one class can have more than one superclass and inherit features from all parent classes.
 * Scala does not support multiple inheritance with classes, but it can be achieved by traits. 
 * Example - Abstract Class and Traits
 * 
 * Overriding - Scala overriding method provides your own implementation of it. When a class inherits 
 * from another, it may want to modify the definition for a method of the superclass or 
 * provide a new version of it. 
 * This is the concept of Scala method overriding and we use the 'override' modifier to implement this. 
 * Example : Method or vals override with different implementations
 * 
 * Encapsulation - Specify access specified/modifier for providing access control to the objects or 
 * values 
 * Example : private var a=100;
 *  
 *  Companion Object - It is again a singleton object called as companion if we 
 *  create the object in the same name of the instantiating class
 *  using companion object we can able to acces the encapsulated members of the class.
*/

  /*
  Class - 
 		A class is a user defined blueprint or prototype from which objects are created.  
 		It represents the set of properties or methods  
  * */
//Primary Constructor
class bankclass(actype:String,intpct:Double) {
  
  //Auxilary Constructor
  def this(actype:String)
  {
       this(actype,0.0);
  }
  
  def this()
  {
       this("unknown",0.0);
  }
  
  def cust(amt:Double):Double=
  {
    return {
      if (actype == "SB")        
      amt+(amt*intpct)
      else
      amt
      }
  }
}

/*
  Singleton object is an object which is declared by using object keyword instead by class. 
  No object is required to call methods declared inside singleton object. 
  In scala, there is no static concept. 
  So scala creates a singleton object to provide entry point for your program execution. * */
   object singletonbankobj{  
     val welcome= "Singleton Object Initialized with his members"  
       }

/*Object - 
  An object is an instance of a class. Objects have states and behaviors.*/
//object can't have params
object bankclassobj
{
  
  def main(args:Array[String])
  {
  println(singletonbankobj.welcome);
  
  println("Primary constructor will be initialized");
    //Primary constructor will be initialized
  val bankclassobjinstance1= new bankclass("SB",8.5);
  
  println("Auxilary constructor will be initialized");
  //Auxilary constructor will be initialized
  val bankclassobjinstance2= new bankclass("CU");
  val bankclassobjinstance3= new bankclass;
  println(bankclassobjinstance1.cust(10000));
  
  println(bankclassobjinstance2.cust(20000));
  
  }
}

//Abstraction, Inheritance
//Trait can't have params
trait cardtrait
{
  def cardtype(ctype:String,withdrawlimit:Long):Int;
}

trait banktypetrait
{
  def banktype(btype:String):Int= btype match
  {
    case "Investment" => 10
    case "Retail" => 20
    case _ => 30
  }
  
  class bankclassinherit extends cardtrait with banktypetrait
  {
   def cardtype (ctype:String,withdrawlimit:Long):Int= ctype match
   {
      case "Credit" => 100000
    case "Debit" => 20000
    case _ => 10000
   }
  }
  
   class bankclassinheritforex extends cardtrait with banktypetrait
  {
   def cardtype (ctype:String,withdrawlimit:Long):Int= ctype match
   {
      case "Forex" => 100000
    case "Visa" => 20000
    case _ => 10000
   }
   
   val btype=banktype("Investment");
  }
  
//Can pass arguments, only 1 abstract class can be extended, can have implementation or not.
abstract class cardtrait1(inputargispossible:Int)
{
  val argassign=inputargispossible;
  def cardtype(ctype:String,withdrawlimit:Long):Int;
}

abstract class  banktypetrait1
{
  def banktype(btype:String):Int= btype match
  {
    case "Investment" => 10
    case "Retail" => 20
    case _ => 30
  }
}
  
  class bankclassinherit1 extends cardtrait1(100) //with banktypetrait1 
  {
   def cardtype (ctype:String,withdrawlimit:Long):Int= ctype match
   {
      case "Credit" => 100000
    case "Debit" => 20000
    case _ => 10000
   }
   
   val btype=banktype("Investment");
  }

    
/// Overriding, Companion Object, Encapsulation
    class bankclassobj 
  {
      private def pvtmethod=
      {
        println("I am a private method, only inline or companion can access me");
      }
      
  private val pvtval=1;
  val FDMaturity = 5+pvtval;
  }
  
    //Companion object has same name of the class, which can access private variable
    object bankclassobj extends bankclassobj
    {
      val objbankclassobj=new bankclassobj;
      override val FDMaturity=7;
      
      if (1==1)
      {
      println(objbankclassobj.pvtval);
      objbankclassobj.pvtmethod;
      }
      else
      {
        println(FDMaturity)
        println(objbankclassobj.pvtval);
      objbankclassobj.pvtmethod;
      }
    }
    
 

}