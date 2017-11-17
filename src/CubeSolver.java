
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
		//mimi i.e
		myCube.turnBack();
		myCube.turnTop();
		myCube.turnLeft();
		
	}
	
	private void solveV2()
	{
		//bailey
		
	}
}
