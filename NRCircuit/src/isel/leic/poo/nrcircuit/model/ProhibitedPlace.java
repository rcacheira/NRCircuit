package isel.leic.poo.nrcircuit.model;

/**
 * class whose instance represents a forbidden passage
 * 
 * @author rcacheira & nreis
 *
 */
public class ProhibitedPlace extends Place {

	/**
	 * initiates an instance with the given parameters
	 * 
	 * @param coordinate The Prohibited Place coordinate
	 */
	public ProhibitedPlace(Coordinate coordinate) {
		super(coordinate);
	}

	@Override
	public boolean canBeCrossed(Direction direction) {
		return false;
	}
	
}