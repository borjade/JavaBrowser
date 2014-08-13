/**
 * tabComponent class 
 * 
 *  @description Class sets up the actual content of the tab, it's basically the meat of the browser, 
 * and could effectively be used on it's own 
 * (alongside BrowserEditorPane and HistoryComponent) embedded in a JFrame
 * 
 * creates all the buttons on the tool bar in a JPane as well as creating a 
 * BrowserEditorPane object for the browser view.
 * 
 * @author Borja De La Viuda
 * @version: 1.0
 * 
 * */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;


import javax.swing.JButton;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;



public class tabComponent extends JPanel implements InterfaceEditorPanel
{
	/**
	 * @serial
	 */
	private static final long serialVersionUID = -2118839700492853760L;

	
	private JButton backButton;
	private JButton forwardButton;
	private JButton goButton;
	private JButton historyButton;
	private JButton homeButton;
	private JButton reloadButton;
	private JButton searchButton;
	
	private BrowserEditorPane editor;
	
	private JTextField urlField;
	private JTextField searchBar;
	private JScrollPane scrollPane;
	private JPanel urlPane;
	private JPanel searchPane;
	private HistoryComponent history;
	private BookMarkComponent bookmark;
	
	/*
	 * tabComponent constructor
	 * sets up the the toolbar (buttons, urlbar) 
	 * sets up the history component
	 * sets up the scroll pane and creates browsereditorpane object
	 * 
	 * @param input - the initial URL that acts as the homepage
	 */
	
	public tabComponent(final String input) throws IOException
	{
		super(new BorderLayout());
		
		//input the string into the urlbar
		urlField = new JTextField(input);
		urlField.setPreferredSize(new Dimension(900,25));
		
		//set the url using the input string
		final URL url = new URL(input);
		
		//create the toolbar
		urlPane = new JPanel();
		urlPane.setLayout(new FlowLayout());
		
		searchButton = new JButton("Search");
		searchPane = new JPanel();
		
		searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(1000,20));
		
		searchPane.add(searchBar);
		searchPane.add(searchButton);
		
		//create buttons for toolbar and add keyboard shortcuts 
		historyButton = new JButton("History");
		historyButton.setMnemonic(KeyEvent.VK_I);
		
		homeButton = new JButton("Home");
		homeButton.setMnemonic(KeyEvent.VK_H);
		
		reloadButton = new JButton("Reload");
		reloadButton.setMnemonic(KeyEvent.VK_R);
		
		goButton = new JButton("Go");
		goButton.setMnemonic(KeyEvent.VK_ENTER);
		
		backButton = new JButton("<");
		backButton.setMnemonic(KeyEvent.VK_LEFT);
		
		forwardButton = new JButton(">");
		forwardButton.setMnemonic(KeyEvent.VK_RIGHT);

		//create the editor pane, pass in the url  & set it non editable
		editor = new BrowserEditorPane(url);
    	editor.setEditable(false);
    	
    	// create the scrollpane
    	scrollPane = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scrollPane.setPreferredSize(new Dimension(1200,800));
    	
    	history = new HistoryComponent(editor, this);
    	history.readInto(input);
    	
    	bookmark = new BookMarkComponent(editor,this);
    	
    	//Add buttons to the toolbar
    	urlPane.add(homeButton);
    	urlPane.add(backButton);
    	urlPane.add(forwardButton);
    	urlPane.add(urlField);
    	urlPane.add(goButton);
    	urlPane.add(reloadButton);
    	urlPane.add(historyButton);

    	//Set up the action listeners & hyperlink listeners
    	setUpButtons();
    	setUpHyperlinks();

    	// add all components on to the pane
    	add(urlPane,BorderLayout.NORTH);
    	add(history,BorderLayout.EAST);
    	add(bookmark,BorderLayout.WEST);
    	add(scrollPane,BorderLayout.CENTER);
    	add(searchPane,BorderLayout.SOUTH);
 
	}
	
	/*
	 * set Up Buttons
	 * set up the action listeners for the buttons on the toolbars
	 * 
	 */
	public void setUpButtons()
	{
    	historyButton.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						history.showHistory();
					}
				}
				
    			);
    	
    	reloadButton.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						refreshPage();
					}
				}
				
    			);
    	homeButton.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						homeButton();
					}
				}
				
    			);
    	
    	goButton.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{                      
                        followURL();
					}
				}
				
    			);
    	
    	backButton.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						goBack();
					}
				}
				
    			);
    	
    	forwardButton.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						goNext();
					}
				}
				
    			);
    	
    	searchButton.addActionListener(
    			
				new ActionListener ()
				{
					public void actionPerformed(ActionEvent event)
					{
						String search = "http://search.yahoo.com/bin/search?p=" + searchBar.getText();
						editor.go(search);
						setURLBar(search);
						history.readInto(search);
						
					}
				}
				
    			);
    	
	}
	/*
	 *  sets the the Url bar to a given input string
	 *  
	 *  @param input the string to which to set the Url bar to
	 */
	
	public void setURLBar(String input)
	{
		urlField.setText(input);
	}
	
	/*
	 * calls the setHomePage of the browser editor pane method to specify a new homepage
	 * 
	 * @param newHomepage the new homepage the user wants
	 */
	public void setURLHome(URL newHomepage)
	{
		editor.setHomePage(newHomepage);
	}
	
	/*
	 * retrieves the url of the currently visited page
	 * 
	 * @return URL URL of the currently visited webpage
	 */
	public URL getURL()
	{
		URL tempURL = editor.getPage();
		return tempURL;
	}
	
	/*
	 * method to refresh the page using the browser editor pane reload method
	 */
	public void refreshPage()
	{
		editor.reload();
		URL tempURL = editor.getPage();
		urlField.setText(tempURL.toString());
	}
	
	/*
	 * method to follow the url that is input in the URL bar
	 */
	public void followURL()
	{
		editor.go(urlField.getText());
        history.readInto(urlField.getText());
	}
	
	/*
	 * method to go back home by calling the browser editor pane goHome method
	 * sets the urlField to the page's URL
	 */
	public void homeButton()
	{
		editor.goHome();
		URL tempURL = editor.getPage();
		urlField.setText(tempURL.toString());
	}
	
	/*
	 * method to visit the next page visited
	 * if there is an error, shows an information message
	 */
	public void goNext()
	{
		try{
		URL tempURL =editor.nextLink();
		String url = tempURL.toString();
		urlField.setText(url);
		editor.setPage(url);
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, e,"Error!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("robot.gif"));
		}
	}
	/*
	 * method to visit the immediate previous webpage
	 * if null shows an information message
	 */
	public void goBack()
	{
		try {
			URL tempURL = editor.previousLink();
			String url = tempURL.toString();
			urlField.setText(url);
			
				editor.setPage(url);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e,"Error!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("robot.gif"));
			}
	}
	
	/*
	 * method to return the history component
	 * 
	 * @return history the component being used in this class
	 */
	public HistoryComponent getHistory()
	{
		return history;
	}
	/*
	 * method to return the bookmark component, same as history but with bookmark object
	 * 
	 * @return bookmark the component being used in this class
	 */
	public BookMarkComponent getBK()
	{
		return bookmark;
	}
	
	/* setUpHyperLinks
	 * set up the hyperlink listeners
	 */
	public void setUpHyperlinks()
	{
		editor.addHyperlinkListener(
		    	
    		new HyperlinkListener() 
    		{
    			public void hyperlinkUpdate(HyperlinkEvent e) 
    			{
    				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
    				{   
    					URL url =e.getURL();
                        String urlStringfied = url.toString();
                        editor.go(urlStringfied);
                        history.readInto(urlStringfied);
                        urlField.setText(urlStringfied);
                    }
                                     
                 }
             });
	}

}
