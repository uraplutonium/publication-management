package view.coordinatorUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

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
import javax.swing.table.TableColumn;

import model.enumeration.UserRole;

import control.PMSystem;
import control.Repository;
import control.controller.CoordinatorController;

/**
 * <strong>SeminarGUI</strong> class create the UI for the Seminar Coordinator. The entry point for this UI is the 
 * HomePage of the {@link model.user.AcademicStaff AcademicStaff}
 * 
 *<p>For any {@link model.user.AcademicStaff AcademicStaff} to have access to this UI, the role must be changed to
 *Co-ordinator by the System Admin
 *
 *@see control.controller.CoordinatorController
 *@see control.controller.GeneralUsercontroller
 *@see control.controller.AcademicStaffController
 *@see control.PMSystem
 */
public class SeminarGUI extends JFrame implements ActionListener, ListSelectionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	
	//set the UI elements
	JPanel addSeminarPanel;
	JPanel displaySeminarPanel;
	JPanel displayPublicationPanel;
	JPanel navPanel;
	
	JTable seminarTable;
	JTable publicationTable;
	
	DefaultTableModel seminarModel;
	DefaultTableModel publicationModel;
	
	JLabel topicLabel;
	JLabel timeLabel;
	JLabel dateLabel;
	JLabel placeLabel;
	JLabel seminarDetail;
	JLabel monthlyPub;
	JLabel showCurrentSeminar;
	
	JTextField topicText;
	JTextField timeText;
	JTextField dateText;
	JTextField placeText;
	
	JButton addSeminarButton;
	JButton clearButton;
	JButton publishButton;
	JButton editButton;
	JButton cancelSeminarButton;
	JButton logout;
	JButton homePage;
	
	ListSelectionModel selectModel;
	int val=1;
	
	 CoordinatorController controller;

	/**<p>
	 * Creates an instance of <strong>SeminarGUI</strong> with the specified controller
	 * {@link model.controller.CoordinatorController CoordinatorController}. This will display the Seminar GUI 
	 * 
	 * @param controller The controller which had the authority to control this class
	 * @see control.controller.CoordinatorController
	 */
	public SeminarGUI( CoordinatorController controller){
		
		this.controller = controller;	//set the controller to access the CoordinatorController
		
		/****************add Seminar Panel*************************************/
		
		addSeminarPanel =  new JPanel();	//set a JPanel for adding the seminar
		topicLabel = new JLabel("Topic:");	//set a topic label
		timeLabel = new JLabel("Time:");	//set a time label
		dateLabel = new JLabel("Date:");	//set a date label
		placeLabel = new JLabel("Place:");	//set a place label
		
		TitledBorder title;  				//Create a border with legend title "Seminar Detail"
		title = BorderFactory.createTitledBorder("Seminar Detail"); 
		
		//set the various textfield for input and editing
		topicText = new JTextField();
		timeText = new JTextField();
		dateText = new JTextField();
		placeText = new JTextField();
		
		//add the button for certain actions to be carried out by the coordinator
		addSeminarButton = new JButton("Add Seminar");
		clearButton = new JButton("Clear");
		publishButton = new JButton("Publish seminar");
		editButton =  new JButton("Update seminar");
		cancelSeminarButton = new JButton("Cancel seminar");

		
		//set the position of the button and the labels
		addSeminarPanel.setBounds(100, 400,800, 280);
		addSeminarPanel.setLayout(null);
		addSeminarPanel.setBorder(title);
		topicLabel.setBounds(65, 60, 50, 25);
		topicText.setBounds(125, 60, 290, 25);
		timeLabel.setBounds(65, 100, 50, 25);
		timeText.setBounds(125, 100, 290, 25);
		dateLabel.setBounds(65, 140, 50, 25);
		dateText.setBounds(125, 140, 290, 25);
		placeLabel.setBounds(65, 180, 50, 25);
		placeText.setBounds(125, 180, 290, 25);
		
		addSeminarButton.setBounds(590, 60, 150, 25);
		editButton.setBounds(590, 100, 150, 25);
		publishButton.setBounds(590, 140,150, 25);
		cancelSeminarButton.setBounds(590, 180,150, 25);
		clearButton.setBounds(590, 220, 150, 25);
		
		//add all the buttons,labels and textfield to the addSeminarPanel
		addSeminarPanel.add(topicLabel);
		addSeminarPanel.add(topicText);
		addSeminarPanel.add(timeLabel);
		addSeminarPanel.add(timeText);
		addSeminarPanel.add(dateLabel);
		addSeminarPanel.add(dateText);
		addSeminarPanel.add(placeLabel);
		addSeminarPanel.add(placeText);
		
		addSeminarPanel.add(editButton);
		addSeminarPanel.add(publishButton);
		addSeminarPanel.add(addSeminarButton);
		addSeminarPanel.add(cancelSeminarButton);
		addSeminarPanel.add(clearButton);
		
		//set the action listener for each button
		editButton.addActionListener(this);
		publishButton.addActionListener(this);
		addSeminarButton.addActionListener(this);
		cancelSeminarButton.addActionListener(this);
		clearButton.addActionListener(this);

		
		/****************display Seminar panel*************************************/
		displaySeminarPanel = new JPanel();	//set a panel to display the seminar added to the system
		displaySeminarPanel.setBounds(50, 50,550, 290);	//set the position and dimension
		seminarDetail = new JLabel("Seminar Detail"); //set the seminarDetails label
		seminarDetail.setBounds(0, 5, 200, 23);		//set the position of the label
		seminarDetail.setForeground(Color.BLUE);
		
		displaySeminarPanel.add(seminarDetail);		//add the seminarDetail label to the panel
		
	
		String[] seminarInfo = {"Topic","Time","Date","Place","Status"};	//column title for the seminar table
		
		seminarModel = new DefaultTableModel(null,seminarInfo){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			

			public boolean isCellEditable(int row, int column)      //set the table unit not editable
	            { 
	                return false; 
	            } 
		};		//set the model and define the title for each column
		seminarTable = new JTable(seminarModel);					//create the table based on the model
		seminarTable.setPreferredScrollableViewportSize(new Dimension(498,200)); // set the size of the table
		seminarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);    //can only choose one item at one time
		
		selectModel = seminarTable.getSelectionModel();	//create a model for the select item
		selectModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //set the model to be a single selection
		selectModel.addListSelectionListener(this); //create a listener for this model
		
		
		displaySeminarPanel.add(new JScrollPane(seminarTable));                //the key to show all the record
		TableColumn firsetColumn = seminarTable.getColumnModel().getColumn(0);	//get the first column
		firsetColumn.setPreferredWidth(175);	//set the size of the first column
		
		for(int semID: controller.getGroupSeminarID())	//get all the seminarId of the current user
		{
			Object[] grpSeminar = {						
					controller.getSeminarTopic(semID),
					controller.getSeminarTime(semID),
					controller.getSeminarDate(semID),
					controller.getSeminarPlace(semID),
					controller.getSeminarStatus(semID)
			};
			seminarModel.addRow(grpSeminar);			//add each of the seminar to the table
		}

		
		/****************display publication panel*************************************/
		displayPublicationPanel = new JPanel();	//set a model for monthly publciation
		displayPublicationPanel.setBounds(590, 50, 400, 290);	//set the size of the panel
		
		monthlyPub = new JLabel(); //set the label for monthlyPub
		Calendar cal = Calendar.getInstance();
		String monthName = "";
		
		switch(cal.get(Calendar.MONTH)){
		case 0 : monthName = "January";
					break;
		case 1: monthName = "February";
					break;
		case 2:	monthName = "March";
					break;
		case 3: monthName = "April";
					break;
		case 4: monthName = "May";
					break;
		case 5: monthName = "June";
					break;
		case 6: monthName = "July";
					break;
		case 7:	monthName = "August";
					break;
		case 8: monthName = "September";
					break;
		case 9: monthName = "October";
					break;
		case 10: monthName = "November";
					break;
		case 11: monthName = "December";
					break;
		}
		
		monthlyPub.setText("Monthly Publication: "+monthName+" "+cal.get(Calendar.YEAR));
		monthlyPub.setForeground(Color.BLUE);
		monthlyPub.setBounds(300, 100, 340, 23);			//set the position of the label inside the panel
		
		
		displayPublicationPanel.add(monthlyPub);
		
		
		String[] publicationInfo = {"Publication Title","type"};	//set the title of the monthly pub table

		publicationModel = new DefaultTableModel(null,publicationInfo){
			 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)      //set the table unit not editable
	            { 
	                return false; 
	            } 
		};//set the model and define the title for each column
		publicationTable = new JTable(publicationModel);				//create the table based on the model
		publicationTable.setPreferredScrollableViewportSize(new Dimension(348,200));// set the size of the table
		displayPublicationPanel.add(new JScrollPane(publicationTable));                //the key to show all the record
		for(String monSemID: controller.getMonthlyPublication())	//get all the ID of this month publication
		{
			Object[] monSeminar = {									//add the title and type to the table
					controller.getPublicationTitle(monSemID),
					controller.getPublicationType(monSemID)
			};
			publicationModel.addRow(monSeminar);
		}
		
		navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));	//set the panel for navigation(homepage and logout)
		navPanel.setBounds(0,10, 975, 40);		//set the position and size of the panel
		logout = new JButton("Logout");			//set the logout button
		homePage = new JButton("Home Page");	//set the Homepage button
		showCurrentSeminar = new JLabel("Current Seminar");
		
		logout.addActionListener(this);			//action listener for logout
		homePage.addActionListener(this);		//action listener for homepage
		navPanel.add(homePage);
		navPanel.add(logout);
		
		
		setLayout(null);	//set the default layout to null
		//add all the panel to the JFrame
		add(navPanel);
		add(displaySeminarPanel);
		add(displayPublicationPanel);
		add(addSeminarPanel);

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(1024, 768);		//set the default window size
	      
	   // setVisible(true);	//set the current frame visible

	}
	
	/**
	  * This method will handle the action trigger by each button click in the 
	  * {@link view.coordinatorUI.SeminarGui SeminarGUI}
	  * 
	  * <p>
	  * <strong>addSeminarButton</strong> listener
	  * This listener will get all the value of each textfield in the addSeminar panel and then save it and display in
	  * the seminar table after successfully adding and displaying a message.
	  * 
	  * <p>
	  * <strong>editButton</strong> listener
	  * This listener will update the value of the seminar ins the system(seminar table) with the new value in the
	  * textfield of addSeminar
	  * 
	  * <p>
	  * <strong>publishedButton</strong>
	  * This listener will published the selected seminar from the table, to all the group member along with the
	  * monthly publication
	  * 
	  * <p>
	  * <strong>cancelSeminarButton</strong>
	  * This listener will remove the seminar from the system(seminar table) and sends out an email to all the
	  * group member that the seminar is cancelled
	  * 
	  * <p>
	  * <strong>logout</strong> button listener
	  * It will call the {@link control.controller.GeneralUserController GeneralUSerController} <code>logout()</code> and 
	  * will change the frame to the {@link view.generalUserUI.HomePage HomePage}
	  * 
	  * <p>
	  * <strong>homePage</strong>
	  * This will change the current frame to the {@link view.generalUserUI.HomePage Homepage} of the 
	  * {@link model.user.AcademicStaff AcademicStaff}
	  * 
	  * <p>
	  * <strong>reset</strong>
	  * This will clear all the value in the addSeminar form
	  * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		if(arg0.getSource() == this.addSeminarButton){	//get the addSeminarButton listener
	
			//check whether the fields are empty, if empty display an error message
			if(topicText.getText().isEmpty() || timeText.getText().isEmpty() ||
					dateText.getText().isEmpty() || placeText.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,
						"All the seminar details must be entered before saving to the system",
					    "Error in Adding Seminar",
					    JOptionPane.PLAIN_MESSAGE);
			} else { 
				//on successful add of a seminar to a system, display a success message, then add the seminar info to table
				int uniqueSemID = controller.createSeminarID();
				if(controller.addSeminar(uniqueSemID, topicText.getText(), timeText.getText(),
											dateText.getText(),  placeText.getText())){
					JOptionPane.showMessageDialog(null,
							topicText.getText()+" is added successfully to the system",
						    "Updating Seminar Success",
						    JOptionPane.PLAIN_MESSAGE);
					
					Object[] newSeminar = {						//store all the addSeminar textfield value to Object array
							topicText.getText(),
							timeText.getText(),
							dateText.getText(),
							placeText.getText(),
							controller.getSeminarStatus(uniqueSemID)
					};
							seminarModel.addRow(newSeminar);
							reset();
							
				} else {	//if there is error in adding the seminar, then display an error message
					JOptionPane.showMessageDialog(null,
							"Error saving the seminar details. Please try again later",
						    "Error",
						    JOptionPane.PLAIN_MESSAGE);
					}
			}
		}
		
		if(arg0.getSource() == this.editButton){	//get the edit button listener
			//if the seminarTable is not empty and if only a row is selected, allow edit seminar
			if(seminarTable.getRowCount() > 0 && seminarTable.getSelectedRowCount() > 0){
			 for(int seminarID: controller.getGroupSeminarID()){	//get all the seminar ID of this user group
				 //then compare the selected seminar title with the title from the ID, if same allow edit
				 if(seminarModel.getValueAt(seminarTable.getSelectedRow(), 0).toString().equals(controller.getSeminarTopic(seminarID))){
			controller.editSeminar(seminarID, topicText.getText().toString(), timeText.getText().toString(),
									dateText.getText().toString(), placeText.getText().toString());
			//then update the value in the seminarTable
			seminarTable.setValueAt(topicText.getText(), seminarTable.getSelectedRow(), 0);
			seminarTable.setValueAt(timeText.getText(), seminarTable.getSelectedRow(), 1);
			seminarTable.setValueAt(dateText.getText(), seminarTable.getSelectedRow(), 2);
			seminarTable.setValueAt(placeText.getText(), seminarTable.getSelectedRow(), 3);
			
			//on successful update, show a message
			JOptionPane.showMessageDialog(null,
					seminarModel.getValueAt(seminarTable.getSelectedRow(), 0)+" is Updated Successfully",
				    "Updating Seminar Success",
				    JOptionPane.PLAIN_MESSAGE);
			reset();
				 }
			}
		} else { //if the seminar table is empty or no row is selected, show an error emssage
			JOptionPane.showMessageDialog(null,
					"No seminar is selected to edit",
				    "Error",
				    JOptionPane.PLAIN_MESSAGE);
		}
			
		}
		
		if(arg0.getSource() == this.publishButton){	//get the publishButton listener
			if(seminarTable.getSelectedRowCount() == 1){ //if only a row is selected, allow publish seminar
			 for(int seminarID: controller.getGroupSeminarID()){ //get all the group seminar ID
				 //compare the ID seminar title, with the row selected value title, if same, published the seminar ID detail
				 if(seminarModel.getValueAt(seminarTable.getSelectedRow(), 0).equals(controller.getSeminarTopic(seminarID)))
				 { controller.publishSeminar(seminarID);
			JOptionPane.showMessageDialog(null, //show a success published message
					seminarModel.getValueAt(seminarTable.getSelectedRow(), 0)+" is Published Successfully to all the" +
							" group member email.",
				    "Published Seminar Success",
				    JOptionPane.PLAIN_MESSAGE);
			
			seminarTable.setValueAt(controller.getSeminarStatus(seminarID), seminarTable.getSelectedRow(), 4);
			System.out.println(controller.getSeminarStatus(seminarID));
			reset();
			 	}
			 }
			}else { //if no row selected, then show an error message
				JOptionPane.showMessageDialog(null,
						"Please select a seminar to publish",
					    "Publication Seminar Error",
					    JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		if(arg0.getSource() == this.cancelSeminarButton){	//get the cancelSeminarButton listener
			
			if(seminarTable.getSelectedRowCount() == 1){ //if only a row is selected, allow canceling a seminar	
				String selectedTitle = seminarModel.getValueAt(seminarTable.getSelectedRow(), 0).toString();
				for(int seminarID: controller.getGroupSeminarID()){	//get all the seminar ID
					//if the ID title is same as the selected row title, allow cancel
					 if(selectedTitle.equals(controller.getSeminarTopic(seminarID)))
					 { if(controller.deleteSeminar(seminarID)){	//on successful deleting of seminar, show message
						 JOptionPane.showMessageDialog(null,
								 selectedTitle+" is Cancelled Successfully" +
											" and an email is send to all the group member",
								    "Deleteing Seminar Success",
								    JOptionPane.PLAIN_MESSAGE);
						 seminarModel.removeRow(seminarTable.getSelectedRow());
						 reset();
						 }
				 }
			}
		} else { //if no row is selected to cancel, show an error message
			JOptionPane.showMessageDialog(null,
					"Please select a seminar to cancel",
				    "Cancel Seminar Error",
				    JOptionPane.PLAIN_MESSAGE);
		}

				
		}
		
		if(arg0.getSource() == this.logout){	//get the logout button listener
			controller.loginout(Repository.currentUserName, null);	//logout the current user and switch frame
			this.dispose();		//hide the current frame and dispose it
			this.hide();
		}
		if(arg0.getSource() == this.homePage){	//get the homePage button listener
			this.dispose();	//hide and dispose the current frame 
			this.hide();
			PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ACADEMIC_STAFF, 0);//switch frame to homePage
		}
		if(arg0.getSource() == this.clearButton){	//get clearButton listener and set all textfield value to null
			reset();
		}
	}
	
	public void reset(){
		topicText.setText("");
		timeText.setText("");
		dateText.setText("");
		placeText.setText("");
	}
	@Override
	
	/**
	 * This method listen the action performed on the seminar Table. On selecting any row, it copy the value to the
	 * addSeminar textfield respectively for editing or publishing.
	 */
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		try		
		{
			if(seminarTable.getSelectedRowCount()>0)	//check if any row is selected
			{	//if selected, populate the textfield respectively
				topicText.setText(seminarModel.getValueAt(seminarTable.getSelectedRow(), 0).toString());
				timeText.setText(seminarModel.getValueAt(seminarTable.getSelectedRow(), 1).toString());
				dateText.setText(seminarModel.getValueAt(seminarTable.getSelectedRow(), 2).toString());
				placeText.setText(seminarModel.getValueAt(seminarTable.getSelectedRow(), 3).toString());
			} 
		} catch (Exception x){ //catch an exception
			
		}
	}
}
