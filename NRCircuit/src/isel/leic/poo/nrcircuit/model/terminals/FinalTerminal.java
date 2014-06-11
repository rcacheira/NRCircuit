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

	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param coordinate The FinalTerminal coordinate
	 */
	public FinalTerminal(Coordinate coordinate) {
		super(coordinate);
	}

	@Override
	public boolean canBeCrossed(Direction direction) {
		return direction.from == Position.CENTER ||
				direction.to == Position.CENTER;
	}

}
