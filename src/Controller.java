public class Controller
{
    private GUI myGUI;
    private Cube myCube;

    public Controller()
    {
        myCube = new Cube();
        myGUI = new GUI(this);
    }
    
    /**
     * Controller constructor; view must be passed in since 
     * controller has responsibility to notify view when 
     * some event takes place.
     *
     * @param view the View this controller has a responsibility to 
     */
    public Controller(GUI gui)
    {
        myGUI = gui;
        myCube = new Cube();
    }
    
    /**
     * Method to return the cube.
     * @return
     */
    public Cube getCube()
    {
    	return myCube;
    }
   
    /**
     * Method to call randomize on the cube and update the GUI.
     */
    public void randomizeCube()
    {
    	myCube.randomize();
    	myGUI.refresh();
    }
    
    /**
     * Method to call reset on the cube and update the GUI.
     */
    public void resetCube()
    {
    	myCube.reset();
    	myGUI.refresh();
    }
    
    /**
     * Method to solve the cube 1.
     */
    public void solveCubeV1()
    {
    	myCube.solveV1();
    	myGUI.refresh();
    }
    
    /**
     * Method to solve the cube 2.
     */
    public void solveCubeV2()
    {
    	myCube.solveV2();
    	myGUI.refresh();
    }
}