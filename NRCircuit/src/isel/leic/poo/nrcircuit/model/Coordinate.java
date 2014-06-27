package isel.leic.poo.nrcircuit.model;

/**
 * class whose instance represents a coordinate on grid. Coordinates are represented as 
 * rectangular coordinates (i.e. x and y) that must be positive values (i.e. >= 0).
 * 
 * @author rcacheira & nreis
 *
 */
public class Coordinate {
	
	private static int nrOfCoordinateToSave = 5;
	
	private static Coordinate[][] coordinates = new Coordinate[nrOfCoordinateToSave][nrOfCoordinateToSave];
	
	public static void setNrOfCoordinatesToSave(int nr){
		nrOfCoordinateToSave = nr;
		coordinates = new Coordinate[nrOfCoordinateToSave][nrOfCoordinateToSave];
	}
	
	/**
	 * Position coordinates values
	 */
	public final int x, y;
	
	/**
	 * Initiates an instance with the given coordinates.
	 * 
	 * @param x The horizontal coordinate value
	 * @param y The vertical coordinate value
	 * @throws IllegalArgumentException if either coordinate has a negative value
	 */
	private Coordinate(int x, int y) {
		if(x<0 || y<0)
				throw new IllegalArgumentException("Negative coordinate value");
		this.x = x;
		this.y = y;
	}
	
	public static Coordinate get(int x, int y){
		if(x >= 0 && x < nrOfCoordinateToSave && y >= 0 && y < nrOfCoordinateToSave){
			if(coordinates[x][y] == null){
				coordinates[x][y] = new Coordinate(x, y);
			}
			return coordinates[x][y];
		}
		else return new Coordinate(x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if(!(obj instanceof Coordinate))
			return false;
		Coordinate c = (Coordinate) obj;
		return c.x == x && c.y == y;
	}
}
