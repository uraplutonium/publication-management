package model.publication;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * The <code>Publication</code> class represents any types of publications, 
 * which could be {@link model.publication.Book Book}, or {@link model.publication.ConferencePaper ConferencePaper}
 * or {@link model.publication.JournalPaper JournalPaper}, that are all subclass of <code>Publication</code>.
 * <p>
 * The <code>Publication</code> class is abstract, which means a publication 
 * entity could only be instantiated by its subclasses, and all attributes provided 
 * by <code>Publication</code> class are those in common.
 * <p>
 * The <code>Publication</code> class implements {@link java.io.Serializable Serializable} interface, which makes 
 * it allowed to write into files by <code>ObjectStream</code>.
 *
 * @see Serializable
 * @see Book
 * @see ConferencePaper
 * @see JournalPaper
 */
public abstract class Publication implements Serializable {

	private static final long serialVersionUID = -7095850394309692746L;
	private String IDNumber;	// primary key
	private String publicationTitle;
	private Set<String> authorSet;
	private Date publishDate;
	private String uploaderUserName;

	/**
	 * Initialises a newly created {@link model.publication.Book Book}, or {@link model.publication.ConferencePaper ConferencePaper},
	 * or {@link model.publication.JournalPaper JournalPaper} object with attributes of the passed parameters.
	 * 
	 * @param IDNumber
	 *		  ID number of a {@code Publication}, which is the primary key of a {@code Publication}, 
	 *		  and is in format of "BK0000", or "CP0000", or "JP0000".
	 * @param publicationTitle
	 *		  Title of this {@code Publication}.
	 * @param authorSet
	 *		  A {@link java.util.Set Set} of author's full names, which may not be recorded in this system.
	 * @param publishDate
	 *		  The date this {@code Publication} is published, namely, is uploaded to this system.
	 *		  And is in {@link java.util.Date Date} type.
	 * @param uploaderUserName
	 *		  The userName of the one who uploads this {@code Publication}.
	 */
	protected Publication(String IDNumber, String publicationTitle, Set<String>authorSet, Date publishDate, String uploaderUserName) {
		this.IDNumber = IDNumber;
		this.publicationTitle = publicationTitle;
		this.authorSet = authorSet;
		this.publishDate = publishDate;
		this.uploaderUserName = uploaderUserName;
	}

	/**
	 * Return the publication ID.
	 * 
	 * @return The publication ID.
	 */
	public String getIDNumber() {
		return IDNumber;
	}
	
	/**
	 * Return the title of the publication.
	 * 
	 * @return The title of publication.
	 */
	public String getPublicationTitle() {
		return publicationTitle;
	}
	
	/**
	 * Return a {@link java.util.Set Set} of author's full names.
	 * 
	 * @return A <code>Set</code> of author's full names.
	 */
	public Set<String> getAuthorSet() {
		return authorSet;
	}
	
	/**
	 * Return the date this {@code Publication} is published, namely, is uploaded to this system.
	 * 
	 * @return The publishing date.
	 */
	public Date getPublishDate() {
		return publishDate;
	}
	
	/**
	 * Return the userName of the one who uploads this {@code Publication}.
	 * 
	 * @return The userName of the one who uploads this {@code Publication}.
	 */
	public String getUploaderUserName() {
		return uploaderUserName;
	}
	
	/**
	 * Set a new title of the publication.
	 * 
	 * @param newPublicationTitle
	 * 		  A new title of the publication
	 */
	public void setPublicationTitle(String newPublicationTitle) {
		publicationTitle = newPublicationTitle;
	}
	
	/**
	 * Set a {@link java.util.Set Set} of new author's full names.
	 * 
	 * @param newAuthorSet
	 * 		  A {@link java.util.Set Set} of new author's full names
	 */
	public void setAuthorSet(Set<String> newAuthorSet) {
		authorSet = newAuthorSet;
	}
	
	/**
	 * Set a new date this {@code Publication} is published, namely, is uploaded to this system.
	 * 
	 * @param newPublishDate
	 * 		  A new date this {@code Publication} is published, namely, is uploaded to this system
	 */
	public void setPublishDate(Date newPublishDate) {
		publishDate = newPublishDate;
	}
	
}
