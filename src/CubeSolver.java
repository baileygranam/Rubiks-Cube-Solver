import java.awt.Color;

public class CubeSolver 
{
	/* Variables */
	private Cube        myCube;
	private Color[][][] faces;
	private int         fail;
	
	/**
	 * Method to run all algorithms to solve the 3x3 Rubik's Cube.
	 * @param cube - Rubik Cube Object
	 */
	public CubeSolver(Cube cube)
	{
		/* Cube Object Reference */
		myCube = cube;
		
		/* Faces Reference */
		faces = myCube.myFaces;
		
		this.solve();
	}

	private void solve()
	{	
		/* Solve the green cross. */
		solveGreenCross(); System.out.println(myCube.getPercentSolved());

		/* Align edges of the green cross with the correct center colors of the cube. */
		fixGreenCross(); System.out.println(myCube.getPercentSolved());

		/* Find the green corners and place them in the correct spot. */
		solveGreenCorners(); System.out.println(myCube.getPercentSolved());

		/* Solve the second layer of the cube. */
		solveSecondLayer(); System.out.println(myCube.getPercentSolved());

		/* Solve Blue Cross */
		solveBlueCross(); System.out.println(myCube.getPercentSolved());
		
		/* Align edges of the blue cross with the correct center colors of the cube. */
		fixBlueCross(); System.out.println(myCube.getPercentSolved());
		
		/* Position blue corners in optimal position to finally be solved. */
		positionBlueCorners(); System.out.println(myCube.getPercentSolved());
		
		/* Finally, place the blue corners. */
		placeBlueCorners(); System.out.println(myCube.getPercentSolved());
	}

	/**
	 * Method to solve the green cross on the top of the cube. This occurs
	 * by scanning through each face and seeing if a green edge exists at a
	 * particular position. If it does exist then we will place it in the correct
	 * position of the green cross.
	 */
	private void solveGreenCross()
	{ 	
		/* While the green cross is not solved... */
		while(!(isCrossSolved(0)))
		{
			/* Loop through each face (not including the top) to find a green edge. */
			for (int face = 1; face <= 5; face++) 
			{
				/* Necessary Variables for performing cube turns. */
				int prevFace = face - 1; if(face == 1 ) prevFace = 4;
				int nextFace = face + 1; if(face == 4)  nextFace = 1;
	
				/* Specific conditions for face #1. */
				if(face == 1)
				{	
					/* Green edge left of center on white. */
					if(faces[face][1][0] == Color.GREEN)
					{
						if(openGreen(1,0)) myCube.turn(prevFace, true);
					}
					/* Green edge right of center on white. */
					else if(faces[face][1][2] == Color.GREEN)
					{
						if(openGreen(1,2)) myCube.turn(nextFace, false);
					}
				}
				
				/* Specific conditions for face #2. */
				if(face == 2)
				{
					/* Green edge left of center on orange. */
					if(faces[face][1][0] == Color.GREEN)
					{
						if(openGreen(2,1)) myCube.turn(prevFace, true);
					}
					/* Green edge right of center on orange. */
					else if(faces[face][1][2] == Color.GREEN)
					{
						if(openGreen(0,1)) myCube.turn(nextFace, false);
					}
				}
				
				/* Specific conditions for face #3. */
				if(face == 3)
				{
					/* Green edge left of center on yellow. */
					if(faces[face][1][0] == Color.GREEN)
					{
						if(openGreen(1,2)) myCube.turn(prevFace, true);
					}
					/* Green edge right of center on yellow. */
					else if(faces[face][1][2] == Color.GREEN)
					{
						if(openGreen(1,0)) myCube.turn(nextFace, false);
					}
				}
				
				/* Specific conditions for face #4. */
				if(face == 4)
				{
					/* Green edge left of center on red. */
					if(faces[face][1][0] == Color.GREEN)
					{
						if(openGreen(0,1)) myCube.turn(prevFace, true);
					}
					/* Green edge right of center on red. */
					else if(faces[face][1][2] == Color.GREEN)
					{
						if(openGreen(2,1)) myCube.turn(nextFace, false);
					}
				}
	
				/* General conditions for faces #1-4. */
				if(face < 5)
				{
					/* Green edge top of center. */
					if(faces[face][0][1] == Color.GREEN)
					{
						myCube.turn(face,  true);
						myCube.turn(prevFace, true);
						myCube.turn(face,  false);
					}
					/* Green edge bottom of center. */
					else if(faces[face][2][1] == Color.GREEN)
					{
						myCube.turn(face, false);
						myCube.turn(prevFace, true);
						myCube.turn(face, true);
					}
				}
	
				/* Specific conditions for face #5. */
				if (face == 5) 
				{
					/* Green edge top of center on bottom. */
					if (faces[face][0][1] == Color.GREEN) 
					{
						if (openGreen(2, 1)) 
						{
							myCube.turn(1, true);
							myCube.turn(1, true);
						}
					} 
					/* Green edge bottom of center on bottom. */
					else if (faces[face][2][1] == Color.GREEN) 
					{
						if (openGreen(0, 1)) 
						{
							myCube.turn(3, true);
							myCube.turn(3, true);
						}
					} 
					/* Green edge left of center on bottom. */
					else if (faces[face][1][0] == Color.GREEN) 
					{
						if (openGreen(1, 0)) 
						{
							myCube.turn(4, true);
							myCube.turn(4, true);
						}
					} 
					/* Green edge right of center on bottom. */
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
	}

	/**
	 * Method to fix the green cross if certain edges are not properly
	 * aligned with the correct center color of faces 1-4. We do this by 
	 * checking certain cases and flipping one green edges with another 
	 * green edge to correct the mistake.
	 */
	public void fixGreenCross()
	{		
		/* While the cross is not properly aligned. */
		while (!(faces[1][0][1] == Color.WHITE
				&& faces[2][0][1] == Color.ORANGE
				&& faces[3][0][1] == Color.YELLOW
				&& faces[4][0][1] == Color.RED)) 
		{
			/* If white & orange are in correct position then flip yellow and red. */
			if (faces[1][0][1] == Color.WHITE && faces[2][0][1] == Color.ORANGE) 
				flipGreens(3, 4);
			
			/* If orange & yellow are in correct position then flip white and red. */
			else if (faces[2][0][1] == Color.ORANGE && faces[3][0][1] == Color.YELLOW) 
				flipGreens(1, 4);
			
			/* If yellow & red are in correct position then flip orange and white. */
			else if(faces[3][0][1] == Color.YELLOW && faces[4][0][1] == Color.RED) 
				flipGreens(1, 2);
			
			/* If red & white are in correct position then flip yellow and orange. */
			else if (faces[4][0][1] == Color.RED && faces[1][0][1] == Color.WHITE) 
				flipGreens(2, 3);
			
			/* If white & yellow are in correct position then flip yellow and red. */
			else if (faces[1][0][1] == Color.WHITE && faces[3][0][1] == Color.YELLOW) 
				flipGreens(2, 4);
			
			/* If orange & red are in correct position then white and yellow. */
			else if (faces[2][0][1] == Color.ORANGE && faces[4][0][1] == Color.RED) 
				flipGreens(1, 3);
			
			/* If no cases match then we simply need to turn the top to align the edges. */
			else myCube.turn(0, false);
		}
	}

	/**
	 * Method to solve/place the green corners pieces of the green cross.
	 */
	private void solveGreenCorners()
	{
		/* Counter to catch unsolvable corner pieces. */
		fail = 0;
		
		/* While the green corners are not placed. */
		while(!(isGreenCornersSolved()))
		{	
			/* Increment the instruction counter. */
			fail++;
			
			/* Looking for Green, White, Red  misoriented corners. If
			 * this case is true then rotate the corners till it is in
			 * the correct position.
			 */  
			if((faces[4][0][2] == Color.GREEN && faces[0][2][0] == Color.WHITE &&
				faces[1][0][0] == Color.RED) || (faces[4][0][2] == Color.WHITE &&
				faces[0][2][0] == Color.RED && faces[1][0][0] == Color.GREEN))
			{
				/* While bottom right green corner is not placed on green top, rotate corner. */
				while(faces[0][2][0] != Color.GREEN)
					rotateCorner(1);
			}

			/* Looking for Green, Orange, White  misoriented corners. If
			 * this case is true then rotate the corners till it is in
			 * the correct position.
			 */
			if((faces[1][0][2] == Color.GREEN && faces[0][2][2] == Color.ORANGE &&
				faces[2][0][0] == Color.WHITE) ||(faces[1][0][2] == Color.ORANGE &&
				faces[0][2][2] == Color.WHITE && faces[2][0][0] == Color.GREEN))
			{
				/* While bottom left green corner is not placed on green top, rotate corner. */
				while(faces[0][2][2] != Color.GREEN)
					rotateCorner(2);
			}

			/* Looking for Green, Yellow, Orange  misoriented corners. If
			 * this case is true then rotate the corners till it is in
			 * the correct position.
			 */
			if((faces[2][0][2] == Color.GREEN && faces[0][0][2] == Color.YELLOW &&
				faces[3][0][0] == Color.ORANGE) || (faces[2][0][2] == Color.YELLOW &&
				faces[0][0][2] == Color.ORANGE && faces[3][0][0] == Color.GREEN))
			{
				/* While top left green corner is not placed on green top, rotate corner. */
				while(faces[0][0][2] != Color.GREEN)
					rotateCorner(3);
			}

			/* Looking for Green, Red, Yellow  misoriented corners. If
			 * this case is true then rotate the corners till it is in
			 * the correct position.
			 */
			if((faces[3][0][2] == Color.GREEN && faces[0][0][0] == Color.RED &&
				faces[4][0][0] == Color.YELLOW) || (faces[3][0][2] == Color.RED &&
				faces[0][0][0] == Color.YELLOW && faces[4][0][0] == Color.GREEN))
			{
				/* While top right green corner is not placed on green top, rotate corner. */
				while(faces[0][0][0] != Color.GREEN)
					rotateCorner(4);
			}

			/* Check if Green, Red, White corner is below the proper socket. If so, place it. */
			for(int i = 0; i < 4; i ++)
			{
				if((faces[1][2][0] == Color.GREEN && faces[4][2][2] == Color.RED &&
					faces[5][0][0] == Color.WHITE) || (faces[1][2][0] == Color.WHITE &&
					faces[4][2][2] == Color.GREEN && faces[5][0][0] == Color.RED))
				{
					while(faces[0][2][0] != Color.GREEN && faces[4][0][2] != Color.RED && faces[1][0][0] != Color.WHITE)
						rotateCorner(1);
				}

				/* Check if Green, White, Orange corner is below the proper socket. If so, place it. */
				if((faces[2][2][0] == Color.GREEN && faces[1][2][2] == Color.WHITE &&
					faces[5][0][2] == Color.ORANGE) || (faces[2][2][0] == Color.ORANGE &&
					faces[1][2][2] == Color.GREEN && faces[5][0][2] == Color.WHITE))
				{
					while(faces[0][2][2] != Color.GREEN && faces[1][0][2] != Color.WHITE && faces[2][0][0] != Color.ORANGE)
						rotateCorner(2);
				}

				/* Check if Green, Orange, Yellow corner is below the proper socket. If so, place it. */
				if((faces[3][2][0] == Color.GREEN &&faces[2][2][2] == Color.ORANGE &&
					faces[5][2][2] == Color.YELLOW) || (faces[3][2][0] == Color.YELLOW &&
					faces[2][2][2] == Color.GREEN && faces[5][2][2] == Color.ORANGE))
				{
					while(faces[0][0][2] != Color.GREEN && faces[2][0][2] != Color.ORANGE && faces[3][0][0] != Color.YELLOW)
						rotateCorner(3);
				}

				/* Check if Green, Yellow, Red corner is below the proper socket. If so, place it. */
				if((faces[4][2][0] == Color.GREEN && faces[3][2][2] == Color.YELLOW &&
					faces[5][2][0] == Color.RED) || (faces[4][2][0] == Color.RED &&
					faces[3][2][2] == Color.GREEN && faces[5][2][0] == Color.YELLOW))
				{
					while(faces[0][0][0] != Color.GREEN && faces[3][0][2] != Color.YELLOW && faces[4][0][0] != Color.RED)
						rotateCorner(4);
				}
				
				/* Turn the bottom. */
				myCube.turnBottom();
			}

			/* 
			 * Check to see if green corner is in wrong spot/corner. If so perform
			 * a rotation to remove green from wrong corner.
			 */
			if(faces[0][2][0] == Color.GREEN && faces[1][0][0] != Color.WHITE && faces[4][0][2] != Color.RED)
				rotateCorner(1);
			
			if(faces[0][2][2] == Color.GREEN && faces[1][0][2] != Color.WHITE && faces[2][0][0] != Color.ORANGE)
				rotateCorner(2);
			
			if(faces[0][0][2] == Color.GREEN && faces[2][0][2] != Color.ORANGE && faces[3][0][0] != Color.YELLOW)
				rotateCorner(3);
			
			if(faces[0][0][0] == Color.GREEN && faces[3][0][2] != Color.YELLOW && faces[4][0][0] != Color.RED)
				rotateCorner(4);

			/*
			 * If the green face contains a corner that is not green then perform
			 * a rotation to get rid of the corner. 
			 */
			if(faces[0][2][0] != Color.GREEN)
				rotateCorner(1);
			if(faces[0][2][2] != Color.GREEN)
				rotateCorner(2);
			if(faces[0][0][2] != Color.GREEN)
				rotateCorner(3);
			if(faces[0][0][0] != Color.GREEN)
				rotateCorner(4);

			/*
			 * If a face contains a corner on the top two positions then rotate it.
			 */
			if(faces[1][0][0] == Color.GREEN || faces[4][0][2] == Color.GREEN)
				rotateCorner(1);
			if(faces[1][0][2] == Color.GREEN || faces[2][0][0] == Color.GREEN)
				rotateCorner(2);
			if(faces[2][0][2] == Color.GREEN || faces[3][0][0] == Color.GREEN)
				rotateCorner(3);
			if(faces[3][0][2] == Color.GREEN || faces[4][0][0] == Color.GREEN)
				rotateCorner(4);

			/* Turn the bottom */
			myCube.turn(5, false);
			
			/*
			 * If the green face contains a corner that is not green then perform
			 * a rotation to get rid of the corner. 
			 */
			if(faces[0][2][0] != Color.GREEN)
				rotateCorner(1);
			if(faces[0][2][2] != Color.GREEN)
				rotateCorner(2);
			if(faces[0][0][2] != Color.GREEN)
				rotateCorner(3);
			if(faces[0][0][0] != Color.GREEN)
				rotateCorner(4);

			/* If fail goes over 100 then something went wrong. */
			if(fail > 100)
			{
				break;
			}
		}
	}
	
	/**
	 * Method to solve the second layer of the cube.
	 */
	private void solveSecondLayer()
	{
		/* Counter to catch stuck edge pieces. */
		fail = 0;
		
		/* While the second layer is not solved... */
		while (faces[1][1][0] != Color.WHITE || faces[1][1][2] != Color.WHITE || 
			   faces[2][1][0] != Color.ORANGE || faces[2][1][2] != Color.ORANGE || 
			   faces[3][1][0] != Color.YELLOW || faces[3][1][2] != Color.YELLOW || 
			   faces[4][1][0] != Color.RED || faces[4][1][2] != Color.RED) 
		{
			/* Increment fail counter. */
			fail++;
			
			/* If white face bottom-center edge is white. */
			if(faces[1][2][1] == Color.WHITE)
			{
				/* If blue face top-center is red. */
				if(faces[5][0][1] == Color.RED)
				{	
					bottomCenterToLeft(4, 1);
				}
				/* If blue face top-center is orange. */
				else if(faces[5][0][1] == Color.ORANGE);
				{
					bottomCenterToRight(2, 1);
				}
			}
			/* If orange face bottom-center edge is orange. */
			if(faces[2][2][1] == Color.ORANGE)
			{
				/* If blue face left-center is white. */
				if(faces[5][1][2] == Color.WHITE)
				{
					bottomCenterToLeft(1, 2);
				}
				/* If blue face left-center is yellow. */
				else if(faces[5][1][2] == Color.YELLOW);
				{
					bottomCenterToRight(3, 2);
				}
			}
			/* If yellow face bottom-center edge is yellow. */
			if(faces[3][2][1] == Color.YELLOW)
			{
				/* If blue face bottom-center is orange. */
				if(faces[5][2][1] == Color.ORANGE)
				{
					bottomCenterToLeft(2, 3);
				}
				/* If blue face bottom-center is red. */
				else if(faces[5][2][1] == Color.RED);
				{
					bottomCenterToRight(4, 3);
				}
			}
			/* If red face bottom-center edge is red. */
			if(faces[4][2][1] == Color.RED)
			{
				/* If blue face right-center is yellow. */
				if(faces[5][1][0] == Color.YELLOW)
				{
					bottomCenterToLeft(3, 4);
				}
				/* If blue face right-center is white. */
				else if(faces[5][1][0] == Color.WHITE);
				{
					bottomCenterToRight(1, 4);
				}
			}
			
			/* If each face contains a blue on an edge...  */
			if((faces[1][2][1] == Color.BLUE || faces[5][0][1] == Color.BLUE) && 
				(faces[2][2][1] == Color.BLUE || faces[5][1][2] == Color.BLUE) && 
				(faces[3][2][1] == Color.BLUE || faces[5][2][1] ==Color.BLUE) && 
				(faces[4][2][1] == Color.BLUE || faces[5][1][0] == Color.BLUE)) 
			{
				/* Check for incorrect or disoriented edges on the right side of a face. */
				if (faces[1][1][2] != Color.WHITE || faces[2][1][0] != Color.ORANGE) 					
					bottomCenterToRight(2, 1);

				if (faces[2][1][2] != Color.ORANGE || faces[3][1][0] != Color.YELLOW) 
					bottomCenterToRight(3, 2);
				

				if (faces[3][1][2] != Color.YELLOW || faces[4][1][0] != Color.RED) 
					bottomCenterToRight(4, 3);

				if (faces[4][1][2] != Color.RED || faces[1][1][0] != Color.WHITE) 
					bottomCenterToRight(1, 4);
			}
			
			/* Turn the bottom. */
			myCube.turn(5, false);
			
			/* Fail prevention. */
			if(fail > 100)
			{
				break;
			}
		}
	}
	
	/**
	 * Method to solve the blue cross. 
	 */
	public void solveBlueCross()
	{
		/* Fail prevention. */
		fail = 0;
		
		/* While the blue cross is not solved... */
		while(!(isCrossSolved(5)))
		{
			fail++;
			
			/* Check for blue vertical line. */
			if(faces[5][0][1] == Color.BLUE &&
			   faces[5][1][1] == Color.BLUE &&
			   faces[5][2][1] == Color.BLUE)
			{
				orientBlueCross(4);
			}
			/* Check for blue horizontal line. */
			else if(faces[5][1][0] == Color.BLUE &&
					faces[5][1][1] == Color.BLUE &&
					faces[5][1][2] == Color.BLUE)
			{
				orientBlueCross(1);
			}
			/* Check for blue 'L' */
			else if(faces[5][0][1] == Color.BLUE &&
					faces[5][1][1] == Color.BLUE &&
					faces[5][1][0] == Color.BLUE)
			{
				orientBlueCross(1);
			}
			/* Check for blue 'L' */
			else if(faces[5][1][0] == Color.BLUE &&
					faces[5][1][1] == Color.BLUE &&
					faces[5][2][1] == Color.BLUE)
			{
				orientBlueCross(4);
			}
			/* Check for blue 'L' */
			else if(faces[5][2][1] == Color.BLUE &&
					faces[5][1][1] == Color.BLUE &&
					faces[5][1][2] == Color.BLUE)
			{
				orientBlueCross(3);
			}
			/* Check for blue 'L' */
			else if(faces[5][1][2] == Color.BLUE &&
					faces[5][1][1] == Color.BLUE &&
					faces[5][0][1] == Color.BLUE)
			{
				orientBlueCross(2);
			}
			/* Check for blue center. */
			else if(faces[5][1][1] == Color.BLUE)
			{
				orientBlueCross(1);
			}
			
			if(fail > 100)
			{
				break;
			}
		}
	}
	
	/**
	 * Method to fix the blue cross. 
	 */
	private void fixBlueCross()
	{
		/* Fail prevention. */
		fail = 0;
		
		/* While the blue cross is not properly aligned... */
		while (!(faces[1][2][1] == Color.WHITE && faces[2][2][1] == Color.ORANGE && 
				 faces[3][2][1] == Color.YELLOW && faces[4][2][1] == Color.RED)) 
		{
			fail++;
			
			/* If red/yellow is correct then we need to swap orange/white edges. */
			if(faces[4][2][1] == Color.RED && faces[3][2][1] == Color.YELLOW)
				swapBlueEdge(1);
			/* If orange/yellow is correct then we need to swap red/white edges. */
			else if(faces[3][2][1] == Color.YELLOW && faces[2][2][1] == Color.ORANGE)
				swapBlueEdge(4);
			/* If white/orange is correct then we need to swap yellow/red edges. */
			else if(faces[2][2][1] == Color.ORANGE && faces[1][2][1] == Color.WHITE)
				swapBlueEdge(3);
			/* If white/red is correct then we need to swap yellow/orange edges. */
			else if(faces[1][2][1] == Color.WHITE && faces[4][2][1] == Color.RED)
				swapBlueEdge(2);
			/* If orange/red is correct then we need to swap yellow/white edges. */
			else if(faces[2][2][1] == Color.ORANGE && faces[4][2][1] == Color.RED)
				swapBlueEdge(4);
			/* If no cases match then we simply need to turn the bottom to align the edges. */
			else
				myCube.turn(5, false);
			
			if(fail > 100)
			{
				this.solve();
				break;
			}
		}
	}
	
	/**
	 * Method to position the blue corners so that they 
	 * can then be turned in the final step.
	 */
	public void positionBlueCorners() 
	{
		/* While the blue corners are not in the optimal position... */
		while (!(checkBottomCorner(1, 2) && 
				 checkBottomCorner(2, 3) && 
				 checkBottomCorner(3, 4) &&
				 checkBottomCorner(4, 1))) 
		{

			/* Check front right for filled corner. Replace blue corners. */
			if (checkBottomCorner(1, 2)) 
			{
				myCube.turn(5, true);
				shiftBlueCorners();
			}

			/* Check back right for filled corner. Replace blue corners. */

			else if (checkBottomCorner(2, 3)) 
			{
				myCube.turn(5, false);
				myCube.turn(5, false);
				shiftBlueCorners();
			}

			/* Check back left for filled corner. Replace blue corners. */
			else if (checkBottomCorner(3, 4)) 
			{
				myCube.turn(5, false);
				shiftBlueCorners();
			}

			/* Check front left for filled corner. Replace blue corners. */
			else if (checkBottomCorner(4, 1)) 
			{
				shiftBlueCorners();
			}

			/* Otherwise turn bottom. */
			else 
			{
				shiftBlueCorners();
			}
			
		}
	}

	/**
	 * Method to orient final corners. 
	 */
	public void placeBlueCorners() 
	{	
		/* While blue top-left-center is not blue and top-right-center is not blue... */
		while(faces[5][0][0] != Color.BLUE &&
		      faces[5][0][2] != Color.BLUE)
		{
			orientCorners(1, 3);
		}
		/* While blue bottom-left-center is not blue and bottom-right-center is not blue... */
		while(faces[5][2][0] != Color.BLUE &&
			  faces[5][2][2] != Color.BLUE)
		{
			orientCorners(3, 1);
		}
		/* While blue top-left-center is not blue and bottom-left-center is not blue... */	
		while(faces[5][0][0] != Color.BLUE &&
				  faces[5][2][0] != Color.BLUE)
		{
			orientCorners(4, 2);
		}
		/* While blue top-right-center is not blue and bottom-right-center is not blue... */
		while(faces[5][0][2] != Color.BLUE &&
		      faces[5][2][2] != Color.BLUE)
		{
			orientCorners(2, 4);
		}
		
		/* If blue top-center and bottom-center are placed... */
		if(faces[5][0][0] == Color.BLUE && 
		   faces[5][2][2] == Color.BLUE &&
		   faces[5][0][2] != Color.BLUE &&
		   faces[5][2][0] != Color.BLUE)
		{
			orientCorners(1, 3);
		}
		/* If bottom-left-center and top-right-center are placed... */
		else if(faces[5][0][0] != Color.BLUE && 
			    faces[5][2][2] != Color.BLUE &&
			    faces[5][0][2] == Color.BLUE &&
			    faces[5][2][0] == Color.BLUE)
		{
			orientCorners(3, 1);
		}
		
		if(faces[5][0][0] != Color.BLUE || faces[5][0][2] != Color.BLUE ||
		   faces[5][2][0] != Color.BLUE || faces[5][2][2] != Color.BLUE)
		{
			this.placeBlueCorners();
		}
	}
	
	/**
	 * Method to correct the blue cross. 
	 * R U2 R' U' R U' R'
	 */
	private void swapBlueEdge(int i)
	{
		myCube.turn(i, false);
		myCube.turn(5, false);
		myCube.turn(5, false);
		myCube.turn(i, true);
		myCube.turn(5, true);
		myCube.turn(i, false);
		myCube.turn(5, true);
		myCube.turn(i, true);
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
	 * Method to check if the green corners are solved and in
	 * the correct place.
	 * @return true if solved, false otherwise.
	 */
	private boolean isGreenCornersSolved()
	{
		if(faces[0][0][0] == Color.GREEN
				&& faces[0][0][2] == Color.GREEN
				&& faces[0][2][0] == Color.GREEN
				&& faces[0][2][2] == Color.GREEN
				&& faces[1][0][0] == Color.WHITE
				&& faces[1][0][2] == Color.WHITE
				&& faces[2][0][0] == Color.ORANGE
				&& faces[2][0][2] == Color.ORANGE
				&& faces[3][0][0] == Color.YELLOW
				&& faces[3][0][2] == Color.YELLOW
				&& faces[4][0][0] == Color.RED
				&& faces[4][0][2] == Color.RED)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Method to take bottom-center edge of a given face 1-4 and 
	 * place it in the left-center edge of that face.
	 * @param i
	 * @param j
	 */
	private void bottomCenterToLeft(int i , int j)
	{
		myCube.turn(5, false);
		myCube.turn(i, false);
		myCube.turn(5, true);
		myCube.turn(i, true);
		myCube.turn(5, true);
		myCube.turn(j, true);
		myCube.turn(5, false);
		myCube.turn(j, false);
	}
	
	/**
	 * Method to take bottom-center edge of a given face 1-4 and 
	 * place it in the right-center edge of that face.
	 * @param i
	 * @param j
	 */
	private void bottomCenterToRight(int i, int j)
	{
		myCube.turn(5, true);
		myCube.turn(i, true);
		myCube.turn(5, false);
		myCube.turn(i, false);
		myCube.turn(5, false);
		myCube.turn(j, false);
		myCube.turn(5, true);
		myCube.turn(j, true);
	}
	
	/**
	 * Method to turn two given blue corners.
	 * R U2 R' U' R U' R' L' U2 L U L' U L 
	 * @param i 
	 * @param j
	 */
	private void orientCorners(int i, int j)
	{
		myCube.turn(i, false);
		myCube.turn(5, false);
		myCube.turn(5, false);
		myCube.turn(i, true);
		myCube.turn(5, true);
		myCube.turn(i, false);
		myCube.turn(5, true);
		myCube.turn(i, true);
		myCube.turn(j, true);
		myCube.turn(5, false);
		myCube.turn(5, false);
		myCube.turn(j, false);
		myCube.turn(5, false);
		myCube.turn(j, true);
		myCube.turn(5, false);
		myCube.turn(j, false);
	}
	
	/**
	 * Method to perform certain rotations to achieve the blue cross.
	 * @param i face
	 */
	private void orientBlueCross(int i)
	{
		int front = i;
		int right = 0;
		if(front - 1 < 1)right = 4; 
		else right = front - 1;
		
		myCube.turn(front, false);
		myCube.turn(right, false);
		myCube.turn(5, false);
		myCube.turn(right, true);
		myCube.turn(5, true);
		myCube.turn(front, true);
	}
	
	/**
	 * Method to check bottom corners.
	 * @param theF
	 * @param theR
	 * @param theD
	 * @param thecolor1
	 * @param thecolor2
	 * @param thecolor3
	 * @return
	 */
	private  boolean checkBottomCorner(int i, int j) 
	{
		if (i == 1) 
		{
			return (faces[i][2][2] == myCube.COLORS[i]
				|| faces[i][2][2] == myCube.COLORS[j]
				|| faces[i][2][2] == myCube.COLORS[5])
			&& (faces[j][2][0] == myCube.COLORS[i]
				|| faces[j][2][0] == myCube.COLORS[j]
				|| faces[j][2][0] == myCube.COLORS[5])
			&& (faces[5][0][2] == myCube.COLORS[i]
				|| faces[5][0][2] == myCube.COLORS[j]
				|| faces[5][0][2] == myCube.COLORS[5]);
		} 
		else if (i == 2) 
		{
			return (faces[i][2][2] == myCube.COLORS[i]
				|| faces[i][2][2] == myCube.COLORS[j]
				|| faces[i][2][2] == myCube.COLORS[5])
			&& (faces[j][2][0] == myCube.COLORS[i]
				|| faces[j][2][0] == myCube.COLORS[j]
				|| faces[j][2][0] == myCube.COLORS[5])
			&& (faces[5][2][2] == myCube.COLORS[i]
				|| faces[5][2][2] == myCube.COLORS[j]
				|| faces[5][2][2] == myCube.COLORS[5]);
		} 
		else if (i == 3) 
		{
			return (faces[i][2][2] == myCube.COLORS[i]
				|| faces[i][2][2] == myCube.COLORS[j]
				|| faces[i][2][2] == myCube.COLORS[5])
			&& (faces[j][2][0] == myCube.COLORS[i]
				|| faces[j][2][0] == myCube.COLORS[j]
				|| faces[j][2][0] == myCube.COLORS[5])
			&& (faces[5][2][0] == myCube.COLORS[i]
				|| faces[5][2][0] == myCube.COLORS[j]
				|| faces[5][2][0] == myCube.COLORS[5]);
		} 
		else if (i == 4) 
		{
			return (faces[i][2][2] == myCube.COLORS[i]
				|| faces[i][2][2] == myCube.COLORS[j]
				|| faces[i][2][2] == myCube.COLORS[5])
			&& (faces[j][2][0] == myCube.COLORS[i]
				|| faces[j][2][0] == myCube.COLORS[j]
				|| faces[j][2][0] == myCube.COLORS[5])
			&& (faces[5][0][0] == myCube.COLORS[i]
				|| faces[5][0][0] == myCube.COLORS[j]
				|| faces[5][0][0] == myCube.COLORS[5]);
		}
		return false;
	}
	
	/**
	 * Replace the blue corners , rotating the back right, back left, and front left.
	 * U R Ui Li U Ri Ui L
	 */
	private  void shiftBlueCorners() 
	{
		myCube.turn(5, false);
		myCube.turn(4, false);
		myCube.turn(5, true);
		myCube.turn(2, true);
		myCube.turn(5, false);
		myCube.turn(4, true);
		myCube.turn(5, true);
		myCube.turn(2, false);
		while(faces[1][2][1] != Color.WHITE) 
		{
			myCube.turn(5, true);
		}
	}

	/**
	 * Method to rotate a corner cube in place so that it is placed in the correct
	 * position. 
	 * @param face to perform turn
	 */
	private void rotateCorner(int face)
	{	
		myCube.turn(face, true);
		myCube.turn(5, true);
		myCube.turn(face, false);
		myCube.turn(5, false);
	}
}
