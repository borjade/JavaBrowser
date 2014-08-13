/**
 * BrowserEditorPane
 * @description Class that extends JEditorPane with several methods to reload a page, 
 * go to a define homepage url, go to a specified url, 
 * uses an ArrayDeque to store recently visited links, 
 * allowing the user to travel between 1 link back and 1 link forwards
 * 
 * @author Borja De La Viuda
 * @version: 1.0
 * 
 * */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;


import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

public class BrowserEditorPane extends JEditorPane 
{
	/**
	 * @serial
	 */
	private static final long serialVersionUID = 1672328872626390585L;

	Deque<URL> list = new ArrayDeque<URL>();
	
	public URL homeURL;

	/*
	 * Constructs the editorpane, 
	 * adds the home url to the list (first visited link) and sets param 
	 * @param url the url to use as homepage
	 */
	
	public BrowserEditorPane(URL url) throws IOException
	{
		super(url);
		list.add(url);
		homeURL = url;

	}
	
	public void setHomePage(URL newURL)
	{
		homeURL = newURL;
	}
	
	/*
	 * When called reloads the current page, by setting it again.
	 * 
	 */
	public void reload()
	{
		try {
			URL currentURL = getPage();
			setPage(currentURL);
		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, e,"Error!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("robot.gif"));
			
		}
	}
	
	public void goHome()
	{
		try {
			setPage(homeURL);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e,"Error!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("robot.gif"));
		}
	}
	
	/*
	 * Sets the page to the given url
	 * 
	 * @param string the url which the page is to be set to
	 */
	
	public void go(String string) 
	{
		try {
			setPage(string);
			list.add(getPage());
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, e,"Error!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("robot.gif"));
		}
	}
	/*
	 * Peeks the last element in list to provide the next link visited
	 * 
	 * @see InterfaceEditorPanel#nextLink()
	 */
	public URL nextLink()
	{
		URL url = list.peekLast();
		return url;
	}
	
	/*
	 * Iterates in a descending order two places 
	 * to give the immediate previous visited link
	 * 
	 * @see InterfaceEditorPanel#previousLink()
	 */
	public URL previousLink()
	{
		Iterator<URL> iter = list.descendingIterator();
		iter.next();
		URL url = iter.next();
		return url;
	}

}
