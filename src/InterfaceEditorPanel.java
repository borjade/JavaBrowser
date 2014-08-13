/**
 * @description Interface to define the new methods the tabComponent class uses
 * 				strategy to try to decouple tabComponent from action listeners, 
 * 				allowing other developers to implement in different manner if wanted
 * @author Borja De La Viuda
 * @version: 1.0
 * 
 * */

import java.net.URL;


public interface InterfaceEditorPanel
{
	/*
	 * 	A method to set the new homepage for the browser, 
	 * 	the page is defined by the user, could use the currently visited page or other.
	 * 
	 *  @param newHomepage the url to be used as the new homepage
	 */
	void setURLHome(URL newHomepage);
	
	/*
	 * 	A method to set the JTextfield used for the URL bar in the browser
	 * 
	 *  @param input the text that is to be put into the URL bar and displayed
	 */
	void setURLBar(String input);
	
	/*
	 * 	method to return the url of a page, could be implement to return the currently visited page
	 * 
	 *  @return URL the url that is being requested, could be from the currently visited page
	 */
	URL getURL();
	
	/*
	 *  method to refresh the page the user is currently visited
	 * 
	 */
	void refreshPage();
	
	/*
	 *  method to follow a given URL, would call the go method in the BrowserEditorPane
	 * 
	 */
	void followURL();
	
	/*
	 * method to prepare action for the home button, would follow the set URL as 'home'.
	 */
	void homeButton();
	
	/*
	 * method to go to the previously visited website
	 */
	void goBack();
	
	/*
	 * method to go to the website that was visited the latest, would only work if the back button has been pressed
	 */
	void goNext();
}
