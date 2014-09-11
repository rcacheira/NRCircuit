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
		super(position, 2);
		this.orientation = orientation;
	}

	/**
	 * Fork's Orientation
	 * 
	 * @author rcacheira & nreis
	 *
	 */
	public static enum Orientation{
		VERTICAL_RIGHT,
		VERTICAL_LEFT,
		HORIZONTAL_UP,
		HORIZONTAL_DOWN
	}
	
	/**
	 * Gets the Fork's direction
	 * @return
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * checks if delta to horizontal link
	 * 
	 * @param rDelta row delta (destiny-origin)
	 * @param cDelta column delta (destiny-origin)
	 * @return
	 */
	private boolean checkHorizontalLink(int rDelta, int cDelta){
		return Math.abs(cDelta) == 1 && rDelta == 0
				|| cDelta == 0 && rDelta == (orientation == Orientation.HORIZONTAL_UP ? -1 : 1);
	}
	
	/**
	 * checks if delta to vertical link
	 * 
	 * @param rDelta row delta (destiny-origin)
	 * @param cDelta column delta (destiny-origin)
	 * @return
	 */
	private boolean checkVerticalLink(int rDelta, int cDelta){
		return cDelta == 0 && Math.abs(rDelta) == 1
				|| rDelta == 0 && cDelta == (orientation == Orientation.VERTICAL_RIGHT ? 1 : -1);
	}
	
	@Override
	public boolean canBeLinkedTo(Place place) {
		int cDelta = place.position.column - position.column;
		int rDelta = place.position.row - position.row;
		
		return orientation == Orientation.HORIZONTAL_DOWN || orientation == Orientation.HORIZONTAL_UP ? 
				checkHorizontalLink(rDelta, cDelta) : 
				checkVerticalLink(rDelta, cDelta);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof Fork)
			return ((Fork)obj).orientation == orientation;
		
		return false;
	}
	
	@Override
	protected void especificClearWork(){
		setLetter(NO_LETTER);
	}
	
	@Override
	public boolean isFullLinked() {
		return super.isFullLinked();
	}
}
