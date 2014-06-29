package isel.leic.poo.nrcircuit.model;

/**
 * class whose instance represents a circuit place
 * 
 * A place have a position
 * 
 * @author rcacheira & nreis
 *
 */
public abstract class Place {

	
	/**
	 * The instance's position
	 */
	public final Position position;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position Place's position
	 */
	public Place(Position position) {
		if(position == null)
			throw new IllegalArgumentException("position can't be null");
		this.position = position;
	}
	
	/**
	 * checks if a place can be linked to other
	 * 
	 * @param from initial place side
	 * @param to final place side
	 * @return {@code true} if a place can be crossed in given direction, 
	 * {@code false}, otherwise
	 */
	public boolean canBeLinkedWith(Place place) {
		int cDelta = Math.abs(position.column - place.position.column);
		int rDelta = Math.abs(position.row - place.position.row);
		
		return cDelta + rDelta == 1;
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
