package control;

import java.util.Set;

/**
 * The <code>EmailSystem</code> class represents the external email system.
 * It provide two public and static methods to send email to one person or several persons.
 * <p>
 * This part could be improved by implementing to truly send email.
 */
public class EmailSystem {

	/**
	 * Send a request email
	 * 
	 * @param to
	 * 		  Email address of the receiver
	 * @param message
	 * 		  Massage to send
	 */
	public static void requestCopy(String to, String message) {
		// TODO send a message of request copy to external email system
	}
	
	/**
	 * Send email to group members of details of seminar and monthly publications
	 * 
	 * @param to
	 * 		  A <code>Set</code> of email address of the receivers
	 * @param message
	 * 		  Massage to send
	 */
	public static void publishSeminar(Set<String> to, String message) {
		// TODO send a message of seminar and monthly publications to group members
	}
	
}
