package view.generalUserUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Set;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.enumeration.PublicationType;
import model.enumeration.UserRole;

import control.PMSystem;
import control.Repository;
import control.controller.GeneralUserController;
/**
 * <p>
 * <strong>ViewPublication</strong> class is the GUI for general users to view the details of the publication they choose 
 * from the {@link view.generalUserUI.HomePage HomePage}.
 * <p>
 * Users can view specific {@link model.publication.Publication Publication} which can be {@link model.publication.Book book}, 
 * {@link model.publication.JournalPaper JournalPaper} and {@link model.publication.ConferencePaper ConferencePaper}. Users can 
 * request copy of publication they view.
 * This class also provide the entry for users to return back to the {@link view.generalUserUI.HomePage HomePage} or login as academic
 * staff and administrator.
 * 
 * @see model.publication.Publication
 * @see model.publication.JournalPaper
 * @see model.publication.ConferencePaper
 * @see view.generalUserUI.HomePage
 */
public class ViewPublication extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	//define the components 
	private JPanel loginPanel;
	private JPanel viewPanel;
	private JPanel ButtonPanel;
	
	private JLabel usernameLable;
	private JLabel pswLabel;
	
	private JLabel publicationID;
	private JLabel publicationTitle;
	private JLabel publicationAuthor;
	private JLabel publishDate;
	
	private JLabel publisherName;
	private JLabel publishPlace;
	
	private JLabel journalName;
	private JLabel issueNumber;
	private JLabel pageNumber;
	
	private JLabel conferencePlace;
	
	private JTextField usernameField;
	private JPasswordField pswField;
	
	private JTextField publicationIDText;
	private JTextField publicationTitleText;
	private JTextField publicationAuthorText;
	private JTextField publishDateText;
	
	private JTextField publisherNameText;
	private JTextField publishPlaceText;
	
	private JTextField journalNameText;
	private JTextField issueNumberText;
	private JTextField pageNumberText;
	
	private JTextField conferencePlaceText;
	
	private JComboBox selectAuthor;
	private JComboBox selectYear;
	private JComboBox selectGroup;

	private JButton loginButton;
	private JButton requestButton;
	private JButton returnButton;
	
	private String currentPublicationID;
	private GeneralUserController controller;
	
	/**
	 * Creates a <code>ViewPublication</code> instance with the specified
     * {@link control.controller.GeneralUserController GeneralUserController}. It will display the frame of ViewPublication.
     * 
	 * @param controller   The controller which has the authority to control this class
	 * @see control.controller.GeneralUserController
	 */
	public ViewPublication(GeneralUserController generalUserController){
		 super("Publication Management System");
		 this.controller=generalUserController;
		 
		 //get the ID of publication which need to be viewed 
		 currentPublicationID = Repository.currentPublication;
		 
		 /****************loginPanel******************************/
		 //inicialize the components of loginPanel and set their location
		 loginPanel = new JPanel();
		 usernameLable = new JLabel("Username");
		 pswLabel = new JLabel("password");
		 usernameField = new JTextField();
		 usernameField.setPreferredSize(new Dimension(100,20));
		 pswField =  new JPasswordField();
		 pswField.setPreferredSize(new Dimension(100,20));
		 loginButton = new JButton("login");
		 loginButton.setPreferredSize(new Dimension(80,25));
		 loginButton.addActionListener(this);
		 
		 //add components to the loginPanel
		 loginPanel.setLayout(new FlowLayout());
		 loginPanel.setPreferredSize(new Dimension(500,30));
		 loginPanel.add(usernameLable);
		 loginPanel.add(usernameField);
		 loginPanel.add(pswLabel);
		 loginPanel.add(pswField);
		 loginPanel.add(loginButton);
		 

		 /***********************ViewPublicationPanel**************************************/
		 //initialize components of viewPanel
		 viewPanel = new JPanel();
		 
		 publicationID = new JLabel("Publication ID:");
		 publicationTitle = new JLabel("Title:");
		 publicationAuthor = new JLabel("Author:");
		 publishDate = new JLabel("Upload Date:"); 
		
		 publicationIDText = new JTextField();
	   	 publicationTitleText = new JTextField();
		 publicationAuthorText = new JTextField();
		 publishDateText = new JTextField();	
		 
		 
		 JPanel blankPanel = new JPanel();
		 blankPanel.setPreferredSize(new Dimension(800,100));
		 //set components locations
		 viewPanel.setLayout(null);
		 viewPanel.setPreferredSize(new Dimension(800,300));
		 TitledBorder publicationBorderTitle;  
		 
		 publicationID.setBounds(220, 20, 80, 25);
		 publicationIDText.setBounds(325, 20, 220, 25);
		 publicationIDText.setEditable(false);
		 
		 publicationTitle.setBounds(220, 55, 80, 25);
		 publicationTitleText.setBounds(325, 55, 420, 25);
		 publicationTitleText.setEditable(false);
		 
		 publicationAuthor.setBounds(220, 90, 80, 25);
		 publicationAuthorText.setBounds(325, 90, 420, 25);
		 publicationAuthorText.setEditable(false);
		 
		 publishDateText.setEditable(false);
		 
		 //get the publication details according to the ID
		 PublicationType viewType = controller.getPublicationType(currentPublicationID);//according to the publication type to show the information
		 String PubTitle = controller.getPublicationTitle(currentPublicationID);
		 String PubAuthor = formalizeAuthor(controller.getPublicationAuthorSet(currentPublicationID));
		 Date PubDate = controller.getPublicationDate(currentPublicationID);
       
         //display the details on textfields
		 publicationIDText.setText(currentPublicationID);
         publicationTitleText.setText(PubTitle);
         publicationAuthorText.setText(PubAuthor);
         publishDateText.setText(PubDate.toString());
        
		 
         //if publication type is Book, show its relative details
         if(viewType.equals(PublicationType.BOOK)){     
        	 //initialize the components
			 publisherName = new JLabel("Publisher:");
			 publishPlace = new JLabel("Publish Place:");
			 publisherNameText = new JTextField();
			 publishPlaceText = new JTextField();	
			 
			//set the components not editable
			 publisherNameText.setEditable(false);
			 publishPlaceText.setEditable(false);
			 
			//set the components location
			 publisherName.setBounds(220, 125, 80, 25);
			 publisherNameText.setBounds(325, 125, 220, 25);
			 publishPlace.setBounds(220, 160, 80, 25);
			 publishPlaceText.setBounds(325, 160,220, 25);
			 
			 publishDate.setBounds(220, 195, 80, 25);
			 publishDateText.setBounds(325, 195, 220, 25);
			
			 //add component to viewPanel
			 
			 viewPanel.add(publisherName);
			 viewPanel.add(publisherNameText);
			 viewPanel.add(publishPlace);
			 viewPanel.add(publishPlaceText);
		     
			 //show the details on the textfield
			
			 String PubPublisher = controller.getBookPublisherName(currentPublicationID);
			 String PubPublishPlace = controller.getBookPublishPlace(currentPublicationID);
			 publisherNameText.setText(PubPublisher);
			 publishPlaceText.setText(PubPublishPlace);
			 
			 publicationBorderTitle = BorderFactory.createTitledBorder("Book"); 
		 }
         //if publication type is Journal, show its relative details
		 else if(viewType.equals(PublicationType.JOURNAL)){
			 //initialize the components
			 journalName = new JLabel("Journal Name:");
			 issueNumber = new JLabel("Issue Number:");
			 pageNumber = new JLabel("Page number:");	
			 journalNameText = new JTextField();
			 issueNumberText = new JTextField();
			 pageNumberText = new JTextField();	
			 
			//set the components location and set them not editable
			 journalName.setBounds(220, 125, 82, 25);
			 journalNameText.setBounds(325, 125, 220, 25);
			 journalNameText.setEditable(false);
			 
			 issueNumber.setBounds(220, 160, 82, 25);
			 issueNumberText.setBounds(325, 160,220, 25);
			 issueNumberText.setEditable(false);  
			 
			 pageNumber.setBounds(220, 195, 80, 25);
			 pageNumberText.setBounds(325, 195, 220, 25);
			 pageNumberText.setEditable(false);
			 
			 publishDate.setBounds(220, 230, 80, 25);
			 publishDateText.setBounds(325, 230, 220, 25);
			 
			 //add component to viewPanel
			
			 viewPanel.add(journalName);
			 viewPanel.add(journalNameText);
			 viewPanel.add(issueNumber);
			 viewPanel.add(issueNumberText);
			 viewPanel.add(pageNumber);
			 viewPanel.add(pageNumberText);
			 
			//show the details on the textfield
			 String PubjournalName = controller.getJournalName(currentPublicationID);
			 String PubissueNumber = controller.getJournalIssueNumber(currentPublicationID);
			 String PubpageNumber = controller.getJournalPageNumber(currentPublicationID);
			 
			 journalNameText.setText(PubjournalName);
			 issueNumberText.setText(PubissueNumber);
			 pageNumberText.setText(PubpageNumber);
			 publicationBorderTitle = BorderFactory.createTitledBorder("Journal"); 
			 
		 }
         //if publication type is conference
		 else if(viewType.equals(PublicationType.CONFERENCE)){
			 //initialize the components and set their location
			 conferencePlace = new JLabel("Place:");
			 conferencePlaceText = new JTextField();
			 
			 conferencePlace.setBounds(220, 125, 80, 25);
			 conferencePlaceText.setBounds(325, 125, 220, 25);
			 conferencePlaceText.setEditable(false);
			 
			 publishDate.setBounds(220, 160, 80, 25);
			 publishDateText.setBounds(325, 160, 220, 25);
			 
			 //add the components to the view Panel
			 viewPanel.add(conferencePlace);
			 viewPanel.add(conferencePlaceText);
			 
			 //show the details
			 String Pubconference = controller.getConferencePlace(currentPublicationID);
			 conferencePlaceText.setText(Pubconference);
			 publicationBorderTitle = BorderFactory.createTitledBorder("Conference Paper"); 
			 
		 }else{
			 publicationBorderTitle = BorderFactory.createTitledBorder("no record"); 
		 }
         
         //add all common components to the viewPanel
		 viewPanel.setBorder(publicationBorderTitle);
		 viewPanel.add(publicationID);
		 viewPanel.add(publicationIDText);
		 viewPanel.add(publicationAuthor);
		 viewPanel.add(publicationAuthorText);
		 viewPanel.add(publicationTitle);
		 viewPanel.add(publicationTitleText);
		 viewPanel.add(publishDate);
		 viewPanel.add(publishDateText);
		 
/************************Button Panel*****************************************************/
		 //initialize the components of ButtonPanel
		 ButtonPanel = new JPanel();
		 ButtonPanel.setPreferredSize(new Dimension(800,50));
		 ButtonPanel.setLayout(null);
 		 requestButton = new JButton("Request Copy");
		 returnButton = new JButton("Home Page");
		 
		 //set components location
		 requestButton.setBounds(230, 10, 130, 25);
		 returnButton.setBounds(390, 10, 130, 25);
		 
		 //add action listener to components
		 requestButton.addActionListener(this);
		 returnButton.addActionListener(this);
		 
		 //add components to ButtonPanel
		 ButtonPanel.add(requestButton);
		 ButtonPanel.add(returnButton);
		 
		 
		 //add all panels to the frame
		 setLayout(new FlowLayout());
	     add(loginPanel);
	     add(blankPanel);
	     add(viewPanel);
	     add(ButtonPanel);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(1024, 768);
         setVisible(true);

	}
	
	/**
	 * This method to implement the Action Listener of different components. 
	 * <p>
	 * For <strong>loginButton</strong> Listener
	 * When user press loginButton and correct username and password are provided. Current frame will switch to relative GUI
	 * <p>
	 * For <strong>requestButton</strong> Listener
	 * When user press requestButton, a request copy email will send to the author
	 * <p>
	 * For <strong>returnButton</strong> Listener
	 * When user press returnButton, the current frame will switch to HomePage GUI
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub	

	    if(arg0.getSource()==this.loginButton){                //if press login buttom
	    	String userName = usernameField.getText();          //get the username and password
	    	String pwd = pswField.getText();
	    	UserRole role=controller.loginout(userName, pwd);      //the login role
	    	if(role == UserRole.GENERAL_USER) {   //if the user is a general user,login fail
	    		JOptionPane.showMessageDialog(null, "Wrong username or password. Enter username and password again please.");
	    	}
	    	else if(role == UserRole.ACADEMIC_STAFF || role==UserRole.COORDINATOR){ // if the user is coordinator or academic staff,switch to staff homepage
	    		this.hide();
	    		this.dispose();
	    	}
	    	else if(role == UserRole.ADMINISTRATOR){                         //if user login as an administrator, current frame will switch to administrator page      
	    		PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 0);
	    		this.hide();
	    		this.dispose();
	    	}
	    }
	    else if(arg0.getSource()==this.requestButton){          //if press request button
	    	JOptionPane.showMessageDialog(null, //show a success published message
					"Request Copy Email has been sent to the author",
		    "Send request Success",
		    JOptionPane.PLAIN_MESSAGE);
	    	 controller.requestPublicationCopy(Repository.currentPublication);
	    }
	    else if(arg0.getSource()== this.returnButton){             // if press returnButton, current frame switch to homePage
	    	PMSystem.getSystemInstance().switchCurrentFrame(UserRole.GENERAL_USER, 0);
	    	this.hide();
	    	this.dispose();     
	    }
	}
	
	/**
	 * The method is used to formalize the Authors format. 
	 * 
	 * @param authorSet  The String Set of author which need to be formalized
	 * @return Return a String which is formalized 	
	 */
	public String formalizeAuthor(Set<String> authorSet){
		String authorString="";
		int count=0;
		for(String name : authorSet){
	    	authorString+=name;
	    	count++;
	    	if(count<authorSet.size() && count!=0){
	    	authorString+=",";
	    	}
		
	   }
		return authorString;
	}

}
