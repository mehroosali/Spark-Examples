package org.inceptez.scalaprograms

object objlooping4 {
    def main(args:Array[String]):Unit =
  {
    /* Control Statements
    * */	
      
    //print numbers from 1 to 10
    val n=10;
    for(i <- 1 to n)
    {
      println("For Loop : " + i)
    }
    
    //Decrement from 10 to 1
    for(i <- 10 to 1 by -1)
    {
      println("Negative For Loop : " + i)
    }
    
    //using until which will not include the final number
    for(i <- 1 until 10)
    {
       
      println("For loop until : " + i)
    }
    
    //using while loop
    var i = 0;
    while (i <= 10)
    {
      println("while loop : " + i)
      i = i + 1
    }
    
    //using do while loop
     
    var j = 20;
    do
    {
       
      println("Do while : " + j)
     j = j + 1
    
    }
    while(j <= 10)
    
    // multi level looping      
    for(i <- 1 to 10; j <- 1 to 3)
    {
      println("i value:" + i + " j value:" + j)
    }
    

}
}








