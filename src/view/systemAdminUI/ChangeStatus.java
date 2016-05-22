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
import javax.swing.border.TitledBorder;

import model.enumeration.UserRole;

import control.PMSystem;
import control.controller.AdministratorController;

/**
 * <strong>ChangeStatus</status> class creates the UI for changing the status of user to active/inactive by the 
 * System Admin
 * 
 * <p>
 * This class will get all the active/inactive user based on user choice, then select the respective user whose 
 * status have to be updated.
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
public class ChangeStatus extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// declare various elements for the forms
	private JPanel navigationPanel, bodyPanel;
	private JButton register, assignRole, changeStatus, deletePublication, submit, reset, logout;
	private JLabel activeLabel, memberLabel, confirmation;
	private JComboBox activeCombo,memberCombo,userNameHiddenCombo;
	
	// declare the controller 
	private AdministratorController controller;
	
	/**
	 * Creates an instance of <strong>changeStatus</strong> with the specified controller
	 * {@link model.controller.AdministratorController AdministratorController}. This will display the Change Status UI 
	 * 
	 *@param controller The controller which had the authority to control this class
	 * @see control.controller.AdministratorController
	 */
	
	public ChangeStatus(AdministratorController controller){
		super("Publication Management System");
		
		this.controller = controller;	//set the controller for the System Admin to access the AdministratorController
		
		//Add two main panel, navigation panel for the navigation and the body panel for the main contents
		navigationPanel = new JPanel();
		navigationPanel.setPreferredSize(new Dimension(900,60));
		
		bodyPanel = new JPanel();
		bodyPanel.setPreferredSize(new Dimension(900, 660));

		TitledBorder titled;  
		titled = BorderFactory.createTitledBorder("Change status of user"); 
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
		
		activeLabel = new JLabel("Status");				//set Status label and add to the body panel
		activeLabel.setBounds(120, 120, 70, 23);
		bodyPanel.add(activeLabel);
		
		activeCombo = new JComboBox();					//set activeCombo add populate with three elements
		String[] active = {"--none--","Active","Inactive"};
		for(int k=0;k<active.length;k++)
			activeCombo.addItem(active[k]);				//add each element of the array active to active Combo
		activeLabel.setLabelFor(activeCombo);
		activeCombo.setBounds(260, 120, 200, 23);
		bodyPanel.add(activeCombo);						//add activeCombo to bodyPanel
		
		memberLabel = new JLabel("Member");				//set the Member label and add to the body panel
		memberLabel.setBounds(120,160,70,23);
		bodyPanel.add(memberLabel);
		
		memberCombo = new JComboBox();					//set the memberCombo and dont populate it yet
		memberCombo.setBounds(260,160,200,23);
		bodyPanel.add(memberCombo);						//add memberCombo to the body panel
		
		userNameHiddenCombo = new JComboBox();			//set the hiddenCombo to store the username with respect 
														//to memberCombo
		
		submit = new JButton("Update Status");			//set the Update Status button
		submit.setBounds(260,200,140,23);
		bodyPanel.add(submit);							//add the button to the panel
		reset = new JButton("Reset");					//set the Reset button
		reset.setBounds(410,200,140,23);
		bodyPanel.add(reset);							//add the reset button to the panel
		
		confirmation = new JLabel();					//set the confirmation button
		confirmation.setBounds(260, 280, 600, 23);
		bodyPanel.add(confirmation);					//add the confirmation button to the panel
		
		setLayout(new FlowLayout());					//set the default layout of the JFrame
		add(navigationPanel);							//add the navigationPanel to the JFrame
		add(bodyPanel);									//add the bodyPanel to the JFrame
		
		setSize(1024, 768);								//set the default window size
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//add the action listener for all the button
		register.addActionListener(this);
		assignRole.addActionListener(this);
		changeStatus.addActionListener(this);
		deletePublication.addActionListener(this);
		logout.addActionListener(this);
		
		activeCombo.addActionListener(this);//this listener is for the combo box to populate the memberCombo

		submit.addActionListener(this);
		reset.addActionListener(this);

	}
	

	/**
	  * <code>setVisibleUI</code> will make the {@link view.systemAdminUI.ChangeStatus ChangeStatus} frame 
	  * visible or hidden based on the parameter passed to it from the caller.
	  * @param val This is a boolean which will display the frame if true else hide the frame
	  */

	public void setVisibleUI(boolean val){
			setVisible(val);
		}
	/**
	 * This method will handle the action trigger by each button click in the 
	 * {@link view.systemAdminUI.ChangeStatus ChangeStatus} UI
	 * 
	 * <p>
	 * <strong>reset</strong> button listener
	 * It will set the selected index of the groupCombo to 0 and remove all the elements in memberCombo and hiddenCombo
	 * 
	 * <p>
	 * <strong>activeCombo</strong> button listener
	 * It will populate the memberCombo based on the selected value.
	 * 
	 * <p>
	 * <strong>submit</strong> button listener
	 * It will update the status of the selected user to the opposite of the current status
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
	 * <p>
	 *  <strong>deletePublication</strong> button listener
	 *  It will change the current frame to the {@link view.systemAdminUI.DeletePublication DeletePublication}
	 *<p>
	 * <strong>assignRole</strong> button listener
	 * It will change the current frame to {@link view.systemAdminUI.AssignRole AssignRole}
	 * 
	 */
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == this.reset){	//get the reset listener and set selected activeCombo to index 0 and
			activeCombo.setSelectedIndex(0);	// remove all elements of memberCombo and activeCombo
			memberCombo.removeAllItems();
			userNameHiddenCombo.removeAllItems();
		}
		
		if(e.getSource() == this.activeCombo){	//get the activeCombo listener
			memberCombo.removeAllItems();		//remove all elements in memberCombo
			userNameHiddenCombo.removeAllItems();	//remove all elements in hiddenCombo
			if(activeCombo.getSelectedItem().toString() == "Active"){	//if select active, populate memberCombo
				
				for(String userName: controller.getAllStaffUserName()){
					if(controller.getStaffStatus(userName)){
						userNameHiddenCombo.addItem(userName);
						memberCombo.addItem(controller.getStaffFullName(userName));	//add active member to memberCombo
						
						}
					}
				
				}
			if(activeCombo.getSelectedItem().toString() == "Inactive"){	//if select inactive, populate memberCombo
					for(String userName: controller.getAllStaffUserName()){
						if(!controller.getStaffStatus(userName)){
							userNameHiddenCombo.addItem(userName);
							memberCombo.addItem(controller.getStaffFullName(userName)); //add inactive member to memberCombo
							
						}
					}
				} 
				
			}
		
		
		if(e.getSource() == this.submit){	//get submit listener
			if(!activeCombo.getSelectedItem().toString().equals("--none--") && memberCombo.getItemCount()!=0){
				String statusValue;
				if(activeCombo.getSelectedIndex() == 1)	//get the selectedIndex
					statusValue = activeCombo.getItemAt(2).toString(); //set statusValue to Inactive
				else
					statusValue = activeCombo.getItemAt(1).toString();	//set statusValue to active
				//if user status is update successfully, display the success message
				if(controller.changeStatus(userNameHiddenCombo.getItemAt(memberCombo.getSelectedIndex()).toString()))
					confirmation.setText("Successfully changing the status of " + memberCombo.getSelectedItem().toString()+ " to "+statusValue);
				else
					confirmation.setText("Error changing the status of "+ memberCombo.getSelectedItem().toString()+ " to "+statusValue);
				}
			else{
				if(activeCombo.getSelectedItem().toString().equals("--none--")){
					JOptionPane.showMessageDialog(null, "Choose the status and then the user to change the status");
				}
				else if(memberCombo.getItemCount()==0){
					JOptionPane.showMessageDialog(null, "There is no member "+activeCombo.getSelectedItem().toString());
				}
				
			}
		}
		
		if(e.getSource() == this.register){		//get register listener
			setVisibleUI(false);				//hide current frame
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 0); //change frame to Register frame
			 
		}
		if(e.getSource() == this.assignRole){	//get assignRole listener
			 setVisibleUI(false);				
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 1);//change frame to Assign Role
		 }
		 
		 if(e.getSource() == this.changeStatus){ //get changeStatus listener and do nothing
			 
		 }
		 
		 if(e.getSource() == this.deletePublication){	//get deletePublication listener
			 setVisibleUI(false);
			 PMSystem.getSystemInstance().switchCurrentFrame(UserRole.ADMINISTRATOR, 3);//change frame to Delete Pub
		 }
		 if(e.getSource() == this.logout){				//get logout listener
			 controller.loginout("Admin", null);		//call logout() and then logout the user
			 this.dispose();
			 this.hide();
			// PMSystem.getSystemInstance().switchCurrentFrame(UserRole.GENERAL_USER, 0);
		 }
	}

}
