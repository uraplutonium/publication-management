package model.enumeration;

/**
 * The <code>UserRole</code> enumeration represents which role
 * user is before and after login and logout.
 * <p>
 * @see model.enumeration.UserRole#GENERAL_USER GENERAL_USER
 * @see model.enumeration.UserRole#ACADEMIC_STAFF ACADEMIC_STAFF
 * @see model.enumeration.UserRole#ADMINISTRATOR ADMINISTRATOR
 * @see model.enumeration.UserRole#COORDINATOR COORDINATOR
 */
public enum UserRole {
	/** General user, who are those users have not login. */
	GENERAL_USER,
	
	/** Academic staff, who are those users login as a staff, that can manage publications. */
	ACADEMIC_STAFF,
	
	/** Administrator, who are those users login as a administrator,
	 *  that can manager user records, and delete publications.
	 */
	ADMINISTRATOR,
	
	/** Coordinator, who are those users login as a coordinator,
	 *  that can do what a academic staff can do, and can also manage seminars.
	 */
	COORDINATOR
}
