package model.user;

import java.io.Serializable;

/**
 * The <code>RegisteredUser</code> class represents a record of a registered user
 * in the system, which is neither a general user, nor a academic staff.
 * <p>
 * In current version, <code>RegisteredUser</code> class is only used to represent
 * a record of administrator.
 * <p>
 * The <code>RegisteredUser</code> class implements {@link java.io.Serializable Serializable} interface, which makes 
 * it allowed to write into files by <code>ObjectStream</code>.
 *
 * @see Serializable
 * @see AcademicStaff
 */
public class RegisteredUser implements Serializable {

	private static final long serialVersionUID = -4219708891410873039L;
	
	private String userName;	// primary key
	private String password;
	private String email;
	
	/**
	 * Initialises a newly created {@code RegisteredUser} object with attributes of the passed parameters.
	 * 
	 * @param userName
	 * 		  UserName, which is the primary key of a <code>RegisteredUser</code> entity.
	 * @param password
	 * 		  Initial password
	 * @param email
	 * 		  Initial email
	 */
	public RegisteredUser(String userName, String password, String email) {
		this.userName = userName;
		this.password = password;
		this.email = email;
	}
	
	/**
	 * Return the primary key, userName.
	 * 
	 * @return UserName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Return a boolean that represents if the password passed is correct.
	 * 
	 * @param password
	 * 		  The password that user entered, which is to be compared with the correct password
	 * @return True if entered password is correct. Otherwise return false.
	 */
	public boolean checkPassword(String password) {		
		if(password.equals(this.password)){
			return true;
		}else
			return false;
	}
	
	/**
	 * Return user's email.
	 * 
	 * @return User's email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * The password will be set as the new one, only if the oloPassword is correct.
	 * 
	 * @param oldPassword
	 * 		  An password entered by user, used to check before the password is set.
	 * @param newPassword
	 * 		  An new password.
	 * @return True if the oldPassword entered is correct. Otherwise, return false.
	 */
	public boolean setPassword(String oldPassword, String newPassword) {
		if(checkPassword(oldPassword)){
			this.password=newPassword;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Set a new email.
	 * 
	 * @param newEmail
	 * 		  New email address
	 */
	public void setEmail(String newEmail) {
		this.email=newEmail;
	}
}
