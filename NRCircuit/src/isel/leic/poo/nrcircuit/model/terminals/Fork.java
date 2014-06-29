package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Place;
import isel.leic.poo.nrcircuit.model.Position;

/**
 * class whose instance represents a fork.
 * 
 * @author rcacheira & nreis
 *
 */
public class Fork extends Terminal {
	
	private char letter;
	
	/**
	 * The instance's orientation
	 */
	private final Orientation orientation;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position The Fork position
	 * @param orientation The Fork orientation
	 */
	public Fork(Position position, Orientation orientation) {
		super(position);
		this.orientation = orientation;
	}

	public static enum Orientation{
		VERTICAL_RIGHT,
		VERTICAL_LEFT,
		HORIZONTAL_UP,
		HORIZONTAL_DOWN
	}

	private boolean checkHorizontalCross(int rDelta, int cDelta){
		return Math.abs(cDelta) == 1 && rDelta == 0
				|| cDelta == 0 && rDelta == (orientation == Orientation.HORIZONTAL_UP ? 1 : -1);
	}
	
	private boolean checkVerticalCross(int rDelta, int cDelta){
		return cDelta == 0 && Math.abs(rDelta) == 1
				|| rDelta == 0 && cDelta == (orientation == Orientation.VERTICAL_RIGHT ? -1 : 1);
	}
	
	@Override
	public boolean canBeLinkedWith(Place place) {
		int cDelta = position.column - place.position.column;
		int rDelta = position.row - place.position.row;
		
		return orientation == Orientation.HORIZONTAL_DOWN || orientation == Orientation.HORIZONTAL_UP ? 
				checkHorizontalCross(rDelta, cDelta) : 
				checkVerticalCross(rDelta, cDelta);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof Fork)
			return ((Fork)obj).orientation == orientation;
		
		return false;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	@Override
	public char getLetter() {
		return letter;
	}
}
