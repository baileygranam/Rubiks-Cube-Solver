import java.awt.Color;

public class CubeSolver 
{
	private Cube myCube;
	
	private int count = 0;
	
	private Color[][][] faces = myCube.myFaces;
	
	public CubeSolver(int version, Cube cube)
	{
		myCube = cube;
		
		if(version == 1)
		{
			solveV1();
		}
		else
		{
			solveV2();
		}
	}
	
	private void solveV1()
	{
		
	}

	
	private void solveV2()
	{	
		/* Loop until green cross is solved. */
		while(!(isCrossSolved(0)))
		{
			solveGreenCross();
		}
		
		fixGreenCross();
		solveGreenCorners();
		
		
		
	}
	
	/**
	 * Method to solve the green cross on the top of the cube. This occurs
	 * by scanning through each face and seeing if a green edge exists at a
	 * particular position. If it does exist then we will place it in the correct
	 * position.
	 */
	private void solveGreenCross()
	{ 		
		for (int face = 1; face <= 5; face++) 
		{
			int prevFace = face - 1; if(face == 1 ) { prevFace = 4; }
			int nextFace = face + 1; if(face == 4) { nextFace = 1;}
			
			if(face == 1)
			{	
				if(faces[face][1][0] == Color.GREEN)
				{
					if(openGreen(1,0)) myCube.turn(prevFace, true);
				}
				else if(faces[face][1][2] == Color.GREEN)
				{
					if(openGreen(1,2)) myCube.turn(nextFace, false);
				}
			}
			
			if(face == 2)
			{
				if(faces[face][1][0] == Color.GREEN)
				{
					if(openGreen(2,1)) myCube.turn(prevFace, true);
				}
				else if(faces[face][1][2] == Color.GREEN)
				{
					if(openGreen(0,1)) myCube.turn(nextFace, false);
				}
			}
			
			if(face == 3)
			{
				if(faces[face][1][0] == Color.GREEN)
				{
					if(openGreen(1,2)) myCube.turn(prevFace, true);
				}
				else if(faces[face][1][2] == Color.GREEN)
				{
					if(openGreen(1,0)) myCube.turn(nextFace, false);
				}
			}
			
		    if(face == 4)
			{
				if(faces[face][1][0] == Color.GREEN)
				{
					if(openGreen(0,1)) myCube.turn(prevFace, true);
				}
				else if(faces[face][1][2] == Color.GREEN)
				{
					if(openGreen(2,1)) myCube.turn(nextFace, false);
				}
			}
		    
		    if(face < 5)
		    {
		    	if(faces[face][0][1] == Color.GREEN)
				{
					myCube.turn(face,  true);
					myCube.turn(prevFace, true);
					myCube.turn(face,  false);
				}
				else if(faces[face][2][1] == Color.GREEN)
				{
					myCube.turn(face, false);
					myCube.turn(prevFace, true);
					myCube.turn(face, true);
				}
		    }
			
			if (face == 5) 
			{
				if (faces[face][0][1] == Color.GREEN) 
				{
					if (openGreen(2, 1)) 
					{
						myCube.turn(1, true);
						myCube.turn(1, true);
					}
				} 
				else if (faces[face][2][1] == Color.GREEN) 
				{
					if (openGreen(0, 1)) 
					{
						myCube.turn(3, true);
						myCube.turn(3, true);
					}
				} 
				else if (faces[face][1][0] == Color.GREEN) 
				{
					if (openGreen(1, 0)) 
					{
						myCube.turn(4, true);
						myCube.turn(4, true);
					}
				} 
				else if (faces[face][1][2] == Color.GREEN) 
				{
					if (openGreen(1, 2)) 
					{
						myCube.turn(2, true);
						myCube.turn(2, true);
					}
				}
			}			
		}			
	}

	/**
	 * Method to fix the green cross if certain squares are not properly
	 * aligned with the correct color.
	 */
	public void fixGreenCross()
	{		
		while (!(faces[1][0][1] == Color.WHITE
				&& faces[2][0][1] == Color.ORANGE
				&& faces[3][0][1] == Color.YELLOW
				&& faces[4][0][1] == Color.RED)) 
		{
			
				if (faces[1][0][1] == Color.WHITE && faces[2][0][1] == Color.ORANGE) 
				{
					flipGreens(3, 4);
				}
				else if (faces[2][0][1] == Color.ORANGE && faces[3][0][1] == Color.YELLOW) 
				{
					flipGreens(1, 4);
				}
				else if(faces[3][0][1] == Color.YELLOW && faces[4][0][1] == Color.RED) 
				{
					flipGreens(1, 2);
				}
				else if (faces[4][0][1] == Color.RED && faces[1][0][1] == Color.WHITE) 
				{
					flipGreens(2, 3);
				}
				else if (faces[1][0][1] == Color.WHITE && faces[3][0][1] == Color.YELLOW) 
				{
					flipGreens(2, 4);
				}
				else if (faces[2][0][1] == Color.ORANGE && faces[4][0][1] == Color.RED) 
				{
					flipGreens(1, 3);
				} 
				else myCube.turn(0, false);
			}
	}
	
	/**
	 * TO DO
	 */
	private void solveGreenCorners()
	{
		
	}
	
	/**
	 * Method to turn the top if a green square already exists where we are
	 * currently trying to place another green square.
	 * @param i column
	 * @param j row
	 * @return True if spot is available. Otherwise turn the top and return false.
	 */
	private boolean openGreen(int i, int j) 
	{
		for (int count = 0; count < 4; count++) 
		{
			if (myCube.myFaces[0][i][j] != Color.GREEN) 
			{ 
				return true;
			}	
			else 
			{
				myCube.turn(0, false);
			}
		}
		return false;
	}
	
	/**
	 * Method to correct mistakes made when solving the cross. For example, if two
	 * green squares are in the wrong spots in the cross this method will flip them
	 * to correct the mistake.
	 * @param i position of spot #1
	 * @param j position of spot #2
	 */
	private void flipGreens(int i, int j) 
	{
		myCube.turn(i, false);
		myCube.turn(i, false);
		myCube.turn(j, false);
		myCube.turn(j, false);
		
		int tempSide = i;
		
		while (tempSide != j) 
		{
			if (tempSide == 4) tempSide = 1;
			else tempSide++;
			myCube.turn(5, false);
		}
		
		myCube.turn(j, false);
		myCube.turn(j, false);
		
		while (tempSide != i) 
		{
			if (tempSide == 1) tempSide = 4;
			else tempSide--;
			myCube.turn(5, true);
		}

		tempSide = j;
		
		while (tempSide != i) 
		{
			if (tempSide == 4) tempSide = 1;
			else tempSide++;
			myCube.turn(5, false);
		}
		
		myCube.turn(i, false);
		myCube.turn(i, false);
	}
	
	/**
	 * Method to check whether the cross of a specified face has been solved.
	 * @param face to see if solved.
	 * @return true if solved, false if not solved.
	 */
	private boolean isCrossSolved(int face)
	{
		Color myColor = myCube.COLORS[face];
		
		if(myCube.myFaces[face][0][1].equals(myColor) &&
		   myCube.myFaces[face][1][0].equals(myColor) &&
		   myCube.myFaces[face][1][2].equals(myColor) &&
		   myCube.myFaces[face][2][1].equals(myColor))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * TO DO
	 * @return
	 */
	private boolean isGreenCornersSolved()
	{
		return false;
	}
}
