package view.academicStaffUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.enumeration.UserRole;

import control.PMSystem;
import control.Repository;
import control.controller.AcademicStaffController;
/**
 * <p>
 * <strong>AddPublication</strong> class is the GUI for academic staff to add new publication details.
 * <p>
 * The new {@link model.publication.Publication Publication} can be {@link model.publication.Book book}, 
 * {@link model.publication.JournalPaper JournalPaper} and {@link model.publication.ConferencePaper ConferencePaper}. 
 * For each type, you need to provide different details.
 * This class also provide the entry for academic staff to the {@link view.academicStaffUI.LoginAsStaff LoginAsStaff} UI and 
 * {@link view.academicStaffUI.EditPublication EditPublication} UI.
 * @author 
 * @version
 * @see model.publication.Publication
 * @see model.publication.JournalPaper
 * @see model.publication.ConferencePaper
 * @see model.publication.Publication
 * @see view.academicStaffUI.LoginAsStaff
 *
 */
public class AddPublication extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	//define componets
	private JPanel operationPanel;
	private JPanel editPanel;
	private JPanel resultPanel;
	private JPanel blankPanel;
	private JPanel ButtonPanel;
	
	private JLabel welcomMessage;
	private JLabel publicationTitle;
	private JLabel publicationAuthor;
	private JLabel tipsLabel;
	
	private JLabel publisherName;
	private JLabel publishPlace;
	
	private JLabel journalName;
	private JLabel issueNumber;
	private JLabel pageNumber;
	
	private JLabel conferencePlace;
	
	private JTextField publicationTitleText;
	private JTextField publicationAuthorText;
	
	private JTextField publisherNameText;
	private JTextField publishPlaceText;
	
	private JTextField journalNameText;
	private JTextField issueNumberText;
	private JTextField pageNumberText;
	
	private JTextField conferencePlaceText;
	
	private ButtonGroup RadioButtonGroup;
	private JRadioButton bookTick;
	private JRadioButton journalTick;
	private JRadioButton conferenceTick;
	
	private JButton returnHomeButton;
	private JButton editMyPublicationButton;
	private JButton submitButton;
	private JButton clearButton;
	private JButton cancelButton;
	private JButton logoutLabel;
	//define AcademicStaffController
	private AcademicStaffController controller;
	
	String userName;
	/**
	 * Creates a <code>AddPublication</code> instance with the specified
     * {@link control.controller.AcademicStaffController AcademicStaffController}. It will display the frame of AddPublication.
     * 
	 * @param controller   The controller which has the authority to control this class
	 * @see control.controller.AcademicStaffController
	 */
	public AddPublication(AcademicStaffController controller){
		 super("Add Publication");
		 this.controller=controller;    //set controller
		 userName = controller.getStaffFullName(Repository.currentUserName);
		 /****************operationPanel******************************/
		 //initialize components of operationPanel
		 operationPanel = new JPanel();
		 
		 editMyPublicationButton = new JButton("Edit My Publication");
		 returnHomeButton = new JButton("HomePage");
		 welcomMessage = new JLabel("Welcome ");
		  
		 if(Repository.currentUserName!=null){                          //welcom message for current user
			 welcomMessage.setText("Welcome"+Repository.currentUserName);
		 }
		
		 logoutLabel = new JButton("logout");
		 Font font=logoutLabel.getFont();
		 int style=3;
		 logoutLabel.setFont(new Font(font.getName(),style,font.getSize()));
		 
		 //set components location
		 operationPanel.setLayout(null);
		 operationPanel.setPreferredSize(new Dimension(800,40));
		 returnHomeButton.setBounds(10, 10, 140, 25);
		 editMyPublicationButton.setBounds(170, 10, 140, 25);
		 welcomMessage.setBounds(510, 10, 230, 25);
		 logoutLabel.setBounds(720,10, 80, 25);
		 
		 //add action listener to the button
		 returnHomeButton.addActionListener(this);
		 editMyPublicationButton.addActionListener(this);
		 logoutLabel.addActionListener(this);
		 
		 //add componnets to operationpaenl
		 operationPanel.add(returnHomeButton);
		 operationPanel.add(editMyPublicationButton);
		 operationPanel.add(welcomMessage);
		 operationPanel.add(logoutLabel);
		 
		 /***********************ResultPanel**************************************/
		 //initialize components of resultpanel
		 TitledBorder chooseType;  
		 chooseType = BorderFactory.createTitledBorder("choose Type");           //title border
		 resultPanel = new JPanel();
		 resultPanel.setLayout(null);
		 resultPanel.setPreferredSize(new Dimension(900,150));
		 resultPanel.setBorder(chooseType);
		 RadioButtonGroup = new ButtonGroup();
		 
		 bookTick = new JRadioButton("Book",true);
		 journalTick = new JRadioButton("Journal",false);
		 conferenceTick = new JRadioButton("Conference Paper",false);
		 
		 //set components location
		 bookTick.setBounds(250, 60, 105, 25);
		 journalTick.setBounds(360, 60, 105, 25);
		 conferenceTick.setBounds(470, 60, 175, 25);

		 //add JRadioButton to the radioButton group
		 RadioButtonGroup.add(bookTick);
		 RadioButtonGroup.add(journalTick);
		 RadioButtonGroup.add(conferenceTick);
		 
		 //add components to the resultPanel
		 resultPanel.add(bookTick);
		 resultPanel.add(journalTick);
		 resultPanel.add(conferenceTick);
		 
		 //add action listener to radio buttons
		 bookTick.addActionListener(this);                         
		 journalTick.addActionListener(this);
		 conferenceTick.addActionListener(this);
		 
		 /******************editPanel**************************/
		 //initlize components of editPanel
		 editPanel = new JPanel();
		 editPanel.setLayout(null);
		 editPanel.setPreferredSize(new Dimension(900,300));
		 TitledBorder title;  
		 title = BorderFactory.createTitledBorder("Add Publication"); 
		 editPanel.setBorder(title);
		 
		 publicShow();                              //show the common parts
		 publisherName = new JLabel("Publisher:");
		 publishPlace = new JLabel("Publish Place:");
		 publisherNameText = new JTextField();
		 publishPlaceText = new JTextField();	
		 
		 //set components location
		 publisherName.setBounds(220, 90, 80, 25);
		 publisherNameText.setBounds(325, 90, 220, 25);
		 publishPlace.setBounds(220, 125, 80, 25);
		 publishPlaceText.setBounds(325, 125,220, 25);
		 
         //add components to editPanel
		 editPanel.add(publisherName);
		 editPanel.add(publisherNameText);
		 editPanel.add(publishPlace);
		 editPanel.add(publishPlaceText);
				 
         /*******************button panel*************************************************************/
		//initlize components of editPanel
		 ButtonPanel = new JPanel();
		 ButtonPanel.setPreferredSize(new Dimension(900,40));
		 ButtonPanel.setLayout(null);
		 clearButton = new JButton("Clear");
		 submitButton = new JButton("Submit");
		 cancelButton = new JButton("Cancel");
		 
		 //set components location
		 submitButton.setBounds(230, 5,90, 30);
		 clearButton.setBounds(340, 5,90, 30);
		 cancelButton.setBounds(450, 5, 90, 30);
		 
		 //add components to ButtonPanel
		 ButtonPanel.add(submitButton);
		 ButtonPanel.add(clearButton);
		 ButtonPanel.add(cancelButton);
		 
		 blankPanel = new JPanel();
		 blankPanel.setPreferredSize(new Dimension(900,20));
		 
		 //add action listener to button
		 clearButton.addActionListener(this);
		 submitButton.addActionListener(this);
		 cancelButton.addActionListener(this);
		 
         //add all the panels to the frame	 
		 setLayout(new FlowLayout());
	     add(operationPanel);
	     add(blankPanel);
	     add(resultPanel);
	     add(editPanel);
	     add(ButtonPanel);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(1024, 768);
         setVisible(true);

	}
	
	/**
	 * The method to implement the Action Listener of different components. 
	 * 
	 * <p>
	 * For <strong>editMyPublicationButton</strong> Listener
	 * When user press editMyPublicationButton, the Frame will switch to EditPublication GUI
	 * <p>
	 * For <strong>returnHomeButton</strong> Listener
	 * When user press returnHomeButton, the Frame will switch to LoginAsStaff GUI
	 * <p>
	 * For <strong>submitButton</strong> Listener
	 * When user press submitButton, the new record will insert into the file.
	 * <p>
	 * For <strong>clearButton</strong> Listener
	 * When user press clearButton, textfields will become empty
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		//when press add New Publication Button
	   if(arg0.getSource()==this.editMyPublicationButton){
		    System.out.println("trying to switch");
		    PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF,2);//switch frame to EditPublication
		    this.hide();                            //hide and dispose current frame
		    this.dispose();
		}
	   //if press returnHomeButton
		else if(arg0.getSource()==this.returnHomeButton){
			 System.out.println("trying to switch");
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF,0);//switch frame to LoginAsStaff
			 this.hide();                           //hide and dispose current frame
			 this.dispose();
		}
	    //when press submit button
		else if(arg0.getSource()==this.submitButton){                    
			
			 
			 Calendar currentCalendar = Calendar.getInstance();             //get current time(upload time)
			 Date currentDate = new Date();
			 currentDate.setTime(currentCalendar.getTimeInMillis());
			
			 String pubTitle = publicationTitleText.getText().trim();              //get content from the text field
			 String[] pubAuthor = publicationAuthorText.getText().split(",");
			 Set<String> authorSet =new HashSet<String>();
			 for(int i=0; i<pubAuthor.length;i++){                        //add authors to string set
				 authorSet.add(pubAuthor[i]);
				 System.out.println("authorSet"+pubAuthor[i]);
			 }
			 
			 boolean insertResult=false;                                 //record the insert result
			 
			 if(bookTick.isSelected()){                    //if add a book
				 String publisher = publisherNameText.getText().trim();     //get content of textfield
				 String pubPlace = publishPlaceText.getText().trim();
				 if(!pubTitle.equals("") &&  pubAuthor.length!=0 && !publisher.equals("") && !pubPlace.equals("")){
				 //user academic staff controller to insert publication record
				 insertResult = controller.addBook(controller.createPubID("BK"), pubTitle, authorSet, currentDate, publisher, pubPlace); //give the user feedback about the insert result
					 if(insertResult){
						 JOptionPane.showMessageDialog(null, "Add new Publication successfully");
					 }else{
						 JOptionPane.showMessageDialog(null, "The publication detail you try to enter already exist", "Error: Adding publication", JOptionPane.PLAIN_MESSAGE);
					 }
				  	
				 }
				 else{
					 JOptionPane.showMessageDialog(null,"Please fill in all of the fields of the book");
				 }
			 }
			 else if(journalTick.isSelected()){          //if add journal
				 String journalname = journalNameText.getText().trim();     //get content of textfield
				 String issueNumber = issueNumberText.getText().trim();
				 String pageNumber =  pageNumberText.getText().trim();
				 if(!pubTitle.equals("") && pubAuthor.length!=0 && !journalname.equals("") && !issueNumber.equals("") && !pageNumber.equals("")){
				 //user academic staff controller to insert publication record
				 insertResult = controller.addJournalPaper(controller.createPubID("JP"), pubTitle, authorSet, currentDate, journalname, issueNumber, pageNumber);
					 if(insertResult){
						 JOptionPane.showMessageDialog(null, "Add new Publication successfully");
					 }else{
						 JOptionPane.showMessageDialog(null, "The publication detail you try to enter already exist", "Error: Adding publication", JOptionPane.PLAIN_MESSAGE);
					 }
				 }
				 else{
					 JOptionPane.showMessageDialog(null,"Please fill in all of the fields of the journal");
				 }
			}
			 else if(conferenceTick.isSelected()){
				 String conferencePlae = conferencePlaceText.getText().trim();//if add conference
				 if(!pubTitle.equals("") &&  pubAuthor.length!=0 && !conferencePlae.equals("")){
				 //user academic staff controller to insert publication record
				 insertResult = controller.addConferencePaper(controller.createPubID("CP"), pubTitle, authorSet, currentDate, conferencePlae);
						 if(insertResult){
							 JOptionPane.showMessageDialog(null, "Add new Publication successfully");
						 }else{
							 JOptionPane.showMessageDialog(null, "The publication detail you try to enter already exist", "Error: Adding publication", JOptionPane.PLAIN_MESSAGE);
						 }
			     }
				 else{
					 JOptionPane.showMessageDialog(null,"Please fill in all of the fields of the conference");
				 }
			}
			
		}
	    
	    //when press clear button
		else if(arg0.getSource()==this.clearButton){
			//empty all the textfield
			 publicationTitleText.setText("");
			 publicationAuthorText.setText(userName);
			 if(bookTick.isSelected()){
				 publisherNameText.setText("");
				 publishPlaceText .setText("");
				 
			 }
			 else if(journalTick.isSelected()){
				 journalNameText.setText("");
				 issueNumberText.setText("");
				 pageNumberText.setText("");
			 }
			 else if(conferenceTick.isSelected()){
				 conferencePlaceText.setText("");
			 }
		}
	    //when press cancel button
		else if(arg0.getSource()==this.cancelButton){
		    System.out.println("trying to switch");
			PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF,0);//switch frame to LoginAsStaff
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
	    //when press book radio button
		else if(arg0.getSource()==this.bookTick){
		   	System.out.println("bookTick");
			 editPanel.removeAll();
			 editPanel.repaint();
			 editPanel.validate();
             
			 publicShow();               //show public part
			 
			 publisherName = new JLabel("Publisher:");
			 publishPlace = new JLabel("Publish Place:");
			 publisherNameText = new JTextField();
			 publishPlaceText = new JTextField();	
			 
			 publisherName.setBounds(220, 90, 80, 25);
			 publisherNameText.setBounds(325, 90, 220, 25);
			 publishPlace.setBounds(220, 125, 80, 25);
			 publishPlaceText.setBounds(325, 125,220, 25);
			 
			 editPanel.add(publisherName);
			 editPanel.add(publisherNameText);
			 editPanel.add(publishPlace);
			 editPanel.add(publishPlaceText);
		}
	    //when press journal radio button
		else if(arg0.getSource()==this.journalTick){
			System.out.println("journalTick");
			 editPanel.removeAll();
			 editPanel.repaint();
			 editPanel.validate();
            
			 publicShow();
			 
			 journalName = new JLabel("Journal Name:");
			 issueNumber = new JLabel("Issue Number:");
			 pageNumber = new JLabel("Page number:");	
			 journalNameText = new JTextField();
			 issueNumberText = new JTextField();
			 pageNumberText = new JTextField();	
			 
			 journalName.setBounds(220, 90, 82, 25);
			 journalNameText.setBounds(325, 90, 220, 25);
			 
			 issueNumber.setBounds(220, 125, 82, 25);
			 issueNumberText.setBounds(325, 125,220, 25);
			 
			 pageNumber.setBounds(220, 160, 80, 25);
			 pageNumberText.setBounds(325, 160, 220, 25);
			
			 editPanel.add(journalName);
			 editPanel.add(journalNameText);
			 editPanel.add(issueNumber);
			 editPanel.add(issueNumberText);
			 editPanel.add(pageNumber);
			 editPanel.add(pageNumberText);
			
			 
		}
	    //when press conference radio button
		else if(arg0.getSource()== this.conferenceTick){
			System.out.println("conferenceTick");
			 editPanel.removeAll();
			 editPanel.repaint();
			 editPanel.validate();
			 
			 publicShow();
			 
			 conferencePlace = new JLabel("Place:");
			 conferencePlaceText = new JTextField();
			 
			 conferencePlace.setBounds(220, 90, 80, 25);
			 conferencePlaceText.setBounds(325, 90, 220, 25);
			 
			 editPanel.add(conferencePlace);
			 editPanel.add(conferencePlaceText);
		}
	}
	
	/**
	 * Show the public components in order to improve the reuse of codes
	 */
	private void publicShow(){
		//initialize common components
		 publicationTitle = new JLabel("Title:");
		 publicationAuthor = new JLabel("Author:");
		 tipsLabel = new JLabel("NB:You need to upload your own work. Use comma(,) to separate each author");
		 tipsLabel.setFont(new   java.awt.Font("Dialog",1,15));
		 tipsLabel.setForeground(Color.BLUE);
		
	   	 publicationTitleText = new JTextField();
		 publicationAuthorText = new JTextField(userName);
		 
		 //set components location
		 publicationTitle.setBounds(220, 20, 80, 25);
		 publicationTitleText.setBounds(325, 20, 220, 25);
		 
		 publicationAuthor.setBounds(220, 55, 80, 25);
		 publicationAuthorText.setBounds(325, 55, 220, 25);
		 
		 tipsLabel.setBounds(160, 200, 560, 25);
		 
		 //add common components to editPanel
		 editPanel.add(publicationAuthor);
		 editPanel.add(publicationAuthorText);
		 editPanel.add(publicationTitle);
		 editPanel.add(publicationTitleText);
		 editPanel.add(tipsLabel);
	}

}
