package isel.leic.poo.nrcircuit.model.connectors;

import isel.leic.poo.nrcircuit.model.Position;
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
	 * @param position The Connector position
	 */
	public Connector(Position position) {
		super(position, 1);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && obj instanceof Connector;
	}
	
}
