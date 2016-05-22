package view.academicStaffUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.enumeration.PublicationType;
import model.enumeration.UserRole;

import control.PMSystem;
import control.Repository;
import control.controller.AcademicStaffController;
import control.controller.GeneralUserController;

/**
 * <p>
 * <strong>EditPublication</strong> class is the GUI for academic staff to edit specific publication details.
 * <p>
 * The specific {@link model.publication.Publication Publication} you choose can be {@link model.publication.Book book}, 
 * {@link model.publication.JournalPaper JournalPaper} and {@link model.publication.ConferencePaper ConferencePaper}. For each type,
 * they have different details.
 * This class also provide the entry for academic staff to the {@link view.academicStaffUI.LoginAsStaff LoginAsStaff} and 
 * {@link view.academicStaffUI.AddPublication AddPublication}.
 * @author 
 * @version
 * @see model.publication.Publication
 * @see model.publication.JournalPaper
 * @see model.publication.ConferencePaper
 * @see model.publication.Publication
 * @see view.academicStaffUI.LoginAsStaff
 *
 */
public class EditPublication extends JFrame implements ActionListener,  ListSelectionListener{

	private static final long serialVersionUID = 1L;
	//define components 
	private JPanel operationPanel;
	private JPanel editPanel;
	private JPanel resultPanel;
	private JPanel buttonPanel;
	
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
	
	private JTable resultTable;
	private DefaultTableModel model;
	
	private JButton returnHomeButton;
	private JButton addNewPublicationButton;
	private JButton submitButton;
	private JButton clearButton;
	private JButton cancelButton;
	private JButton logoutLabel;
	
	private AcademicStaffController controller;
	private PublicationType editType;
	private ListSelectionModel selectionModel;
	private HashMap<String,String> publicationSet;
	
	/**
	 * Creates a <code>EditPublication</code> instance with the specified
     * {@link control.controller.AcademicStaffController AcademicStaffController}. 
     * It will display the frame of EditPublication.
	 * @param controller   The controller which has the authority to control this class
	 * @see control.controller.AcademicStaffController
	 */
	public EditPublication(AcademicStaffController controller1){
		 super("Welcome");
		 this.controller=controller1;                //set controller
		 
		 /****************operationPanel******************************/
		 //initialize operation of operationPanel
		 operationPanel = new JPanel();
		 
		 addNewPublicationButton = new JButton("Add New Publication");
		 returnHomeButton = new JButton("HomePage");
		 welcomMessage = new JLabel();
		 if(Repository.currentUserName!=null){                    //set current user
			 welcomMessage.setText("Welcom"+Repository.currentUserName);
		 }
		 
		 logoutLabel = new JButton("logout");
		 Font font=logoutLabel.getFont();
		 int style=3;
		 logoutLabel.setFont(new Font(font.getName(),style,font.getSize()));
		 
		 //set components location
		 operationPanel.setLayout(null);
		 operationPanel.setPreferredSize(new Dimension(800,40));
		 returnHomeButton.setBounds(10, 10, 140, 25);
		 addNewPublicationButton.setBounds(170, 10, 150, 25);
		 welcomMessage.setBounds(500, 10, 220, 25);
		 logoutLabel.setBounds(720,10, 80, 25);
		 
		 //add action listener to the buttons
		 returnHomeButton.addActionListener(this);
		 addNewPublicationButton.addActionListener(this);
		 logoutLabel.addActionListener(this);
		 
		 //add components to operationPanel
		 operationPanel.add(returnHomeButton);
		 operationPanel.add(addNewPublicationButton);
		 operationPanel.add(welcomMessage);
		 operationPanel.add(logoutLabel);
		 
		 
		 /***********************ResultPanel**************************************/
		 //initialize components of ResultPanel
		 resultPanel = new JPanel();
		 String[] columnName = {"Title","Authors","Type","Upload Date"};
		 model = new DefaultTableModel(null,columnName){ 
	            public boolean isCellEditable(int row, int column)        //set the table unit not editable
	            { 
	                return false; 
	            } 
	        };
		 resultTable =  new JTable(model);
		 
		 updateTable();
		 resultPanel.setPreferredSize(new Dimension(1000,330));
		 resultTable.setPreferredScrollableViewportSize(new Dimension(880,300));
		 
		 //set table
		 selectionModel = resultTable.getSelectionModel();
		 selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 selectionModel.addListSelectionListener(this);
		 resultPanel.add(new JScrollPane(resultTable));                           //the key to show all the record
		 
		 /******************editPanel**************************/
		 TitledBorder title;  
		 title = BorderFactory.createTitledBorder("Edit Publication"); 
		 editPanel = new JPanel();
		 editPanel.setLayout(null);
		 editPanel.setPreferredSize(new Dimension(900,260));
		 editPanel.setBorder(title);
	
		JLabel notice = new JLabel("NB:Please select a publication you want to edit from the table"); //show tips for users
		notice.setFont(new   java.awt.Font("Dialog",1,20));
		notice.setForeground(Color.BLUE);
		notice.setBounds(150, 100, 600, 50);
		editPanel.add(notice);
		 
		 /*******************button panel*************************************************************/
		 //initialize componets of buttonPanel
		 buttonPanel = new JPanel();
		 buttonPanel.setPreferredSize(new Dimension(900,40));
		 buttonPanel.setLayout(null);
		 clearButton = new JButton("Clear");
		 submitButton = new JButton("Submit");
		 cancelButton = new JButton("Cancel");
		 
		 //set components location
		 submitButton.setBounds(230, 5,90, 30);
		 clearButton.setBounds(340, 5,90, 30);
		 cancelButton.setBounds(450, 5, 90, 30);
		 
		 //add components to buttonPanel
		 buttonPanel.add(submitButton);
		 buttonPanel.add(clearButton);
		 buttonPanel.add(cancelButton);
		 
		 //add action listener to button
		 clearButton.addActionListener(this);
		 submitButton.addActionListener(this);
		 cancelButton.addActionListener(this);
		 
		 //add all panels to the frame
		 setLayout(new FlowLayout());
	     add(operationPanel);
	     add(resultPanel);
	     add(editPanel);
	     add(buttonPanel);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(1024, 768);
         setVisible(true);

	}
	
	/**
	 * The method to implement the Action Listener of different components. 
	 * <p>
	 * For <strong>addNewPublicationButton</strong> Listener
	 * When user press addNewPublicationButton, the Frame will switch to AddPublication GUI
	 * <p>
	 * For <strong>returnHomeButton</strong> Listener
	 * When user press returnHomeButton, the Frame will switch to LoginAsStaff GUI
	 * <p>
	 * For <strong>submitButton</strong> Listener
	 * When user press submitButton, the details of selected publication will be updated
	 * <p>
	 * For <strong>clearButton</strong> Listener
	 * When user press clearButton, the textfield will become empty.
	 * <p>
	 * For <strong>cancelButton</strong> Listener
	 * When user press cancelButton, the Frame will switch to LoginAsStaff GUI
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	    if(arg0.getSource()==this.addNewPublicationButton){    //press addNewPublicationButton
	    	System.out.println("trying to switch");
	    	PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF,1);//switch to AddPublication frame
	    	this.hide();                              //hide and dispose current frame
		    this.dispose();
	    }
	    else if(arg0.getSource()==this.returnHomeButton){    //press returnHomeButton
	    	System.out.println("trying to switch");
		    PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF,0);//switch to LoginAsStaff frame
		    this.hide();                               //hide and dispose current frame
		    this.dispose();
	    }
		else if(arg0.getSource()==this.submitButton){
			if(resultTable.getSelectedRow()==-1){
				JOptionPane.showMessageDialog(null, "You did not choose any item to edit");
			}
			else{
			 Object originTitle = model.getValueAt(resultTable.getSelectedRow(), 0);  //the original publication title
			
			 String Pubtitle = publicationTitleText.getText().trim();                   //get new title
			 Set<String> PubAuthor = new HashSet<String>();
			 String[] authorName = publicationAuthorText.getText().split(",");   //get new authors
		
			 for(String name : authorName){
				 PubAuthor.add(name);
			 }

			 String PubID =  publicationSet.get(originTitle).trim();                    //get publicationID according to the old title
			 String originAuthor =controller.getStaffFullName(controller.getPublicationUploaderUserName(PubID));
			 if(!publicationAuthorText.getText().contains(originAuthor)){ PubAuthor.add(originAuthor);}
			
			 Date Pubdate = controller.getPublicationDate(PubID);                //get publication upload date
			 boolean changeResult = false;                                     //record the result of update
			 if(editType.equals(PublicationType.BOOK)){                         //if edit book
				 String PublishierName = publisherNameText.getText().trim();           //get new content
				 String PublisherPlace = publishPlaceText .getText().trim();
				 
				 if(!Pubtitle.equals("") &&  authorName.length!=0 && !PublishierName.equals("") && !PublisherPlace.equals("")){
				 changeResult=controller.editBook(PubID, Pubtitle, PubAuthor, Pubdate, PublishierName, PublisherPlace);//execute update
				 System.out.println("Book"+PubID+","+Pubtitle+","+authorName+","+Pubdate+","+PublishierName+","+PublisherPlace+";");
						 if(changeResult){                                                  //if update successfully
							 JOptionPane.showMessageDialog(null, "Update successfully."); 
							 publicationSet.put(Pubtitle,PubID);                            //update the table
							 model.setValueAt(Pubtitle, resultTable.getSelectedRow(), 0);
							 if(publicationAuthorText.getText().trim().contains(originAuthor)){
							 model.setValueAt(publicationAuthorText.getText().trim(), resultTable.getSelectedRow(), 1);
							 }else{
							 model.setValueAt(originAuthor+","+publicationAuthorText.getText().trim(), resultTable.getSelectedRow(), 1);	 
							 }
						 }
						 else{                                                              //if failed, give relative message
							 JOptionPane.showMessageDialog(null, "Sorry, update failed.");
						 }
						 
				 }
				 else{
					 JOptionPane.showMessageDialog(null,"Please fill in all of the fields of the book");
				 }
			 }
			 else if(editType.equals(PublicationType.JOURNAL)){                 //if edit journal
				 String journalName = journalNameText.getText().trim();                 //get new content
				 String issueNumber = issueNumberText.getText().trim();
				 String pageNumber = pageNumberText.getText().trim();
				 if(!Pubtitle.equals("") &&  authorName.length!=0 && !journalName.equals("") && !issueNumber.equals("") && !pageNumber.equals("")){
				 changeResult=controller.editJournalPaper(PubID, Pubtitle, PubAuthor, Pubdate, journalName, issueNumber, pageNumber);//execute update
				 System.out.println("Journal"+PubID+","+Pubtitle+","+PubAuthor+","+Pubdate+","+journalName+","+issueNumber+","+pageNumber+";");
					 if(changeResult){                                                  //if update successfully
						 JOptionPane.showMessageDialog(null, "Update successfully."); 
						 publicationSet.put(Pubtitle,PubID);                            //update the table
						 model.setValueAt(Pubtitle, resultTable.getSelectedRow(), 0);
						 if(publicationAuthorText.getText().trim().contains(originAuthor)){
							 model.setValueAt(publicationAuthorText.getText().trim(), resultTable.getSelectedRow(), 1);
							 }else{
							 model.setValueAt(originAuthor+","+publicationAuthorText.getText().trim(), resultTable.getSelectedRow(), 1);	 
							 }
					 }
					 else{                                                              //if failed, give relative message
						 JOptionPane.showMessageDialog(null, "Sorry, update failed.");
					 }
				 
				 }
				 else{
					 JOptionPane.showMessageDialog(null,"Please fill in all of the fields of the journal");
				 }
			 }
			 else if(editType.equals(PublicationType.CONFERENCE)){             //if edit conference
				 String conPlace = conferencePlaceText.getText();                //get new content
				 if(!Pubtitle.equals("") &&  authorName.length!=0 && !conPlace.equals("")){
				 changeResult=controller.editConferencePaper(PubID, Pubtitle, PubAuthor, Pubdate, conPlace);//execute update
				 System.out.println("conference"+PubID+","+Pubtitle+","+PubAuthor+","+Pubdate+","+conPlace+";");
					 if(changeResult){                                                  //if update successfully
						 JOptionPane.showMessageDialog(null, "Update successfully."); 
						 publicationSet.put(Pubtitle,PubID);                            //update the table
						 model.setValueAt(Pubtitle, resultTable.getSelectedRow(), 0);
						 if(publicationAuthorText.getText().trim().contains(originAuthor)){
							 model.setValueAt(publicationAuthorText.getText().trim(), resultTable.getSelectedRow(), 1);
							 }else{
							 model.setValueAt(originAuthor+","+publicationAuthorText.getText().trim(), resultTable.getSelectedRow(), 1);	 
							 }
					 }
					 else{                                                              //if failed, give relative message
						 JOptionPane.showMessageDialog(null, "Sorry, update failed.");
					 }
				 }
				 else{
					 JOptionPane.showMessageDialog(null,"Please fill in all of the fields of the conference");
				 }
			 }
			
		}}
		else if(arg0.getSource()==this.clearButton){               // if press clearButton
			if(resultTable.getSelectedRow()==-1){
				JOptionPane.showMessageDialog(null, "You did not choose any item to edit");
			}
			else{ 
			publicationTitleText.setText("");                       //empty all textfields
			 publicationAuthorText.setText("");
			 if(editType.equals(PublicationType.BOOK)){
				 publisherNameText.setText("");
				 publishPlaceText .setText("");
				 
			 }
			 else if(editType.equals(PublicationType.JOURNAL)){
				 journalNameText.setText("");
				 issueNumberText.setText("");
				 pageNumberText.setText("");
			 }
			 else if(editType.equals(PublicationType.CONFERENCE)){
				 conferencePlaceText.setText("");
			 }
			
		}}
		else if(arg0.getSource()==this.cancelButton){              //if press cancelButton
			 System.out.println("trying to switch");
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF,0);//switch to LoginAsStaff frame
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
	 * If selected row changed, the seleced items will be displayed in the textfield.
	 * The details of the publication vary from type to type
	 */
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		//get each column of selected items
		Object title = model.getValueAt(resultTable.getSelectedRow(), 0);
		Object author = model.getValueAt(resultTable.getSelectedRow(), 1);
		Object type = model.getValueAt(resultTable.getSelectedRow(), 2);
		Object date = model.getValueAt(resultTable.getSelectedRow(), 3);
	    
		//get publicationID 
		String checkID = publicationSet.get(title);
		editType = (PublicationType) type;
		
		//dispaly different details according to the publication type
		if(type.equals(PublicationType.BOOK)){
			 
			 editPanel.removeAll();          //remove all the components of editPanel
			 editPanel.repaint();            //repaint editPanel
			 editPanel.validate();
			 
			 showCommon();                   //display common components
			 //re-initialize all the components of book
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
			 
			 //set textfields
			 publicationTitleText.setText(title.toString());
			 publicationAuthorText.setText(author.toString());
			 publisherNameText.setText(controller.getBookPublisherName(checkID));
			 publishPlaceText.setText(controller.getBookPublishPlace(checkID));
			 
			 
		}
		else if(type.equals(PublicationType.JOURNAL)){
			
			 editPanel.removeAll();          //remove all the components of editPanel
			 editPanel.repaint();            //repaint editPanel
			 editPanel.validate();
			 
			 showCommon();                   //display common components
			 
			//re-initialize all the components of journal
			 journalName = new JLabel("Journal Name:");
			 issueNumber = new JLabel("Issue Number:");
			 pageNumber = new JLabel("Page number:");	
			 journalNameText = new JTextField();
			 issueNumberText = new JTextField();
			 pageNumberText = new JTextField();	
			 
			//set components location
			 journalName.setBounds(220, 90, 82, 25);
			 journalNameText.setBounds(325, 90, 220, 25);
			 
			 issueNumber.setBounds(220, 125, 82, 25);
			 issueNumberText.setBounds(325, 125,220, 25);
			 
			 pageNumber.setBounds(220, 160, 80, 25);
			 pageNumberText.setBounds(325, 160, 220, 25);
             
			 //add components to editPanel
			 editPanel.add(journalName);
			 editPanel.add(journalNameText);
			 editPanel.add(issueNumber);
			 editPanel.add(issueNumberText);
			 editPanel.add(pageNumber);
			 editPanel.add(pageNumberText);
			 
			//set textfields
			 publicationTitleText.setText(title.toString());
			 publicationAuthorText.setText(author.toString());
			 journalNameText.setText(controller.getJournalName(checkID));
			 issueNumberText.setText(controller.getJournalIssueNumber(checkID));
			 pageNumberText.setText(controller.getJournalPageNumber(checkID));
			 
		}
		else if(type.equals(PublicationType.CONFERENCE)){
			editPanel.removeAll();          //remove all the components of editPanel
			 editPanel.repaint();            //repaint editPanel
			 editPanel.validate();
			
			 showCommon();                   //show common components
			 
			//re-initialize all the components of journal
			 conferencePlace = new JLabel("Place:");
			 conferencePlaceText = new JTextField();
			
			 //set location
			 conferencePlace.setBounds(220, 90, 80, 25);
			 conferencePlaceText.setBounds(325, 90, 220, 25);
			
			 //add components to editPanel
			 editPanel.add(conferencePlace);
			 editPanel.add(conferencePlaceText);
			 
			 //set textfields
			 publicationTitleText.setText(title.toString());
			 publicationAuthorText.setText(author.toString());
			 conferencePlaceText.setText(controller.getConferencePlace(checkID));
		}
		
	}
	
	/**
	 * Show the public components in order to improve the reuse of codes
	 */
 private void showCommon(){
	     //initialize common components
		 publicationTitle = new JLabel("Title:");
		 publicationAuthor = new JLabel("Author:");
		 tipsLabel = new JLabel("For Author, you don't need to provide your name");
		
	   	 publicationTitleText = new JTextField();
		 publicationAuthorText = new JTextField();
		 
		 //set components location
		 publicationTitle.setBounds(220, 20, 80, 25);
		 publicationTitleText.setBounds(325, 20, 420, 25);
		 
		 publicationAuthor.setBounds(220, 55, 80, 25);
		 publicationAuthorText.setBounds(325, 55, 420, 25);
		 
		 tipsLabel.setBounds(220, 200, 500, 25);
		
		 //add common components to editPanel
		 editPanel.add(publicationAuthor);
		 editPanel.add(publicationAuthorText);
		 editPanel.add(publicationTitle);
		 editPanel.add(publicationTitleText);
		 editPanel.add(tipsLabel);
 }
 
 private void updateTable(){
	 publicationSet = new HashMap<String,String>();
	 String currentUser = Repository.currentUserName;
	 Set<String> authorArray = new HashSet<String>();
	
	 for(String ID : controller.getPublicationID(currentUser,-1, -1)){
		 authorArray = controller.getPublicationAuthorSet(ID);
		 String authorString="";
		 int count=0;
		for(String name : authorArray){
	    	authorString+=name;
	    	count++;
	    	if(count<authorArray.size()&& count!=0){
	    	authorString+=",";
	    	}
	    }
		Object[] row ={
		 controller.getPublicationTitle(ID),
		 authorString,
		 controller.getPublicationType(ID),
		 controller.getPublicationDate(ID)
		 };
		 model.addRow(row);
		 publicationSet.put(controller.getPublicationTitle(ID),ID);
	 };
	 
 }

}
