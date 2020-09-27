package org.inceptez.scalaprograms

object objconditional3 {
	def main(args:Array[String]):Unit =
		{
			/* if statement consists of Boolean expression followed by one or more statements.
    Syntax:
    if(boolean_expression) {
				statements
			}
			 * */	
	  
			var studmarks = 75

					if( studmarks > 65) 
					{
						println("Student passed the exam with distinction")
					}

			//Else
			var marks1 = 80
					if( marks1 > 65) 
					{
						println("Student passed the exam with distinction")

					} 
					else 
					{
						println("Student mark is less than 65")
					}

			//Else If
			val x =10;
			val attendance="p";
			var marks = 90;
					//mark 70 and 40 - c, mark 71 and 79 - b, mark 71 and 79 - b

			
			if ((marks == 0 | attendance != "p") & x==10)
				println("Either Student didnt attended exam or he scored 0");
			else 
			{  
				if ( attendance == "p") 
				{
					if ( marks < 70 & marks > 40)
					{
						println("Grade is C")
					}
					else if(marks >= 70 & marks < 80) 
					{
						println("Grade is B")
					} 
					else if(marks >= 80 & marks < 90) 
					{
						println("Grade is A")
					} 
					else if(marks >= 90) 
					{
						println("Grade is A+")
					} 
					else 
					{
						println("Student is not performing well hence no grade is awarded")
					}
				}
			}
					
}
}