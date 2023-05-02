import java.awt.Color;

/**
 * Demonstrates three Instush.java services: flipping an image horizontally, flipping an image 
 * vertically, and greyscaling an image.
 * 
 * The program recieves two command-line arguments: the name of the PPM file that represents 
 * the source image (a string), and one of the strings "fh", "fv", or "gs" (a string). The program
 * creates and displays a new image which is either the horizontally flipped version of the source
 * image ("fh"), or the vertically flipped version of the source image ("fv"), or the greyscaled 
 * version of the source image ("gs"). For example: 
 * java Editor1 thor.ppm gs
 */
public class Editor1 {

	public static void main (String[] args)
	{
		String NameOfFile = args[0];
		String Operation = args[1];
		 
		if (Operation.equals("fv"))
			Instush.show(Instush.flippedVertically(Instush.read(NameOfFile)));
		
		if (Operation.equals("fh"))
		Instush.show(Instush.flippedHorizontally(Instush.read(NameOfFile)));	

		if (Operation.equals("gs"))
		Instush.show(Instush.greyscaled(Instush.read(NameOfFile)));


		// Write your code here									
	}
}
