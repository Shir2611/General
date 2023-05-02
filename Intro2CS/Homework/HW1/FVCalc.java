public class FVCalc
{
	public static void main(String[] args)
	{
		int currentV=Integer.parseInt(args[0]);
		double rate=Double.parseDouble(args[1]);
		int numY=Integer.parseInt(args[2]);
		 double rate1=rate/100;
		double futureV=currentV*(Math.pow((1+rate1),numY));
		System.out.println("After " + numY + " years, $" + currentV + " saved at " + rate + "% will yield $" + futureV);
	}
}