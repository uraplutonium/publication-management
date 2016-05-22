package control.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import control.EmailSystem;
import control.PMSystem;
import control.Repository;

import model.enumeration.PublicationType;
import model.enumeration.StaffTitle;
import model.enumeration.UserRole;
import model.publication.Book;
import model.publication.ConferencePaper;
import model.publication.JournalPaper;
import model.publication.Publication;
import model.user.AcademicStaff;
import model.user.RegisteredUser;

/**
 * The <code>GeneralUserController</code> class is a control class that provides
 * operations for general users, which are login/logout, operation of reading profiles
 * of any staffs, and reading details of any publications.
 * 
 * @see Repository
 * @see AcademicStaff
 * @see Publication
 */
public class GeneralUserController {

	/** A reference of {@code repository}, initialised when controller is created */
	protected Repository repository;

	/**
	 * Initialise a newly created <code>GeneralUserController</code>.
	 * 
	 * @param repository
	 * 		  The reference of <code>repository</code> in <code>PMSystem</code>
	 */
	public GeneralUserController(Repository repository) {
		this.repository = repository;
	}

	/**
	 * Login if the current user is a general user. Otherwise, logout.
	 * 
	 * @param userName
	 * 		  UserName of the user that either to login or logout
	 * @param password
	 * 		  To login, pass the correct password. To logout, pass null.
	 * @return Role of user after login or logout. Return <code>UserRole.GENERAL_USER</code>
	 * 		   if passed wrong userName or password when login.
	 * @see UserRole
	 * @see PMSystem#switchCurrentFrame(UserRole, int)
	 */
	public UserRole loginout(String userName, String password) {
		UserRole role;
		
		if(password == null && userName.equals(Repository.currentUserName)) {
			Repository.currentUserName = null;
			role = UserRole.GENERAL_USER;
		}
		else {
			RegisteredUser administrator = repository.getAdministrator();
			if(administrator.getUserName().equals(userName) && administrator.checkPassword(password)) {
				role = UserRole.ADMINISTRATOR;
				Repository.currentUserName = userName;
			}
			else {
				Map<String, AcademicStaff> staffMap = repository.getStaffMap();
				
				if(staffMap.containsKey(userName) &&						// staff must exist in the system
						staffMap.get(userName).getStatus() == true &&		// staff must be activated
						staffMap.get(userName).checkPassword(password)) {	// password must be correct
					Repository.currentUserName = userName;
					if(staffMap.get(userName).isCoordinator())
						role = UserRole.COORDINATOR;
					else
						role = UserRole.ACADEMIC_STAFF;
				}
				else {
					role = UserRole.GENERAL_USER;
				}
			}
		}
		
		// switch to the correct frame, and switch as a staff even if it is a coordinator
		PMSystem.getSystemInstance().switchCurrentFrame(
				(role==UserRole.COORDINATOR ? UserRole.ACADEMIC_STAFF : role), 0);
		
		return role;
	}
	
	//////////////// Read authority for all staffs' profiles ////////////////
	/**
	 * Return email of a particular academic staff.
	 * 
	 * @param userName
	 * 		  UserName of a user
	 * @return Email address of the user with the passed userName.
	 * 		   Return null if user does not exist with such a userName.
	 */
	public String getStaffEmail(String userName) {
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();
		if(staffMap.containsKey(userName))
			return staffMap.get(userName).getEmail();
		else
			return null;
	}
	
	/**
	 * Return full name of a particular academic staff.
	 * 
	 * @param userName
	 * 		  UserName of a user
	 * @return Full name of the user with the passed userName.
	 * 		   Return null if user does not exist with such a userName.
	 */
	public String getStaffFullName(String userName) {
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();
		if(staffMap.containsKey(userName))
			return staffMap.get(userName).getFullName();
		else
			return null;
	}
	
	/**
	 * Return title of a particular academic staff.
	 * 
	 * @param userName
	 * 		  UserName of a user
	 * @return Title of the user with the passed userName.
	 * 		   Return null if user does not exist with such a userName.
	 */
	public StaffTitle getStaffTitle(String userName) {
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();
		if(staffMap.containsKey(userName))
			return staffMap.get(userName).getTitle();
		else
			return null;
	}
	
	/**
	 * Return group number of a particular academic staff.
	 * 
	 * @param userName
	 * 		  UserName of a user
	 * @return Group number of the user with the passed userName.
	 * 		   Return -1 if user does not exist with such a userName.
	 */
	public int getStaffGroup(String userName) {
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();
		if(staffMap.containsKey(userName))
			return staffMap.get(userName).getGroup();
		else
			return -1;
	}
	
	/**
	 * Return group number of a particular academic staff.
	 * 
	 * @param userName
	 * 		  UserName of a user
	 * @return Group number of the user with the passed userName.
	 * 		   Return -1 if user does not exist with such a userName.
	 */
	public int isCoordinator(String userName) {
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();
		if(staffMap.containsKey(userName))
			return (staffMap.get(userName).isCoordinator() ? 1 : 0);
		else
			return -1;
	}
	
	//////////////// Read authority for all publications ////////////////
	/**
	 * Return a {@link Set} of String representing ID for all the publications.
	 * 
	 * @return A {@code Set} of String representing ID for all the publications
	 */
	public Set<String> getPublicationID() {
		return getPublicationID(null, -1, -1);
	}
	
	/**
	 * Return a {@link Set} of String representing ID for searching result of publications,
	 * with the searching conditions of <code>authorName</code>, <code>year</code> and 
	 * <code>groupNumber</code>. Parameters could be passed by null or -1 representing "Any".
	 * <p>
	 * When parameters are <code>(null, -1, -1)</code>, all publications ID will be returned.
	 * 
	 * @param authorName
	 * 		  UserName of whom the authors of publications must be
	 * @param year
	 * 		  Year that publications must be published/uploaded, should be counted from 1900
	 * @param groupNumber
	 * 		  Number of group that the author of publications must belongs to
	 * @return A <code>Set</code> of String representing ID for searching result of publications.
	 * 		   Return null if passed searching conditions are invalid.
	 */
	@SuppressWarnings("deprecation")
	public Set<String> getPublicationID(String authorName, int year, int groupNumber) {
		Set<String> results = new HashSet<String>();
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		Map<String, AcademicStaff> staffMap = repository.getStaffMap();
		
		if(authorName == null && year == -1 && groupNumber == -1) {
			// this branch could be done by the following one, just to make it faster since this is used quite often
			results = publicationMap.keySet();
		}
		else if(// the author's userName must exists in staffMap, or been passed as null
				(authorName == null || staffMap.containsKey(authorName)) &&	
				// the group number must be any one from 1 to 9, or been passed as -1
				(groupNumber == -1 || (groupNumber >=1 &&	groupNumber <= 9)) &&
				// the year must be equals or greater than 0, or is -1
				(year >= -1) &&
				// the author and group number must matches to each other, when none of them are passed as null or -1
				((authorName != null && groupNumber != -1) ? staffMap.get(authorName).getGroup() == groupNumber : true)) {
			
			for(String publicationID : publicationMap.keySet()) {
				Publication publication = publicationMap.get(publicationID);
				// whether author' userName meet the searching condition
				boolean correctAuthor = (authorName == null ? true : publication.getUploaderUserName().equals(authorName));
				// whether publish/upload year meet the searching condition
				boolean correctYear = (year == -1 ? true : publication.getPublishDate().getYear() == year);
				// whether group number meet the searching conditions
				boolean correctGroup = (groupNumber == -1 ? true : staffMap.get(publication.getUploaderUserName()).getGroup() == groupNumber);

				if(correctAuthor && correctYear && correctGroup) {
					results.add(publicationID);		// if all searching conditions have been met, add it's ID into results set
				}
			}
			
			if(results.size() == 0 && groupNumber ==2){
				System.out.println("staffMap.get(authorName).getGroup() " + staffMap.get(authorName).getGroup());
				System.out.println("groupNumber " + groupNumber);
			}
		}
		else {
			// invalid searching conditions have been passed
			results = null;
		}
		
		return results;
	}
	
	/**
	 * Return the title of a particular {@link Publication}.
	 * 
	 * @param publicationID
	 * 		  ID of the publication
	 * @return Title of the publication. Return null if the <code>Publication</code> does not exist.
	 */
	public String getPublicationTitle(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID))
			return publicationMap.get(publicationID).getPublicationTitle();
		else
			return null;
	}
	
	/**
	 * Return a {@link Set} authors' full names of a particular {@link Publication}.
	 * 
	 * @param publicationID
	 * 		  ID of the publication
	 * @return A {@code Set} authors' full names of the publication.
	 *		   Return null if the <code>Publication</code> does not exist.
	 */
	public Set<String> getPublicationAuthorSet(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID))
			return publicationMap.get(publicationID).getAuthorSet();
		else
			return null;
	}
	
	/**
	 * Return the date when a particular {@link Publication} is published/uploaded.
	 * 
	 * @param publicationID
	 * 		  ID of the publication
	 * @return The date when a particular publication is published/uploaded.
	 *		   Return null if the <code>Publication</code> does not exist.
	 */
	public Date getPublicationDate(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID))

			return publicationMap.get(publicationID).getPublishDate();
		else
			return null;
	}
	
	/**
	 * Return the uploader's userName of a particular {@link Publication}.
	 * 
	 * @param publicationID
	 * 		  ID of the publication
	 * @return Uploader's userName of the publication. Return null if the <code>Publication</code> does not exist.
	 */
	public String getPublicationUploaderUserName(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID))
			return publicationMap.get(publicationID).getUploaderUserName();
		else
			return null;
	}
	
	/**
	 * Return the publisher's name of a particular {@link Book}.
	 * 
	 * @param publicationID
	 * 		  ID of the book
	 * @return Publisher's name of the publication. Return null if the <code>Book</code> does not exist.
	 */
	public String getBookPublisherName(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID) && publicationMap.get(publicationID) instanceof Book) {
			Book book = (Book)publicationMap.get(publicationID);
			return book.getPublisherName();
		}
		else
			return null;
	}
	
	/**
	 * Return the publishing place of a particular {@link Book}.
	 * 
	 * @param publicationID
	 * 		  ID of the book
	 * @return Publishing place of the book. Return null if the <code>Book</code> does not exist.
	 */
	public String getBookPublishPlace(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID) && publicationMap.get(publicationID) instanceof Book) {
			Book book = (Book)publicationMap.get(publicationID);
			return book.getPublishPlace();
		}
		else
			return null;
	}
	
	/**
	 * Return the conference place of a particular {@link ConferencePaper}.
	 * 
	 * @param publicationID
	 * 		  ID of the conference paper
	 * @return Place of the conference. Return null if the <code>ConferencePaper</code> does not exist.
	 */
	public String getConferencePlace(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID) && publicationMap.get(publicationID) instanceof ConferencePaper) {
			ConferencePaper conference = (ConferencePaper)publicationMap.get(publicationID);
			return conference.getPlace();
		}
		else
			return null;
	}
	
	/**
	 * Return the journal name of a particular {@link JournalPaper}.
	 * 
	 * @param publicationID
	 * 		  ID of the journal paper
	 * @return Journal name of the journal paper. Return null if the <code>JournalPaper</code> does not exist.
	 */
	public String getJournalName(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID) && publicationMap.get(publicationID) instanceof JournalPaper) {
			JournalPaper journal = (JournalPaper)publicationMap.get(publicationID);
			return journal.getJournalName();
		}
		else
			return null;
	}
	
	/**
	 * Return the issue number of a particular {@link JournalPaper}.
	 * 
	 * @param publicationID
	 * 		  ID of the journal paper
	 * @return Issue number of the journal paper. Return null if the <code>JournalPaper</code> does not exist.
	 */
	public String getJournalIssueNumber(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID) && publicationMap.get(publicationID) instanceof JournalPaper) {
			JournalPaper journal = (JournalPaper)publicationMap.get(publicationID);
			return journal.getIssueNumber();
		}
		else
			return null;
	}
	
	/**
	 * Return the page numbers of a particular {@link JournalPaper}.
	 * 
	 * @param publicationID
	 * 		  ID of the journal paper
	 * @return Page numbers of the journal paper. Return null if the <code>JournalPaper</code> does not exist.
	 */
	public String getJournalPageNumber(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID) && publicationMap.get(publicationID) instanceof JournalPaper) {
			JournalPaper journal = (JournalPaper)publicationMap.get(publicationID);
			return journal.getPageNumber();
		}
		else
			return null;
	}
	
	/**
	 * Return the {@link PublicationType} of a particular {@link Publication}.
	 * 
	 * @param publicationID
	 * 		  ID of the publication
	 * @return Publication type of the publication
	 * @see PublicationType
	 */
	public PublicationType getPublicationType(String publicationID) {
		Publication publication = repository.getPublicationMap().get(publicationID);
		if(publication instanceof Book) {
			return PublicationType.BOOK;
		}
		else if(publication instanceof ConferencePaper) {
			return PublicationType.CONFERENCE;
		}
		else {
			// It is not a good way to always return JOURNAL when the publication even does not exist.
			return PublicationType.JOURNAL;
		}
	}
	
	/**
	 * Request a copy of publication. Which will send a email to notify the uploader of the publication.
	 * 
	 * @param publicationID
	 * 		  ID of the publication to request copy of
	 * @return Uploader's email. This return value is only used for testing so far.
	 */
	public String requestPublicationCopy(String publicationID) {
		Map<String, Publication> publicationMap = repository.getPublicationMap();
		if(publicationMap.containsKey(publicationID)) {
			Publication publication = publicationMap.get(publicationID);
			String email=repository.getStaffMap().get(publication.getUploaderUserName()).getEmail();
			
			String message = "This message should be generated from information of 'publication'.";
			EmailSystem.requestCopy(email, message);
			
			return email;
		} else {
			return null;
		}
	}
	
}
