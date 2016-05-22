package model.group;

import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Group</code> class represents entities that save index of
 * staff ID and seminar ID in a particular group, and the userName of 
 * group's coordinator.
 */
public class Group {

	private int groupNumber;	// primary key
	private String coordinatorUserName;
	private Set<String> staffSet;
	private Set<Integer> seminarSet;
	
	/**
	 * Initialises a newly created {@code AcademicStaff} object with passed group number, and empty content.
	 * 
	 * @param groupNumber
	 * 		  Initial research group number
	 */
	public Group(int groupNumber) {
		this.groupNumber = groupNumber;
		this.coordinatorUserName = null;
		this.staffSet = new HashSet<String>();
		this.seminarSet = new HashSet<Integer>();
	}
	
	/**
	 * Return the number of research group.
	 * 
	 * @return The number of research group
	 */
	public int getGroupNumber() {
		return groupNumber;
	}
	
	/**
	 * Return userName of the coordinator of this group.
	 * 
	 * @return UserName of the coordinator of this group
	 */
	public String getCoordinatorUserName() {
		return coordinatorUserName;
	}
	
	/**
	 * Return a {@link java.util.Set Set} of String, representing IDs of staffs
	 * in this group.
	 * 
	 * @return A <code>Set</code> of String, representing IDs of staffs in this group
	 */
	public Set<String> getStaffSet() {
		return staffSet;
	}
	
	/**
	 * Return a {@link java.util.Set Set} of String, representing IDs of seminars
	 * in this group.
	 * 
	 * @return A <code>Set</code> of String, representing IDs of seminars in this group
	 */
	public Set<Integer> getSeminarSet() {
		return seminarSet;
	}
	
	/**
	 * Set name of a new coordinator. The old coordinator, if exists, will be overwrote.
	 * 
	 * @param newCoordinatorUserName
	 * 		  Name of the new coordinator
	 */
	public void setCoordinatorUserName(String newCoordinatorUserName) {
		coordinatorUserName = newCoordinatorUserName;
	}
	
	/**
	 * Add userName of a new staff into this <code>Group</code>,
	 * exactly saying, into the <code>staffSet</code> Set.
	 * <p>
	 * Whether there already exists a staff with the same userName will be checked before adding.
	 * 
	 * @param newStaff
	 * 		  UserName of a new staff that to be added into this group
	 * @return True if there does not exist a staff with the same userName. Otherwise, return false.
	 */
	public boolean addStaff(String newStaff) {
		if(!staffSet.contains(newStaff)) {
			staffSet.add(newStaff);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Remove userName of a staff from this <code>Group</code>,
	 * exactly saying, from the <code>staffSet</code> Set.
	 * <p>
	 * Whether there exists staff with such a userName will be checked before removing.
	 * 
	 * @param staff
	 * 		  UserName of the staff that to be removed from this group
	 * @return True if staff with such a userName exists in the <code>staffSet</code> Set.
	 * 		   Otherwise, return false.
	 */
	public boolean removeStaff(String staff) {
		if(staffSet.contains(staff)) {
			staffSet.remove(staff);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Return whether staff with a particular userName exists in this <code>Group</code>,
	 * exactly saying, in the <code>staffSet</code> Set.
	 * 
	 * @param staff
	 * 		  UserName of the staff that to be checked.
	 * @return True if there exists a staff with the userName in <code>staffSet</code> Set.
	 * 		   Otherwise, return false.
	 */
	public boolean containsStaff(String staff) {
		return staffSet.contains(staff);
	}
	
	/**
	 * Add ID of a new seminar into this <code>Group</code>,
	 * exactly saying, into the <code>seminarSet</code> Set.
	 * <p>
	 * Whether there already exists a seminar with the same ID will be checked before adding.
	 * 
	 * @param newSeminar
	 * 		  ID of a new seminar that to be added into this group
	 * @return True if there does not exist a seminar with the same ID. Otherwise, return false.
	 */
	public boolean addSeminar(int newSeminar) {
		if(!seminarSet.contains(newSeminar)) {
			seminarSet.add(newSeminar);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Remove ID of a seminar from this <code>Group</code>,
	 * exactly saying, from the <code>seminarSet</code> Set.
	 * <p>
	 * Whether there exists seminar with such a ID will be checked before removing.
	 * 
	 * @param seminarID
	 * 		  ID of the seminar that to be removed from this group
	 * @return True if seminar with such an ID exists in the <code>seminarSet</code> Set.
	 * 		   Otherwise, return false.
	 */
	public boolean removeSeminar(int seminarID) {
		if(seminarSet.contains(seminarID)) {
			seminarSet.remove(seminarID);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Return whether seminar with a particular ID exists in this <code>Group</code>,
	 * exactly saying, in the <code>seminarSet</code> Set.
	 * 
	 * @param seminarID
	 * 		  ID of the seminar that to be checked.
	 * @return True if there exists a seminar with the ID in <code>seminarSet</code> Set.
	 * 		   Otherwise, return false.
	 */
	public boolean containsSeminar(int seminarID) {
		return seminarSet.contains(seminarID);
	}
	
}
