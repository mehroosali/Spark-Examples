package org.inceptez.scalaprograms
/*Singleton object is an object which is declared by using object keyword instead by class. 
  No object is required to call methods declared inside singleton object. 
  In scala, there is no static concept. 
  So scala creates a singleton object to provide entry point for your program execution. * */

object oops8 {

  // Companion Object/Class An object with the same name as a class is called a companion object. 
  // Conversely, the class is the object's companion class. A companion class or object can access the private members of its companion.
def main(args:Array[String])
{
   
  def polymorph(a:Any):Any=
{
    return a
    
  }
  
  println(polymorph("hello"))
  println(polymorph(10))

 val obj1=new oops8();
 println(obj1.x);
  
  }
    
}  


class oops8 {
  private val x= "private value";
};

trait trait1{
  def tr1(b:Int);
  def tr3()={};
}

trait trait2{
  def tr2(b:Int);
}

class classtrait extends trait1 with trait2
{
  val p1=10;
  val p2=20;
  def tr1(b:Int):Unit= b+p1;
  def tr2(b:Int):Unit= b-p2;
  def tr3(i:Int)={println(i)}
  
}