package model.user;

import java.util.Set;

import model.enumeration.StaffTitle;

/**
 * The <code>AcademicStaff</code> class represents records of staffs.
 * <p>
 * The <code>AcademicStaff</code> class is subclass of RegisteredUserr, which makes
 * it also implements {@link java.io.Serializable Serializable} interface.
 *
 * @see Serializable
 * @see RegisteredUser
 */
public class AcademicStaff extends RegisteredUser {

	private static final long serialVersionUID = 2833812747035519793L;
	
	private boolean active;
	private String fullName;
	private StaffTitle title;
	private int researchGroup;
	private boolean isCoordinator;
	private Set<String> publicationSet;
	
	/**
	 * Initialises a newly created {@code AcademicStaff} object with attributes of the passed parameters,
	 * and <code>active</code> attribute is true initially.
	 * 
	 * @param userName
	 * 		  Initial userName, which is the primary key of a <code>AcademicStaff</code>
	 * @param password
	 * 		  Initial password
	 * @param email
	 * 		  Initial email
	 * @param fullName
	 * 		  Initial fullName
	 * @param title
	 * 		  Initial title
	 * @param researchGroup
	 * 		  Number of the initial group 
	 * @param isCoordinator
	 * 		  A boolean that represents whether staff is a coordinator
	 * @param listOfPublication
	 * 		  A {@link java.util.Set Set} of publication's ID, that is uploaded by this staff
	 */
	public AcademicStaff(String userName, String password, String email, String fullName,
			StaffTitle title, int researchGroup, boolean isCoordinator,Set<String> listOfPublication) {
		super(userName, password, email);
		this.active = true;
		this.fullName = fullName;
		this.title = title;
		this.researchGroup = researchGroup;
		this.isCoordinator = isCoordinator;
		this.publicationSet = listOfPublication;
	}

	/**
	 * Return staff's full name.
	 * 
	 * @return Staff's full name
	 */
	public String getFullName() {
		return fullName;
	}
	
	/**
	 * Return staff's title, which is in {@link model.enumeration.StaffTitle StaffTitle} type.
	 * 
	 * @return Staff's title
	 * @see model.enumeration.StaffTitle
	 */
	public StaffTitle getTitle() {
		return title;
	}
	
	/**
	 * Return the number of group that staff belongs to.
	 * 
	 * @return The number of group that staff belongs to.
	 */
	public int getGroup() {
		return researchGroup;
	}
	
	/**
	 * Return a boolean representing whether staff is a coordinator.
	 * 
	 * @return True if staff is a coordinator. Otherwise, return false.
	 */
	public boolean isCoordinator() {
		return isCoordinator;
	}
	
	/**
	 * Return a boolean representing whether staff is activated.
	 * An <code>AcademicStaff</code> object is activated when it is created first time.
	 * It will be set as not activated when staff is deleted, and will be set as activated
	 * again if the same staff come back to department and is going to register again.
	 * 
	 * @return True if staff is activated. Otherwise, return false.
	 */
	public boolean getStatus() {
		return active;
	}
	
	/**
	 * Set a new staff full name.
	 * 
	 * @param newFullName
	 * 		  New full name
	 */
	public void setFullName(String newFullName) {
	     this.fullName=newFullName;	
	}
	
	/**
	 * Set a new staff title, which is in {@link model.enumeration.StaffTitle StaffTitle} type.
	 * 
	 * @param newTitle
	 * 		  New staff title.
	 * @see model.enumeration.StaffTitle
	 */
	public void setTitle(StaffTitle newTitle) {
		this.title=newTitle;
	}
	
	/**
	 * Set number of the new group.
	 * 
	 * @param newGroup
	 * 		  Number of the new research group
	 */
	public void setGroup(int newGroup) {
		this.researchGroup=newGroup;
		
	}
	
	/**
	 * Set a staff as a coordinator of the group he/she belongs to, or cancel this coordinator
	 * if he/she already is one. 
	 * 
	 * @param isCoordinator
	 * 		  New status representing whether staff is a coordinator.
	 * 		  Pass true if staff is going to be set as a coordinator. Otherwise, pass false.
	 */
	public void setCoordinator(boolean isCoordinator) {
		this.isCoordinator=isCoordinator;
	}
	
	/**
	 * Set a staff as activated or not activated.
	 * 
	 * @param newStatus
	 * 		  Pass true is staff is going to be set as activated. Otherwise, pass false.
	 */
	public void setStatus(boolean newStatus) {
		active = newStatus;
	}
	
	/**
	 * Add ID of a new publication, which is uploaded by this staff.
	 * 
	 * @param IDNumber
	 * 		  ID of the new publication
	 */
	public void addPublication(String IDNumber){
		publicationSet.add(IDNumber);
	}
	
	/**
	 * Return a {@link java.util.Set Set} of ID of publications that are uploaded by this staff.
	 * 
	 * @return A <code>Set</code> of ID of publications that are uploaded by this staff.
	 */
	public Set<String> getSetOfPublication(){
	    return publicationSet;	
	}
	
}
