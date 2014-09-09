package isel.leic.poo.nrcircuit.model.connectors;

import isel.leic.poo.nrcircuit.model.Position;

/**
 * class whose instance represents a ordered passage point 
 * 
 * @author rcacheira & nreis
 *
 */
public class NumberedConnector extends Connector {
	
	public final int orderNumber;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position The OneWayConnector position
	 * @param orientation The OneWayConnector orientation
	 */
	public NumberedConnector(Position position, int orderNumber) {
		super(position);
		if(orderNumber < 1)
			throw new IllegalArgumentException("NumberedConnector order should be greater than 0");
		this.orderNumber = orderNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof NumberedConnector)
			return ((NumberedConnector)obj).orderNumber == orderNumber;
		
		return false;
	}
	
}
