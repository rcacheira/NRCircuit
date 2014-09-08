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
	 * @param position The Prohibited Place position
	 */
	public ProhibitedPlace(Position position) {
		super(position, 0);
	}

	@Override
	public boolean canBeLinkedTo(Place place) {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && obj instanceof ProhibitedPlace;
	}
	
}