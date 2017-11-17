import java.awt.Color;
import java.util.Random;

/**
 * The Cube class represents a six sided Rubik's Cube with methods to
 * turn the various sides of the cube.
 * 
 * @author Bailey Granam, Maria Shimkovska
 * @version 1.0
 *
 */
public class Cube 
{
	/** 
	 * Initialize and Define Variables 
	 */
	public  Color[][][] myFaces = new Color[6][3][3];;
	private final Color[] COLORS = {Color.GREEN, Color.WHITE, Color.YELLOW, Color.ORANGE, Color.RED, Color.BLUE};
	private final int CUBE_SIZE = 3;
	
	/**
	 * Class constructor for the Cube.
	 */
	public Cube()
	{
		setFaces();
	}
	
	/**
	 * Get the color of a specific square.
	 * @param i, j, k - Index's of the location of the square to retrieve.
	 */
	public Color getColor(int i, int j, int k)
	{
		return myFaces[i][j][k];
	}
	
	/**
	 * Method to print each face of the cube. Used for testing purposes.
	 */
	private void printFaces()
	{
		for(int i = 0; i < myFaces.length; i++)
		{
			System.out.println("FACE #" + i);
			for(int j = 0; j < CUBE_SIZE; j++)
			{
				for(int k = 0; k < CUBE_SIZE; k++)
				{
					System.out.print(myFaces[i][j][k]);
				}
				System.out.println("\n");
			}
			System.out.println("\n");
		}
	}
	
	/**
	 * Method to reset the cube to default solved.
	 */
	public void reset()
	{
		setFaces();
	}
	
	/**
	 * Method to solve the cube using algorithm #1.
	 */
	public void solveV1()
	{
		new CubeSolver(1, this);
	}
	
	/**
	 * Method to solve the cube using algorithm #2.
	 */
	public void solveV2()
	{
		new CubeSolver(2, this);
	}
	
	/**
	 * Loops through each face of the cube and assigns the appropriate color to each
	 * row/column of the face.
	 */
	private void setFaces()
	{
		for(int i = 0; i < myFaces.length; i++)
		{
			for(int j = 0; j < CUBE_SIZE; j++)
			{
				for(int k = 0; k < CUBE_SIZE; k++)
				{
					myFaces[i][j][k] = COLORS[i];
				}
			}
		}
	}
	
	/**
	 * Method to randomize the faces of the cube. 
	 */
	public void randomize() 
	{
		Random myRand = new Random();
		int myNum;
		for (int i = 0; i < 100; i++) 
		{
			myNum = myRand.nextInt(6);
			turn(myNum);
		}
	}
	
	/**
	 * Perform a random turn based on the randomize value.
	 * @param face - Turn to be performed.
	 */
	private void turn(int face)
	{
		switch (face) {
        case 1:  face = 0;
                 turnTop();
        case 2:  face = 1;
        		 turnFront();
        case 3:  face = 2;
                 turnRight();
        case 4:  face = 3;
                 turnLeft();
        case 5:  face = 4;
                 turnBottom();
        case 6:  face = 5;
                 turnBack();
		}
	}
	
	/**
	 * Turn the face of the cube.
	 * 
	 * @param face - Index of the specific face to be turned.
	 */
	private void turnFace(int face)
	{
		Color temp;
		
		temp = myFaces[face][0][0];
		myFaces[face][0][0] = myFaces[face][2][0];
		myFaces[face][2][0] = myFaces[face][2][2];
		myFaces[face][2][2] = myFaces[face][0][2];
		myFaces[face][0][2] = temp;

		temp = myFaces[face][0][1];
		myFaces[face][0][1] = myFaces[face][1][0];
		myFaces[face][1][0] = myFaces[face][2][1];
		myFaces[face][2][1] = myFaces[face][1][2];
		myFaces[face][1][2] = temp;
	}
	
	/**
	 * Turn the right side of the cube. 
	 */
	public void turnRight()
	{
		turnFace(3);
				
		Color[] myTemps = {myFaces[2][0][0], myFaces[2][1][0], myFaces[2][2][0]};
		
		myFaces[2][0][0] = myFaces[0][2][2];
		myFaces[2][1][0] = myFaces[0][1][2];
		myFaces[2][2][0] = myFaces[0][0][2];
		
		myFaces[0][2][2] = myFaces[1][2][2];
		myFaces[0][1][2] = myFaces[1][1][2];
		myFaces[0][0][2] = myFaces[1][0][2];
		
		myFaces[1][2][2] = myFaces[5][2][2];
		myFaces[1][1][2] = myFaces[5][1][2];
		myFaces[1][0][2] = myFaces[5][0][2];
		
		myFaces[5][2][2] = myTemps[0];
		myFaces[5][1][2] = myTemps[1];
		myFaces[5][0][2] = myTemps[2];
	}
	
	/**
	 *  Call the turnRight() method three times to replicate a 'counter-clockwise' turn.
	 */
	public void turnRightInverse()
	{
		turnRight();
		turnRight();
		turnRight();
	}
	
	/**
	 * Turn the left side of the cube. 
	 */
	public void turnLeft()
	{
		turnFace(4);
		
		Color[] myTemps = {myFaces[1][0][0], myFaces[1][1][0], myFaces[1][2][0]};
		
		myFaces[1][0][0] = myFaces[0][0][0];
		myFaces[1][1][0] = myFaces[0][1][0];
		myFaces[1][2][0] = myFaces[0][2][0];
		
		myFaces[0][0][0] = myFaces[2][2][2];
		myFaces[0][1][0] = myFaces[2][1][2];
		myFaces[0][2][0] = myFaces[2][0][2];
		
		myFaces[2][2][2] = myFaces[5][0][0];
		myFaces[2][1][2] = myFaces[5][1][0];
		myFaces[2][0][2] = myFaces[5][2][0];
		
		myFaces[5][0][0] = myTemps[0];
		myFaces[5][1][0] = myTemps[1];
		myFaces[5][2][0] = myTemps[2];
	}
	
	/**
	 *  Call the turnLeft() method three times to replicate a 'counter-clockwise' turn.
	 */
	public void turnLeftInverse()
	{
		turnLeft();
		turnLeft();
		turnLeft();
	}
	
	/**
	 * Turn the front layer of the cube. 
	 */
	public void turnFront()
	{
		turnFace(1);
		
		Color[] myTemps = {myFaces[3][0][0], myFaces[3][1][0], myFaces[3][2][0]};
		
		myFaces[3][0][0] = myFaces[0][2][0];
		myFaces[3][1][0] = myFaces[0][2][1];
		myFaces[3][2][0] = myFaces[0][2][2];
		
		myFaces[0][2][0] = myFaces[4][2][2];
		myFaces[0][2][1] = myFaces[4][1][2];
		myFaces[0][2][2] = myFaces[4][0][2];
		
		myFaces[4][2][2] = myFaces[5][0][2];
		myFaces[4][1][2] = myFaces[5][0][1];
		myFaces[4][0][2] = myFaces[5][0][0];
		
		myFaces[5][0][2] = myTemps[0];
		myFaces[5][0][1] = myTemps[1];
		myFaces[5][0][0] = myTemps[2];
	}
	
	/**
	 *  Call the turnFront() method three times to replicate a 'counter-clockwise' turn.
	 */
	public void turnFrontInverse()
	{
		turnFront();
		turnFront();
		turnFront();
	}
	
	/**
	 * Turn the top layer of the cube. 
	 */
	public void turnTop()
	{
		turnFace(0);
		
		Color[] myTemps = {myFaces[1][0][0], myFaces[1][0][1], myFaces[1][0][2]};
		
		myFaces[1][0][0] = myFaces[3][0][0];
		myFaces[1][0][1] = myFaces[3][0][1];
		myFaces[1][0][2] = myFaces[3][0][2];
		
		myFaces[3][0][0] = myFaces[2][0][0];
		myFaces[3][0][1] = myFaces[2][0][1];
		myFaces[3][0][2] = myFaces[2][0][2];
		
		myFaces[2][0][0] = myFaces[4][0][0];
		myFaces[2][0][1] = myFaces[4][0][1];
		myFaces[2][0][2] = myFaces[4][0][2];
		
		myFaces[4][0][0] = myTemps[0];
		myFaces[4][0][1] = myTemps[1];
		myFaces[4][0][2] = myTemps[2];
	}
	
	/**
	 *  Call the turnTop() method three times to replicate a 'counter-clockwise' turn.
	 */
	public void turnTopInverse()
	{
		turnTop();
		turnTop();
		turnTop();
	}
	
	/**
	 * Turn the back layer of the cube. 
	 */
	public void turnBack()
	{
		turnFace(2);
		
		Color[] myTemps = {myFaces[3][0][2], myFaces[3][1][2], myFaces[3][2][2]};
		
		myFaces[3][0][2] = myFaces[0][0][0];
		myFaces[3][1][2] = myFaces[0][0][1];
		myFaces[3][2][2] = myFaces[0][0][2];
		
		myFaces[0][0][0] = myFaces[4][2][0];
		myFaces[0][0][1] = myFaces[4][1][0];
		myFaces[0][0][2] = myFaces[4][0][0];
		
		myFaces[4][2][0] = myFaces[5][2][2];
		myFaces[4][1][0] = myFaces[5][2][1];
		myFaces[4][0][0] = myFaces[5][2][0];
		
		myFaces[5][2][2] = myTemps[0];
		myFaces[5][2][1] = myTemps[1];
		myFaces[5][2][0] = myTemps[2];
	}
	
	/**
	 *  Call the turnBack() method three times to replicate a 'counter-clockwise' turn.
	 */
	public void turnBackInverse()
	{
		turnBack();
		turnBack();
		turnBack();
	}
	
	/**
	 * Turn the bottom layer of the cube. 
	 */
	public void turnBottom()
	{
		turnFace(5);
		
		Color[] myTemps = {myFaces[1][2][0], myFaces[1][2][1], myFaces[1][2][2]};
		
		myFaces[1][2][0] = myFaces[3][2][0];
		myFaces[1][2][1] = myFaces[3][2][1];
		myFaces[1][2][2] = myFaces[3][2][2];
		
		myFaces[3][2][0] = myFaces[2][2][0];
		myFaces[3][2][1] = myFaces[2][2][1];
		myFaces[3][2][2] = myFaces[2][2][2];
		
		myFaces[2][2][0] = myFaces[4][2][0];
		myFaces[2][2][1] = myFaces[4][2][1];
		myFaces[2][2][2] = myFaces[4][2][2];
		
		myFaces[4][2][0] = myTemps[0];
		myFaces[4][2][1] = myTemps[1];
		myFaces[4][2][2] = myTemps[2];	
	}
	
	/**
	 *  Call the turnBottom() method three times to replicate a 'counter-clockwise' turn.
	 */
	public void turnBottomInverse()
	{
		turnBottom();
		turnBottom();
		turnBottom();
	}
}
