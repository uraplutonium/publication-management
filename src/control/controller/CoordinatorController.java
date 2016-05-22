package control.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.group.Group;
import model.group.Seminar;
import model.publication.Publication;
import model.user.AcademicStaff;

//import control.EmailSystem;
import control.EmailSystem;
import control.Repository;
/**
 *	The <code>CoordinatorController</code> class takes care of all the actions performed by the co-ordinator.
 *This class extends from {@link control.controller.AcademicStaffController AcademicStaffController} and thus have all
 *the functions of the {@link model.user.AcademicStaff AcademicStaff}.  
 *  
 *@see model.user.AcademicStaff
 *@see model.group.Seminar
 *@see model.group.Group
 *@see model.publication.Publication
 */
public class CoordinatorController extends AcademicStaffController {

	/**
	 * <strong>CoordinatorController Constructor</strong> creates an instance of the controller.
	 * 
	 * @param repository: The file handling and all the System data Map accessor.
	 * @see control.controller.RegisteredUserController
	 */	
	public CoordinatorController(Repository repository) {
		super(repository);
	}

	/**
	 * This method is used to published a seminar from the list of added seminar. The method checks whether the input
	 * seminarID exist, if it do, it published the seminar to the groupMember.
	 * @param seminarID:	The id of the seminar to be published
	 * @return
	 * emailSet:	If the seminar exist, return the group member email
	 * null:		If the seminarId does not exist
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	// Read authority for group seminars and group staffs' profiles
	public Set<String> publishSeminar(int seminarID) {
		Map<Integer, Group> groupMap = repository.getGroupMap();	//set and get groupMap
		Map<Integer, Seminar> seminarMap = repository.getSeminarMap();	//set and get seminarMap
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();	//set and get staffMap
		Set<String> monthlyPublication=new HashSet<String>();
		//Check if seminarID already exist in the seminarMap 
		if(seminarMap.containsKey(seminarID)) { //if true
			Seminar seminar = seminarMap.get(seminarID);	// get the instance of the seminarID
			seminar.publish();	// publish the seminar to change its status
			
			Set<String> memberSet = groupMap.get(seminar.getGroupNumber()).getStaffSet(); //get the staffSet from the groupMap of the seminar
			Set<String> emailSet = new HashSet<String>(); //set an emailSet
			for(String member : memberSet) {
				emailSet.add(staffMap.get(member).getEmail()); //add each member of the groupMap to the emailSet
			}
			
			monthlyPublication = getMonthlyPublication();
			
			String message = "This message should be generated from information of 'monthlyPublication'." + monthlyPublication.toString();
			
			EmailSystem.publishSeminar(emailSet, message);
			repository.updateSeminarFile(); //after publishing, update the seminar file
			return emailSet;	//return the set of emials of the group member
		}
		else	//if seminar didn't exist
			return null;		//return null
	}
	
	/**
	 * getGroupSeminarID is a method to get the set of seminars' IDs. The seminars belongs to 
	 * the coordinator's group.
	 * 
	 * @return a set of integers which are the IDs of semianr 
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	// Read authority for group seminars
	public Set<Integer> getGroupSeminarID() {
		Map<Integer, Group> groupMap = repository.getGroupMap();     //get the group map from repository
		Map<String, AcademicStaff> staffMap = repository.getStaffMap(); //get the staff map from repository
		
		String coordinatorUserName = Repository.currentUserName;     //get current user name
		int groupNumber = staffMap.get(coordinatorUserName).getGroup(); //get the ID of the group the user belongs to 
		
		return groupMap.get(groupNumber).getSeminarSet();  //return the seminar set of that group
	}
	
	/**
	 * getsemianrTopic is the method to get the topic of specific seminar according to the seminar ID
	 * 
	 * @param seminarID  The unique ID of seminar
	 * @return   The title of topic
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	public String getSeminarTopic(int seminarID) {
		Map<Integer, Seminar> seminarMap = repository.getSeminarMap();    //get the group map from repository
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();   //get the staff map from repository
		
		if(seminarMap.containsKey(seminarID) &&        //if the seminarID exist in the seminarMap and the seminar group is the group current user belongs to
				seminarMap.get(seminarID).getGroupNumber() == staffMap.get(Repository.currentUserName).getGroup()) {
			return seminarMap.get(seminarID).getTopic();              //return the seminar topic
		}
		else
			return null;                                              //else return null
	}
	
	/**
	 * getSeminarTime is the method to get the time of specific seminar according to the seminar ID
	 * 
	 * @param seminarID The unique ID of seminar
	 * @return  return a String which represent the time of seminar
	 * 
	 * @see model.group.Group
	 * @see Repository
	 * @see model.group.Seminar
	 */
	public String getSeminarTime(int seminarID) {
		Map<Integer, Seminar> seminarMap = repository.getSeminarMap();  //get the group map from repository
		Map<String, AcademicStaff> staffMap = repository.getStaffMap(); //get the staff map from repository
		
		if(seminarMap.containsKey(seminarID) &&  //if the seminarID exist in the seminarMap and the seminar group is the group current user belongs to
				seminarMap.get(seminarID).getGroupNumber() == staffMap.get(Repository.currentUserName).getGroup()) {
			return seminarMap.get(seminarID).getTime();     //return the seminar time
		}
		else
			return null;                                   //else return null
	}
	
	/**
	 * getSeminarTime is the method to get the date of specific seminar according to the seminar ID
	 * 
	 * @param seminarID The unique ID of seminar
	 * @return  return a String which represent the date of seminar
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	public String getSeminarDate(int seminarID) {
		Map<Integer, Seminar> seminarMap = repository.getSeminarMap();   //get the group map from repository
		Map<String, AcademicStaff> staffMap = repository.getStaffMap(); //get the staff map from repository
		
		if(seminarMap.containsKey(seminarID) &&         //if the seminarID exist in the seminarMap and the seminar group is the group current user belongs to
				seminarMap.get(seminarID).getGroupNumber() == staffMap.get(Repository.currentUserName).getGroup()) {
			return seminarMap.get(seminarID).getDate();     //return the seminar date
		}
		else
			return null;									//else return null
	}
	
	/**
	 * getSeminarTime is the method to get the place of specific seminar according to the seminar ID
	 * 
	 * @param seminarID The unique ID of seminar
	 * @return  return a String which represent the place of seminar
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	public String getSeminarPlace(int seminarID) {
		Map<Integer, Seminar> seminarMap = repository.getSeminarMap(); 	//get the group map from repository
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();  //get the staff map from repository
		
		if(seminarMap.containsKey(seminarID) &&			 //if the seminarID exist in the seminarMap and the seminar group is the group current user belongs to
				seminarMap.get(seminarID).getGroupNumber() == staffMap.get(Repository.currentUserName).getGroup()) {
			return seminarMap.get(seminarID).getPlace();		  //return the seminar date
		}
		else
			return null;							//else return null
	}
	
	/**
	 * getSeminarGroupNumber provide a method to get the group number the seminar belongs to
	 * 
	 * @param seminarID The unique ID of seminar
	 * @return return a integer. If find the group return its number, otherwise, return -1	 
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	public int getSeminarGroupNumber(int seminarID) {
		Map<Integer, Seminar> seminarMap = repository.getSeminarMap();   //get the group map from repository
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();  //get the staff map from repository
		
		if(seminarMap.containsKey(seminarID) &&			 //if the seminarID exist in the seminarMap and the seminar group is the group current user belongs to
				seminarMap.get(seminarID).getGroupNumber() == staffMap.get(Repository.currentUserName).getGroup()) {
			return seminarMap.get(seminarID).getGroupNumber();   //return the group number the seminar blongs to
		}
		else													 //else return -1
			return -1;
	}
	
	/**
	 * addSeminar provide a method to add new Seminar for the 
	 * @param seminarID   The unique ID of seminar
	 * @param topic       The topic of seminar
	 * @param time        The time of semianr
	 * @param date        The date of seminar
	 * @param place       The place of seminar
	 * @return   return a boolean type. If add successfully, return true. Else return false.
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	// Write authority for group seminars
	public boolean addSeminar(int seminarID, String topic, String time, String date, String place) {
		Map<Integer, Group> groupMap = repository.getGroupMap();   //get the group map from repository
		Map<Integer, Seminar> seminarMap = repository.getSeminarMap();  //get the seminar map from repository
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();  //get the staff map from repository
        //if the seminarID is not exist in the set of seminars of current user's group 
		if(! groupMap.get(staffMap.get(Repository.currentUserName).getGroup()).getSeminarSet().contains(seminarID)) {
			//Calendar currentCalendar = Calendar.getInstance();
			// TODO check whether currentDate is the current date
			
			//Date currentDate = new Date();
			//currentDate.setTime(currentCalendar.getTimeInMillis());
			
//			if(time.before(currentDate))
//				return false;
//			System.out.println("1 : "+seminarID+" topic: "+topic );
			
			//initialize new seminar with the parameters
			Seminar newSeminar = new Seminar(seminarID, topic, time, date, place, staffMap.get(Repository.currentUserName).getGroup());
			seminarMap.put(seminarID, newSeminar);             //put the new seminar into the seminar map
			//insert the seminar to the group record
			groupMap.get(staffMap.get(Repository.currentUserName).getGroup()).addSeminar(seminarID); //add seminar id
			
			repository.updateSeminarFile();    //update seminar file, write the seminar to the file
			
			int seminarSize = repository.getMapSize().get("seminarIDAutoIncrement")+1;  //get the seminar map size
			repository.getMapSize().put("seminarIDAutoIncrement", seminarSize);   
			repository.updateMapSizeFile();     //update the map size file
			return true;
		}
		return false;
	}
	
	/**
	 * editSeminar provides the method to change the details of semianr
	 * 
	 * @param seminarID   The unique ID of seminar
	 * @param topic       The topic of seminar
	 * @param time        The time of semianr
	 * @param date        The date of seminar
	 * @param place       The place of seminar
	 * @return return a boolean type. If edit seminar successfully,return true. Else return false
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	public boolean editSeminar(int seminarID, String topic, String date, String time, String place) {
		Map<Integer, Group> groupMap = repository.getGroupMap();   // get the group map from repository
		Map<Integer, Seminar> seminarMap = repository.getSeminarMap(); // get the seminar map from repository
		Map<String, AcademicStaff> staffMap = repository.getStaffMap(); // get the staff map from repository
		 //if the seminarID is exist in the set of seminars of current user's group 
		if(groupMap.get(staffMap.get(Repository.currentUserName).getGroup()).getSeminarSet().contains(seminarID)) {
			Seminar seminar = seminarMap.get(seminarID);    //get the seminar from the seminar map according to the ID
			seminar.setTopic(topic);  // set the new topic 
			seminar.setDate(date);    //set the new date
			seminar.setTime(time);    //set the new time
			seminar.setPlace(place);  //set the new place
			
			repository.updateSeminarFile();    //update the seminar file. Write the edited seminar back to the file
			return true;                     //update successfully return true
		}
		return false;                 //else return false
	}
	
	/**
	 * deleteSeminar is a method to delete an existed seminar accroding to its ID
	 * @param seminarID   The unique ID of seminar
	 * @return return a boolean type. If delete successfully, else return false.
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	public boolean deleteSeminar(int seminarID) {
		Map<Integer, Group> groupMap = repository.getGroupMap(); // get the group map from repository
		Map<Integer, Seminar> seminarMap = repository.getSeminarMap();  // get the seminar map from repository
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();   // get the staff map from repository
		 //if the seminarID is exist in the set of seminars of current user's group 
		if(groupMap.get(staffMap.get(Repository.currentUserName).getGroup()).getSeminarSet().contains(seminarID)) {
			seminarMap.remove(seminarID);       //remove the seminar from the seminar map according to the ID
			groupMap.get(staffMap.get(Repository.currentUserName).getGroup()).removeSeminar(seminarID); //remove the seminar from the group according to the ID
			repository.updateSeminarFile();  //update the file. Delete the seminar from the file
			return true;       //delete successfully return true
		}
		return false;   //else return false
	}
	
	/**
	 * a method get the monthly publication of current coordinator's group
	 * 
	 * @return return a set of publication IDs which are published in current month.
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	@SuppressWarnings("deprecation")
	public Set<String> getMonthlyPublication() {
		Map<Integer, Group> groupMap = repository.getGroupMap();  // get the group map from repository
		Set<String> monthlyPublication=new HashSet<String>();    //initialize a set of string to store the monthly publication
		Map<String, AcademicStaff> staffMap = repository.getStaffMap(); //get the staff map from repository
		
		Group currentgroup=groupMap.get(staffMap.get(Repository.currentUserName).getGroup());  //get current user's group
		Set<String> staffs=currentgroup.getStaffSet();    //get the group members of the current's group
		Calendar currentCalendar = Calendar.getInstance();
		Date currentDate = new Date();
		currentDate.setTime(currentCalendar.getTimeInMillis());   //set current time
		
		for(String staff: staffs){              //get each staff from the staff set
			
			AcademicStaff academicstaff=repository.getStaffMap().get(staff);   //get the specific staff from the repository
			Set<String> publicationSet=academicstaff.getSetOfPublication();  //get the publiation set
			
			for(String publication: publicationSet){      //get each publication from the publication set
				Publication pub= repository.getPublicationMap().get(publication); //get the publication from the repository according to the ID
				if(pub.getPublishDate().getMonth() == currentDate.getMonth()){  //judge the publication is published in current month
					monthlyPublication.add(pub.getIDNumber()); //add the monthly publicaton to the set 
				}
			}
		}

		return monthlyPublication;
	}
	
	/**
	 * getSeminarStatus is a method to get seminar status according to the ID. The seminar have two status, seminars 
	 * are added and then they can be published.
	 * 
	 * @param seminarID  The unique ID of seminar
	 * @return return a String wich represent the status of seminar
	 *
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	public String getSeminarStatus(int seminarID) {
		//if the seminar exist in the seminar map
		if(repository.getSeminarMap().containsKey(seminarID)) {
			return (repository.getSeminarMap().get(seminarID).isPublished() ? "Published" : "Pending");
		}
		else {
			return null;
		}
	}
	
	/**
	 * createSeminarID is the method to generate  semianr ID
	 * @return return an integer which reprsents the semianr ID generated.
	 * 
	 * @see model.group.Group
	 * @see model.group.Seminar
	 * @see Repository
	 */
	//Create a seminar id
	public int createSeminarID(){
		return repository.getMapSize().get("seminarIDAutoIncrement")+1;
	}
	
}
