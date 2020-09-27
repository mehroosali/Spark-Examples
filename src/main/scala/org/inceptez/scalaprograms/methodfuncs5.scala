package org.inceptez.scalaprograms

object methodfuncs5 {
	def main(args:Array[String]):Unit =
		{
			// Methods, accept/returns arguments or not (Unit), overloading , currying , default params
			// Functions -> Ananymous, lambda, literals, value functions - quick functionalities 
			// without considering reuability accross , cant have return keyword

			/* Methods in Scala
     Syntax:
         def functionName(parameters : typeofparameters) : returntypeoffunction = 
         {  
    				statements to be executed  
    		 }  
			 * */
			println(add1(100,200));
			println(add2(150,300));
			println(add3(100,400));
			println(add4(20,3));
			println(add5(200,500));
			println("add5 curly braces is optional if there is no more than 1 expression")
			println(add6(100));
			println(add6("200" ));
			println(add6("How are you"," doing"));
			//println(add6("Inceptez"));
		//	add8();
			//Function call with named arguments
			
	    add8;
	    add8(100);
	    add8(100,200)
	    add8();
			add9(15)(30);
			
			//Anonymous function or Lambda function or Function literal
			println(add10(25,40));
			
			println(sub(20,50))
			println(add11(50,10)) 

	  /*println(add61(100));
		println(add61("Irfan "));			
		println(add61(200.10));	*/
			val curry=add9(100)_
			println(curry(300))
		add9(200)(400);
		}


	// no return values
	def add1(a:Int,b:Int):Unit =  
		{
			println("add1 No return values")
			val c = a + b;
			println(c);
			//c
			//return c;

		}

	// return an expression
	def add2 (a:Int,b:Int):Int = 
		{
			println("add2 Return an expression")
			val x=a;
			val c=a+b
			return c
			}

	//return keyword optional
	def add3(a:Int,b:Int):Int = 
		{
			println("add3 Return keyword in optional")
			val y =a + b;
      a - y
		}

	//return/input datatype is optional
def add4(a:Int,b:Int):Any=  
{
println("add4 Return datatype is optional")
val x  = a.toFloat/b.toFloat;
println(x)
//return x
x
}

	//for single statement curly braces are optional
	def add5(a:Int,b:Int) = println(a + b)


// Overloading
def add6 (a:Int):Int = 
{
println("add6 Function overloading")
return a + a
}

// Overloading  
def add6(a:String):Int = 
{
println("add6 Function Type overloading with 1 arg")
return 100 + a.toInt;
} 

def add6(a:String,b:String):String = 
{
println("add6 Function Type overloading with 2 args")
return "Hello " + a;
} 

	//Parameter example with default value
//add8(a=100)
def add8(a:Int=30,b:Int=10) = 
{
println("add7 Example for Default Arguments")
println(a + b)
//return (1,"hello")
}

//add8(100)
def add8(x:Int) = 
{
println("add8 Example for overloading Arguments")
println(x + 50)
//return (1,"hello")
}


	// Method with no arguments
def add8 = 
{
println("add8 with no arguments")
println(100 + 50)
}

	//add6(20,30);

	//currying
def add9(a:Int)(b:Int):(Int,Int) =  
{
println("Currying method with multiple input and output arguments")
val c = a + b;
println(c)
return (c,b);
}  
//add9(10)(20);
//val xxx=add9(10)_;

	//store function in a variable
	// Anonymous function or Lambda function or Function literal

val add10 = (a:Int,b:Int) => 
	{
		println("add10 function call, cant have return, only last expression returns")
		a - b
		a + b
	}


  //Also we can define the same function in the short form assigned to a variable called as anonymous functions
	var sub = (a:Int,b:Int) => a - b

	//Also we can define the same function in the short form
	var add11 = (_:Float) + (_:Float)

}














