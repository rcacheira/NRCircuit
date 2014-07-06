package isel.leic.poo.nrcircuit.model;

/**
 * class whose instance represents a coordinate on grid. Coordinates are represented as 
 * rectangular coordinates (i.e. x and y) that must be positive values (i.e. >= 0).
 * 
 * @author rcacheira & nreis
 *
 */
public class Position {
	
	/**
	 * number of positions to maintain in memory
	 * 
	 * the positions to save in memory will be (positionsToSave x positionsToSave)
	 */
	private static int positionsToSave = 5;
	
	/**
	 * array of positions saved in memory
	 */
	private static Position[][] positions = new Position[positionsToSave][positionsToSave];
	
	/**
	 * sets new number of positions to maintain in memory
	 * 
	 * the positions to save in memory will be (nr x nr)
	 * 
	 * @param Number of positions
	 */
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
	
	/**
	 * get's a position with given parameters
	 * 
	 * @param row position's row
	 * @param column position's column
	 * @return position matching the given parameters
	 */
	public static Position get(int row, int column){
		if(column >= 0 && column < positionsToSave && row >= 0 && row < positionsToSave){
			if(positions[row][column] == null){
				positions[row][column] = new Position(row , column);
			}
			return positions[row][column];
		}
		else return new Position(row, column);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if(!(obj instanceof Position))
			return false;
		Position c = (Position)obj;
		return column == c.column && row == c.row;
	}
}
