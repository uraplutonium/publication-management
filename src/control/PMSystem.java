package control;

import java.util.ArrayList;
import javax.swing.JFrame;

import view.academicStaffUI.AddPublication;
import view.academicStaffUI.EditPublication;
import view.academicStaffUI.LoginAsStaff;
import view.academicStaffUI.ViewPublicationAsStaff;
import view.coordinatorUI.SeminarGUI;
import view.generalUserUI.HomePage;
import view.generalUserUI.ViewPublication;
import view.systemAdminUI.AssignRole;
import view.systemAdminUI.ChangeStatus;
import view.systemAdminUI.DeletePublication;
import view.systemAdminUI.RegisterUser;
import control.controller.*;
import model.enumeration.UserRole;

/**
 * The <code>PMSystem</code> class represents the publication management system,
 * which is the entry of this program, and includes the most important components
 * of this system: repository, the current frame and four controllers.
 * <p>
 * The <code>PMSystem</code> class is using singleton pattern, that makes it only
 * possible to be one instance of <code>PMSystem</code>.
 * <p>
 * The <code>PMSystem</code> class is responsible for initialisation of repository,
 * first frame, four controllers and the static final <code>groupName</code> list.
 *
 * @see Repository
 * 
 * @see GeneralUserController
 * @see AcademicStaffController
 * @see AdministratorController
 * @see CoordinatorController
 *
 * @see HomePage
 * @see ViewPublication
 * @see AddPublication
 * @see EditPublication
 * @see LoginAsStaff
 * @see ViewPublicationAsStaff
 * @see AssignRole
 * @see ChangeStatus
 * @see DeletePublication
 * @see RegisterUser
 * @see SeminarGUI
 */
public class PMSystem {
	
	/** The singleton instance of <code>PMSystem</code> */
	private static PMSystem system = null;
	
	/** The constant list of research group's names.
	 *  Used to get group name from group number by get(int index),
	 *  and to get group number from group name by indexOf(Object o).
	 */
	public static final ArrayList<String> groupName = new ArrayList<String>();
	
	/** The entity of <code>repository</code>, that save all records in this system. */
	protected Repository repository;
	
	/** The entity of <code>currentFrame</code>, which is the frame displaying. */
	private JFrame currentFrame;
	
	/** The entity of <code>generalUserController</code>, using Lazy Initialisation Pattern.
	 *  And is only used by user interfaces for general user.
	 *  
	 *  @see control.PMSystem#getGeneralUserController()
	 */
	private GeneralUserController generalUserController;
	
	/** The entity of <code>academicStaffController</code>, using Lazy Initialisation Pattern.
	 *  And is only used by user interfaces for academic staff.
	 *  
	 *  @see control.PMSystem#getAcademicStaffController()
	 */
	private AcademicStaffController academicStaffController;
	
	/** The entity of <code>administratorController</code>, using Lazy Initialisation Pattern.
	 *  And is only used by user interfaces for administrator.
	 *  
	 *  @see control.PMSystem#getAdministratorController()
	 */
	private AdministratorController administratorController;
	
	/** The entity of <code>coordinatorController</code>, using Lazy Initialisation Pattern.
	 *  And is only used by user interfaces for coordinator.
	 *  
	 *  @see control.PMSystem#getCoordinatorController()
	 */
	private CoordinatorController coordinatorController;
	
	/**
	 * Initialises a newly created {@code PMSystem} object, and assign this instance to
	 * {@link PMSystem#system}.
	 * <p>
	 * Since PMSystem is using Singleton Pattern, the constructor of <code>PMSystem</code>
	 * is protected, which could only be called by public static method {@link PMSystem#getSystemInstance()}.
	 * <p>
	 * Since using Singleton Pattern, the <code>PMSystem</code> class must not be public.
	 * And since in test cases, the constructor will be extended, the <code>PMSystem</code>
	 * must not be private. As a result, it can only be protected.
	 * <p>
	 * While initialising, {@link PMSystem#repository} will be assigned with a newly created
	 * {@link Repository}.
	 * <p>
	 * Since four controllers are using Lazy Initialisation Pattern, they will be assigned with null.
	 * <p>
	 * ArrayList {@link groupName} will be initialised with nine research groups names.
	 * 
	 * @see PMSystem#repository
	 * @see PMSystem#system
	 * @see PMSystem#academicStaffController
	 * @see PMSystem#administratorController
	 * @see PMSystem#coordinatorController
	 * @see PMSystem#generalUserController
	 * @see PMSystem#currentFrame
	 * @see PMSystem#groupName
	 */
	protected PMSystem() {
		// initialise repository with a newly created Repository object
		repository = new Repository();
		
		// initialise four controller with null
		academicStaffController = null;
		administratorController = null;
		coordinatorController = null;
		generalUserController = null;
		
		// initialise currentFrame with null
		currentFrame = null;
		
		// add nine research groups names to groupName ArrayList
		groupName.add("Advanced Computer Architectures");
		groupName.add("Artificial Intelligence");
		groupName.add("Computer Vision");
		groupName.add("Enterprise System");
		groupName.add("High Integrity Systems Engineering");
		groupName.add("Human Computer Interaction");
		groupName.add("Non-Standard Computation");
		groupName.add("Programming Languages and Systems");
		groupName.add("Real-Time Systems");
		
		// assign static attribute PMSystem.system with this object
		PMSystem.system = this;
	}
	
	/**
	 * Return the singleton instance of <code>PMSystem</code>.
	 * <p>
	 * A new instance of <code>PMSystem</code> will be created and assigned to
	 * <code>PMSystem.system</code> if it has not been initialised.
	 * 
	 * @return The singleton instance of <code>PMSystem</code>
	 */
	public static PMSystem getSystemInstance() {
		if(system == null)
			system = new PMSystem();
		return system;
	}
	
	/**
	 * Return the {@link PMSystem#generalUserController} to user interfaces for general user.
	 * New instance of <code>GeneralUserController</code> will be created and assigned to
	 * <code>generalUserController</code>, if and only if it has not been initialised.
	 * 
	 * @return The <code>generalUserController</code> of system
	 * @see GeneralUserController
	 */
	protected GeneralUserController getGeneralUserController() {
		if(generalUserController == null)
			generalUserController = new GeneralUserController(repository);
		return generalUserController;
	}
	
	/**
	 * Return the {@link PMSystem#academicStaffController} to user interfaces for general user.
	 * New instance of <code>AcademicStaffController</code> will be created and assigned to
	 * <code>academicStaffController</code>, if and only if it has not been initialised.
	 * 
	 * @return The <code>academicStaffController</code> of system
	 * @see AcademicStaffController
	 */
	protected AcademicStaffController getAcademicStaffController() {
		if(academicStaffController == null)
			academicStaffController = new AcademicStaffController(repository);
		return academicStaffController;
	}
	
	/**
	 * Return the {@link PMSystem#administratorController} to user interfaces for general user.
	 * New instance of <code>AdministratorController</code> will be created and assigned to
	 * <code>administratorController</code>, if and only if it has not been initialised.
	 * 
	 * @return The <code>administratorController</code> of system
	 * @see AdministratorController
	 */
	protected AdministratorController getAdministratorController() {
		if(administratorController == null)
			administratorController = new AdministratorController(repository);
		return administratorController;
	}
	
	/**
	 * Return the {@link PMSystem#coordinatorController} to user interfaces for general user.
	 * New instance of <code>CoordinatorController</code> will be created and assigned to
	 * <code>coordinatorController</code>, if and only if it has not been initialised.
	 * 
	 * @return The <code>coordinatorController</code> of system
	 * @see CoordinatorController
	 */
	protected CoordinatorController getCoordinatorController() {
		if(coordinatorController == null)
			coordinatorController = new CoordinatorController(repository);
		return coordinatorController;
	}
	
	/**
	 * The run() method initialises the first frame as <code>HomePage</code>.
	 * The system start running when this method is called.
	 */
	public void run() {
		currentFrame = new HomePage(getGeneralUserController());
		currentFrame.setVisible(true);
	}
	
	/**
	 * Switch to another frame. The switchCurrentFrame() method could be called by any user interfaces.
	 * 
	 * @param role
	 * 		  UserRole type parameter representing which role the current user is.
	 * @param pagenumber
	 * 		  Integer parameter representing which new frame is to be displayed.
	 * @see UserRole
	 */
	public void switchCurrentFrame(UserRole role, int pagenumber) {
		switch(role) {
			case GENERAL_USER: {
				if(pagenumber==0){			// switch to HomePage
					currentFrame = new HomePage(getGeneralUserController());
					currentFrame.setVisible(true);
				}
				else if(pagenumber==1){		// switch to ViewPublication
					currentFrame = new ViewPublication(getGeneralUserController());
					currentFrame.setVisible(true);
				}

				break;
			}
			case ADMINISTRATOR: {
				if(pagenumber==0){			// switch to RegisterUser
					currentFrame=new RegisterUser(getAdministratorController());
					currentFrame.setVisible(true);
				}
				else if(pagenumber==1){		// switch to AssignRole
					currentFrame=new AssignRole(getAdministratorController());
					currentFrame.setVisible(true);
				}
				else if(pagenumber==2){		// switch to ChangeStatus
					currentFrame=new ChangeStatus(getAdministratorController());
					currentFrame.setVisible(true);
				}
				else if(pagenumber==3){		// switch to DeletePublication
					currentFrame=new DeletePublication(getAdministratorController());
					currentFrame.setVisible(true);
				}
				break;
			}
			case ACADEMIC_STAFF: {
				if(pagenumber==0){			// switch to LoginAsStaff
					currentFrame= new LoginAsStaff(getAcademicStaffController());
					currentFrame.setVisible(true);
				}
				else if(pagenumber==1){		// switch to AddPublication
					currentFrame= new AddPublication(getAcademicStaffController());
					currentFrame.setVisible(true);
				}
				else if(pagenumber==2){		// switch to EditPublication
					currentFrame= new EditPublication(getAcademicStaffController());
					currentFrame.setVisible(true);
				}
				else if(pagenumber==3){		// switch to ViewPublicationAsStaff
					currentFrame= new ViewPublicationAsStaff(getAcademicStaffController());
					currentFrame.setVisible(true);
				}
				break;
			}
			case COORDINATOR: {			// switch to SeminarGUI
				currentFrame = new SeminarGUI(getCoordinatorController());
				currentFrame.setVisible(true);
				break;
			}
		}
	}

	/**
	 * The main() method, entry of this program, starting PMSystem.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Using getSystemInstance() to get the singleton instance of PMSystem, and run.
		PMSystem.getSystemInstance().run();
	}
	
}
