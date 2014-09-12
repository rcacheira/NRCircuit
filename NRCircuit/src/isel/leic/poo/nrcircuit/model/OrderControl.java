package isel.leic.poo.nrcircuit.model;

import java.util.HashMap;
import java.util.Map;

import isel.leic.poo.nrcircuit.model.connectors.NumberedConnector;

public class OrderControl {

	private Map<Character, Integer> lastOrders;
	
	public OrderControl() {
		lastOrders = new HashMap<Character, Integer>();
	}

	public boolean canLinkPlace(char letter, Place place){
		if(place instanceof NumberedConnector){
			if(lastOrders.containsKey(letter)){
				return ((NumberedConnector)place).orderNumber - lastOrders.get(letter) == 1;
			}
			return ((NumberedConnector)place).orderNumber == 1;
		}
		return true;
	}
	
	public void linkedPlace(Place place){
		if(place instanceof NumberedConnector)
			lastOrders.put(place.getLetter(), ((NumberedConnector)place).orderNumber);
	}
	
	public void unlinkedPlace(char letter, Place place){
		if(place instanceof NumberedConnector
				&& lastOrders.containsKey(letter)
				&& ((NumberedConnector)place).orderNumber <= lastOrders.get(letter)){
			lastOrders.put(letter, ((NumberedConnector)place).orderNumber-1);
		}
	}
	
}
