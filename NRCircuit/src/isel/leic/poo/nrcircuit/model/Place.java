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
	protected final Coordinate coordinate;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param coordinate Place's coordinate
	 */
	public Place(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	/**
	 * Gets the place's position
	 * @return
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	/**
	 * checks if a place can be crossed in one direction
	 * 
	 * @param from initial place side
	 * @param to final place side
	 * @return {@code true} if a place can be crossed in given direction, 
	 * {@code false}, otherwise
	 */
	public abstract boolean canBeCrossed(Direction direction);
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof Place))
			return false;
		Place p = (Place)obj;
		
		return p.getCoordinate().equals(coordinate);
	}
	
}
