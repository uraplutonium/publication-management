package control.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import control.Repository;

import model.enumeration.StaffTitle;
import model.group.Group;
import model.publication.Book;
import model.publication.ConferencePaper;
import model.publication.JournalPaper;
import model.publication.Publication;
import model.user.AcademicStaff;
/**
 * The <code>AcademicStaffController</code> class is a control class that provides
 * operations for academic staff. This class extends from {@link control.controller.RegisteredUserController RegisteredUserController},
 * which means AcademicStaffController have all the functions of  RegisteredUserController. In addition, AcademicStaffController provide
 * the function to add {@link model.publication.Publication Publication} which can be {@link Book Book}, {@link ConferencePaper ConferencePaper} 
 * and {@link JournalPaper JournalPaper} and check the duplication at the same time.
 * <p>
 * 
 * @see Repository
 * @see AcademicStaff
 * @see Publication
 */  
public class AcademicStaffController extends RegisteredUserController {
	private Map<String, AcademicStaff> staffcollection;            //store AcademicStaff in the Map, Staff id is the key
	private Map<String, Publication> publicationcollection;        //store Publication in the Map, Staff id is the key
	

	/**
	 * Creates a <code>AcademicStaffController</code> instance with the specified
     * repository.
     * 
	 *@param repository
	 * 		  The reference of <code>repository</code> in <code>PMSystem</code>
	 */
	public AcademicStaffController(Repository repository) {
		super(repository);
	}

	/**
	 * <strong>editProfile</strong> is used to edit user's own profile. The detials which can be 
	 * changed could be fullName, title and researchGroup
	 * 
	 * @param fullName  The full name of academic staff
	 * @param title     The title of academic staff
	 * @param researchGroup  The research group number which academic staff belongs to
	 * 
	 * @see AcademicStaff
	 * @see Repository
	 */
	public void editProfile(String fullName, StaffTitle title, int researchGroup) {
		staffcollection=repository.getStaffMap();
		AcademicStaff staff=(AcademicStaff) staffcollection.get(Repository.currentUserName);   //get current user name
		staff.setFullName(fullName);                           //set new fullName
		staff.setTitle(title);                                 //set new title
		//may or may not required
		staffcollection.put(Repository.currentUserName, staff);        //refresh the map
		
		if(researchGroup != staff.getGroup()) {
			Group oldGroup = repository.getGroupMap().get(staff.getGroup());	// get group number of current group
			Group newGroup = repository.getGroupMap().get(researchGroup);		// get group number of the new group
			oldGroup.removeStaff(staff.getUserName());                          //remove academic staff from the original group
			newGroup.addStaff(staff.getUserName());                             //add academic staff to the new group
			
			// update staff's group information
			staff.setGroup(researchGroup);
		}

		repository.updateProfileFile();	
	}
	
	/**
	 * <strong>addBook</strong> provide the method to write new book to the file. 
	 * 
	 * @param IDNumber             The unique ID for book
	 * @param publicationTitle     The title of book
	 * @param authorSet            The authors of book
	 * @param publishDate          The upload time of book
	 * @param publisherName        The publisher name of book
	 * @param publishPlace         The publish place of book
	 * @return              Return boolean. If add book successfully, it will return true. Else, return false
	 * 
	 * @see Book
	 * @see Repository
	 * @see Publication
	 */
	// Write authority for own publications
	public boolean addBook(String IDNumber, String publicationTitle, Set<String> authorSet,
			Date publishDate, String publisherName, String publishPlace) {
		publicationcollection=repository.getPublicationMap();                 //get the collection of all the publication
		boolean duplicate = checkDuplicationOfBookPublication(publicationTitle,publisherName,publishPlace); //check the duplication of book
		if(duplicate){ 
			//if duplication appear, add book failed
			return false;       
		}
		else   //if duplication not appear
		{
			AcademicStaff staff = repository.getStaffMap().get(Repository.currentUserName); //get current academic staff
			String uploaderUserName = staff.getUserName();                       //get username
			authorSet.add(staff.getFullName());                                  //add current username to the author set. The uploader must be the one of the author
			Book newbook=new Book(IDNumber,publicationTitle,authorSet,publishDate,publisherName,publishPlace,uploaderUserName);//initialize new publication
			
	//		System.out.println("AS controller. before add book: " + staff.getSetOfPublication());
			
			staff.addPublication(IDNumber);         
			
	//		System.out.println("AS controller. after add book: " + staff.getSetOfPublication());
			
			publicationcollection.put(IDNumber, newbook);    //put new book to the publication collection with unique ID
			
			repository.updateProfileFile();                  //update profile file
			repository.updatePublicationFile();	             //update publication file
			
			int pubSize = repository.getMapSize().get("pubIDAutoIncrement")+1;          //get publication number
			repository.getMapSize().put("pubIDAutoIncrement", pubSize);                  //put the 
			repository.updateMapSizeFile();                                              //update the mapSeze file
			return true;
		}
	}
	
	/**
	 * checkDuplicationOfBookPublication provides the function to avoid add duplicated book to the file
	 * 
	 * @param publicationTitle   The title of book
	 * @param publishDate        The upload time of book
	 * @param publisherName      The publisher name of book
	 * @param publishPlace       The publish place of book
	 * @return                   return boolean. If duplication appear, it will return true. Else, return true.
	 * 
	 * @see Book
	 * @see Publication
	 * @see Repository
	 */
	private boolean checkDuplicationOfBookPublication(String publicationTitle, 
			String publisherName, String publishPlace) {
		
		Iterator<Entry<String, Publication>> it = publicationcollection.entrySet().iterator();    //iterate all publication of the collection
		while (it.hasNext()) { 
			Map.Entry<String, Publication> entry = it.next();                   //if it has next record
			Publication value = entry.getValue();                               //get each publication
			 
			if(value.getIDNumber().startsWith("BK")){                           //if the type of publication is book
				Book entryBook = (Book) entry.getValue();
			
				String bookTitle = entryBook.getPublicationTitle();             //get publication title
				String bookPublisherName = entryBook.getPublisherName();        //get publisher name
				String bookPublishPlace = entryBook.getPublishPlace();          //get pulish place
			
				if(publicationTitle.equals(bookTitle) && publisherName.equals(bookPublisherName) && publishPlace.equals(bookPublishPlace)){
					return true;                                               //if duplication appear, return true
				}
			 }
		}
		return false;                            //if not apear, return false
	}
	
	/**
	 * checkDuplicationOfJournalPaperPublications provides the function to avoid add duplicated journalpaper to the file
	 * 
	 * @param publicationTitle     The article title
	 * @param journalName          The jouranl name
	 * @param pageNumber           The page number of article
	 * @param issueNumber          The issue number of journal
	 * @param publishDate          The upload time of journal
	 * @return                     return boolean. If duplication appear, it will return true. Else, return true.
	 * 
	 * @see Publication
	 * @see JournalPaper
	 * @see Repository
	 */
	private boolean checkDuplicationOfJournalPaperPublications(String publicationTitle, String journalName, 
			String pageNumber, String issueNumber){
		//conference paper
		Iterator<Entry<String, Publication>> it = publicationcollection.entrySet().iterator();  //iterate all publication of the collection
		while (it.hasNext()) {
			Map.Entry<String, Publication> entry = it.next();        //if it has next record
			Publication value = (Publication) entry.getValue();      //get each publication
			
			if(value.getIDNumber().startsWith("JP")){                //if the type of publication is journal
				JournalPaper entryJP = (JournalPaper) entry.getValue();        //get the entity of journal
				String jpPublicationTitle = entryJP.getPublicationTitle();     //get the journal title
				//Date jpPublishDate = entryJP.getPublishDate();                 //get upload time
				String jpJournalName = entryJP.getJournalName();               //get journal name
				String jpPageNumber = entryJP.getPageNumber();                 //get article page number
				String jpIssueNumber = entryJP.getIssueNumber();               //get issue number of journal
				
				if(publicationTitle.equals(jpPublicationTitle) && journalName.equals(jpJournalName)
						&& pageNumber.equals(jpPageNumber) && issueNumber.equals(jpIssueNumber) ){
					return true;                                             //if duplication appear, return true
				}		
			}
		}
		return false;                                       //if not appear, return false
	}
		
	/**
	 * checkDuplicationOfConferencePaperPublication provides the function to avoid add duplicated conferencepaper to the file
	 * 
	 * @param publicationTitle  The title of confernece paper
	 * @param publishDate       The upload time of conference paper
	 * @param place             The place of conference paper
	 * @return                  return boolean. If duplication appear, it will return true. Else, return true.
	 * 
	 * @see Publication
	 * @see ConferencePaper
	 * @see Repository
	 */
	private boolean checkDuplicationOfConferencePaperPublication(String publicationTitle, String place){
			
		Iterator<Entry<String, Publication>> it = publicationcollection.entrySet().iterator();       //iterate all publication of the collection
		while (it.hasNext()) {
			Map.Entry<String, Publication> entry = it.next();                  //if it has next record
			Publication value = (Publication) entry.getValue();                 //get each publication
			
			if(value.getIDNumber().startsWith("CP")){                          //if the type of publication is conference paper
				ConferencePaper entryCP = (ConferencePaper) entry.getValue();  //get the entity of conference paper
				String cpPublicationTitle = entryCP.getPublicationTitle();     //get conference title
				String cpPlace = entryCP.getPlace();                           //get conference place
				if(publicationTitle.equals(cpPublicationTitle) && place.equals(cpPlace)){
					return true;                                       //if duplication appear, return true
				}
			}
		}
		//when there is no publication in the repository
		return false;
	}

/**
 * <strong>addJournalPaper</strong> provide the method to write new Journal Paper to the file. 
 *  
 * @param IDNumber                     The unique ID of Journal Paper
 * @param publicationTitle             The title of Journal article
 * @param authorSet                    The authors of Journal
 * @param publishDate                  The upload time of Journal
 * @param journalName                  The name of Journal
 * @param issueNumber                  The issue number of Journal
 * @param pageNumber                   The page number of Journal
 * @return            Return boolean. If add Journal Paper successfully, it will return true. Else, return false
 * 
 * @see Publication
 * @see JournalPaper
 * @see Repository
 * 
 */
	public boolean addJournalPaper(String IDNumber, String publicationTitle, Set<String> authorSet,
			Date publishDate, String journalName, String issueNumber, String pageNumber) {
		
		publicationcollection=repository.getPublicationMap();                  //get the collection of all the publication
		boolean duplicate= checkDuplicationOfJournalPaperPublications(publicationTitle,journalName, //check the duplication of journal
	             pageNumber,issueNumber);
		if(duplicate){
			//if duplication appear, return false
			return false;
		}
		else  //if duplication not appear
		{
			AcademicStaff staff = repository.getStaffMap().get(Repository.currentUserName);  //get current academic staff
			String uploaderUserName = staff.getUserName();                           //get uploader name
			authorSet.add(staff.getFullName());                                      //add current username to the author set. The uploader must be the one of the author
			JournalPaper newjournal=new JournalPaper(IDNumber,publicationTitle,authorSet,   //initialize new journal
					publishDate, journalName, issueNumber, pageNumber, uploaderUserName);
			publicationcollection.put(IDNumber, newjournal);                          //put the new journal into the publication collection
			staff.addPublication(IDNumber);                                           
			
			repository.updatePublicationFile();                                        //update publication file
			repository.updateProfileFile();                                            //update profile file
			
			int pubSize = repository.getMapSize().get("pubIDAutoIncrement")+1;         //get the publication number
			repository.getMapSize().put("pubIDAutoIncrement", pubSize);                 
			repository.updateMapSizeFile();                                             //update mapsize file
			return true;
		}

	}

/**
 * <strong>addConferencePaper</strong> provide the method to write new Conference Paper to the file. 
 *  
 * @param IDNumber              The unique ID of conference paper
 * @param publicationTitle      The title of conference paper
 * @param authorSet             The author set of conference paper
 * @param publishDate           The upload time of conference paper
 * @param place                 The place of conference paper
 * @return                    Return boolean. If add Conference Paper successfully, it will return true. Else, return false
 * 
 * @see Publication
 * @ss ConferencePaper
 * @see Repository
 */
	public boolean addConferencePaper(String IDNumber, String publicationTitle, Set<String> authorSet,
			Date publishDate, String place) {
		publicationcollection=repository.getPublicationMap();               //get the collection of all the publication
		boolean duplicate= checkDuplicationOfConferencePaperPublication(publicationTitle, //check the duplication of conference
	             place);
		if(duplicate){
			//if duplication appear, return false
			return false;
		}
		else      //if duplication not appear
		{
			AcademicStaff staff = repository.getStaffMap().get(Repository.currentUserName);   //get current academic staff
			String uploaderUserName = staff.getUserName();                            //get uploader name
			authorSet.add(staff.getFullName());                                        //add current username to the author set. The uploader must be the one of the author
			ConferencePaper newConferencePaper=new ConferencePaper(IDNumber,publicationTitle, authorSet,//initialize new conference Paper
					publishDate, place, uploaderUserName);
			publicationcollection.put(IDNumber,newConferencePaper);                      //put the new conference paper into the publication collection
			staff.addPublication(IDNumber);
			
			repository.updatePublicationFile();                                        //update publication file
			repository.updateProfileFile();                                             //update profile file
			
			int pubSize = repository.getMapSize().get("pubIDAutoIncrement")+1;           //get the publication number
			repository.getMapSize().put("pubIDAutoIncrement", pubSize);
			repository.updateMapSizeFile();                                               //update mapsize file
			return true;
		}
	}
		
	/**
	 *<strong> editBook </strong> provides the method to change the details of book.
	 * 
	 * @param IDNumber             The unique ID for book
	 * @param publicationTitle     The title of book
	 * @param authorSet            The authors of book
	 * @param publishDate          The upload time of book
	 * @param publisherName        The publisher name of book
	 * @param publishPlace         The publish place of book
	 * @return              Return boolean. If edit book successfully, it will return true. Else, return false
	 * 
	 * @see Publication
	 * @see Book
	 * @see Repository
	 */
	public boolean editBook(String IDNumber, String publicationTitle, Set<String> authorSet,
			Date publishDate, String publisherName, String publishPlace) {
		
		publicationcollection=repository.getPublicationMap();                  //get teh publication collection
		Book bookToEdit = (Book) publicationcollection.get(IDNumber);          //get the book to edit according to the ID
		if(bookToEdit!=null){                                                 //if the book exist
			bookToEdit.setPublicationTitle(publicationTitle);                  //set the new details of book
			bookToEdit.setPublishDate(publishDate);
			bookToEdit.setPublisherName(publisherName);
			bookToEdit.setPublishPlace(publishPlace);
			bookToEdit.setAuthorSet(authorSet);
			
			repository.updatePublicationFile();                                 //update publication file
			return true; 
		} else {                                                               //if failed, return false
			return false;
		}
	}
	
	/**
	 * <strong> editJournalPaper </strong> provides the method to change the details of JournalPaper.
	 *  
	 * @param IDNumber                     The unique ID of Journal Paper
	 * @param publicationTitle             The title of Journal article
	 * @param authorSet                    The authors of Journal
	 * @param publishDate                  The upload time of Journal
	 * @param journalName                  The name of Journal
	 * @param issueNumber                  The issue number of Journal
	 * @param pageNumber                   The page number of Journal
	 * @return                            Return boolean. If edit JournalPaper successfully, it will return true. Else, return false
	 * 
	 * @see Publication
	 * @see JournalPaper
	 * @see Repository
	 */
	public boolean editJournalPaper(String IDNumber, String publicationTitle, Set<String> authorSet,
			Date publishDate, String journalName, String issueNumber, String pageNumber) {
		publicationcollection=repository.getPublicationMap();                             //get teh publication collection
		JournalPaper JPToEdit = (JournalPaper) publicationcollection.get(IDNumber);       //get the journal to edit according to the ID
		if(JPToEdit!= null){                                                             //if the journal exist
			JPToEdit.setAuthorSet(authorSet);                                             //set the new details of journal
			JPToEdit.setIssueNumber(issueNumber);
			JPToEdit.setJournalName(journalName);
			JPToEdit.setPageNumber(pageNumber);
			JPToEdit.setPublicationTitle(publicationTitle);
			JPToEdit.setPublishDate(publishDate);
			
			repository.updatePublicationFile();                                           //update publication file
			return true;
		} else {                                                                          //if failed, return false
			return false;	           
		}
		
	}
	
	/**
	 * <strong> editConferencePaper </strong> provides the method to change the details of ConferencePaper
	 * 
	 * @param IDNumber              The unique ID of conference paper
	 * @param publicationTitle      The title of conference paper
	 * @param authorSet             The author set of conference paper
	 * @param publishDate           The upload time of conference paper
	 * @param place                 The place of conference paper
	 * @return         Return boolean. If edit ConferencePaper successfully, it will return true. Else, return false
	 * 
	 * @see Publication
	 * @see ConferencePaper
	 * @see Repository
	 */
	public boolean editConferencePaper(String IDNumber, String publicationTitle, Set<String> authorSet,
			Date publishDate, String place) {
		publicationcollection=repository.getPublicationMap();                              //get teh publication collection
		ConferencePaper CPToEdit = (ConferencePaper) publicationcollection.get(IDNumber);   //get the conference to edit according to the ID
		if(CPToEdit!=null){                                                               //if the conference exist
			CPToEdit.setPublicationTitle(publicationTitle);                                //set the new details of conference
			CPToEdit.setAuthorSet(authorSet);
			CPToEdit.setPlace(place);
			CPToEdit.setPublishDate(publishDate);
			 
			repository.updatePublicationFile();                                            //update publication file
			return true;
		} else {                                                                            //if failed, return false
		return false;
		}
	}
	
	/**
	 * createPubID is used to generate the publication ID
	 * 
	 * @param type    the type of publication
	 * @return        return the unique ID of publication
	 */
	public String createPubID(String type){
		return type+repository.getMapSize().get("pubIDAutoIncrement");
	}
	
}
