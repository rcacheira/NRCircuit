package isel.leic.poo.nrcircuit.model;

import java.util.List;

/**
 * class whose instance represents a circuit place
 * 
 * A place have a position
 * 
 * @author rcacheira & nreis
 *
 */
public abstract class Place {
	
	public final static char NO_LETTER = 0;
	
	/**
	 * The instance's position
	 */
	public final Position position;
	
	/**
	 * followed place links
	 */
	protected final Place[] followedLinks;

	/**
	 * number of permited followed links
	 */
	private final int nLinks;
	
	/**
	 * number of followed links used
	 */
	private int linksUsed;
	
	/**
	 * previous link
	 */
	protected Place previousLink;
	
	/**
	 * place letter
	 */
	private char letter;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position Place's position
	 */
	public Place(Position position, int nLinks) {
		if(position == null)
			throw new IllegalArgumentException("position can't be null");
		this.position = position;
		this.nLinks = nLinks;
		followedLinks = new Place[nLinks];
		linksUsed = 0;
		previousLink = null;
		letter = NO_LETTER;
	}
	
	/**
	 * checks if a place can be linked to other
	 * 
	 * @param place {@link Place} to test link to
	 * @return {@code true} if a place can be linked to given place, 
	 * {@code false}, otherwise
	 */
	public boolean canBeLinkedTo(Place place) {
		if(place == null)
			throw new IllegalArgumentException("place can't be null");
		int cDelta = Math.abs(place.position.column - position.column);
		int rDelta = Math.abs(place.position.row - position.row);
		
		return cDelta + rDelta == 1;
	}
	
	/**
	 * clears previous links
	 * 
	 * @param linksCleared list to add cleared links
	 */
	public void clearPreviousLinks(List<Link> linksCleared){
		if(previousLink != null){
			previousLink.clearPreviousLinks(linksCleared);
			return;
		}
		deleteLinks(linksCleared);
	}
	
	/**
	 * clear followedLinks
	 * 
	 * @param linksCleared list to add cleared links
	 */
	public void clearLinks(List<Link> linksCleared){
		if(linksCleared == null)
			throw new IllegalArgumentException("placesCleared can't be null");
		deleteLinks(linksCleared);
	}
	
	/**
	 * Clear all links
	 * 
	 * @param linksCleared
	 */
	private void deleteLinks(List<Link> linksCleared){
		if(linksCleared == null)
			throw new IllegalArgumentException("placesCleared can't be null");
		if(linksUsed > 0){
			for (int i=0; i<linksUsed; i++) {
				if(followedLinks[i] != null){
					linksCleared.add(new Link(this.position, followedLinks[i].position));
					followedLinks[i].deleteLinks(linksCleared);
					followedLinks[i].previousLink = null;
					followedLinks[i].especificClearWork();
					followedLinks[i] = null;
				}
			}
			linksUsed = 0;
		}
		
	}
	
	/**
	 * especific clear work for any type of piece
	 */
	protected void especificClearWork(){
		setLetter(NO_LETTER);
	}
	
	/**
	 * adds a new followed link
	 * 
	 * @param place followed {@code Place}
	 */
	public void addLink(Place place){
		if(place == null)
			throw new IllegalArgumentException("place can't be null");
		if(linksUsed < nLinks){
			followedLinks[linksUsed++] = place;
		}
		else throw new IllegalStateException("Trying to add more than possible links");
	}
	
	/**
	 * adds a new previous link
	 * 
	 * @param place previous {@code place}
	 */
	public void addPreviousLink(Place place){
		if(place == null)
			throw new IllegalArgumentException("place can't be null");
		if(previousLink == null){
			previousLink = place;
		}
		else throw new IllegalStateException("Trying to add more than possible previous links");
	}
	
	/**
	 * verifies if place argument is a followed link
	 * 
	 * @param place {@code Place} to verify
	 * @return {@code true} if instance {@code Place} is followed by given 
	 * {@code Place}
	 */
	public boolean isLinkedWith(Place place){
		for (Place link : followedLinks) {
			if(link != null && (link == place || link.isLinkedWith(place)))
				return true;
		}
		return false;
	}
	
	/**
	 * verifies if instance place is fully linked
	 * 
	 * @return {@code true} if instance {@code place} is fully linked
	 */
	public boolean isFullLinked(){
		return nLinks == linksUsed;
	}
	
	/**
	 * gets the previous {@code Place}
	 * 
	 * @return previous {@code Place}
	 */
	public Place getPrevious() {
		return previousLink;
	}
	
	/**
	 * gets the instance {@code Place} letter
	 * @return the instance {@code Place} letter
	 */
	public char getLetter() {
		return letter;
	}
	
	/**
	 * sets the instance place letter
	 * 
	 * @param letter new instance {@code Place} letter
	 */
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof Place))
			return false;
		Place p = (Place)obj;
		
		return p.position.equals(position);
	}
	
}
