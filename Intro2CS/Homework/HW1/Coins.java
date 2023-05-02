public class Coins
{
	public static void main(String[] args) 
	{
		int num= Integer.parseInt(args[0]);
		int quarters=0;
		int cents=0;
		quarters=num/25;
		cents=num%25;
		System.out.println("Use "+ quarters + " quarters and " + cents + " cents");
	
	}
}