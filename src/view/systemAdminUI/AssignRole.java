package view.systemAdminUI;

import java.awt.Dimension;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.enumeration.UserRole;

import control.PMSystem;
import control.controller.AdministratorController;

/**
 * <p>
 * <strong>AssignRole</strong> class create the UI for assigning the co-ordinator role to academic staff by the 
 * System Admin
 * 
 * <p>
 * By default when a user is created it is {@link model.user.AcademicStaff AcademicStaff}, and every 
 * {@link model.group.Group ResearchGroup} must have one co-ordinator. When assigning a co-ordinator for a
 * {@link model.group.Group ResearchGroup}, if the group already have a co-ordinator, the existing co-ordinator is
 * change to an {@link model.user.AcademicStaff AcademicStaff} and the new selected  
 * {@link model.user.AcademicStaff AcademicStaff} becomes the co-ordinator.
 * 
 * <p>
 * This class also provide an entry point for the rest of the <code>System Admin</code> UI.
 * 
 * @see view.systemAdminUI.RegisterUser
 * @see view.systemAdminUI.ChangeStatus
 * @see view.systemAdminUI.DeletePublication 
 * @see control.controller.SystemAdminController
 * @see control.PMSystem
 *
 */

public class AssignRole extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// declare various elements for the forms
	private JPanel navigationPanel, bodyPanel;
	private JButton register, assignRole, changeStatus, deletePublication, submit, reset,logout;
	private JLabel groupLabel,memberLabel,confirmation;
	private JComboBox groupCombo, memberCombo,userNameHiddenCombo;
	// declare the controller 
	private AdministratorController controller;
	private Set<String> groupMemberSet;
	
	/**<p>
	 * Creates an instance of <strong>AssignRole</strong> with the specified controller
	 * {@link model.controller.AdministratorController AdministratorController}. This will display the Assign Role UI 
	 * 
	 * @param controller The controller which had the authority to control this class
	 * @see control.controller.AdministratorController
	 */
	public AssignRole(AdministratorController controller){
		
		super("Publication Management System");
		
		this.controller = controller; //set the controller for the System Admin to access the AdministratorController
		
		//Add two main panel, navigation panel for the navigation and the body panel for the main contents
		navigationPanel = new JPanel();
		navigationPanel.setPreferredSize(new Dimension(900,60));
		
		bodyPanel = new JPanel();
		bodyPanel.setPreferredSize(new Dimension(900, 660));

		TitledBorder titled;  
		titled = BorderFactory.createTitledBorder("Assign Co-ordinator for a research group"); 
		bodyPanel.setBorder(titled);
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
		
				groupLabel = new JLabel("Group");			//create a group label and add to the bodyPanel
				groupLabel.setBounds(120, 120, 70, 23);
				bodyPanel.add(groupLabel);
					
				groupCombo = new JComboBox();				//create a group combo, populate the combo element
				groupCombo.addItem("--Select Group--");
				for(String groupName: PMSystem.groupName)
					groupCombo.addItem(groupName);			
				
				groupLabel.setLabelFor(groupCombo);
				groupCombo.setBounds(260,120,300,23);
				bodyPanel.add(groupCombo);					//add the group combo to the body panel
				
				memberLabel = new JLabel("Member");			//create a member label and add to the body panel
				memberLabel.setBounds(120, 160, 70, 23);
				bodyPanel.add(memberLabel);
				
				memberCombo = new JComboBox();
				memberCombo.addItem("--No Member--");		//set the default selected combo box
				
				memberLabel.setLabelFor(memberCombo);
				memberCombo.setBounds(260,160,300,23);
				bodyPanel.add(memberCombo);
				
				
				submit = new JButton("Assign Co-Ordinator");	//create the assign button
				submit.setBounds(260,200,185,23);
				bodyPanel.add(submit);
				reset = new JButton("Reset");
				reset.setBounds(450,200,110,23);
				bodyPanel.add(reset);
				
				userNameHiddenCombo = new JComboBox();			//create a hidden jcombo box to store the username
																//since the member combo have to display the Name
				
				
				confirmation = new JLabel();					//successful operation will be display in this label
				confirmation.setBounds(120, 350, 600, 23);
				bodyPanel.add(confirmation);
				
				setLayout(new FlowLayout());					//set the default layout for the JFrame
				add(navigationPanel);							//add the navigationPanel to the JFrame
				add(bodyPanel);									//add the bodyPanel to the JFrame
				
				setSize(1024, 768);								//set the size of the window
				
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(true);
				
				//create a listener for all the buttons in the frame
				register.addActionListener(this);
				assignRole.addActionListener(this);
				changeStatus.addActionListener(this);
				deletePublication.addActionListener(this);
				logout.addActionListener(this);
				
				submit.addActionListener(this);
				reset.addActionListener(this);
				groupCombo.addActionListener(this);	//we use this listener to populate the member combo box
				
		
	}
	
	/**
	  * <code>setVisibleUI</code> will make the {@link view.systemAdminUI.AssignRole AssignRole} frame 
	  * visible or hidden based on the parameter passed to it from the caller.
	  * @param val This is a boolean which will display the frame if true else hide the frame
	  */
	 
	 public void setVisibleUI(boolean val){		
		setVisible(val);
	 }
	 
	 /**
	  * This method will handle the action trigger by each button click in the 
	  * {@link view.systemAdminUI.AssignRole AssignRole} UI
	  * 
	  * <p>
	  * <strong>reset</strong> button listener
	  * It will reset the combo box to index 0
	  * 
	  * <p>
	  * <strong>groupCombo</strong> button listener
	  * It will send the group number of the selected group from the combo which inturn will be used to select the
	  * member of the group to populate the member combo
	  * 
	  * <p>
	  * <strong>submit</strong> button listener
	  * It will submit the value of the form elements to the repository which will update the map and save to a file
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
	  *  <p>
	  *  <strong>deletePublication</strong> button listener
	  *  It will change the current frame to the {@link view.systemAdminUI.DeletePublication DeletePublication}
	  */
	 public void actionPerformed(ActionEvent e){
		 
		 if(e.getSource() == this.reset){		//get the reset listener and set the selected index of the combo to 0
			 groupCombo.setSelectedIndex(0);
			 memberCombo.setSelectedIndex(0);
			 confirmation.setText("");
		 }
		 
		 else if(e.getSource() == this.groupCombo){	//get the groupCombo listener
			 	memberCombo.removeAllItems();	
			 	//the hidden combo will store the username
			 	userNameHiddenCombo.removeAllItems();
			 	if(groupCombo.getSelectedItem().toString().equals("--Select Group--")){
			 		
			 	}else{
				int userGroup = PMSystem.groupName.indexOf(groupCombo.getSelectedItem()); //store selected index
				
				groupMemberSet = controller.getMemberOfGroup(userGroup+1); //get the group member
				if(groupMemberSet.size()>0){	
				for(String memberName: groupMemberSet){
					memberCombo.addItem(controller.getStaffFullName(memberName));	//populate the memberCombo with Name
					userNameHiddenCombo.addItem(memberName);						//add the hiddenCombo with username
					}	
				} else {
					memberCombo.addItem("--No Member--");
				}
			   }
		 }
		 
		 else if(e.getSource() == this.submit){	//get the submit listener
		    if(!groupCombo.getSelectedItem().toString().equals("--Select Group--") && !memberCombo.getSelectedItem().toString().equals("--No Member--")){
			 //if assign co-ordinator operation is success, display a success dialogue message
				 if(controller.assignCoordinator(userNameHiddenCombo.getItemAt(memberCombo.getSelectedIndex()).toString())){
					 confirmation.setText(memberCombo.getSelectedItem().toString()
							 +" is the new Co-ordinator of "+groupCombo.getSelectedItem().toString()+" group");
				 } else {	// or a failure dialogue message
					 confirmation.setText(memberCombo.getSelectedItem().toString()+" is already the co-ordinator of "+
							 groupCombo.getSelectedItem().toString()+" group");
				 }
			} 
		    else{
		    	
		    	if(groupCombo.getSelectedItem().toString().equals("--Select Group--")){
		    		JOptionPane.showMessageDialog(null, "Please select the group");	
		    	}
		    	else if(memberCombo.getSelectedItem().toString().equals("--No Member--")){
		    	    JOptionPane.showMessageDialog(null, "There is no member in this group");
		    	}
		    }
		}
		else if(e.getSource() == this.register){	//get the register listener
			 setVisibleUI(false);		//hide the current frame
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 0); //show the register frame
		 }
		 
		 if(e.getSource() == this.assignRole){	//get the assignRole listener and do nothing
			 
		 }
		 
		 if(e.getSource() == this.changeStatus){	//get the changeStatus listener
			 setVisibleUI(false);	//hide the current frame
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 2); // show the changeStatus frame
		 }
		 
		 if(e.getSource() == this.deletePublication){	//get the deletePublication listener
			 setVisibleUI(false);	//hide the current frame
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 3);	//show the deletePub frame
		 }
		 
		 if(e.getSource() == this.logout){		//get the logout listener
			 controller.loginout("Admin", null);	// logout the Admin
			 this.dispose();
			 this.hide();
		 }
		 
	 }

}
