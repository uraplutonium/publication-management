package control.controller;

import java.util.Map;

import model.user.AcademicStaff;
import control.Repository;

/**
 * The <code>RegisteredUserController</code> class is an abstract class that
 * extends from {@link GeneralUserController}. It provide methods for registered
 * users rather than general users. However, it can not be instantiated since
 * a registered user must use either {@link AdministratorController} or
 * {@link AcademicStaffController} to operate.
 *
 * @see GeneralUserController
 */
public abstract class RegisteredUserController extends GeneralUserController {

	/**
	 * Initialise a newly created <code>RegisteredUserController</code>.
	 * 
	 * @param repository
	 * 		  The reference of <code>repository</code> in <code>PMSystem</code>
	 */
	public RegisteredUserController(Repository repository) {
		super(repository);
	}

	//////////////// Write authority for own profile ////////////////
	/**
	 * Change the password with <code>newPassword</code> if the passed <code>oldPassword</code> is correct.
	 * 
	 * @param oldPassword
	 * 		  The old password, used to check before changing password
	 * @param newPassword
	 * 		  The new password
	 * @return True if oldPassword is correct. Otherwise, return false.
	 */
	public boolean changePassword(String oldPassword, String newPassword) {
		Map<String, AcademicStaff> newmap=repository.getStaffMap();
		AcademicStaff user= newmap.get(Repository.currentUserName);
		if(user.checkPassword(oldPassword)){
			user.setPassword(oldPassword, newPassword);
			repository.updateProfileFile();
			return true;
		}
		else{
			return false;
		}
		
	}

	/**
	 * Change user's email address.
	 * 
	 * @param email
	 * 		  New email
	 */
	public void editEmail(String email) {
		Map<String, AcademicStaff> newmap=repository.getStaffMap();
		AcademicStaff user= newmap.get(Repository.currentUserName);
		user.setEmail(email);
		repository.updateProfileFile();
	}
	
}
