package view.academicStaffUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

/**
 * <p>
 * LoginAsStaff class is the GUI for academic staff to access the System.
 * <p>
 * Users can search specific {@link model.publication.Publication Publication} which can be 
 * {@link model.publication.Book book}, {@link model.publication.JournalPaper JournalPaper} 
 * and {@link model.publication.ConferencePaper ConferencePaper}by using different filters. 
 * Users can search by research group, author, upload Date and publication type. They can also 
 * view the details of each publication. This class also provide the entry for academic staff 
 * to add new publication and edit their own publication. If the user is a team {@link model.enumeration.UserRole Coordinator}, 
 * he can add new Seminar.
 * When user finish their operation, they can logout from the system.  
 * 
 * @version
 * @author
 * @see model.publication.Publication
 * @see model.publication.JournalPaper
 * @see model.publication.ConferencePaper
 * @see model.publication.Publication
 */
public class LoginAsStaff extends JFrame implements ActionListener ,  ListSelectionListener{

	private static final long serialVersionUID = 1L;
	
	// frame updating flag
	private boolean frameUpdating;
	
	//define different components
	private JPanel operationPanel;
	private JPanel filterPanel;
	private JPanel filterLeft;
	private JPanel filterRight;
	private JPanel resultPanel;

	
	private JLabel groupLabel;
	private JLabel authorLabel;
	private JLabel yearLabel;
	private JLabel publicationType;
	
	private JLabel welcomMessage;
	
	
	private JLabel bookType;
	private JLabel journalType;
	private JLabel conferenceType;
		
	private JComboBox selectAuthor;
	private JComboBox selectYear;
	private JComboBox selectGroup;
	
	private JCheckBox bookTick;
	private JCheckBox journalTick;
	private JCheckBox conferenceTick;
	
	private JTable resultTable;
	private DefaultTableModel model;
	
	private JButton editMyPublicationButton;
	private JButton addNewPublicationButton;
	private JButton addNewSeminarButton;

	private JButton clearfilterButton; 
	private JButton viewPublicationButton;
	private JButton requestCopyButton;
	private JButton logoutLabel;
	
	private Map<String, String> authorNameMap;
	private Set<String> yearSet;
	private boolean showBook;
	private boolean showJournal;
	private boolean showConference;
	private Set<String> publicationList;
	private ListSelectionModel selectionModel;
	
	//controller
	private AcademicStaffController controller;
	
	/**
	 * Creates a <code>LoginAsStaff</code> instance with the specified
     * AcademicStaffController. It will display the frame of LoginAsStaff.
     * 
	 * @param AScontroller   The controller which has the authority to control this class
	 * @see control.controller.AcademicStaffController
	 */
	public LoginAsStaff(AcademicStaffController AScontroller){
		 super("Welcome");
		 frameUpdating = false;
		 this.controller=AScontroller;                              //set controller
		 
		 /****************operationPanel******************************/
		 //initialize components of operationPanel
		 operationPanel = new JPanel();
		 
		 addNewPublicationButton = new JButton("Add New Publication");
		 editMyPublicationButton = new JButton("Edit My Publication");
		 welcomMessage = new JLabel();
		 
		 //get current system user
		 String currentUser = Repository.currentUserName;
		 if(currentUser!=null){                                   //set current user
			 welcomMessage.setText("Wecome "+Repository.currentUserName +" !");
		 }
		 
		 logoutLabel = new JButton("logout");
		 Font font=logoutLabel.getFont();
		 int style=3;
		 logoutLabel.setFont(new Font(font.getName(),style,font.getSize()));
		 
		 //set components location
		 operationPanel.setLayout(null);
		 operationPanel.setPreferredSize(new Dimension(800,40));
		 addNewPublicationButton.setBounds(20, 10, 150, 25);
		 editMyPublicationButton.setBounds(180, 10, 140, 25);
		
		 welcomMessage.setBounds(540, 10, 180, 25);
		 logoutLabel.setBounds(720,10, 80, 25);
		 
		 //according to the role to decide whether to show the add seminar button
		 if(controller.isCoordinator(currentUser)==1){
			 addNewSeminarButton = new JButton("Manage Seminar");
			 addNewSeminarButton.setBounds(330, 10, 130, 25);
			 addNewSeminarButton.addActionListener(this);
			 operationPanel.add(addNewSeminarButton);
		 }
		 
		 //add action listener to buttons 
		 addNewPublicationButton.addActionListener(this);
		 editMyPublicationButton.addActionListener(this);
		 logoutLabel.addActionListener(this);
		 
		 //add components to operationPanel
		 operationPanel.add(addNewPublicationButton);
		 operationPanel.add(editMyPublicationButton);
		
		 operationPanel.add(welcomMessage);
		 operationPanel.add(logoutLabel);
		 
		 
		 /******************filterPanel**************************/
		 //initialize the components of filterPanel
		 TitledBorder title;  
		 title = BorderFactory.createTitledBorder("Filter"); 
		 filterPanel = new JPanel();
		 filterLeft = new JPanel();
		 filterRight = new JPanel();
		 groupLabel =  new JLabel("Research Group");
		 
		 // initialize publicationList, authorNameMap and yearSet
		 authorNameMap = new HashMap<String, String>();
		 yearSet = new HashSet<String>();
		 publicationList = controller.getPublicationID();
		 
		 for(String publicationID : publicationList) {
			 String uploaderUserName = controller.getPublicationUploaderUserName(publicationID);
			 String fullName = controller.getStaffFullName(uploaderUserName);
			 authorNameMap.put(fullName, uploaderUserName);
			 // here the year need to be added by 1900 because in Date the integer is counted from 1900
			 // and the duplication of years could be solved by Set itself
			 yearSet.add(Integer.toString(1900 + controller.getPublicationDate(publicationID).getYear()));
		 }
		 
		 selectGroup = new JComboBox();
		 selectGroup.addItem("Any");
		 for(String name : PMSystem.groupName)
			 selectGroup.addItem(name);
		 selectGroup.setSelectedItem("Any");
		 authorLabel = new JLabel("Author");
		 selectAuthor =  new JComboBox();
		 yearLabel =  new JLabel("Year");
		 selectYear =  new JComboBox();
		 clearfilterButton =  new JButton("Clear");
		 
		 bookTick = new JCheckBox();
		 journalTick = new JCheckBox();
		 conferenceTick = new JCheckBox();
		 bookType = new JLabel("Book");
		 journalType = new JLabel("Journal");
		 conferenceType = new JLabel("Conference Paper");
		 publicationType = new JLabel("Publication Type");
		 
		 //add components to the filterPanel
		 filterPanel.setBorder(title);
		 filterPanel.setLayout(null);
		 filterPanel.setPreferredSize(new Dimension(900,250));
		 filterPanel.add(filterLeft);
		 filterPanel.add(filterRight);
		
		 /***********************filterLeft******************************/
		 
		 //set components location 
		 filterLeft.setLayout(null);
		 filterLeft.setBounds(60, 25, 400, 200);
		 
		 groupLabel.setBounds(40, 10,100 , 25);
		 selectGroup.setBounds(140, 10, 230, 25);
		 authorLabel.setBounds(40, 45,100,30);
		 selectAuthor.setBounds(140,45,150, 25);
		 yearLabel.setBounds(40,80,100,30);
		 selectYear.setBounds(140, 80, 80, 25);
		 
		 clearfilterButton.setBounds(140,145, 100, 25);
		 
		 //add components to filterleft
		 filterLeft.add(groupLabel);
		 filterLeft.add(selectGroup);
		 filterLeft.add(authorLabel);
		 filterLeft.add(selectAuthor);
		 filterLeft.add(yearLabel);
		 filterLeft.add(selectYear);
		 filterLeft.add(clearfilterButton);
		 
		 /************************filterRight************************************/
		 //set components location 
		 TitledBorder titled;  
		 titled = BorderFactory.createTitledBorder("publication types"); 
	     filterRight.setLayout(null);
		 filterRight.setBounds(510, 25, 300, 200);
		 filterRight.setBorder(titled);
		 
		 bookTick.setBounds(20,30,30,25);
		 bookType.setBounds(55 , 30, 100, 25);
		 journalTick.setBounds(20,60,30, 25);
		 journalType.setBounds(55, 60, 100, 25);
		 conferenceTick.setBounds(20,90, 30, 25);
		 conferenceType.setBounds(55, 90, 200, 25);
		 
		 showBook = true;
		 showJournal = true;
		 showConference = true;
		 
		 //add components to filterRight
		 filterRight.add(publicationType);
		 filterRight.add(bookTick);
		 filterRight.add(bookType);
		 filterRight.add(journalTick);
		 filterRight.add(journalType);
		 filterRight.add(conferenceTick);
		 filterRight.add(conferenceType);
		 
		 /***********************ResultPanel**************************************/
		 //initialize the components of resultPanel
		 resultPanel = new JPanel();
		 viewPublicationButton =  new JButton("View Publication");
		 requestCopyButton = new JButton("Request Copy");
		 JLabel blankLabel = new JLabel();
		 blankLabel.setPreferredSize(new Dimension(80,20));
		 String[] columnName = {"Title","Authors","Type","Upload Date"};
		
		 model = new DefaultTableModel(null,columnName){ 
	            public boolean isCellEditable(int row, int column)   //set the table unit not editable
	            { 
	                return false; 
	            } 
	        }; 
		 resultTable =  new JTable(model);
		 resultPanel.setPreferredSize(new Dimension(1000,500));
		 resultTable.setPreferredScrollableViewportSize(new Dimension(880,320));
		
		 selectionModel = resultTable.getSelectionModel();
		 selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 selectionModel.addListSelectionListener(this);
		 resultPanel.add(new JScrollPane(resultTable));                           //the key to show all the record                         //the key to show all the record
		
		 resultPanel.add(viewPublicationButton); 
		 resultPanel.add(blankLabel);
		 resultPanel.add(requestCopyButton);
		 
		 //add action listener to viewPublicationButton
		 viewPublicationButton.addActionListener(this);
		 requestCopyButton.addActionListener(this);
		 
		 updateFrame();	// update the information in six components by status variables
		 selectAuthor.setSelectedItem("Any");
		 selectYear.setSelectedIndex(0);
         
		 //add all panels to the frame
		 setLayout(new FlowLayout());
	     add(operationPanel);
	     add(filterPanel);
	     add(resultPanel);
	     
	     // adding listeners must be at the very end of constructor, in order to prevent triggering any actions
		 selectGroup.addActionListener(this);			// add Listener to display staff in this group
		 selectAuthor.addActionListener(this);			// add Listener to switch to this staff's group
		 selectYear.addActionListener(this);			// add Listener to display publication by year
		 clearfilterButton.addActionListener(this);		// add Listener to clear button
		 bookTick.addActionListener(this);				// add Listener to bookTick checkBox
		 journalTick.addActionListener(this);			// add Listener to journalTick checkBox
		 conferenceTick.addActionListener(this);		// add Listener to conferenceTick checkBox
	     
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     setSize(1024, 768);
	}
	
	/**
	 * refresh the Frame when some event  triggered to change the GUI 
	 */
	private void updateFrame() {	// information stream: Status Variables-->>Components	
		// update selectAuthor ComboBox
		selectAuthor.removeAllItems();
		if(authorNameMap.size() == 0) {
			selectAuthor.addItem("No staff in this group.");
		}
		else if(authorNameMap.size() == 1) {
			for(String name : authorNameMap.keySet())
				selectAuthor.addItem(name);
		}
		else {
			selectAuthor.addItem("Any");
			for(String name : authorNameMap.keySet())
				selectAuthor.addItem(name);
		}

		// update selectYear ComboBox
		selectYear.removeAllItems();
		if(yearSet.size() == 0) {
			selectYear.addItem("No staff in this group.");
		}
		else if(yearSet.size() == 1) {
			for(String year : yearSet)
				selectYear.addItem(year);
		}
		else {
			selectYear.addItem("Any");
			for(String year : yearSet)
				selectYear.addItem(year);
		}

		// update three JCheckBox
		bookTick.setSelected(showBook);
		journalTick.setSelected(showJournal);
		conferenceTick.setSelected(showConference);
		
		// update model DefaultTableModel, in order to update the JTable		
		while(model.getRowCount() > 0)
			model.removeRow(0);
		
		for(String ID : publicationList) {
		String authorNames = formalizeAuthor(controller.getPublicationAuthorSet(ID));
			Object[] row = {
				controller.getPublicationTitle(ID),
				authorNames,
				controller.getPublicationType(ID),
				controller.getPublicationDate(ID)
			};
			
			model.addRow(row);
		}
	}
	
	
	/**
	 * The method to implement the Action Listener of different components. 
	 * <p>
	 * For <strong>addNewPublicationButton</strong> Listener
	 * When user press addNewPublicationButton, the Frame will switch to AddPublication GUI
	 * <p>
	 * For <strong>editMyPublicationButton</strong> Listener
	 * When user press editMyPublicationButton, the Frame will switch to EditPublication GUI
	 * <p>
	 * For <strong>viewPulibationButton</strong> Listener
	 * When user press viewPublicationButton, the Frame will switch to ViewPublicationAsStaff GUI
	 * <p>
	 * For <strong>addNewSeminarButton</strong> Listener
	 * When user press addNewSeminarButton, the Frame will switch to SeminarGUI
	 * <p>
	 * For <strong>clearfilterButton</strong> Listener
	 * When user press clearfilterButton, the filter part will be initialized
	 * <p>
	 * For <strong>selectGroup</strong> Listener
	 * When  selected item of selectGroup is changed, the selectAuthor will display all  members within that group.
	 * The result table will show the relative records of publication.
	 * <p>
	 * For <strong>selectAuthor</strong> Listener
	 * When  selected item of selectAuthor is changed, the selectGroup will display the group the author belongs to.
	 * The result table will show the relative records of publication.
	 * <p>
	 * For <strong>selectYear</strong>r Listener
	 * The result table will show the relative records of publication.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// only do any action when frame is not being updating
		if(!frameUpdating) {
			frameUpdating = true;
			
		    if(arg0.getSource()==this.addNewPublicationButton){  //if press addNewPublicationButton
		    	PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF, 1);//switch to AddPublication frame
		    	 this.hide();                                    //hide and dispose the current frame
				 this.dispose();
		    }
		    else if(arg0.getSource()==this.editMyPublicationButton){//if press editMyPublicationButton
		    	PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF, 2);//switch to EditPublication frame
		        this.hide();                                       //hide and dispose the current frame
				this.dispose();
		    }
		   
			else if(arg0.getSource()==this.viewPublicationButton){ //if press viewPublicationButton
				int selectedRow = resultTable.getSelectedRow();     //get selected row
				if(selectedRow != -1) {                             //if select a row, get the publication
				String publicationID = (String)publicationList.toArray()[selectedRow];

                Repository.currentPublication = publicationID;      //set current publication
                PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF, 3);//switch to viewPublicationAsStaff frame
                this.hide();                                       //hide and dispose the current frame
				this.dispose();
                }
				else{
					JOptionPane.showMessageDialog(null, "Please select a Publication");
				}
			}
			else if(arg0.getSource()==this.requestCopyButton){
				 if(resultTable.getSelectedRow()!=-1){
					 JOptionPane.showMessageDialog(null, //show a success published message
								"Request Copy Email has been sent to the author",
					    "Send request Success",
					    JOptionPane.PLAIN_MESSAGE);
					  controller.requestPublicationCopy(Repository.currentPublication);
					  }
					  else{
						  JOptionPane.showMessageDialog(null, "Please select a publication you want to request copy");
					  }
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
			else if(arg0.getSource()==this.addNewSeminarButton){   //if press addNewSeminarButton
				PMSystem.getSystemInstance().switchCurrentFrame(UserRole.COORDINATOR, 0);//switch to SeminarUI 
		        this.hide();                                         //hide and dispose the current frame
				this.dispose();
			}
			else if(arg0.getSource()==this.clearfilterButton){     //if press clearfilterButton
				authorNameMap.clear();
				yearSet = new HashSet<String>();
				//initialize all  the filters
				publicationList = controller.getPublicationID();
				for(String publicationID : publicationList) {
					String uploaderUserName = controller.getPublicationUploaderUserName(publicationID);
					String fullName = controller.getStaffFullName(uploaderUserName);
					authorNameMap.put(fullName, uploaderUserName);
					// here the year need to be added by 1900 because in Date the integer is counted from 1900
					// and the duplication of years could be solved by Set itself
					yearSet.add(Integer.toString(1900 + controller.getPublicationDate(publicationID).getYear()));
				}
				 
				showBook = true;
				showJournal = true;
				showConference = true;
				 
				updateFrame();
				
				selectGroup.setSelectedItem("Any");
				selectAuthor.setSelectedIndex(0);
				selectYear.setSelectedIndex(0);
			}
			else if(arg0.getSource()==this.selectGroup){
				// selectAuthor and selectYear ComboBox could be changed when a group is selected
				authorNameMap.clear();
				yearSet.clear();
				
				// get selected group number, which could be -1("Any")
				String groupName = (String)selectGroup.getSelectedItem();
				int groupNumber = groupName == "Any" ? -1 : 1+PMSystem.groupName.indexOf(selectGroup.getSelectedItem());
				
				// get publications in one group, of any years, of any authors
				publicationList = controller.getPublicationID(null, -1, groupNumber);
				
				for(String publicationID : publicationList) {
					String uploaderUserName = controller.getPublicationUploaderUserName(publicationID);
					authorNameMap.put(controller.getStaffFullName(uploaderUserName), uploaderUserName);
					yearSet.add(Integer.toString(1900 + controller.getPublicationDate(publicationID).getYear()));
				}

				showBook = bookTick.isSelected();
				showJournal = journalTick.isSelected();
				showConference = conferenceTick.isSelected();

				// check publication type after finishing selecting comboBoxes
				Set<String> tempPub = new HashSet<String>();
				for(String ID : publicationList) {
					PublicationType type = controller.getPublicationType(ID);
					
					boolean toDisplay =
							(type == PublicationType.BOOK ? bookTick.isSelected() :
								(type == PublicationType.JOURNAL ? journalTick.isSelected() :
									conferenceTick.isSelected()));
					
					if(toDisplay)
						tempPub.add(ID);
				}
				publicationList = tempPub;
				
				updateFrame();
				
				selectGroup.setSelectedItem(groupName);
				selectAuthor.setSelectedIndex(0);
				selectYear.setSelectedIndex(0);
			}
			else if(arg0.getSource()==this.selectAuthor) {
				// selectGroup and selectYear ComboBox could be changed when a author is selected
				yearSet.clear();
				
				String groupName;
				String authorFullName = (String)selectAuthor.getSelectedItem();
				
				if(authorFullName != "Any") {	// SITUATION 1: select one particular staff, either from one group or from any groups
					String authorUserName = authorNameMap.get(authorFullName);
					int groupNumber = controller.getStaffGroup(authorUserName);
					
					groupName = PMSystem.groupName.get(groupNumber-1);	// REMEMBER to -1 from the groupNumber to get group name index
					
					// get publications in one staff's group, of any years, of a particular author
					publicationList = controller.getPublicationID(authorUserName, -1, groupNumber);
				}
				else if(authorFullName == "No staff in this group.") {
					publicationList = new HashSet<String>();
					groupName = (String)selectGroup.getSelectedItem();					
				}
				else {
					Set<String> allStaffFullNames = new HashSet<String>();
					
					// selectAuthor ComboBox either show all staff or group staff, group Name doesn't change
					groupName = (String)selectGroup.getSelectedItem();					
					
					for(String publicationID : controller.getPublicationID(null, -1, -1)) {
						String uploaderUserName = controller.getPublicationUploaderUserName(publicationID);
						allStaffFullNames.add(uploaderUserName);
					}
					
					if(selectAuthor.getItemCount() == allStaffFullNames.size()) {
						// SITUATION 2: select "Any" staff when all staffs are shown in selectAuthor ComboBox
						// get all publications to display in the JTable
						publicationList = controller.getPublicationID();
					}
					else {
						// SITUATION 3: select "Any" staff when staffs in particular group are shown in selectAuthor ComboBox
						
						int groupNumber = PMSystem.groupName.indexOf(groupName) + 1;
						// get publications in a particular group, of any staffs, in any years
						publicationList = controller.getPublicationID(null, -1, groupNumber);	
					}
				}
				
				for(String publicationID : publicationList)
					yearSet.add(Integer.toString(1900 + controller.getPublicationDate(publicationID).getYear()));
	
				showBook = bookTick.isSelected();
				showJournal = journalTick.isSelected();
				showConference = conferenceTick.isSelected();

				// check publication type after finishing selecting comboBoxes
				Set<String> tempPub = new HashSet<String>();
				for(String ID : publicationList) {
					PublicationType type = controller.getPublicationType(ID);
					
					boolean toDisplay =
							(type == PublicationType.BOOK ? bookTick.isSelected() :
								(type == PublicationType.JOURNAL ? journalTick.isSelected() :
									conferenceTick.isSelected()));
					
					if(toDisplay)
						tempPub.add(ID);
				}
				publicationList = tempPub;
				
				updateFrame();

				selectGroup.setSelectedItem(groupName);
				selectAuthor.setSelectedItem(authorFullName);
				selectYear.setSelectedIndex(0);
			}
			else if(arg0.getSource()==this.selectYear){
				// selectGroup, selectAuthor and selectYear ComboBox don't change when a year is selected
				String groupName = (String)selectGroup.getSelectedItem();
				String year = (String)selectYear.getSelectedItem();

				if(selectYear.getSelectedItem().equals("No staff in this group.")) {
					publicationList = new HashSet<String>();
				}
				else {
					// get selected group number, which could be -1("Any")
					int groupNumber = groupName == "Any" ? -1 : 1+PMSystem.groupName.indexOf(selectGroup.getSelectedItem());
					
					// get selected author userName, which could be -1("Any")
					String authorFullName = (String)selectAuthor.getSelectedItem();
					String authorUserName = authorFullName == "Any" ? null : authorNameMap.get(authorFullName);
					
					// get selected year, which could be -1("Any")
					int y = year == "Any" ? -1 : Integer.valueOf((String)selectYear.getSelectedItem())-1900;
					
					// get publication in a particular group, of a particular author, and in a particular year
					publicationList = controller.getPublicationID(authorUserName, y, groupNumber);
				}
				
				showBook = bookTick.isSelected();
				showJournal = journalTick.isSelected();
				showConference = conferenceTick.isSelected();
				
				// check publication type after finishing selecting comboBoxes
				Set<String> tempPub = new HashSet<String>();
				for(String ID : publicationList) {
					PublicationType type = controller.getPublicationType(ID);
					
					boolean toDisplay =
							(type == PublicationType.BOOK ? bookTick.isSelected() :
								(type == PublicationType.JOURNAL ? journalTick.isSelected() :
									conferenceTick.isSelected()));
					
					if(toDisplay)
						tempPub.add(ID);
				}
				publicationList = tempPub;
				 
				updateFrame();
				
				selectGroup.setSelectedItem(groupName);
				selectYear.setSelectedItem(year);
			}
			else if(arg0.getSource()==this.bookTick ||
					arg0.getSource()==this.journalTick ||
					arg0.getSource()==this.conferenceTick) {
				// read and save all the current conditions, including 3 comboBox and 3 checkBox
				// display the searching results, and no components changed here
				
				String groupName = (String)selectGroup.getSelectedItem();
				String year = (String)selectYear.getSelectedItem();
				String authorFullName = (String)selectAuthor.getSelectedItem();
				
				if(selectAuthor.getSelectedItem().equals("No staff in this group.") ||
						selectYear.getSelectedItem().equals("No staff in this group.")) {
					publicationList = new HashSet<String>();
				}
				else {
					// get selected group number, which could be -1("Any")
					int groupNumber = groupName == "Any" ? -1 : 1+PMSystem.groupName.indexOf(selectGroup.getSelectedItem());
					
					// get selected author userName, which could be -1("Any")
					authorFullName = (String)selectAuthor.getSelectedItem();
					String authorUserName = authorFullName == "Any" ? null : authorNameMap.get(authorFullName);
					
					// get selected year, which could be -1("Any")
					int y = year == "Any" ? -1 : Integer.valueOf((String)selectYear.getSelectedItem())-1900;
					
					// get publication in a particular group, of a particular author, and in a particular year
					publicationList = controller.getPublicationID(authorUserName, y, groupNumber);
				}

				showBook = bookTick.isSelected();
				showJournal = journalTick.isSelected();
				showConference = conferenceTick.isSelected();
				
				// check publication type after finishing selecting comboBoxes
				Set<String> tempPub = new HashSet<String>();
				for(String ID : publicationList) {
					PublicationType type = controller.getPublicationType(ID);
					
					boolean toDisplay =
							(type == PublicationType.BOOK ? bookTick.isSelected() :
								(type == PublicationType.JOURNAL ? journalTick.isSelected() :
									conferenceTick.isSelected()));
					
					if(toDisplay)
						tempPub.add(ID);
				}
				publicationList = tempPub;
				 
				updateFrame();				
			}
		    
		    // finish updating frame
			frameUpdating = false;
		
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

@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	String publicationID = (String) publicationList.toArray()[resultTable.getSelectedRow()];
	Repository.currentPublication = publicationID;
}
}
