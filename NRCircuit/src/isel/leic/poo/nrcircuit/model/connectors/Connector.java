package isel.leic.poo.nrcircuit.model.connectors;

import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;
import isel.leic.poo.nrcircuit.model.Place;

/**
 * class whose instance represents a path passage point
 * 
 * @author rcacheira & nreis
 *
 */
public class Connector extends Place {

	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param coordinate The Connector coordinate
	 */
	public Connector(Coordinate coordinate) {
		super(coordinate);
	}

	@Override
	public boolean canBeCrossed(Direction direction) {
		return direction.from != Position.CENTER && direction.to != Position.CENTER;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && obj instanceof Connector;
	}
	
}
