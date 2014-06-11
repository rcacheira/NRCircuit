package isel.leic.poo.nrcircuit.model;

/**
 * class whose instance represents a coordinate on grid. Coordinates are represented as 
 * rectangular coordinates (i.e. x and y) that must be positive values (i.e. >= 0).
 * 
 * @author rcacheira & nreis
 *
 */
public class Coordinate {
	/**
	 * Position coordinates values
	 */
	private final int x, y;
	
	/**
	 * Initiates an instance with the given coordinates.
	 * 
	 * @param x The horizontal coordinate value
	 * @param y The vertical coordinate value
	 * @throws IllegalArgumentException if either coordinate has a negative value
	 */
	public Coordinate(int x, int y) {
		if(x<0 || y<0)
				throw new IllegalArgumentException("Negative coordinate value");
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the position's horizontal coordinate.
	 * 
	 * @return The horizontal coordinate value
	 */
	public int getX() {
		return x;
	}
	/**
	 * Gets the position's vertical coordinate.
	 * 
	 * @return The vertical coordinate value
	 */
	public int getY() {
		return y;
	}
}
