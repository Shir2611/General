public class LinearEq
{
	public static void main(String[] args)
	{
		double a1=Double.parseDouble(args[0]);
		double b1=Double.parseDouble(args[1]);
		double c1=Double.parseDouble(args[2]);

		
		System.out.println(a1 + " * x + " + b1 + " = " + c1); 
		
		double x= (c1-b1)/a1;
		System.out.println("x = " + x);

		
		
		
		
		
	}
}