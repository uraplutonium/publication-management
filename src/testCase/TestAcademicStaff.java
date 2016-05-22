package testCase;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import model.enumeration.StaffTitle;
import model.enumeration.UserRole;
import control.PMSystem;
import control.controller.AcademicStaffController;
import control.controller.GeneralUserController;

/**
 * Test cased for <code>AcademicStaffController</code>.
 * This test case may takes about 10 seconds or more.
 * Please wait before it is finished.
 *
 * @see AcademicStaffController
 * @see PMSystem
 * @see SelfResetRepository
 */
public class TestAcademicStaff extends PMSystem {

	/**
	 * Create an example that contains several staffs and publication and seminars for testing.
	 * Initialise the PMSystem and replace normal <code>Repository</code>
	 * with a <code>SelfResetRepository</code> at the beginning.
	 * <p>
	 * <blockquote><table cellpadding=1 cellspacing=0 summary="Split example showing regex, limit, and result">
     * <tr>
     *     <th>UserName | </th>
     *     <th>Book | </th>
     *     <th>Journal | </th>
     *     <th>Conference | </th>
     *     <th>Seminar</th>
     * </tr>
     * <tr><td align=center>hr101</td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     * <tr><td align=center>*tr102</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     * <tr><td align=center>ll103</td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     *     <td align=center></td>
     * <tr><td align=center>sb201</td>
     *     <td align=center>2</td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     *     <td align=center></td>
     * <tr><td align=center>cl202</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     * <tr><td align=center>lm203</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     * <tr><td align=center>me401</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *      <td align=center></td>
     *     <td align=center></td>
     * <tr><td align=center>*cp901</td>
     *     <td align=center>1</td>
     *     <td align=center></td>
     *     <td align=center></td>
     *     <td align=center>2</td>
     * </table></blockquote>
     */
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		repository = new SelfResetRepository("testCaseFolder/academic_staff_test/");
		
		//////////////// login as administrator to add staff details ////////////////
		getGeneralUserController().loginout("Admin", "pwd123");
		
		// Group 1 - 3 people
		getAdministratorController().addStaff("hr101", "hr101", "hr101@york.ac.uk",
				"Howard Rheingold", StaffTitle.MR, 1);
		getAdministratorController().addStaff("tr102", "tr102", "tr102@york.ac.uk",
				"Tim O'Reilly", StaffTitle.PROFESSOR, 1);
		getAdministratorController().addStaff("ll103", "ll103", "ll103@york.ac.uk",
				"Linda Lawrey", StaffTitle.DOCTOR, 1);
		getAdministratorController().assignCoordinator("tr102");
				
		// Group 2 - 3 people
		getAdministratorController().addStaff("sb201", "sb201", "sb201@york.ac.uk",
				"Sergey Brin", StaffTitle.DOCTOR, 2);
		getAdministratorController().addStaff("cl202", "cl202", "cl202@york.ac.uk",
				"Cali Lewis", StaffTitle.MISS, 2);
		getAdministratorController().addStaff("lm203", "lm203", "lm203@york.ac.uk",
				"Loic Le Meur", StaffTitle.DOCTOR, 2);
				
		// Group 4 - 1 people
		getAdministratorController().addStaff("me401", "me401", "me401@york.ac.uk",
				"Mike Elgan", StaffTitle.PROFESSOR, 4);
		
		// Group 9 - 1 people
		getAdministratorController().addStaff("cp901", "cp901", "cp901@york.ac.uk",
				"Chris Pirillon", StaffTitle.MASTER, 9);
		getAdministratorController().assignCoordinator("cp901");
		
		getAdministratorController().loginout("Admin", null);
		
		//////////////// login as academic staff to add publication seminar details ////////////////
		
		// Group 1 - hr101(Howard Rheingold) - 2 publication
		getGeneralUserController().loginout("hr101", "hr101");
		Set<String> authorSet_101_1 = new HashSet<String>();
		authorSet_101_1.add("Linda Lawrey");
		Date date_101_1 = new Date();
		date_101_1.setYear(100);
		getAcademicStaffController().addBook("BK0001", "Modeling Information Sciences",
				authorSet_101_1, date_101_1, "University of Calgary Publishing", "Calgary, CA");
		Set<String> authorSet_101_2 = new HashSet<String>();
		Date date_101_2 = new Date();
		date_101_2.setYear(105);
		getAcademicStaffController().addConferencePaper("CP0001", "Testing Culture",
				authorSet_101_2, date_101_2, "New York, US");
		getAcademicStaffController().loginout("hr101", null);
		
		// Group 1 - tr102(Tim O'Reilly) - 0 publication, 1 seminar
		getGeneralUserController().loginout("tr102", "tr102");
		getCoordinatorController().addSeminar(1, "Requirements Engineering", "15:00", "01, Jan, 2012", "University of York");
		getCoordinatorController().loginout("tr102", null);
		
		// Group 1 - ll103(Linda Lawrey) - 1 publication
		getGeneralUserController().loginout("ll103", "ll103");
		Set<String> authorSet_103_1 = new HashSet<String>();
		authorSet_103_1.add("Tim O'Reilly");
		authorSet_103_1.add("Robert Scoble");
		Date date_103_1 = new Date();
		date_103_1.setYear(105);
		getAcademicStaffController().addJournalPaper("JP0001", "Defining Quality Intuitively",
				authorSet_103_1, date_103_1, "Technical Report", "TR-201-74", "pp.35-37");
		getAcademicStaffController().loginout("ll103", null);

		// Group 2 - sb201(Sergey Brin) - 3 publication
		getGeneralUserController().loginout("sb201", "sb201");
		Set<String> authorSet_201_1 = new HashSet<String>();
		authorSet_201_1.add("Robert Scoble");
		authorSet_201_1.add("Mike Elgan");
		Date date_201_1 = new Date();
		date_201_1.setYear(100);
		getAcademicStaffController().addBook("BK0002", "Essential Software Architecture", authorSet_201_1,
				date_201_1, "Dorset Hourse", "Southern California, US");
		Set<String> authorSet_201_2 = new HashSet<String>();
		authorSet_201_2.add("Amber Mac");
		authorSet_201_2.add("Zee M Kane");
		Date date_201_2 = new Date();
		date_201_2.setYear(105);
		getAcademicStaffController().addBook("BK0003", "Metrics for Small Projects", authorSet_201_2,
				date_201_2, "Prentice Hall", "London, UK");
		Set<String> authorSet_201_3 = new HashSet<String>();
		authorSet_201_3.add("Zee M Kane");
		authorSet_201_3.add("Mike Elgan");
		Date date_201_3 = new Date();
		date_201_3.setYear(112);
		getAcademicStaffController().addJournalPaper("JP0002", "Stakeholders in Requirements Engineering",
				authorSet_201_3, date_201_3, "IEEE Software, vol.24, no.2", "IEEE-3467-90", "pp.18-20");
		getAcademicStaffController().loginout("sb201", null);

		// Group 2 - cl202(Cali Lewis) - 1 publication
		getGeneralUserController().loginout("cl202", "cl202");
		Set<String> authorSet_202_1 = new HashSet<String>();
		authorSet_202_1.add("Chris Pirillon");
		Date date_202_1 = new Date();
		date_202_1.setYear(112);
		getAcademicStaffController().addConferencePaper("CP0002", "Elements of Software Science",
				authorSet_202_1, date_202_1, "North Holland");
		getAcademicStaffController().loginout("cl202", null);

		// Group 2 - lm203(Loic Le Meur) - 1 publication
		getGeneralUserController().loginout("lm203", "lm203");
		Set<String> authorSet_203_1 = new HashSet<String>();
		authorSet_203_1.add("Cali Lewis");
		Date date_203_1 = new Date();
		date_203_1.setYear(103);
		getAcademicStaffController().addConferencePaper("CP0003", "Aspect Oriented Programming",
				authorSet_203_1, date_203_1, "York, UK");
		getAcademicStaffController().loginout("lm203", null);

		// Group 9 - cp901(Chris Pirillon) - 1 publication, 2 seminars
		getGeneralUserController().loginout("cp901", "cp901");
		Set<String> authorSet_901_1 = new HashSet<String>();
		authorSet_901_1.add("Howard Rheingold");
		authorSet_901_1.add("Linus Torvalds");
		authorSet_901_1.add("Linda Lawrey");
		Date date_901_1 = new Date();
		date_901_1.setYear(112);
		getCoordinatorController().addBook("BK0004", "Languages for Formal Specification",
				authorSet_901_1, date_901_1, "Prentice Hall", "London, UK");
		getCoordinatorController().addSeminar(2, "Integrated Envirenment", "13:00", "01, Mar, 2012", "Kings College, London");
		getCoordinatorController().addSeminar(3, "Improving the Design of Existing Code", "14:00", "01, Apr, 2012", "University of York");
		getCoordinatorController().loginout("cp901", null);
	}
	
	/**
	 * Test <code>loginout(String userName, String password)</code>.
	 */
	@Test
	public void testLoginout() {
		GeneralUserController generalUserController = getGeneralUserController();
		AcademicStaffController academicStaffController = getAcademicStaffController();
		
		// normal login and logout with correct userName and password for a academic staff
		assertEquals(UserRole.ACADEMIC_STAFF, generalUserController.loginout("sb201", "sb201"));
		assertEquals(UserRole.GENERAL_USER, academicStaffController.loginout("sb201", null));

		// normal login and logout with correct userName and password for a coordinator
		assertEquals(UserRole.COORDINATOR, generalUserController.loginout("tr102", "tr102"));
		assertEquals(UserRole.GENERAL_USER, academicStaffController.loginout("tr102", null));
		
		// should fail when try to login with wrong userName or password
		assertEquals(UserRole.GENERAL_USER, generalUserController.loginout("hr101", "wrong password"));
		assertEquals(UserRole.GENERAL_USER, academicStaffController.loginout("wrong userName", "hr101"));
		
		// should still be login if try to login more than once
		assertEquals(UserRole.ACADEMIC_STAFF, generalUserController.loginout("hr101", "hr101"));
		assertEquals(UserRole.ACADEMIC_STAFF, generalUserController.loginout("hr101", "hr101"));
		
		// should fail if try to logout before login
		assertEquals(UserRole.GENERAL_USER, generalUserController.loginout("hr101", null));
	}

	/**
	 * Test <code>changePassword(String oldPassword, String newPassword)</code>.
	 */
	@Test
	public void testChangePassword() {
		GeneralUserController generalUserController = getGeneralUserController();
		AcademicStaffController academicStaffController = getAcademicStaffController();
		
		// normal sequence to change password and login by new password
		generalUserController.loginout("ll103", "ll103");
		assertEquals(true, academicStaffController.changePassword("ll103", "newPassword"));
		academicStaffController.loginout("ll103", null);

		assertEquals(UserRole.ACADEMIC_STAFF, generalUserController.loginout("ll103", "newPassword"));
		
		// should fail when try to change to new password, but providing a wrong old password 
		assertEquals(false, academicStaffController.changePassword("ll103", "newPassword"));
	}
	
	/**
	 * Test <code>editEmail(String email)</code> and 
	 * <code>editProfile(String fullName, StaffTitle title, int researchGroup)</code>.
	 */
	@Test
	public void testEditProfile() {
		GeneralUserController generalUserController = getGeneralUserController();
		AcademicStaffController academicStaffController = getAcademicStaffController();
		
		generalUserController.loginout("cl202", "cl202");

		// check before updating profile
		assertEquals("Cali Lewis", repository.getStaffMap().get("cl202").getFullName());
		assertEquals(StaffTitle.MISS, repository.getStaffMap().get("cl202").getTitle());
		assertEquals("cl202@york.ac.uk", repository.getStaffMap().get("cl202").getEmail());
		assertEquals(2, repository.getStaffMap().get("cl202").getGroup());
		assertEquals(true, repository.getGroupMap().get(2).containsStaff("cl202"));
		assertEquals(false, repository.getGroupMap().get(6).containsStaff("cl202"));
		
		// update profile
		academicStaffController.editEmail("newEmail");
		academicStaffController.editProfile("newFullName", StaffTitle.PROFESSOR, 6);

		// check after updating profile
		assertEquals("newFullName", repository.getStaffMap().get("cl202").getFullName());
		assertEquals(StaffTitle.PROFESSOR, repository.getStaffMap().get("cl202").getTitle());
		assertEquals("newEmail", repository.getStaffMap().get("cl202").getEmail());
		assertEquals(6, repository.getStaffMap().get("cl202").getGroup());
		assertEquals(false, repository.getGroupMap().get(2).containsStaff("cl202"));
		assertEquals(true, repository.getGroupMap().get(6).containsStaff("cl202"));
				
		academicStaffController.loginout("cl202", null);
	}
	
	/**
	 * Test <code>editBook(String IDNumber, String publicationTitle, Set<String> authorSet,
	 *		Date publishDate, String publisherName, String publishPlace)</code>.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testEditBook() {
		GeneralUserController generalUserController = getGeneralUserController();
		AcademicStaffController academicStaffController = getAcademicStaffController();
		
		generalUserController.loginout("cp901", "cp901");
		
		// check BK0004 details before editing
		assertEquals("Languages for Formal Specification", academicStaffController.getPublicationTitle("BK0004"));
		assertEquals(4, academicStaffController.getPublicationAuthorSet("BK0004").size());
		assertEquals(true, academicStaffController.getPublicationAuthorSet("BK0004").contains("Chris Pirillon"));
		assertEquals(true, academicStaffController.getPublicationAuthorSet("BK0004").contains("Howard Rheingold"));
		assertEquals(true, academicStaffController.getPublicationAuthorSet("BK0004").contains("Linus Torvalds"));
		assertEquals(true, academicStaffController.getPublicationAuthorSet("BK0004").contains("Linda Lawrey"));
		assertEquals(112, academicStaffController.getPublicationDate("BK0004").getYear());
		assertEquals("Prentice Hall", academicStaffController.getBookPublisherName("BK0004"));
		assertEquals("London, UK", academicStaffController.getBookPublishPlace("BK0004"));
		
		Set<String> newAuthor = new HashSet<String>();
		newAuthor.add("newAuthor");
		Date newDate = new Date();
		newDate.setYear(99);
		assertEquals(true, academicStaffController.editBook("BK0004", "newTitle",
				newAuthor, newDate, "newPublisher", "newPlace"));
		
		// check BK0004 details after editing
		assertEquals("newTitle", academicStaffController.getPublicationTitle("BK0004"));
		assertEquals(1, academicStaffController.getPublicationAuthorSet("BK0004").size());
		assertEquals(true, academicStaffController.getPublicationAuthorSet("BK0004").contains("newAuthor"));
		assertEquals(99, academicStaffController.getPublicationDate("BK0004").getYear());
		assertEquals("newPublisher", academicStaffController.getBookPublisherName("BK0004"));
		assertEquals("newPlace", academicStaffController.getBookPublishPlace("BK0004"));
		
		// should fail if try to edit a book does not exist
		assertEquals(false, academicStaffController.editBook("BK0000", "newTitle",
				newAuthor, newDate, "newPublisher", "newPlace"));
		
		academicStaffController.loginout("cp901", null);
	}
	
	/**
	 * Test <code>editJournalPaper(String IDNumber, String publicationTitle, Set<String> authorSet,
	 *		Date publishDate, String journalName, String issueNumber, String pageNumber)</code>.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testEditJournal() {
		GeneralUserController generalUserController = getGeneralUserController();
		AcademicStaffController academicStaffController = getAcademicStaffController();
		
		generalUserController.loginout("ll103", "ll103");
		
		// check JP0001 details before editing
		assertEquals("Defining Quality Intuitively", academicStaffController.getPublicationTitle("JP0001"));
		assertEquals(3, academicStaffController.getPublicationAuthorSet("JP0001").size());
		assertEquals(true, academicStaffController.getPublicationAuthorSet("JP0001").contains("Linda Lawrey"));
		assertEquals(true, academicStaffController.getPublicationAuthorSet("JP0001").contains("Tim O'Reilly"));
		assertEquals(true, academicStaffController.getPublicationAuthorSet("JP0001").contains("Robert Scoble"));
		assertEquals(105, academicStaffController.getPublicationDate("JP0001").getYear());
		assertEquals("TR-201-74", academicStaffController.getJournalIssueNumber("JP0001"));
		assertEquals("Technical Report", academicStaffController.getJournalName("JP0001"));
		assertEquals("pp.35-37", academicStaffController.getJournalPageNumber("JP0001"));
		
		Set<String> newAuthor = new HashSet<String>();
		newAuthor.add("newAuthor");
		Date newDate = new Date();
		newDate.setYear(99);
		assertEquals(true, academicStaffController.editJournalPaper("JP0001", "newTitle",
				newAuthor, newDate, "newJournal", "newIssueNumber", "newPageNumber"));

		// check JP0001 details before editing
		assertEquals("newTitle", academicStaffController.getPublicationTitle("JP0001"));
		assertEquals(1, academicStaffController.getPublicationAuthorSet("JP0001").size());
		assertEquals(true, academicStaffController.getPublicationAuthorSet("JP0001").contains("newAuthor"));
		assertEquals(99, academicStaffController.getPublicationDate("JP0001").getYear());
		assertEquals("newIssueNumber", academicStaffController.getJournalIssueNumber("JP0001"));
		assertEquals("newJournal", academicStaffController.getJournalName("JP0001"));
		assertEquals("newPageNumber", academicStaffController.getJournalPageNumber("JP0001"));
		
		// should fail if try to edit a journal paper does not exist
		assertEquals(false, academicStaffController.editJournalPaper("JP0000", "newTitle",
				newAuthor, newDate, "newJournal", "newIssueNumber", "newPageNumber"));
		
		academicStaffController.loginout("ll103", null);
	}
	
	/**
	 * Test <code>editConferencePaper(String IDNumber, String publicationTitle, Set<String> authorSet,
	 *		Date publishDate, String place)</code>.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testEditConference() {
		GeneralUserController generalUserController = getGeneralUserController();
		AcademicStaffController academicStaffController = getAcademicStaffController();
		
		generalUserController.loginout("lm203", "lm203");
		
		// check CP0003 details before editing
		assertEquals("Aspect Oriented Programming", academicStaffController.getPublicationTitle("CP0003"));
		assertEquals(2, academicStaffController.getPublicationAuthorSet("CP0003").size());
		assertEquals(true, academicStaffController.getPublicationAuthorSet("CP0003").contains("Loic Le Meur"));
		assertEquals(true, academicStaffController.getPublicationAuthorSet("CP0003").contains("Cali Lewis"));
		assertEquals(103, academicStaffController.getPublicationDate("CP0003").getYear());
		assertEquals("York, UK", academicStaffController.getConferencePlace("CP0003"));
				
		Set<String> newAuthor = new HashSet<String>();
		newAuthor.add("newAuthor");
		Date newDate = new Date();
		newDate.setYear(99);
		assertEquals(true, academicStaffController.editConferencePaper("CP0003", "newTitle",
				newAuthor, newDate, "newPlace"));

		// check CP0003 details before editing
		assertEquals("newTitle", academicStaffController.getPublicationTitle("CP0003"));
		assertEquals(1, academicStaffController.getPublicationAuthorSet("CP0003").size());
		assertEquals(true, academicStaffController.getPublicationAuthorSet("CP0003").contains("newAuthor"));
		assertEquals(99, academicStaffController.getPublicationDate("CP0003").getYear());
		assertEquals("newPlace", academicStaffController.getConferencePlace("CP0003"));
		
		// should fail if try to edit a conference paper does not exist
		assertEquals(false, academicStaffController.editConferencePaper("CP0000", "newTitle",
				newAuthor, newDate, "newPlace"));
		
		academicStaffController.loginout("lm203", null);
	}
	
	/**
	 * Test <code>addBook(String IDNumber, String publicationTitle, Set<String> authorSet,
	 *		Date publishDate, String publisherName, String publishPlace)</code>.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testAddBook() {
		GeneralUserController generalUserController = getGeneralUserController();
		AcademicStaffController academicStaffController = getAcademicStaffController();
		
		generalUserController.loginout("tr102", "tr102");

		// check before adding a new book
		assertEquals(9, repository.getPublicationMap().size());

		// add a new book
		Set<String> newAuthor = new HashSet<String>();
		newAuthor.add("newAuthor");
		Date newDate = new Date();
		newDate.setYear(99);
		assertEquals(true, academicStaffController.addBook("BK0005", "newTitle",
				newAuthor, newDate, "newPublisher", "newPlace"));

		// check after adding a new book
		assertEquals(true, repository.getPublicationMap().containsKey("BK0005"));
		assertEquals(10, repository.getPublicationMap().size());
		
		// should fail if try to add a duplication book
		assertEquals(false, academicStaffController.addBook("BK0005", "newTitle",
				newAuthor, newDate, "newPublisher", "newPlace"));
		
		academicStaffController.loginout("tr102", null);
	}
	
	/**
	 * Test <code>addJournalPaper(String IDNumber, String publicationTitle, Set<String> authorSet,
	 *		Date publishDate, String journalName, String issueNumber, String pageNumber)</code>.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testAddJournalPaper() {
		GeneralUserController generalUserController = getGeneralUserController();
		AcademicStaffController academicStaffController = getAcademicStaffController();
		
		generalUserController.loginout("tr102", "tr102");

		// check before adding a new journal paper
		assertEquals(9, repository.getPublicationMap().size());

		// add a new journal paper
		Set<String> newAuthor = new HashSet<String>();
		newAuthor.add("newAuthor");
		Date newDate = new Date();
		newDate.setYear(99);
		assertEquals(true, academicStaffController.addJournalPaper("JP0003", "newTitle",
				newAuthor, newDate, "newJournal", "newIssueNumber", "newPageNumber"));

		// check after adding a new journal paper
		assertEquals(true, repository.getPublicationMap().containsKey("JP0003"));
		assertEquals(10, repository.getPublicationMap().size());
		
		// should fail if try to add a duplication journal paper
		assertEquals(false, academicStaffController.addJournalPaper("JP0003", "newTitle",
				newAuthor, newDate, "newJournal", "newIssueNumber", "newPageNumber"));
		
		academicStaffController.loginout("tr102", null);
	}
	
	/**
	 * Test <code>addConferencePaper(String IDNumber, String publicationTitle, Set<String> authorSet,
	 *		Date publishDate, String place)</code>.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testAddConferencePaper() {
		GeneralUserController generalUserController = getGeneralUserController();
		AcademicStaffController academicStaffController = getAcademicStaffController();
		
		generalUserController.loginout("tr102", "tr102");

		// check before adding a new conference paper
		assertEquals(9, repository.getPublicationMap().size());

		// add a new conference paper
		Set<String> newAuthor = new HashSet<String>();
		newAuthor.add("newAuthor");
		Date newDate = new Date();
		newDate.setYear(99);
		assertEquals(true, academicStaffController.addConferencePaper("CP0004", "newTitle",
				newAuthor, newDate, "newPlace"));

		// check after adding a new conference paper
		assertEquals(true, repository.getPublicationMap().containsKey("CP0004"));
		assertEquals(10, repository.getPublicationMap().size());
		
		// should fail if try to add a duplication conference paper
		assertEquals(false, academicStaffController.addConferencePaper("CP0004", "newTitle",
				newAuthor, newDate, "newPlace"));
		
		academicStaffController.loginout("tr102", null);
	}

}
