package view.systemAdminUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.table.DefaultTableModel;

import model.enumeration.UserRole;

import control.PMSystem;
import control.controller.AdministratorController;

/**
 * <strong>DeletePublication</strong> class creates the UI for deleting a publication for the System Admin
 * <p>
 * Using the {@link model.user.AcademicStaff AcademicStaff} username, publication can be fetch from the 
 * {@link control.repository Repository} and then delete.
 * <p>
 * This class also provides access to other Sytem Admin UI
 * 
 * @see view.systemAdminUI.RegisterUser
 * @see view.systemAdminUI.ChangeStatus
 * @see view.systemAdminUI.DeletePublication 
 * @see control.controller.SystemAdminController
 * @see control.PMSystem
 */
public class DeletePublication extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	//declare the form UI elements
	private JPanel navigationPanel, bodyPanel, pubPanel, filterPanel;
	private JButton register, assignRole, changeStatus, deletePublication, submit, delete, logout;
	private JLabel userNameLabel;
	private JTextField userID;
	private JTable pubTable;
	private DefaultTableModel model;
	//set the controller for the actions
	private AdministratorController controller;
	
	/**
	 * Creates an instance of <strong>deletePublication</strong> with the specified controller
	 * {@link model.controller.AdministratorController AdministratorController}. This will display the Delete Publication
	 * UI
	 * 
	 * @param controller The controller which had the authority to control this class
	 * @see control.controller.AdministratorController
	 */
	public DeletePublication(AdministratorController controller){
		super("Publication Management System");
		
		this.controller = controller;		//set the controller
		
		//Add two main panel, navigation panel for the navigation and the body panel for the main contents
		navigationPanel = new JPanel();
		navigationPanel.setPreferredSize(new Dimension(900,80));
		
		bodyPanel = new JPanel();
		bodyPanel.setPreferredSize(new Dimension(1000,500));
		
		filterPanel = new JPanel();
		filterPanel.setLayout(null);
		filterPanel.setPreferredSize(new Dimension(900,120));
        
		TitledBorder titled;  
		titled = BorderFactory.createTitledBorder("Delete staff publication"); 
		filterPanel.setBorder(titled);
		bodyPanel.setLayout(null);
		
		//Buttons in the navigation Panel
		register = new JButton("Register");
		register.setPreferredSize(new Dimension(150,40));	
		assignRole = new JButton("Assign Role");
		assignRole.setPreferredSize(new Dimension(150,40));
		changeStatus = new JButton("Change Status");
		changeStatus.setPreferredSize(new Dimension(150,40));
		deletePublication = new JButton("Delete Publication");
		deletePublication.setPreferredSize(new Dimension(150,40));
		logout = new JButton("Logout");
		logout.setPreferredSize(new Dimension(150,40));
		
		//Adding Buttons to the navigation Panel
		navigationPanel.add(register);
		navigationPanel.add(assignRole);
		navigationPanel.add(changeStatus);
		navigationPanel.add(deletePublication);
		navigationPanel.add(logout);
		
		userNameLabel = new JLabel("User Name");		//set userNameLabel and add to the body panel
		userNameLabel.setBounds(120,30, 70, 23);
		filterPanel.add(userNameLabel);
		
		userID = new JTextField();						//set the userID textfield and add to the body panel
		userID.setBounds(260, 30, 200, 23);
		filterPanel.add(userID);
		
		submit = new JButton("Get Publication");		//set the submit button and add to the body panel
		submit.setBounds(500,30,140,23);
		filterPanel.add(submit);

		
		pubPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));	//create a new panel pubPanel for the table
		String[] columnName = {"Title","Authors","Type","Upload Date"};	//column name for the table

		 model = new DefaultTableModel(null,columnName){
			 public boolean isCellEditable(int row, int column)      //set the table unit not editable
	            { 
	                return false; 
	            } 
		 };	//set the model for the JTable
		 pubTable =  new JTable(model);						//set the table
		 pubTable.setPreferredScrollableViewportSize(new Dimension(880,340));//set the table
		 pubTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 pubPanel.add(new JScrollPane(pubTable));			//add the table to pubPanel
		 
		 delete = new JButton("Delete");		//set delete button and add to the pubPanel
		 pubPanel.add(delete);
		 delete.addActionListener(this);		//set the delete button listener
		 	
		setLayout(new FlowLayout());			//set the default layout for the JFrame
		add(navigationPanel);					//add the navigationPanel to the JFrame
		add(filterPanel);
		add(bodyPanel);							//add the bodyPanel to the JFrame
		pubPanel.setBounds(1, 20, 940, 420);
		bodyPanel.add(pubPanel);				//add the pubPanel to the body Panel
		
		setSize(1024, 768);						//set the window size
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//set the listener for all the buttons
		register.addActionListener(this);
		assignRole.addActionListener(this);
		changeStatus.addActionListener(this);
		deletePublication.addActionListener(this);
		logout.addActionListener(this);
		submit.addActionListener(this);

	}

	/**
	  * <code>setVisibleUI</code> will make the {@link view.systemAdminUI.DeletePublication DeletePublication} frame 
	  * visible or hidden based on the parameter passed to it from the caller.
	  * @param val This is a boolean which will display the frame if true else hide the frame
	  */	
	 
	 public void setVisibleUI(boolean val){
		setVisible(val);
	 }
	 
	 /**
	  * This method will handle the action trigger by each button click in the 
	  * {@link view.systemAdminUI.DeletePublication DeletePublication} UI
	  * 
	  * <p>
	  * <strong>submit</strong> button listener
	  * This will get all the publication based on the username input and then populate the pubTable
	  * 
	  * <p>
	  * <strong>delete</strong> button listener
	  * This will delete the selected {@link model.publication.Publication Publication} from the system and the pubTable
	  * 
	  * <p>
	  * <strong>logout</strong> button listener
	  * It will call the {@link control.controller.GeneralUserController GeneralUSerController} <code>logout()</code> and 
	  * will change the frame to the {@link view.generalUserUI.HomePage HomePage}
	  * 
	  * <p>
	  * <strong>register</strong> button listener
	  * It will change the current frame to {@link view.systemAdminUI.RegisterUser RegisterUser}
	  *  
	  *  <p>
	  *  <strong>changeStatus</strong> button listener
	  *  It will change the current frame to {@link view.systemAdminUI.ChangeStatus ChangeStatus}
	  * 
	  * <p>
	  * <strong>assignRole</strong> button listener
	  * It will change the current frame to {@link view.systemAdminUI.AssignRole AssignRole}
	  *
	  */
	 
	 public void actionPerformed(ActionEvent e){
		 
		 if(e.getSource() == this.submit){	//get the submit button listener
			 while(model.getRowCount() > 0)	//if there is something in the table, remove it
				 model.removeRow(0);
			 
			if(!userID.getText().isEmpty()){	//check whether the username textfield is filled or not
				if(controller.checkUserName(userID.getText())){ //check whether the username enter exist
			 for(String staffPubID: controller.getPublicationID(userID.getText(), -1, -1)){ //get all the username pub
					Object[] row = { 
						controller.getPublicationTitle(staffPubID),
						controller.getPublicationAuthorSet(staffPubID),
						controller.getPublicationType(staffPubID),
						controller.getPublicationDate(staffPubID)
						};
					model.addRow(row);	//add all the username publication into the table
					}
				}else {	//if the username didnt exist, display an error message
					JOptionPane.showMessageDialog(null,
					 		userID.getText()+ " did not exist in the system",
					 		"Error",
					 		JOptionPane.PLAIN_MESSAGE);
				}
		 	} else {	//if the username textfield is blank, display an error message
		 		JOptionPane.showMessageDialog(null,
				 		"Please enter the username to get the publication list",
				 		"Error",
				 		JOptionPane.PLAIN_MESSAGE);
		 	}
		 }
		 if(e.getSource() == this.delete){	//get the delete button listener
			 if(pubTable.getRowCount()>0){	//if the pubTable is not empty
				 //get all the publicationID of the enter username and for each,
				 //compare the selected row first column value(title) with pubtitle obtain using getPublicationTitle()
			 	for(String staffPublicationID: controller.getPublicationID(userID.getText(), -1, -1)){
					 if(model.getValueAt(pubTable.getSelectedRow(), 0) == controller.getPublicationTitle(staffPublicationID))
						 {
						 	//if the title match, then delete the publication using the pubID
						 	if(controller.deletePublication(staffPublicationID, controller.getPublicationUploaderUserName(staffPublicationID)))
						 	{	//display a success message and remove from the table
						 		System.out.println("Successfully deleted "+ controller.getPublicationTitle(staffPublicationID));
						 		JOptionPane.showMessageDialog(null,
						 		model.getValueAt(pubTable.getSelectedRow(), 0)+" : deleted from the system",
						 		"Publication Delete Success",
						 		JOptionPane.PLAIN_MESSAGE);
						 		model.removeRow(pubTable.getSelectedRow());
						 	}
	
						 }
			 } 
			} else { 	//if table is empty, show an error message
				JOptionPane.showMessageDialog(null,
				 		"Please select a publication to delete OR get the publication using the username",
				 		"Error",
				 		JOptionPane.PLAIN_MESSAGE);
			}
		 }
		 
		 if(e.getSource() == this.register){	//get the register button listener
			 setVisibleUI(false);				//hide the current frame
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 0); //switch frame to Register frame 
		 }
		 
		 if(e.getSource() == this.deletePublication){	//get the deletePublication button listener and do nothing
			
		 }
		 
		 if(e.getSource() == this.changeStatus){	//get the changeStatus button listener
			 setVisibleUI(false);					//hide the current frame
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 2);//switch frame to Change Status frame
		 }
		 
		 if(e.getSource() == this.assignRole){	//get the assignRole button listener
			 setVisibleUI(false);				//hide the current frame
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 1);//switch frame to Assign Role Frame
		 }
		 if(e.getSource() == this.logout){			//get the logout button listener
			 controller.loginout("Admin", null);	//logout the admin
			 this.dispose();
			 this.hide();
		 }
		 
	 }

}

