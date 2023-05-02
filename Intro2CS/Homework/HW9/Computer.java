
/** Represents a Vic computer.
 *  It is assumed that users of this class are familiar with the Vic computer, 
 *  and the Vic machine language, described in www1.idc.ac.il/vic. 
 * <br/> The Computer's hardware consists of the following components:
 * <UL>  
 * <LI> Data register: a register.
 * <LI> Program counter: a register.
 * <LI> Input unit: a stream of numbers. In this implementation, the input unit is simulated 
 *      by a text file. When the computer is instructed to execute a READ instruction, it reads 
 *      the next number from this file and puts it in the data register.
 * <LI> Output unit: a stream of numbers. In this implementation, the output unit is simulated by
 *      standard output (by default, the console).
 *      When the computer is instructed to execute a WRITE instruction, it writes the current  
 *      value of the data register to the standard output.
 * <LI> Processor: In this implementation, the processor is emulated by the run method of this class.
 * </UL>  
 * The Computer executes programs written in the numeric Vic machine language.
 * The program is stored in a text file that can be loaded into the computer's memory.
 * This is done by the loadProgram method of this class. */

public class Computer {

    /** This constant represents the size of the memory unit of this Computer
     *  (number of memory registers). */
    public final static int MEM_SIZE = 100;

    /** This constant represents the memory address at which the constant 0 is stored. */
    public final static int LOCATION_OF_ZERO = MEM_SIZE - 2;
	
    /** This constant represents the memory address at which the number 1 is stored. */
    public final static int LOCATION_OF_ONE = MEM_SIZE - 1;
	
    // Op-code definitions:
    private final static int ADD = 1;
    private final static int SUB = 2;
    private final static int LOAD = 3;
    private final static int STORE = 4;
    private final static int GOTO = 5;
    private final static int GOTOZ = 6;
    private final static int GOTOP = 7;
    private final static int READ = 8;
    private final static int WRITE = 9;
    private final static int STOP = 0;

    /** The Computer consists of a Memory unit, and two registers, as follows: */
    private Memory m;
    private Register dReg;
    private Register pc;
	
    /** Constructs a Vic computer. Specifically: 
     *  Constructs a memory that has MEM_SIZE registers, a data register, 
     *  and a program counter. Next, resets the computer (see the reset method API).
     *
     *  Note: the initialization of the input unit and the loading of a program into 
     *  memory are not done by the constructor. This is done by the public methods 
     *  loadInput and loadProgram, respectively. */
    public Computer() 
    {
    	m=new Memory(MEM_SIZE);
        dReg=new Register();
        pc=new Register();
        reset();
    }
	
    /** Resets the computer. Specifically:
     *  Resets the memory, sets the memory registers at addresses LOCATION_OF_ZERO
     *  and LOCATION_OF_ONE to 0 and to 1, respectively, sets the data register 
     *  and the program counter to 0. */
    public void reset()
    {
        m.reset();
        m.setValue(LOCATION_OF_ZERO,0);
        m.setValue(LOCATION_OF_ONE, 1);
        dReg.setValue(0);
        pc.setValue(0);
    }
	
    /** Executes the program currently stored in memory.
     *  This is done by affecting the following fetch-execute cycle:
     *  Fetches from memory the next instruction (3-digit number), i.e. the contents of the
     *  memory register whose address is the current value of the program counter.
     *  Extracts from this word the op-code (left-most digit) and the address (next 2 digits).
     *  Next, executes the command specified by the op-code, using the address if necessary.
     *  As a side-effect of executing the instruction, modifies the program counter.
     *  Next, loops to fetch the next instruction, and so on. */
    public void run()
    {
        int addr=0;
        int op=100; //we will never reach 100
            while(op!=STOP)
            {
                op=m.getValue(addr)/100;
                addr=m.getValue(addr)-(op*100);
                if(op==ADD)
                    exceAdd(addr);
                if(op==SUB)
                    exceSub(addr);
                if(op==LOAD)
                    execLoad(addr);
                if(op==STORE)
                    exceStore(addr);
                if(op==GOTO)
                    exceGoto(addr);
                if(op==GOTOZ)
                    exceGotoZ(addr);
                if(op==GOTOP)
                    exceGotoP(addr);
                if(op==READ)
                    exceRead();
                if(op==WRITE)
                    exceWrite();

                addr=pc.getValue();
            } 
            exceStop();               
    }
	
    // Private execution routines, one for each Vic command
    private void execLoad (int addr)
    {
        dReg.setValue(m.getValue(addr));
        pc.addOne();
    }
	
    private void exceAdd (int addr)
    {
        dReg.setValue(m.getValue(addr) +  dReg.getValue());
        pc.addOne();
    }

    private void exceSub (int addr)
    {
        dReg.setValue(dReg.getValue()-m.getValue(addr));
        pc.addOne();
    }

    private void exceStore (int addr)
    {
        m.setValue(addr, dReg.getValue());
        pc.addOne();
    }

    private void exceGoto (int addr)
    {
        
        dReg.setValue(m.getValue(addr));
        pc.setValue(addr);
    }

    private void exceGotoZ (int addr)
    {
        if(dReg.getValue()==0)
           exceGoto(addr);
        else
            pc.addOne();
    }

    private void exceGotoP (int addr)
    {
        if(dReg.getValue()>0)
            exceGoto(addr);
        else
            pc.addOne(); 
    }


    private void exceRead ()
    {
        int num = StdIn.readInt();
        dReg.setValue(num);
        pc.addOne();
    }


    private void exceWrite()
    {
        pc.addOne();
        System.out.println(dReg.getValue());
    }

    private void exceStop()
    {
        pc.addOne();
        System.out.println("Program terminated normally");
    }
    
    // Implement the other private methods here (execRead, execWrite, execAdd, etc.).
    // For each mehod, you have to write its siganture, and implement it.
	
    /** Loads a program into memory, starting at address 0, using the standard input.
     *  The program is stored in a text file whose name is the given fileName.
     *  It is assumed that the file contains a stream of valid commands written
     *  in the numeric Vic machine language (described in www1.idc.ac.il/vic).
     *  The program is stored in the memory, starting at address 0. */
    public void loadProgram(String fileName)
    {		     
        StdIn.setInput(fileName);
        int [] v= StdIn.readAllInts();
        for(int i=0; i<v.length; i++)
        {
            m.setValue(i, v[i]);
        }
        // Write below the code that reads the Vic nstructions
        // from StdIn and stores them in the computer memory.
    }
	
	/** Initializes the input unit from a given text file using the standard input.
	 *  It is assumed that the file contains a stream of valid data values,
	 *  each being an integer in the range -999 to 999.
	 *  Each time the computer is instructed to execute a READ instruction,
	 *  the next line from this file is read and placed in the data register
	 *  (this READ logic is part of the run method implementation).
	 *  The role of this method is to initialize the file in order to
	 *  enable the execution of subsequent READ commands. */
    public void loadInput(String fileName) {    
        StdIn.setInput(fileName);       
    }
	
    /** This method is used for debugging purposes.
     *  It displays the current contents of the data register, 
     *  the program counter, and the first and last 10 memory cells. */
    public String toString ()
    {

        return "D register  = " + dReg.getValue()+ "\n" + "PC register = " + pc.getValue() + "\n" + "Memory state:\n" + m.toString();
    }
}