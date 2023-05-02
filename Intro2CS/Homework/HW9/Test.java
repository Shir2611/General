// A basic test class. Feel free to extend it, as needed.
public class Test {
	
	public static void main (String args[]) {		
		//registerTest();
		//memoryTest();
    //computerTest();
    //computerTest2();
    //computerLW_Test();
    //computerA_Test();
    //computerSUB_Test();
    //computerStore_Test();
    //computerTest4();
    //computerGOTO_Test();
    //computerGOTOP_Test();
    

    }	
	
    public static void registerTest () {	
		Register r1 = new Register();
        r1.setValue(10);
        System.out.println(r1);
        Register r2 = new Register(20);
        System.out.println(r2);
        r1.addOne();
        r2.setValue(r1.getValue());
        System.out.println(r2);
    }
    
    public static void memoryTest ()
    {
    	Memory m = new Memory(100);
    	m.setValue(0, 100);
	  	m.setValue(1, 200);
		  m.setValue(2, -17);
		  System.out.println(m.getValue(0));
		  m.setValue(1, 300);
		  System.out.println(m);
    }

    public static void computerTest()
    {
      Computer c1=new Computer();
      System.out.println(c1);
    }

    public static void computerTest2()
    {
      Computer vic = new Computer();
      vic.loadProgram("program1.vic");
      System.out.println(vic);
    }

    public static void computerLW_Test()
    {
      Computer vic = new Computer();
      vic.loadProgram("program1.vic");
      vic.run();
      System.out.println(vic);
    }

    public static void computerA_Test()
    {
      Computer vic = new Computer();
      vic.loadProgram("program2.vic");
      vic.run();
      System.out.println(vic); 
    }

    public static void computerSUB_Test()
    {
      Computer vic = new Computer();
      vic.loadProgram("program3.vic");
      vic.run();
      System.out.println(vic);
    }

    public static void computerStore_Test()
    {
      Computer vic = new Computer();
      vic.loadProgram("program4.vic");
      vic.run();
      System.out.println(vic);
    }

    public static void computerTest4()
    {
      Computer vic = new Computer();
      vic.loadProgram("program5.vic");
      vic.loadInput("input1.txt");
      vic.run();
      System.out.println(vic);
    }

    public static void computerGOTO_Test()
    {
      Computer vic = new Computer();
      vic.loadProgram("program6.vic");
      vic.loadInput("input2.txt");
      vic.run();
      System.out.println(vic);
    }

    public static void computerGOTOP_Test()
    {
      Computer vic = new Computer();
      vic.loadProgram("max2.vic");
      vic.loadInput("input3.txt");
      vic.run();
      System.out.println(vic);
    }

}
