import java.awt.GridLayout;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
 
/**
 *  The GUI class represents the visual aspect of the 
 *  Cube.java class. 
 *  
 * @author Bailey Granam, Maria Shimkovska
 * @version 1.0
 */
public class GUI 
{
	/**
	 * Initialize and Define Variables
	 */
	private final String   VERSION = "1.0";
	private final int      HEIGHT  = 1200;
	private final int      WIDTH   = 800;
	
	private JFrame         myFrame;
	
	private JPanel         myCubePanel, myButtonsPanel;
	 
	private JPanel[]       myFaces;
	
	private JButton        myRandomizeButton, myResetButton, mySolveButton1;
	
	private JLabel[][]     mySquares;
	
	private Controller     myController;
	
    private ButtonListener myResetListener, myRandomizeListener, mySolve1Listener;

	
	/**
	 * Constructor for the GUI Class. Requires a Cube object to color the faces/squares.
	 */
	public GUI(Controller Controller)
	{
		myController = Controller;
		
		getFrame();
		getButtons();
		getCube();
		
		myFrame.setVisible(true);
	}
	
	/**
	 * Define the frame and its properties.
	 */
	private void getFrame()
	{
		myFrame = new JFrame("Rubik's Cube Solver "+VERSION);
		myFrame.setSize(HEIGHT, WIDTH);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLocationRelativeTo(null);
		myFrame.setLayout(new GridLayout(0,2));
		
	}
	
	/**
	 * Method to visually update the cube faces.
	 */
	public void refresh()
	{
	    myFrame.remove(myCubePanel);
	    getCube();
	    myFrame.revalidate();
	    myFrame.repaint();
	}
	
	/**
	 * This method creates a Cube JPanel with a 4 x 4 grid. We create an array of JPanels of
	 * size 6 to represent the faces of the cube. Each face is of size 3 x 3. We insert a label
	 * into each row/column of the face with the appropriate color. Finally we add the faces
	 * to myCubePanel so that it displays as a 2D representation of the cube.
	 */
	public void getCube()
	{
		myCubePanel = null;
		myFaces = null;
		mySquares = new JLabel[3][3];
		
		
		myCubePanel = new JPanel();
		myCubePanel.setLayout(new GridLayout(5, 4));
		
		myFaces = new JPanel[6];
		
		for(int i = 0; i < 6; i++)
		{
			myFaces[i] = new JPanel();
			myFaces[i].setLayout(new GridLayout(3,3));
			
			for(int j = 0; j < 3; j++)
			{
				for(int k = 0; k < 3; k++)
				{
					mySquares[j][k] = new JLabel();
					mySquares[j][k].setBackground(myController.getCube().getColor(i, j, k));
					mySquares[j][k].setOpaque(true);
					mySquares[j][k].setBorder(LineBorder.createBlackLineBorder());
					myFaces[i].add(mySquares[j][k]);
				
				}
			}
		}
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(myFaces[1]);
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(myFaces[0]);
		myCubePanel.add(myFaces[2]);
		myCubePanel.add(myFaces[4]);
		myCubePanel.add(myFaces[5]);
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(myFaces[3]);
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		myCubePanel.add(new JPanel().add(new JLabel()));
		
		myFrame.add(myCubePanel);
	}
	
	
	
	/**
	 * Method to define the buttons.
	 */
	private void getButtons()
	{
		this.associateListeners(myController);
		
		myButtonsPanel    = new JPanel();
		
		myRandomizeButton = new JButton("Randomize");
		myResetButton     = new JButton("Reset");
		mySolveButton1    = new JButton("Solve");
	
		myRandomizeButton.addMouseListener(myRandomizeListener);
		myResetButton.addMouseListener(myResetListener);
		mySolveButton1.addMouseListener(mySolve1Listener);
		
		myRandomizeButton.setSize(200,50);
		myResetButton.setSize(200,50);
		mySolveButton1.setSize(200,50);
		
		myButtonsPanel.add(myRandomizeButton);
		myButtonsPanel.add(myResetButton);
		myButtonsPanel.add(mySolveButton1);
		
		myFrame.add(myButtonsPanel);
	}
	
	   /**
     * Associates each component's listener with the controller
     * and the correct method to invoke when triggered.
     *
     * <pre>
     * pre:  the controller class has to be instantiated
     * post: all listeners have been associated to the controller
     *       and the method it must invoke
     * </pre>
     */
    public void associateListeners(Controller controller)
    {
        Class<? extends Controller> controllerClass;
        Method randomizeCubeMethod, resetCubeMethod, solveCube;
               
        controllerClass = controller.getClass();
        
        randomizeCubeMethod = null;
        resetCubeMethod = null;
        solveCube = null;
        
        try
        {
        	randomizeCubeMethod = controllerClass.getMethod("randomizeCube",(Class<?>[])null);
        	resetCubeMethod = controllerClass.getMethod("resetCube",(Class<?>[])null);
        	solveCube = controllerClass.getMethod("solveCube",(Class<?>[])null);
        }
        catch(NoSuchMethodException exception)
        {
           String error;

           error = exception.toString();
           System.out.println(error);
        }
        catch(SecurityException exception)
        {
           String error;

           error = exception.toString();
           System.out.println(error);
        }
        
        myRandomizeListener = new ButtonListener(controller, randomizeCubeMethod, null);
        myResetListener = new ButtonListener(controller, resetCubeMethod, null);
        mySolve1Listener = new ButtonListener(controller, solveCube, null);
    }
	
	
	
}