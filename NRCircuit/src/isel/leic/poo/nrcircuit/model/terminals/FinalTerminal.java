package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Position;

/**
 * class whose instance represents a color terminal
 *  
 * @author rcacheira & nreis
 *
 */
public class FinalTerminal extends Terminal {
	
	private char letter;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position The FinalTerminal position
	 */
	public FinalTerminal(Position position, char letter) {
		super(position);
		this.letter = letter;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof FinalTerminal)
			return ((FinalTerminal)obj).letter == letter;
		
		return false;
	}
	
	@Override
	public char getLetter() {
		return letter;
	}

}
