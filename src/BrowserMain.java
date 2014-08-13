/**
 * @description BrowserMain Class contains the main method, 
 * the constructor sets up a frame which contains an object of type MenuSection and a JTabbedPane
 * 
 * @author Borja De La Viuda
 * @version: 1.0
 * 
 * */



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;



public class BrowserMain 
{

	public tabComponent browserView;
	public static void main(String[] args) 
	{
	    SwingUtilities.invokeLater(new Runnable(){
            public void run(){
            UIManager.put("swing.boldMetal", Boolean.FALSE);
                new BrowserMain();
            }
        });
	}
	
	/*	
	 * Constructor Method for the class, creates a frame and two objects 
	 */
	public BrowserMain() {
		try {
	//Home page	
	String home = "http://www.ncl.ac.uk";
	
    //Create and set up the window.
    JFrame frame = new JFrame("Robot");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(new Dimension(1200,800));
     
     browserView =new tabComponent(home);

    frame.add(browserView,BorderLayout.CENTER);
	frame.add(new MenuSection(browserView), BorderLayout.NORTH);
	

    
    
    //Display the window.
    frame.pack();
    frame.setVisible(true);
		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, e,"Error!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("robot.gif"));
		} 
	}
}
