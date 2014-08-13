/**
 * @description Creates the Bookmarks component in a similar fashion to that of the history component
 * 				creates a list to which strings can be saved to, can also delete the members of the list
 * 				with remove method
 * @author Borja De La Viuda
 * @version: 1.0
 * 
 * */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class BookMarkComponent extends JPanel implements ListSelectionListener
{
	/**
	 * @serial
	 */
	private static final long serialVersionUID = -7140787082540029043L;
	
	private boolean visibility;
	private DefaultListModel listModel;
	private JList list;
	private BrowserEditorPane editor;
	private tabComponent view;
	
	private JButton goButton;
	private JButton deleteButton;
	 
	 /*
	  * History Component constructor sets the list model and the components,
	  * takes in the editor used for the tab.
	  * 
	  * @param editor the BrowserEditorPane object used by the current tab
	  * 
	  */
	
	public BookMarkComponent(BrowserEditorPane editor, tabComponent view)
	{
		super(new BorderLayout());
		
		this.view = view;
		this.editor = editor;
		
		//create a DefaultListModel
		listModel = new DefaultListModel();
		
		//set initial visibility of the pane false
		visibility = false;
		setPreferredSize(new Dimension(200,100));
		setVisible(false);
		
		//create the list, pass in the listModel
		list = new JList(listModel);
		
		//allow only to select one thing in the list at a time
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(list);
        
        // create a button to allow users to go to the desired link
        goButton = new JButton("Go");
        goButton.addActionListener(new goListener());
        
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new deleteListener());
        
        // create a panel to put the button in
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.LINE_AXIS));
        buttonPane.add(goButton);
        buttonPane.add(deleteButton);
        
        add(buttonPane,BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.CENTER);
	}
	//action listener for the goButton to use
	class goListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int index = list.getSelectedIndex();
            view.setURLBar(((String) listModel.get(index)));
            editor.go((String) listModel.get(index));
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
            
        }
    }
	
	class deleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) 
        {
        	delete();
        }
    }
	
	/*	
	 * Reads the given url into the list at the end of the list
	 * 
	 * @param url the url to be added to the end of the list
	 */
	public void readInto(String url)
	{
		listModel.addElement(url);
	}
	
	/*
	 * Deletes the selected url from the bookmarks
	 */
	public void delete()
	{
		listModel.remove(list.getSelectedIndex());
	}
	
	/*
	 *  Toggles the visibility of the bookmark component
	 */
	
	public void showBookmarks()
	{
		if (visibility == false)
		{
			setVisible(true);
			visibility = true;
		} 
		else 
		{
			setVisible(false);
			visibility = false;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
