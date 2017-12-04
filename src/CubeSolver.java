import java.awt.Color;

public class CubeSolver 
{
	private Cube myCube;
	
	private int count = 0;
	
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
		while(!(isCrossSolved(0)))
		{
			solveGreenCross();
			break;
		}
	}
	
	private void solveGreenCross()
	{
		for(int face = 1; face < 6; face++)
		{
			int tempFace = face;
			
			if(face == 1)
			{
				if(myCube.myFaces[face][0][1].equals(Color.GREEN))
				{
					if(!(myCube.myFaces[0][2][1].equals(myCube.COLORS[face])))
					{
						int numOfTurns = myCube.getColorInt(myCube.myFaces[0][2][1]) - face;
						tempFace = myCube.getColorInt(myCube.myFaces[0][2][1]);

						for(int i = 0; i < numOfTurns; i++)
						{
							myCube.turn(0, true);
						}
					}
					
					myCube.turn(0, true);
					myCube.turn(tempFace+1, true);
					myCube.turn(tempFace, true);
				}
			}
		}
	}

	private void connectTheGreen()
	{
		
	}
	
	private void correctMistakes()
	{
		
	}
	
	
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
}
