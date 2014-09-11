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
	
	protected final Place[] links;
	protected Place previousLink;
	private final int nLinks;
	private int linksUsed;
	
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
		links = new Place[nLinks];
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
	
	public void clearPreviousLinks(List<Link> linksCleared){
		if(previousLink != null){
			previousLink.clearPreviousLinks(linksCleared);
			return;
		}
		deleteLinks(linksCleared);
	}
	
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
				if(links[i] != null){
					linksCleared.add(new Link(this.position, links[i].position));
					links[i].deleteLinks(linksCleared);
					links[i].previousLink = null;
					links[i].especificClearWork();
					links[i] = null;
				}
			}
			linksUsed = 0;
		}
		
	}
	
	protected void especificClearWork(){
		setLetter(NO_LETTER);
	}
	
	public void addLink(Place place){
		if(place == null)
			throw new IllegalArgumentException("place can't be null");
		if(linksUsed < nLinks){
			links[linksUsed++] = place;
		}
		else throw new IllegalStateException("Trying to add more than possible links");
	}
	
	public void addPreviousLink(Place place){
		if(place == null)
			throw new IllegalArgumentException("place can't be null");
		if(previousLink == null){
			previousLink = place;
		}
		else throw new IllegalStateException("Trying to add more than possible previous links");
	}
	
	public boolean isLinkedWith(Place place){
		for (Place link : links) {
			if(link != null && (link == place || link.isLinkedWith(place)))
				return true;
		}
		return false;
	}
	
	public boolean isFullLinked(){
		return nLinks == linksUsed;
	}
	
	public Place getPrevious() {
		return previousLink;
	}
	
	public char getLetter() {
		return letter;
	}
	
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
