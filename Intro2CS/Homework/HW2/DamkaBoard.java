public class DamkaBoard
{
	public static void main(String[] args)
	{
	int x=Integer.parseInt(args[0]);
	
	if (x<=0)
		System.out.println("Please insert a number that is larger than 0");
	
	for (int i=0; i<x; i++)
	{	
		for (int n=0; n<x; n++)
			if (i%2==0)
			System.out.print("* ");
			else
			System.out.print(" *");
		System.out.println("");
		
	}		
			
		
	
	}
}