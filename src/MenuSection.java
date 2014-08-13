/**
 *	@description MenuSection Class extends JMenuBar & deals with creating the menu bar at the top of the browser 
 * 	This class is used to dynamically create more tabs 
 * 	as well as being responsible for the initial tab in the browser.
 * 
 * @author Borja De La Viuda
 * @version: 1.0
 * 
 * */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


import javax.swing.KeyStroke;



public class MenuSection extends JMenuBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -465203737629338034L;
	
	private JMenu bookmarks;
	private JMenu history;
	private JMenu file;
	
	private JMenuItem newHomepage;
	
	private JMenuItem showHistory;
	private JMenuItem goBack;
	private JMenuItem next;
	
	private JMenuItem newMark;
	private JMenuItem showBookmarks;

	private tabComponent browserView;
	private HistoryComponent historyPanel;
	private BookMarkComponent bookmark;
	

	public int index;
	
	/*	Constructor Method for MenuSection class
	 * 	creates the JMenu, takes in parameters homepage URL 
	 * 	and the tabbedPane where the tabs will be created in 
	 * 	
	 * 	@param home the homepage URL String (defined in BrowserMain)
	 * 	@param tabPane the tabbed pane where the tabs will be created
	 */
	public MenuSection(tabComponent browserView) 
	{
		super();
		
		this.browserView = browserView;
		
		historyPanel = browserView.getHistory();
		bookmark = browserView.getBK();
		
		file = new JMenu("File");
		bookmarks = new JMenu("Bookmarks");
		history = new JMenu("History");
		
		newHomepage = new JMenuItem("New Homepage");
		newHomepage.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_N, ActionEvent.CTRL_MASK));

		//Set up the new bookmark menu item
		newMark = new JMenuItem("New Bookmark");
		newMark.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		
		//Set up the close tab menu item
		showBookmarks = new JMenuItem("Show Bookmarks");
		showBookmarks.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		
		showHistory = new JMenuItem("Show History");
		showHistory.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		goBack = new JMenuItem("Back");
		next = new JMenuItem("Next");
		
		//set up the action listeners for menus
		setUpBookmarksMI();
		setUpHistoryMI();
		setUpFileMI();
		
		
		//Menus added to menubar
		add(file);
		add(bookmarks);	
		add(history);
		
	}
	
	
	/* setUpFileMI
	 * Sets up newTab action listeners to create the new tabs in the tabPane
	 */
	
	public void setUpFileMI()
	{
		//add action listener to the menu item
		newHomepage.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						browserView.setURLHome(browserView.getURL());
					}
                 
				}
				
    	);
		file.add(newHomepage);
	}
	
	public void setUpBookmarksMI()
	{
		//add action listener to the menu item
		showBookmarks.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						bookmark.showBookmarks();
					}
                 
				}
				
    	);
		//add action listener to closeAllTabs menu item
		newMark.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						URL temporaryURL =  browserView.getURL();
						bookmark.readInto(temporaryURL.toString());
					}
				
				});
		
		bookmarks.add(showBookmarks);
		bookmarks.add(newMark);
	}
	
	public void setUpHistoryMI()
	{
		//add action listener to the menu item
		showHistory.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						historyPanel.showHistory();
					}
				}
				
    	);
		goBack.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						browserView.goBack();
					}
				}
				
    	);
		next.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						browserView.goNext();
					}
				}
				
    	);

		
		history.add(showHistory);
		history.add(goBack);
		history.add(next);

	}
	

}
