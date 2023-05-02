import java.awt.Color;

import javax.swing.text.Highlighter.Highlight;

/**
 * A library of image processing functions.
 */
public class Instush {
	
	public static void main(String[] args)
	{
	    // Can be used for testing, as needed.
		///Instush.print(read("tinypic.ppm"));
		///Instush.print(flippedHorizontally(read("tinypic.ppm")));
		///Instush.print(flippedVertically(read("tinypic.ppm")));
		///Instush.print(greyscaled(read("tinypic.ppm")));
		///Instush.show(scaled(read("ironman.ppm"),600,300));
		///Color c1 = new Color (100,40,100);
		///Color c2 = new Color (200,20,40);
		///System.out.println(blend(c1, c2, 0.25);
		///Instush.print(blend(read("tinypic.ppm"), read("thor.ppm"), 0.25));

	}

	/**
	 * Returns an image created from a given PPM file.
	 * SIDE EFFECT: Sets standard input to the given file.
	 * @return the image, as a 2D array of Color values
	 */
	public static Color[][] read(String filename) {
		StdIn.setInput(filename);
		// Reads the PPM file header (ignoring some items)
		StdIn.readString();
		int numRows = StdIn.readInt();
		int numCols = StdIn.readInt();
		StdIn.readInt();
		// Creates the image
		Color[][] image = new Color[numCols][numRows];

		for (int i=0; i<image.length; i++)
		{
			for (int j=0; j<image[0].length; j++)
			{
				int numone = StdIn.readInt();
				int numtwo = StdIn.readInt();
				int numthree = StdIn.readInt();
				image[i][j]= new Color(numone, numtwo, numthree);
			}
			
		}
		// Reads the RGB values from the file, into the image. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.		
		// Replace the following statement with your code.
		return image;
	}

	/**
	 * Prints the pixels of a given image.
	 * Each pixel is printed as a triplet of (r,g,b) values.
	 * For debugging purposes.
	 * @param image - the image to be printed
	 */
	public static void print(Color[][] image)
	{
	    // Write your code here
		for (int i=0; i<image.length; i++)
		{
			for (int j=0; j<image[0].length; j++)
			{
				System.out.print("(");
				System.out.printf("%4s", image[i][j].getRed() +",");    // Prints the color's red component
				System.out.printf("%4s", image[i][j].getGreen()+",");  // Prints the color's green component
				System.out.printf("%3s", image[i][j].getBlue());   // Prints the color's blue component
				System.out.print(") ");
				System.out.print(" ");

			}
			System.out.println("");
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 * @param image - the image to flip
	 * @return the horizontally flipped image
	 */
	public static Color[][] flippedHorizontally(Color[][] image) 
	{
		Color [][] newarr = new Color [image.length][image[0].length];
		for (int i=0; i<image.length; i++)
		{
			for (int j=0; j<image[0].length; j++)
			{
				newarr[i][j]=image[i][image[0].length-j-1];
			}
		}
		return newarr;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 * @param image - the image to flip
	 * @return the vertically flipped image
	 */
	public static Color[][] flippedVertically(Color[][] image)
	{
		// Replace the following statement with your code
		Color [][] newarr = new Color [image.length][image[0].length];
		for (int i=0; i<image.length; i++)
		{
			for (int j=0; j<image[0].length; j++)
			{
				newarr[i][j]=image[image.length-i-1][j];
			}
		}
		return newarr;
	}
	
	/**
	 * Returns the average of the RGB values of all the pixels in a given image.
	 * @param image - the image
	 * @return the average of all the RGB values of the image
	 */
	public static double average(Color[][] image) {
		// Replace the following statement with your code
		return 0.0;
	}

	/**
	 * Returns the luminance value of a given pixel. Luminance is a weighted average
	 * of the RGB values of the pixel, given by 0.299 * r + 0.587 * g + 0.114 * b.
	 * Used as a shade of grey, as part of the greyscaling process.
	 * @param pixel - the pixel
	 * @return the greyscale value of the pixel, as a Color object
	 *         (r = g = b = the greyscale value)
	 */
	public static Color luminance(Color pixel) 
	{
		// Replace the following statement with your code
		int value= ((int)((pixel.getRed()*0.299)+(pixel.getGreen()*0.587)+(pixel.getBlue()*0.114)));
		return new Color(value, value, value);
	}
	
	/**
	 * Returns an image which is the greyscaled version of the given image.
	 * @param image - the image
	 * @return rhe greyscaled version of the image
	 */
	public static Color[][] greyscaled(Color[][] image)
	 {
		// Replace the following statement with your code
		// I am saving the original array before I change it, so it won't have a side effect.
		Color [][] newarr = new Color [image.length][image[0].length];
		
		for (int k=0; k<image.length; k++)
		{
			for (int l=0; l<image[0].length; l++)
			{
				newarr[k][l]=image[k][l];	
			}
		}
		for (int i=0; i<newarr.length; i++)
		{
			for (int j=0; j<newarr[0].length; j++)
			{
				newarr[i][j]=luminance(image[i][j]);
			}
		}

		return newarr;
	}	
	
	/**
	 * Returns an umage which is the scaled version of the given image. 
	 * The image is scaled (resized) to be of the given width and height.
	 * @param image - the image
	 * @param width - the width of the scaled image
	 * @param height - the height of the scaled image
	 * @return - the scaled image
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) 
	{
		// Replace the following statement with your code
		Color [][] newsize = new Color [height][width];
		double newh= ((double)(image.length)/height);
		double neww= ((double)(image[0].length)/width);
		for (int i=0; i<height; i++)
		{
			for (int j=0; j< width; j++)
			{
				newsize[i][j]=image[(int)(i*newh)][(int)(j*neww)];
			}

		}
		return newsize;
	}
	
	/**
	 * Returns a blended color which is the linear combination of two colors.
	 * Each r, g, b, value v is calculated using v = (1 - alpha) * v1 + alpha * v2.
	 * 
	 * @param pixel1 - the first color
	 * @param pixel2 - the second color
	 * @param alpha - the linear combination parameter
	 * @return the blended color
	 */
	public static Color blend(Color c1, Color c2, double alpha)
	 {
		 double newred = (double)(c1.getRed()*alpha)+ (double)((1-alpha)*(c2.getRed()));
		 double newgreen = (double)(c1.getGreen()*alpha)+ (double)((1-alpha)*(c2.getGreen()));
		 double newblue = (double)(c1.getBlue()*alpha)+ (double)((1-alpha)*(c2.getBlue()));

		Color c = new Color ((int)newred,(int)newgreen,(int)newblue);
		return c;
		// Replace the following statement with your code
	}
	
	/**
	 * Returns an image which is the blending of the two given images.
	 * The blending is the linear combination of (1 - alpha) parts the
	 * first image and (alpha) parts the second image.
	 * The two images must have the same dimensions. 
	 * @param image1 - the first image
	 * @param image2 - the second image
	 * @param alpha - the linear combination parameter
	 * @return - the blended image
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) 
	{
		Color [][] blended = new Color [image1.length][image1[0].length];
		for (int i=0; i<image1.length; i++)
		{
			for (int j=0; j<image1[0].length; j++)
			{
				blended[i][j]=blend(image1[i][j],image2[i][j], alpha);
			}
		}
		// Replace the following statement with your code
		return blended;
	}
	
	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * The target image is an image which is scaled to be a version of the target
	 * image, scaled to have the width and height of the source image.
	 * @param source - source image
	 * @param target - target image
	 * @param n - number of morphing steps
	 */
	public static void morph(Color[][] source, Color[][] target, int n)
	{
		Color [][] backup = new Color [target.length][target[0].length];
		backup = scaled(target, source[0].length,source.length);
		
		for(int i=0; i<=n; i++)
		{
			show(blend(source,backup,(double)(n-i)/n));
		}
		// Write your code here
	}
	
     /**
	 * Renders (displays) an image on the screen, using StdDraw. 
	 * 
	 * @param image - the image to show
	 */
	public static void show(Color[][] image) {
		StdDraw.setCanvasSize(image[0].length, image.length);
		int width = image[0].length;
		int height = image.length;
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		StdDraw.show(25); 
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the color of the pixel
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a tiny filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

