package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Place;
import isel.leic.poo.nrcircuit.model.Position;

/**
 * class whose instance represents the initial or final position of a path
 * 
 * @author rcacheira & nreis
 *
 */
public abstract class Terminal extends Place {
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position The terminal position
	 */
	public Terminal(Position position) {
		super(position);
	}
	
	/**
	 * Gets a terminal letter
	 * 
	 * @return
	 */
	public abstract char getLetter();
	
}
