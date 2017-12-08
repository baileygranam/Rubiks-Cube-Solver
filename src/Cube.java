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
	public final Color[] COLORS = {Color.GREEN, Color.WHITE, Color.ORANGE, Color.YELLOW, Color.RED, Color.BLUE};
	private final int CUBE_SIZE = 3;
	public int numOfMoves = 0;
	
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
	
	
	public int getColorInt(Color color) 
	{
		if (color.equals(Color.GREEN)) 
		{
			return 0;
		} else if (color.equals(Color.WHITE)) 
		{
			return 1;
		} else if (color.equals(Color.ORANGE)) 
		{
			return 2;
		} else if (color.equals(Color.YELLOW)) 
		{
			return 3;
		} else if (color.equals(Color.RED)) 
		{
			return 4;
		} else if (color.equals(Color.BLUE)) 
		{
			return 5;
		}	
		return -1;
	}
	
	/**
	 * Method to reset the cube to default solved.
	 */
	public void reset()
	{
		setFaces();
		numOfMoves = 0;
	}
	
	/**
	 * Method to solve the cube using algorithm .
	 */
	public void solve()
	{
		new CubeSolver(this);
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
		for (int i = 0; i < 100000; i++) 
		{
			myNum = myRand.nextInt(5);
			turn(myNum, false);
		}
		
		numOfMoves = 0;
	}
	
	public double getPercentSolved() {
		double total = 0;
		double faceTotal = 0;
		for (int face = 0; face < 6; face++) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (myFaces[face][i][j] == COLORS[face]) faceTotal++;
				}
			}
			total += faceTotal;
			faceTotal = 0;
		}
		return 100 * total / 54;
	}
	
	/**
	 * Perform a random turn based on the randomize value.
	 * @param face - Turn to be performed.
	 */
	public void turn(int face, boolean inverse)
	{	
		numOfMoves++;
		switch (face) {
        case 0:  if(inverse) {
                 	turnTopInverse();
        		 } else {
        			 turnTop();
        		 }
        		 break;
        case 1:  if(inverse) {
            	 	turnFrontInverse();
		   		 } else {
		   			 turnFront();
		   		 }
   		 		 break;
        case 2:  if(inverse) {
        		 	turnRightInverse();
        		 } else {
        			 turnRight();
        		 }
        		 break;
        case 3:  if(inverse)
        		 {
        		 	turnBackInverse();
        		 } else {
        			 turnBack();
        		 }
                 break;
        case 4:  if(inverse)
				 {
				 	turnLeftInverse();
				 } else {
					 turnLeft();
				 }
        		 break;
        case 5:  if(inverse)
				 {
				 	turnBottomInverse();
				 } else {
					 turnBottom();
				 }
        		 break;
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
		turnFace(2);
				
		Color[] myTemps = {myFaces[3][0][0], myFaces[3][1][0], myFaces[3][2][0]};
		
		myFaces[3][0][0] = myFaces[0][2][2];
		myFaces[3][1][0] = myFaces[0][1][2];
		myFaces[3][2][0] = myFaces[0][0][2];
		
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
		
		myFaces[0][0][0] = myFaces[3][2][2];
		myFaces[0][1][0] = myFaces[3][1][2];
		myFaces[0][2][0] = myFaces[3][0][2];
		
		myFaces[3][2][2] = myFaces[5][0][0];
		myFaces[3][1][2] = myFaces[5][1][0];
		myFaces[3][0][2] = myFaces[5][2][0];
		
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
		
		Color[] myTemps = {myFaces[2][0][0], myFaces[2][1][0], myFaces[2][2][0]};
		
		myFaces[2][0][0] = myFaces[0][2][0];
		myFaces[2][1][0] = myFaces[0][2][1];
		myFaces[2][2][0] = myFaces[0][2][2];
		
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
		
		myFaces[1][0][0] = myFaces[2][0][0];
		myFaces[1][0][1] = myFaces[2][0][1];
		myFaces[1][0][2] = myFaces[2][0][2];
		
		myFaces[2][0][0] = myFaces[3][0][0];
		myFaces[2][0][1] = myFaces[3][0][1];
		myFaces[2][0][2] = myFaces[3][0][2];
		
		myFaces[3][0][0] = myFaces[4][0][0];
		myFaces[3][0][1] = myFaces[4][0][1];
		myFaces[3][0][2] = myFaces[4][0][2];
		
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
		turnFace(3);
		
		Color[] myTemps = {myFaces[0][0][2], myFaces[0][0][1], myFaces[0][0][0]};
		
		myFaces[0][0][2] = myFaces[2][2][2];
		myFaces[0][0][1] = myFaces[2][1][2];
		myFaces[0][0][0] = myFaces[2][0][2];
		
		myFaces[2][2][2] = myFaces[5][2][0];
		myFaces[2][1][2] = myFaces[5][2][1];
		myFaces[2][0][2] = myFaces[5][2][2];
		
		myFaces[5][2][0] = myFaces[4][0][0];
		myFaces[5][2][1] = myFaces[4][1][0];
		myFaces[5][2][2] = myFaces[4][2][0];
		
		myFaces[4][0][0] = myTemps[0];
		myFaces[4][1][0] = myTemps[1];
		myFaces[4][2][0] = myTemps[2];
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
		
		
		myFaces[1][2][0] = myFaces[4][2][0];
		myFaces[1][2][1] = myFaces[4][2][1];
		myFaces[1][2][2] = myFaces[4][2][2];
		
		myFaces[4][2][0] = myFaces[3][2][0];
		myFaces[4][2][1] = myFaces[3][2][1];
		myFaces[4][2][2] = myFaces[3][2][2];
		
		myFaces[3][2][0] = myFaces[2][2][0];
		myFaces[3][2][1] = myFaces[2][2][1];
		myFaces[3][2][2] = myFaces[2][2][2];
		
		myFaces[2][2][0] = myTemps[0];
		myFaces[2][2][1] = myTemps[1];
		myFaces[2][2][2] = myTemps[2];	
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
