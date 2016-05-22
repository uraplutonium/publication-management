package view.systemAdminUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import model.enumeration.StaffTitle;
import model.enumeration.UserRole;
import control.PMSystem;
import control.controller.AdministratorController;

/**<p>
 *  <strong>RegisterUser</strong> is a class that creates the UI for the <code>System Admin</code> to Register a new
 *   {@link model.user.AcademicStaff AcademicStaff} 
 * <p>
 *  To register a new {@link model.user.AcademicStaff AcademicStaff}, the <code>System Admin</code> must provide the
 *  Title, Research Group, Name, Email and Password. The system then generate the username.
 * <p>
 * 	<strong>RegisterUser</strong> class is the entry point for the <code>System Admin</code> to 
 * {@link view.systemAdminUI.AssignRole AssignRole}, {@link view.systemAdminUI.ChangeStatus ChangeStatus} and
 * {@link view.systemAdminUI.DeletePublication DeletePublication}
 *
 * @see view.systemAdminUI.AssignRole
 * @see view.systemAdminUI.ChangeStatus
 * @see view.systemAdminUI.DeletePublication 
 * @see control.controller.SystemAdminController
 * @see control.PMSystem
 * 
 */
public class RegisterUser extends JFrame implements ActionListener{


	private static final long serialVersionUID = 1L;
	
	// declare the controller 
	private AdministratorController controller;
	
	//declare the various elements for the UI
	private JPanel navigationPanel, bodyPanel;
	private JButton register, assignRole, changeStatus, deletePublication, submit, reset,logout;
	private JComboBox titleCombo,groupCombo;
	private JTextField name,email;
	private JPasswordField password;
	
	/**
	 *  <p>
	 *  Creates an instance of <code>RegisterUser</code> with the specified controller
	 *  {@link model.controller.AdministratorController AdministratorController}. This will display the Register User UI
	 *  
	 * 
	 *  @param controller   The controller which has the authority to control this class
	 *  @see {@link model.controller.AdministratorController AdministratorController}
	 */
	
	public RegisterUser(AdministratorController controller){
		super("Publication Management System: Administrator");
		
		this.controller=controller;	//set the controller for the System Admin to access the AdministratorController
	
		//Add two main panel, navigation panel for the navigation and the body panel for the main contents
		navigationPanel = new JPanel();
		navigationPanel.setPreferredSize(new Dimension(900,60));
		
		bodyPanel = new JPanel();
		bodyPanel.setPreferredSize(new Dimension(900, 660));
		
		TitledBorder titled;  
		titled = BorderFactory.createTitledBorder("Register New User"); 
		bodyPanel.setBorder(titled);
		bodyPanel.setLayout(null);
		
		
		
		// declare buttons for the navigation panel with their size
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
		
		// add the buttons to the panel
		navigationPanel.add(register);
		navigationPanel.add(assignRole);
		navigationPanel.add(changeStatus);
		navigationPanel.add(deletePublication);
		navigationPanel.add(logout);

		//Content of the body Panel
		//create a string of array for the label and for each, create a respective form elements
		String[] registerLabels = {"Title","Name","Group","Email","Password"};
		for(int i=0;i<registerLabels.length;i++)
		{

			if(i==0){
				// add the Title label and assign it to the title combo box
				JLabel rl = new JLabel(registerLabels[i], JLabel.TRAILING);
				rl.setBounds(120, 120, 70, 20);
				bodyPanel.add(rl);
				
				titleCombo = new JComboBox();
				//elements of the title combo box
				StaffTitle[] title = new StaffTitle[]{StaffTitle.MS,StaffTitle.MRS,StaffTitle.MR,StaffTitle.MISS,
						StaffTitle.MASTER,StaffTitle.DOCTOR,StaffTitle.PROFESSOR};
		
				for(int j=0;j<title.length;j++)
					titleCombo.addItem(title[j]); // adding the element to the combo box
				rl.setLabelFor(titleCombo);
				titleCombo.setBounds(260, 120, 300, 23);
				// add the title combo box to the body panel
				bodyPanel.add(titleCombo);
				
			} else if(i==2){
				// add the research group label and assign it to the group combo box
				JLabel rl = new JLabel(registerLabels[i], JLabel.TRAILING);
				rl.setBounds(120, 160, 70, 20);
				bodyPanel.add(rl);
				
				groupCombo = new JComboBox();
				for(String groupName: PMSystem.groupName)
					groupCombo.addItem(groupName);			//get the group name from the PMSystem class and add to combo
	
				rl.setLabelFor(groupCombo);
				groupCombo.setBounds(260, 160, 300, 23);
				bodyPanel.add(groupCombo);	// add group combo box to the body panel
			} else if(i==1){
				// add the name label and the textfield for the name
				JLabel rl = new JLabel(registerLabels[i], JLabel.TRAILING);
				rl.setBounds(120, 200, 70, 20);
				bodyPanel.add(rl);
				
				name = new JTextField(45);	
				rl.setLabelFor(name);
				name.setBounds(260, 200, 300, 23);
				bodyPanel.add(name);	// add the textfield to the body panel
			} else if(i==3){
				// add the email label and the textfield for the email
				JLabel rl = new JLabel(registerLabels[i], JLabel.TRAILING);
				rl.setBounds(120, 240, 70, 20);
				bodyPanel.add(rl);
				
				email = new JTextField(45);	
				rl.setLabelFor(email);
				email.setBounds(260, 240, 300, 23);
				bodyPanel.add(email);	// add the textfield to the body panel
			} else {
				// add the label for password and a passwordfield
				JLabel rl = new JLabel(registerLabels[i], JLabel.TRAILING);
				rl.setBounds(120, 280, 70, 20);
				bodyPanel.add(rl);
				
				password = new JPasswordField(25);	
				rl.setLabelFor(password);
				password.setBounds(260, 280, 300, 23);
				bodyPanel.add(password);	// add passwordfield to the body panel
			}
			
		}
		
		submit = new JButton("Submit");	//set the submit button
		submit.setBounds(260, 350, 140, 23);
		bodyPanel.add(submit);	
		
		reset = new JButton("Reset");	//set the reset button
		reset.setBounds(410, 350, 140, 23);
		bodyPanel.add(reset);
		
		
		setLayout(new FlowLayout());	//set the layout and add the two panel to the jframe
		add(navigationPanel);		
		add(bodyPanel);		
		
		setSize(1024, 768);				//size of the jframe
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//add a listener for all the buttons
		register.addActionListener(this);
		assignRole.addActionListener(this);
		changeStatus.addActionListener(this);
		deletePublication.addActionListener(this);
		logout.addActionListener(this);
		
		submit.addActionListener(this);
		reset.addActionListener(this);
		
	}
	
	 /**
	  * <code>setVisibleUI</code> will make the {@link view.systemAdminUI.RegisterUser RegisterUSer} frame 
	  * visible or hidden based on the parameter passed to it from the caller.
	  * @param val This is a boolean which will display the frame if true else hide the frame
	  */
	 public void setVisibleUI(boolean val){
		 	setVisible(val);
	 }
	 /**
	  * This method will handle the action trigger by each button click in the 
	  * {@link view.systemAdminUI.RegisterUser RegisterUser} UI
	  * 
	  * <p>
	  * <strong>submit</strong> button listener
	  * It will catch the action of the submit button, which inturn will gather the information entered in the form 
	  * fields and then save it.
	  * 
	  * <p>
	  * <strong>reset</strong> button listener
	  * This will clear all the fields in the form
	  * 
	  * <p>
	  * <strong>logout</strong> button listener
	  * It will call the {@link control.controller.GeneralUserController GeneralUSerController} <code>logout()</code> and 
	  * will change the frame to the {@link view.generalUserUI.HomePage HomePage}
	  * 
	  * <p>
	  * <strong>assignRole</strong> button listener
	  * It will change the current frame to {@link view.systemAdminUI.AssignRole AssignRole}
	  * 
	  *  <p>
	  *  <strong>changeStatus</strong> button listener
	  *  It will change the current frame to {@link view.systemAdminUI.ChangeStatus ChangeStatus}
	  *  
	  *  <p>
	  *  <strong>deletePublication</strong> button listener
	  *  It will change the current frame to the {@link view.systemAdminUI.DeletePublication DeletePublication}
	  *  
	  */
	 public void actionPerformed(ActionEvent e){
		 if(e.getSource() == this.reset){		// get the reset button listener and set all the forms value to null
			 name.setText(null);
			 email.setText(null);
			 password.setText(null);
			 groupCombo.setSelectedIndex(0);	// set the combo box selected index to 0
			 titleCombo.setSelectedIndex(0);
		 }
		 
		 else if(e.getSource() == this.submit){	// get the submit button listener and save the value to the file
			 int userGroup = PMSystem.groupName.indexOf(groupCombo.getSelectedItem());
			 
			 //break the Name entered by the user and then get the first character of each word to form his username
			 String nameToParse = name.getText().trim();		//get the Name textfield value						
			 String[] nameTokens = nameToParse.split(" ");	//break the Name using whitespaces and 
			 												//store each word of the Name into a string array
		  if(!nameToParse.equals("") && !email.getText().isEmpty() && password.getPassword().length!=0){
			System.out.println("hhhhhhhhhhhhh");
			 String prefix = "";
			 for(int i=0;i<nameTokens.length;i++)
			 		prefix = prefix+nameTokens[i].substring(0,1);	//get the first character of each word
			 
			 // call the createUserName() and pass the prefix of the word combination, this method will return the username
			 String systemGeneratedUserName = controller.createUserName(prefix.toLowerCase());
			 
			 //Add user
			 if(controller.addStaff(systemGeneratedUserName, String.valueOf(password.getPassword()), 
					 			email.getText(), name.getText(), (StaffTitle) titleCombo.getSelectedItem(), userGroup+1))
				 	
			 {
				 JOptionPane.showMessageDialog(null,
							"Successfully Added with Username: "+systemGeneratedUserName+ ". Copy and save the username generated.",
						    "Registration Success",
						    JOptionPane.PLAIN_MESSAGE);
			 }
			 else
				 JOptionPane.showMessageDialog(null,
							"Error in registering the user. addStaff() in SystemAdminController is not working",
						    "Registration Error",
						    JOptionPane.PLAIN_MESSAGE);
			 
		 }
		  else{
		 	      JOptionPane.showMessageDialog(null, "Please provide all the details");
		  }
		 }
		 else if(e.getSource() == this.logout){	// get the logout listener and call the logout function to logout
			 controller.loginout("Admin", null);
			// PMSystem.getSystemInstance().switchCurrentFrame(UserRole.GENERAL_USER, 0);
			 this.hide();
			 this.dispose();
			 
		 }
		 
		 else if(e.getSource() == this.assignRole){ //get the assignRole listener and change the frame
			 setVisibleUI(false);
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 1); 
		 }
		 
		 else if(e.getSource() == this.changeStatus){	//get the changeStatus listener and change the frame
			 setVisibleUI(false);
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 2);
		 }
		 
		 else if(e.getSource() == this.deletePublication){	//get the deletePublication listener and change the frame
			 setVisibleUI(false);
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 3);
		 }
	 }
	 

}
