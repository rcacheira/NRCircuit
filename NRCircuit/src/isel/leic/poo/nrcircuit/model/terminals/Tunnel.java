package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Position;

/**
 * class whose instance represents a tunnel to other side of board.
 * Tunnels is always connected to a brother. So a grid should have always a even
 * number of tunnels
 * 
 * @author rcacheira & nreis
 *
 */
public class Tunnel extends Terminal {

	char letter;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position the Tunnel position
	 */
	public Tunnel(Position position) {
		super(position);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof Tunnel)
			return ((Tunnel)obj).letter == letter;
		
		return false;
	}
	
	/**
	 * sets Tunnel letter
	 * 
	 * @param letter
	 */
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	@Override
	public char getLetter() {
		return letter;
	}
}
