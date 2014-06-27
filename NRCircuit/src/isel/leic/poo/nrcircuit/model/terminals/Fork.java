package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;

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
	 * @param coordinate The Fork coordinate
	 * @param orientation The Fork orientation
	 */
	public Fork(Coordinate coordinate, Orientation orientation) {
		super(coordinate);
		this.orientation = orientation;
	}

	public static enum Orientation{
		VERTICAL_RIGHT,
		VERTICAL_LEFT,
		HORIZONTAL_UP,
		HORIZONTAL_DOWN
	}

	private boolean checkHorizontalCross(Direction direction, Position branch){
		if(branch == Position.RIGHT || branch == Position.LEFT)
			throw new IllegalArgumentException("branch can't be left or right");
		return direction.from == Position.LEFT && direction.to == Position.RIGHT
				|| direction.from == Position.RIGHT && direction.to == Position.LEFT
				|| direction.from == Position.CENTER && direction.to == branch
				|| direction.to == Position.CENTER && direction.from == branch
				|| (direction.from == Position.CENTER || direction.from == branch)
					&& (direction.to == Position.LEFT || direction.to == Position.RIGHT)
				|| (direction.to == Position.CENTER || direction.to == branch)
						&& (direction.from == Position.LEFT || direction.from == Position.RIGHT);
	}
	
	private boolean checkVerticalCross(Direction direction, Position branch){
		if(branch == Position.UP || branch == Position.DOWN)
			throw new IllegalArgumentException("branch can't be up or down");
		return direction.from == Position.UP && direction.to == Position.DOWN
				|| direction.from == Position.DOWN && direction.to == Position.UP
				|| direction.from == Position.CENTER && direction.to == branch
				|| direction.to == Position.CENTER && direction.from == branch
				|| (direction.from == Position.CENTER || direction.from == branch)
					&& (direction.to == Position.UP || direction.to == Position.DOWN)
				|| (direction.to == Position.CENTER || direction.to == branch)
					&& (direction.from == Position.UP || direction.from == Position.DOWN);
	}
	
	@Override
	public boolean canBeCrossed(Direction direction) {
		switch(orientation){
			case HORIZONTAL_UP: return checkHorizontalCross(direction, Position.UP);
			case HORIZONTAL_DOWN: return checkHorizontalCross(direction, Position.DOWN);
			case VERTICAL_LEFT: return checkVerticalCross(direction, Position.LEFT);
			case VERTICAL_RIGHT: return checkVerticalCross(direction, Position.RIGHT);
		}
		return false;
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
