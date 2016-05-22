package model.publication;

import java.util.Date;
import java.util.Set;

/**
 * The <code>ConferencePaper</code> class represents conference paper entities
 *  and is subclass of <code>Publication</code>.
 * <p>
 * Besides attributes provided by <code>Publication</code>, publishPlace is
 * provided by <code>ConferencePaper</code> class.
 * 
 * @see Publication
 */
public class ConferencePaper extends Publication {

	private static final long serialVersionUID = 7783882950277921913L;
	private String place;
	
	/**
	 * Initialises a newly created {@code ConferencePaper} object with attributes of the passed parameters.
	 * 
	 * @param IDNumber
	 *		  ID number of a {@code ConferencePaper}, which is the primary key of a {@code ConferencePaper}, 
	 *		  and is in format of "CP0000".
	 * @param publicationTitle
	 *		  Title of this {@code ConferencePaper}.
	 * @param authorSet
	 *		  A {@link java.util.Set Set} of author's full names, which may not be recorded in this system.
	 * @param publishDate
	 *		  The date this {@code ConferencePaper} is published, namely, is uploaded to this system.
	 *		  And is in {@link java.util.Date Date} type.
	 * @param place
	 *		  The place where this conference is hold.
	 * @param uploaderUserName
	 *		  The userName of the one who uploads this {@code ConferencePaper}.
	 */
	public ConferencePaper(String IDNumber, String publicationTitle,
			Set<String> authorSet, Date publishDate, String place, String uploaderUserName) {
		super(IDNumber, publicationTitle, authorSet, publishDate, uploaderUserName);
		this.place = place;
	}
	
	/**
	 * Return place that the conference is hold.
	 * 
	 * @return The place that the conference is hold.
	 */
	public String getPlace() {
		return place;
	}
	
	/**
	 * Set the place the conference is hold.
	 * 
	 * @param newPublishPlace
	 * 		  The new conference place.
	 */
	public void setPlace(String newPlace) {
		place = newPlace;
	}
}
