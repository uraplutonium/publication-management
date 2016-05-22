package model.publication;

import java.util.Date;
import java.util.Set;

/**
 * The <code>JournalPaper</code> class represents journal paper entities and
 *  is subclass of <code>Publication</code>.
 * <p>
 * Besides attributes provided by <code>Publication</code>, journalName, 
 * issureNumber and pageNumber are provided by <code>JournalPaper</code> class.
 * 
 * @see Publication
 */
public class JournalPaper extends Publication {
	
	private static final long serialVersionUID = 5777682083933249596L;
	private String journalName;
	private String issueNumber;
	private String pageNumber;
	
	/**
	 * Initialises a newly created {@code JournalPaper} object with attributes of the passed parameters.
	 * 
	 * @param IDNumber
	 *		  ID number of a {@code JournalPaper}, which is the primary key of a {@code JournalPaper}, 
	 *		  and is in format of "JP0000".
	 * @param publicationTitle
	 *		  Title of this {@code JournalPaper}.
	 * @param authorSet
	 *		  A {@link java.util.Set Set} of author's full names, which may not be recorded in this system.
	 * @param publishDate
	 *		  The date this {@code JournalPaper} is published, namely, is uploaded to this system.
	 *		  And is in {@link java.util.Date Date} type.
	 * @param journalName
	 *		  The name of journal where this {@code JournalPaper} is published.
	 * @param issueNumber
	 *		  The issureNumber of the {@code JournalPaper}.
	 * @param pageNumber
	 * 		  The pages that this {@code JournalPaper} is on the journal.
	 * @param uploaderUserName
	 *		  The userName of the one who uploads this {@code JournalPaper}.
	 */
	public JournalPaper(String IDNumber, String publicationTitle, Set<String> authorSet,
			Date publishDate, String journalName, String issueNumber, String pageNumber, String uploaderUserName) {
		super(IDNumber, publicationTitle, authorSet, publishDate, uploaderUserName);
		// TODO Auto-generated constructor stub
		this.pageNumber = pageNumber;
		this.issueNumber = issueNumber;
		this.journalName = journalName;
	}

	/**
	 * Return name of the journal where this {@code JournalPaper} is published.
	 * 
	 * @return The name of the journal.
	 */
	public String getJournalName() {
		return journalName;
	}

	/**
	 * Return the issureNumber of this {@code JournalPaper}.
	 * 
	 * @return The issureNumber of this {@code JournalPaper}.
	 */
	public String getIssueNumber() {
		return issueNumber;
	}

	/**
	 * Return pages that this {@code JournalPaper} is on the journal.
	 * 
	 * @return The pages that this {@code JournalPaper} is on the journal.
	 */
	public String getPageNumber() {
		return pageNumber;
	}

	/**
	 * Set a new name of the journal where this {@code JournalPaper} is published.
	 * 
	 * @param newJournalName
	 * 		  The new journal's name.
	 */
	public void setJournalName(String newJournalName) {
				journalName=newJournalName;
	}

	/**
	 * Set a new issureNumber of this {@code JournalPaper}.
	 * 
	 * @param newJournalName
	 * 		  The new issureNumber of this {@code JournalPaper}.
	 */
	public void setIssueNumber(String newIssueNumber) {
		issueNumber = newIssueNumber;
	}

	/**
	 * Set new pages that this {@code JournalPaper} is on the journal.
	 * 
	 * @param newJournalName
	 * 		  The new pages on the journal.
	 */
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
}