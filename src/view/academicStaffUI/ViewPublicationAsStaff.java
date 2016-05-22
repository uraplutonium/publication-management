package view.academicStaffUI;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Set;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.enumeration.PublicationType;
import model.enumeration.UserRole;
import control.PMSystem;
import control.Repository;
import control.controller.AcademicStaffController;
/**
 * <p>
 * <strong>ViewPublicationAsStaff</strong> class is the GUI for academic staff to view the details of the publication they choose 
 * from the {@link view.academicStaffUI.LoginAsStaff LoginAsStaff}.
 * <p>
 * Users can view specific {@link model.publication.Publication Publication} which can be {@link model.publication.Book book}, 
 * {@link model.publication.JournalPaper JournalPaper} and {@link model.publication.ConferencePaper ConferencePaper}. Users can 
 * request copy of publication they view.
 * This class also provide the entry for general users to return back to the {@link view.academicStaffUI.LoginAsStaff LoginAsStaff}.
 * 
 * @author 
 * @version
 * @see model.publication.Publication
 * @see model.publication.JournalPaper
 * @see model.publication.ConferencePaper
 * @see model.publication.Publication
 * @see view.academicStaffUI.LoginAsStaff
 *
 */
public class ViewPublicationAsStaff extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
    //define components
	private JPanel operationPanel;
	private JPanel viewPanel;
	private JPanel ButtonPanel;
	
	private JLabel welcomMessage;
	
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
	
	private JButton editMyPublicationButton;
	private JButton addNewPublicationButton;
	private JButton requestButton;
	private JButton returnButton;
	private JButton logoutLabel;
	
	private String currentPublicationID;
	//define academicstaff controller
	private AcademicStaffController controller;
	
	/**
	 * Creates a <code>ViewPublicationAsStaff</code> instance with the specified
     * {@link control.controller.AcademicStaffController AcademicStaffController}. 
     * It will display the frame of ViewPublicationAsStaff.
     * 
	 * @param controller   The controller which has the authority to control this class
	 * @see control.controller.AcademicStaffController
	 */
		public ViewPublicationAsStaff(AcademicStaffController controller) {
			 super("Welcome");
		this.controller = controller;                          //set controller
		currentPublicationID = Repository.currentPublication;   //get current publication
		 /****************operationPanel******************************/
	     //initialize the component of operationPanel
		 operationPanel = new JPanel();
		 
		 addNewPublicationButton = new JButton("Add New Publication");
		 editMyPublicationButton = new JButton("Edit My Publication");
		 welcomMessage = new JLabel();
		 String currentUser = Repository.currentUserName;          //get current user
		 if(currentUser!=null){
			 welcomMessage.setText("Wecome "+Repository.currentUserName +" !");
		 }
		 logoutLabel = new JButton("logout");
		 Font font=logoutLabel.getFont();
		 int style=3;
		 logoutLabel.setFont(new Font(font.getName(),style,font.getSize()));
		 
		 //set components location
		 operationPanel.setLayout(null);
		 operationPanel.setPreferredSize(new Dimension(800,40));
		 addNewPublicationButton.setBounds(30, 10, 150, 25);
		 editMyPublicationButton.setBounds(200, 10, 150, 25);
		 welcomMessage.setBounds(500, 10, 220, 25);
		 logoutLabel.setBounds(720,10, 80, 25);
		 
		 //add components to operationPanel
		 operationPanel.add(addNewPublicationButton);
		 operationPanel.add(editMyPublicationButton);
		 operationPanel.add(welcomMessage);
		 operationPanel.add(logoutLabel);
		 
		 //add action listener to buttons
		 addNewPublicationButton.addActionListener(this);
		 editMyPublicationButton.addActionListener(this);
		 logoutLabel.addActionListener(this);
		 
		 

		 /***********************ViewPublicationPanel**************************************/
		 //initialize components of viewPublicationPanel
		 viewPanel = new JPanel();
		 
		 publicationID = new JLabel("Publication ID:");
		 publicationTitle = new JLabel("Title:");
		 publicationAuthor = new JLabel("Author:");
		 publishDate = new JLabel("Upload Date:"); 
		
		 publicationIDText = new JTextField();
	   	 publicationTitleText = new JTextField();
		 publicationAuthorText = new JTextField();
		 publishDateText = new JTextField();	
		 
		 //set components location and set textfield not editable
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
		 
		 //get publication details according to the ID
		 PublicationType viewType = controller.getPublicationType(currentPublicationID);//according to the publication type to show the information
		 String PubTitle = controller.getPublicationTitle(currentPublicationID);
		 String PubAuthor = formalizeAuthor(controller.getPublicationAuthorSet(currentPublicationID));
		 Date PubDate = controller.getPublicationDate(currentPublicationID);
         
		 //set textfield
		 publicationIDText.setText(currentPublicationID);
         publicationTitleText.setText(PubTitle);
         publicationAuthorText.setText(PubAuthor);
         publishDateText.setText(PubDate.toString());
		 
         //if publication type is book, them show relative details
         if(viewType.equals(PublicationType.BOOK)){
        	 //initialize components;
			 publisherName = new JLabel("Publisher:");
			 publishPlace = new JLabel("Publish Place:");
			 publisherNameText = new JTextField();
			 publishPlaceText = new JTextField();	
			 
			 //set textfield not editable
			 publisherNameText.setEditable(false);
			 publishPlaceText.setEditable(false);
			 
			 //set components location
			 publisherName.setBounds(220, 125, 80, 25);
			 publisherNameText.setBounds(325, 125, 220, 25);
			 publishPlace.setBounds(220, 160, 80, 25);
			 publishPlaceText.setBounds(325, 160,220, 25);
			 
			 publishDate.setBounds(220, 195, 80, 25);
			 publishDateText.setBounds(325, 195, 220, 25);
			
			 //add components to viewPanel
			 viewPanel.add(publisherName);
			 viewPanel.add(publisherNameText);
			 viewPanel.add(publishPlace);
			 viewPanel.add(publishPlaceText);
		     
			 //get relative details
			 String PubPublisher = controller.getBookPublisherName(currentPublicationID);
			 String PubPublishPlace = controller.getBookPublishPlace(currentPublicationID);
			 publisherNameText.setText(PubPublisher);
			 publishPlaceText.setText(PubPublishPlace);
			 publicationBorderTitle = BorderFactory.createTitledBorder("Book"); 
		 }
         //if publication type is JOURNAL, them show relative details
		 else if(viewType.equals(PublicationType.JOURNAL)){
			 //initialize components;
			 journalName = new JLabel("Journal Name:");
			 issueNumber = new JLabel("Issue Number:");
			 pageNumber = new JLabel("Page number:");	
			 journalNameText = new JTextField();
			 issueNumberText = new JTextField();
			 pageNumberText = new JTextField();	
			 
			//set components location and set textfield not editable
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
			 
			 //add components to viewPanel
			 viewPanel.add(journalName);
			 viewPanel.add(journalNameText);
			 viewPanel.add(issueNumber);
			 viewPanel.add(issueNumberText);
			 viewPanel.add(pageNumber);
			 viewPanel.add(pageNumberText);
			 
			 //get relative details
			 String PubjournalName = controller.getJournalName(currentPublicationID);
			 String PubissueNumber = controller.getJournalIssueNumber(currentPublicationID);
			 String PubpageNumber = controller.getJournalPageNumber(currentPublicationID);
			 
			 //set textfield
			 journalNameText.setText(PubjournalName);
			 issueNumberText.setText(PubissueNumber);
			 pageNumberText.setText(PubpageNumber);
			 publicationBorderTitle = BorderFactory.createTitledBorder("Journal"); 
			 
		 }
         //if publication type is CONFERENCE, them show relative details
		 else if(viewType.equals(PublicationType.CONFERENCE)){
			 //initialize components;
			 conferencePlace = new JLabel("Place:");
			 conferencePlaceText = new JTextField();
			 
			//set components location and set textfield not editable
			 conferencePlace.setBounds(220, 125, 80, 25);
			 conferencePlaceText.setBounds(325, 125, 220, 25);
			 conferencePlaceText.setEditable(false);
			 
			 publishDate.setBounds(220, 160, 80, 25);
			 publishDateText.setBounds(325, 160, 220, 25);
			 
			 //add components to viewPanel
			 viewPanel.add(conferencePlace);
			 viewPanel.add(conferencePlaceText);
			 
			 //get relative details
			 String Pubconference = controller.getConferencePlace(currentPublicationID);
			 conferencePlaceText.setText(Pubconference);
			 publicationBorderTitle = BorderFactory.createTitledBorder("Conference Paper"); 
			 
		 }else{
			 publicationBorderTitle = BorderFactory.createTitledBorder("no record"); 
		 }
         //get all common components to viewPanel
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
		 //initialize components of ButtonPanel
		 ButtonPanel = new JPanel();
		 ButtonPanel.setPreferredSize(new Dimension(800,50));
		 ButtonPanel.setLayout(null);
 		 requestButton = new JButton("Request Copy");
		 returnButton = new JButton("Academic HomePage");
		 
		 //set components location
		 requestButton.setBounds(230, 10, 130, 25);
		 returnButton.setBounds(390, 10, 160, 25);
		 
		 //add action listener to buttons
		 requestButton.addActionListener(this);
		 returnButton.addActionListener(this);
		 
		 //add components to ButtonPanel
		 ButtonPanel.add(requestButton);
		 ButtonPanel.add(returnButton);
		 
		 JPanel blankPanel = new JPanel();
		 blankPanel.setPreferredSize(new Dimension(800,100));
		 
		 //add all panels to the frame
		 setLayout(new FlowLayout());
	     add(operationPanel);
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
	 * For <strong>addNewPublicationButton</strong> Listener
	 * When user press addNewPublicationButton, the Frame will switch to AddPublication GUI
	 * <p>
	 * For <strong>editMyPublicationButton</strong> Listener
	 * When user press editMyPublicationButton, the Frame will switch to EditPublication GUI
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
	   if(arg0.getSource()==this.addNewPublicationButton){            //if press addNewPublicationButton
		    System.out.println("trying to switch");
		    PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF,1);//switch to AddPublication frame
		}
	    //when press edit Publication Button
		else if(arg0.getSource()==this.editMyPublicationButton){     //if press editMyPublicationButton
		    System.out.println("trying to switch");
		    PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF,2);//switch to EditPublication frame
		}
	    else if(arg0.getSource()==this.requestButton){               //if press requestButton
	    	JOptionPane.showMessageDialog(null, //show a success published message
					"Request Copy Email has been sent to the author",
		    "Send request Success",
		    JOptionPane.PLAIN_MESSAGE);
	    	controller.requestPublicationCopy(Repository.currentPublication);
	    } 
	    else if(arg0.getSource()== this.returnButton){               //if press returnButton
	    	PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF, 0);//switch to LoginAsStaff frame
	    	this.hide();
	    	this.dispose();     
	    }
	    else if(arg0.getSource()==this.logoutLabel){
		    int result=JOptionPane.showConfirmDialog(null, "Do you want to logout?");
		    System.out.println("result:"+result);
			if(result==0){
			controller.loginout(Repository.currentUserName, null);
			this.hide();
			this.dispose();
			 } 
			
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
