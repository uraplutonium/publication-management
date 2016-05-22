package control.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import control.Repository;

import model.enumeration.StaffTitle;
import model.group.Group;
import model.user.AcademicStaff;
	/**
	 * 
	 * <strong>AdministratorController</strong> class control all the actions that can be perform by the System Admin. 
	 * This class is access by only the System Admin and this class is an extension of 
	 * {@link control.controller.RegisteredUserController RegisteredUserController}.
	 * 
	 * @see model.user.AcademicStaff
	 * @see model.group.Group
	 * @see model.enumeration.StaffTitle 
	 */

public class AdministratorController extends RegisteredUserController {
    
	private Map<String, AcademicStaff> staffCollection;
	
	/**
	 * <strong>AdministratorController Constructor</strong> creates an instance of the controller.
	 * 
	 * @param repository: The file handling and all the System data Map accessor.
	 * @see control.controller.RegisteredUserController
	 */
	public AdministratorController(Repository repository) {
		super(repository);
	}

	
	/**
	 * This method gets all the {@link model.user.AcademicStaff AcademicStaff} username.
	 * 
	 * @return Set of all the {@link model.user.AcademicStaff AcademicStaff} username.
	 */
	
	public Set<String> getAllStaffUserName() {
		
		staffCollection=repository.getStaffMap();		//get and set the staffMap
		Set<String> staffList=staffCollection.keySet();	//get all the username of staff and add to a staffList set
	    return staffList;
	}
	
	/**
	 * This method add a new {@link model.user.AcademicStaff AcademicStaff} to the system repository. Restrictions
	 * are apply when adding a new {@link model.user.AcademicStaff AcademicStaff} to prevent duplicate entry using
	 * FullName field by checking whether the FullName is null, "ANY" or already exist in the system.
	 * 
	 * <p>
	 * On successful operation, the repository file (profile.group2) is update.
	 * 
	 * @param userName	:	System Generated identification for the new registered user.
	 * @param password	:	The password entered for the user.
	 * @param email		:	The contact email for the user.
	 * @param fullName	:	The Full Name of the new user.
	 * @param title		:	Can be marital status or academic status like Mr. Mrs, Ms or Doctor, Professor
	 * @param researchGroup	:	The research group for the new user.
	 * @param isCoordinator	:	By default, this is false, the System Admin have to assign the role.
	 * <p>
	 * @return	
	 * True, if {@link model.user.AcademicStaff AcademicStaff} is added successfully.
	 * <br />
	 * False, if {@link model.user.AcademicStaff AcademicStaff} is not added. The reason for this can be a 
	 * null FullName, "ANY" or similar FullName already exist in the system.
	 * <p>
	 * @see model.user.AcademicStaff
	 * @see view.systemAdminUI.RegisterUser
	 */
	public boolean addStaff(String userName, String password, String email,
			String fullName, StaffTitle title, int researchGroup) {
		 
		staffCollection=repository.getStaffMap();	//get and set the staffMap
		//Checking if the username of the new staff already exist OR the new name is "ANY"
		if(staffCollection.get(userName)!=null || fullName == "ANY"){
			// it is not allowed to add a new staff with a userName that already exists
			return false;
		}else{
			// it is not allowed to add a new staff with a fullname that already exists
			for(AcademicStaff staff : repository.getStaffMap().values()) {
				if(staff.getFullName() == fullName || staff.getEmail() == email)
					return false;
			}
			
			Group group = repository.getGroupMap().get(researchGroup); // get the group of the new user
//			//if the groupCoordinator is not set and the
//			if(group.getCoordinatorUserName() != null && isCoordinator == true)
//				isCoordinator = false;
			
			//create a new instance of the Academic Staff with the value entered
			AcademicStaff newStaff=new AcademicStaff(userName,password,email,fullName,title,researchGroup,false,new HashSet<String>());
			staffCollection.put(userName, newStaff);	//add the new instance to the staffCollection Map
			group.addStaff(userName);					//add to the groupMap as well
			
//			if(isCoordinator) {
//				group.setCoordinatorUserName(userName);
//			}
			
			repository.updateProfileFile();	//update the repository file
			return true;
		}	
	}
	
	/**
	 * This method will change the status of the user. If the user is active, it will change to inactive and vice versa.
	 * If the user is a co-ordinator, then the user is first remove from the co-ordinator status before being deactivated.
	 * Whe the user is deactivate, the groupMap is also updated. If an inactive user is change back to active, then the
	 * groupMap is update as well.
	 * 
	 * @param userName	:	The username of the user whose status have to be changed.
	 * <p>
	 * @return
	 * <p>
	 * True, on successful opertion
	 * <br />
	 * False, if the username provide didnt exist
	 * 
	 * @see view.systemAdminUI.ChangeStatus
	 */
	
	public boolean changeStatus(String userName) {
		staffCollection=repository.getStaffMap();	//get and set the staffMap
		if(staffCollection.get(userName)!=null){	//check if the staff exist
			AcademicStaff staff = staffCollection.get(userName);	//get the instance of the staff
			if(staff.getStatus()) {					//if the staff is active
				Group group = repository.getGroupMap().get(staff.getGroup());	//get his group
				if(staff.isCoordinator())	//if staff is co-ordinator
					group.setCoordinatorUserName(null);	//set the co-ordinator username of his group to null
				group.removeStaff(userName);	//remove the user from the group
				
				staff.setStatus(false);	//set the status of the staff to inactive
				staff.setCoordinator(false);	//set the status of the co-ordinator of this staff to false
			}
			else {	//if staff is inactive, then activate 
				staff.setStatus(true);	//set the staff status to true
				Group group = repository.getGroupMap().get(staff.getGroup());	// get the group of the staff
				group.addStaff(userName);	//add the staff to the group collection
			}
			
			repository.updateProfileFile();		//update the file
		    return true;
		}else{
			//if the staff didnt exist
			return false;
		}
	
	}
	
	/**
	 * This method will change the co-ordinator of a research group. If the user selected is already a co-ordinator
	 * then an error message is displayed, else, it assign the user as the new co-ordinator. In case there exist a
	 * co-ordinator, then the old coordinator is changed and the new user became the co-ordinator. After successful
	 * operation the respective file is updated.
	 * <p>
	 * @param userName	: This is the username of the staff to be set as a co-ordinator
	 * <p>
	 * @return
	 * True	: If the user entered is changed to a co-ordinator
	 * <br />
	 * False : If there is a problem
	 */
	public boolean assignCoordinator(String userName) {
		staffCollection = repository.getStaffMap();	//get and set the staffMap
		//if the username exist in the staffMap and the user is not already a co-ordinator
		if(staffCollection.get(userName)!=null && staffCollection.get(userName).isCoordinator() == false){
			AcademicStaff staff= staffCollection.get(userName); //make an instance of the staff
			staff.setCoordinator(true);		//change the co-ordinator status to true
			//may require or may not be required
			//staffCollection.put(userName, staff);
			int groupNumber = staff.getGroup();	//get the group number
			Group group = repository.getGroupMap().get(groupNumber);	//get the group
			String oldCoordinator = group.getCoordinatorUserName();	//get the old co-ordinator
			if(oldCoordinator != null)	//if old co-ordinator exist
				staffCollection.get(oldCoordinator).setCoordinator(false); //change the old co-ordinator status to false
			group.setCoordinatorUserName(userName);	//set the new co-ordinator of the group
			
			repository.updateProfileFile();	//update the file
		    return true;
		}else{
			//if the username didnt exist or the username input is already a co-ordinator of a group
			return false;
		}
	}
	
	/**
	 * This method create a username for the new user created. It takes the first character of the word in the Full name
	 * and then concatenate with the staffMap size to generate a unique id.
	 *  
	 * @param uname : A string that contains the first character of each word of the user full name
	 * <p>
	 * @return	
	 * A string of unique username for the user
	 * 
	 * @see control.Repository
	 */
	//For System generated username
	public String createUserName(String uname){
		return String.format("%s%d", uname,repository.getStaffMap().size());
		
	}
	
	/**
	 * This method get all the staff that belongs to a particular group.
	 * 
	 * @param groupNumber	: The research group number
	 * @return	Set of {@link model.user.AcademicStaff AcademicStaff} of a specified group
	 */
	//For getting the groupMap, to be used in SystemAdminUI changing roles
	public Set<String> getMemberOfGroup(int groupNumber){
		return repository.getGroupMap().get(groupNumber).getStaffSet();
	}
	
	/**
	 * This method returns the status of the user by checking from the staffMap.
	 * @param userName : The username of the staff whose status is to be obtained
	 * @return
	 * True	: If the staff is avtive
	 * False : If the staff is inactive
	 * 
	 *  @see model.user.AcademicStaff
	 *  @see control.Repository
	 */
	//Need to know the status of the staff in the change status UI
	public boolean getStaffStatus(String userName){
		return repository.getStaffMap().get(userName).getStatus();
	}
	
	/**
	 * This method delete a publication from the publicationMap and from the user publication set. The method first 
	 * remove the publication from the publicationMap and then remove the pubId from the publicaiton set of the user
	 *  
	 * @param pubID	: This is the publication Id of the publication to be deleted. 
	 * @param userID	: The username who owns this publication.
	 * <p>
	 * @return True	: If the publication is deleted successfully
	 * <br />	False	: If the publication did not exist
	 * 
	 * @see control.Repository
	 */
	//to delete publication from the publicationMap
	public boolean deletePublication(String pubID,String userID){
		if(repository.getPublicationMap().containsKey(pubID))	//check pubId in the publicaitonMap, if exist
		{
			repository.getPublicationMap().remove(pubID);	//remove from the pubMap
			if(repository.getStaffMap().get(userID).getSetOfPublication().contains(pubID)) //check pubID in pub set of user
				repository.getStaffMap().get(userID).getSetOfPublication().remove(pubID); //remove from the user pub set
			return true;
		}
		return false;
	}
	
	/**
	 * This method check whether the input username is a valid staff username before fetching his publication
	 * 
	 * @param inputUserName	: The username of the staff
	 * @return	True	: If the username exist
	 * <br />	False	: If it did not exist
	 * 
	 * @see control.Repository
	 * @see model.view.SystemAdminUI.DeletePublication
	 */
	//to check whether the username entered to get the publication, is present in the system or not
	public boolean checkUserName(String inputUserName){
		if(repository.getStaffMap().containsKey(inputUserName)) //check whether the username exist, if yes
			return true;
		else
			return false;
	}
	
	
}
