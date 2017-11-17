public class Main
{
    // Properties
    private Controller myController;
    
    // Methods
    public static void main(String[] args)
    {
        new Main();
    }
    
    public Main()
    {
        setController(new Controller());
    }

	public void setController(Controller controller) 
	{
		myController = controller;
	}

	public Controller getController() 
	{
		return myController;
	}
}