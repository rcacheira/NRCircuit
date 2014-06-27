package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;

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
	 * @param coordinate The FinalTerminal coordinate
	 */
	public FinalTerminal(Coordinate coordinate, char letter) {
		super(coordinate);
		this.letter = letter;
	}

	@Override
	public boolean canBeCrossed(Direction direction) {
		return direction.from == Position.CENTER ||
				direction.to == Position.CENTER;
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
