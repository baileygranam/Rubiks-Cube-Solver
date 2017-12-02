import java.awt.Color;

public class CubeSolver 
{
	private Cube myCube;
	
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
		findGreenEdge();
	}
	
	private void findGreenEdge()
	{

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
