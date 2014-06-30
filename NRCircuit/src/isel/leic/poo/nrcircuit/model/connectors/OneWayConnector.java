package isel.leic.poo.nrcircuit.model.connectors;

import isel.leic.poo.nrcircuit.model.Place;
import isel.leic.poo.nrcircuit.model.Position;

/**
 * class whose instance represents a path passage point in only on direction, 
 * vertically or horizontally
 * 
 * @author rcacheira & nreis
 *
 */
public class OneWayConnector extends Connector {
	/**
	 * OneWayConnector orientation possible values
	 * 
	 * @author rcacheira & nreis
	 *
	 */
	public static enum Orientation{
		HORIZONTAL,
		VERTICAL
	}
	
	/**
	 * The instance's orientation
	 */
	private final Orientation orientation;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position The OneWayConnector position
	 * @param orientation The OneWayConnector orientation
	 */
	public OneWayConnector(Position position, Orientation orientation) {
		super(position);
		this.orientation = orientation;
	}
	
	/**
	 * Gets the OneWayConnector's direction
	 * @return
	 */
	public Orientation getOrientation() {
		return orientation;
	}
	
	@Override
	public boolean canBeLinkedTo(Place place) {

		int cDelta = Math.abs(place.position.column - position.column);
		int rDelta = Math.abs(place.position.row - position.row);
		
		return orientation == Orientation.HORIZONTAL ? 
				cDelta == 1 && rDelta == 0 : 
				cDelta == 0 && rDelta == 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof OneWayConnector)
			return ((OneWayConnector)obj).orientation == orientation;
		
		return false;
	}
	
}
