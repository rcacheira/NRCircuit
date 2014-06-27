package isel.leic.poo.nrcircuit.model.connectors;

import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;

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
	 * @param coordinate The OneWayConnector coordinate
	 * @param orientation The OneWayConnector oritentation
	 */
	public OneWayConnector(Coordinate coordinate, Orientation orientation) {
		super(coordinate);
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
	public boolean canBeCrossed(Direction direction) {
		switch(orientation){
			case HORIZONTAL:
				return direction.from == Position.LEFT && direction.to == Position.RIGHT || 
						direction.from == Position.RIGHT && direction.to == Position.LEFT;
			case VERTICAL:
				return direction.from == Position.UP && direction.to == Position.DOWN || 
						direction.from == Position.DOWN && direction.to == Position.UP;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof OneWayConnector)
			return ((OneWayConnector)obj).orientation == orientation;
		
		return false;
	}
	
}
