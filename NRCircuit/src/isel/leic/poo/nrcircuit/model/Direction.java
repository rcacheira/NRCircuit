package isel.leic.poo.nrcircuit.model;

/**
 * class whose instance represents a direction.
 * 
 * @author rcacheira & nreis
 *
 */
public class Direction {
	/**
	 * Direction position possible values
	 * 
	 * @author rcacheira & nreis
	 *
	 */
	public static enum Position{
		UP,
		DOWN,
		LEFT,
		RIGHT,
		CENTER
	}
	
	private static Direction[][] directions = new Direction[5][5];
	
	/**
	 * Direction position values
	 */
	public final Position from, to;
	
	/**
	 * Instantiate an instance with the given parameters
	 * 
	 * @param from initial position
	 * @param to final position
	 * @throws IllegalArgumentException if either from or to is null or from 
	 * and to positions are equal
	 */
	private Direction(Position from, Position to) {
		if(from == null || to == null) throw new IllegalArgumentException("any position can't be null");
		if(from == to) throw new IllegalArgumentException("from can't equals to");
		
		this.from = from;
		this.to = to;
	}
	
	public static Direction get(Position from, Position to){
		if(from == null || to == null) throw new IllegalArgumentException("any position can't be null");
		if(directions[from.ordinal()][to.ordinal()] == null){
			directions[from.ordinal()][to.ordinal()] = new Direction(from,to);
		}
		return directions[from.ordinal()][to.ordinal()];
	}
}
