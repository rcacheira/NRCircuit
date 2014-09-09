package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.connectors.NumberedConnector;

public class OrderControl {

	int lastOrder;
	
	public OrderControl() {
		lastOrder = 0;
	}

	public boolean canLinkPlace(Place place){
		if(place instanceof NumberedConnector)
			return ((NumberedConnector)place).orderNumber - lastOrder == 1;
		return true;
	}
	
	public void linkedPlace(Place place){
		if(place instanceof NumberedConnector)
			lastOrder = ((NumberedConnector)place).orderNumber;
	}
	
	public void unlikedPlace(Place place){
		if(place instanceof NumberedConnector
				&& ((NumberedConnector)place).orderNumber <= lastOrder)
			lastOrder = ((NumberedConnector)place).orderNumber-1;
	}
	
}
