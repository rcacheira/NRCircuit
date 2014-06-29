package isel.leic.poo.nrcircuit.model;

/**
 * class whose instance represents a coordinate on grid. Coordinates are represented as 
 * rectangular coordinates (i.e. x and y) that must be positive values (i.e. >= 0).
 * 
 * @author rcacheira & nreis
 *
 */
public class Position {
	
	private static int positionsToSave = 5;
	
	private static Position[][] positions = new Position[positionsToSave][positionsToSave];
	
	public static void setNrOfCoordinatesToSave(int nr){
		positionsToSave = nr;
		positions = new Position[positionsToSave][positionsToSave];
	}
	
	/**
	 * Position coordinates values
	 */
	public final int column, row;
	
	/**
	 * Initiates an instance with the given coordinates.
	 * 
	 * @param column The horizontal coordinate value
	 * @param row The vertical coordinate value
	 * @throws IllegalArgumentException if either coordinate has a negative value
	 */
	private Position(int row, int column) {
		if(column<0 || row<0)
				throw new IllegalArgumentException("Negative coordinate value");
		this.column = column;
		this.row = row;
	}
	
	public static Position get(int column, int line){
		if(column >= 0 && column < positionsToSave && line >= 0 && line < positionsToSave){
			if(positions[column][line] == null){
				positions[column][line] = new Position(column , line);
			}
			return positions[column][line];
		}
		else return new Position(column, line);
	}
	
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if(!(obj instanceof Position))
			return false;
		Position c = (Position)obj;
		return column == c.column && row == c.row;
	}
}
