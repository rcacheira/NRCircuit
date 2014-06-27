package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector.Orientation;
import isel.leic.poo.nrcircuit.model.terminals.FinalTerminal;
import isel.leic.poo.nrcircuit.model.terminals.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class whose instance represents a Circuit Grid
 * 
 * Grid has a level, width and a height
 * 
 * @author rcacheira & nreis
 *
 */
public class Grid {
	/**
	 * grid level
	 */
	private final int level;
	
	/**
	 * Horizontal number of places
	 */
	private final int width;
	
	/**
	 * Vertical number of places
	 */
	private final int height;
	
	/**
	 * Array of places
	 */
	private Place grid[][];
	
	/**
	 * List of paths
	 */
	private List<Path> paths;
	
	/**
	 * Path being draw
	 */
	private Path workingPath;
	
	/**
	 * Creates a new instance of grid with the given parameters
	 * 
	 * @param level Grid Level
	 * @param width horizontal number of places 
	 * @param height vertical number of places
	 */
	private Grid(int level, int width, int height) {
		this.level = level;
		this.width = width;
		this.height = height;
		
		grid = new Place[width][height];
		paths = new ArrayList<Path>(10);
		
		workingPath = null;
	}
	
	/**
	 * Verifies if coordinate is in bounds
	 * 
	 * @param coordinate Coordinate of Place
	 * @return {@code true} if either x and y is greater or equal to zero 
	 * and x is less then width and y is less than height {@code false} otherwise
	 */
	private boolean isCoordinateWithinBounds(Coordinate coordinate)
	{
		return !(coordinate.x < 0 || coordinate.x >= width || coordinate.y < 0 || coordinate.y >= height);
	}
	
	public Place getPlaceAtPosition(Coordinate coordinate){
		if(!isCoordinateWithinBounds(coordinate))
			throw new IllegalArgumentException();
		
		return grid[coordinate.x][coordinate.y];
	}
	
	public boolean setWorkingPath(Coordinate coordinate, Direction direction){
		Place place = getPlaceAtPosition(coordinate);
		for (Path path : paths) {
			if(path.hasPlace(place) && place.canBeCrossed(direction)){
				workingPath = path;
				workingPath.clear(place);
				return true;
			}
		}
		if(place instanceof Terminal && place.canBeCrossed(direction)){
			workingPath = new Path((Terminal)place);
			paths.add(workingPath);
			return true;
		}
		return false;
	}
	
	public boolean doCross(Coordinate coordinate, Direction direction){
		if(!isCoordinateWithinBounds(coordinate))
			throw new IllegalArgumentException();
		
		if(workingPath == null || workingPath.isFull())
			return false;
		
		Place place = workingPath.getLastPlace();
		
		int xDelta = Math.abs(place.coordinate.x - coordinate.x);
		int yDelta = Math.abs(place.coordinate.y - coordinate.y);
		
		if(!(xDelta == 1 && yDelta == 0 || xDelta == 0 && yDelta == 1) )
			throw new IllegalStateException();
		
		if(place instanceof Terminal && ((Terminal) place).getLetter() != workingPath.getLetter())
			return false;
		
		if(getPlaceAtPosition(coordinate) instanceof ProhibitedPlace){
			return false;
		}
		
		if(!getPlaceAtPosition(coordinate).canBeCrossed(direction)){
			return false;
		}
		
		workingPath.add(getPlaceAtPosition(coordinate));
		
		return true;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getSize(){
		return width*height;
	}
	
	public boolean isComplete(){
		boolean[][] crossed = new boolean[width][height];
		
		for (Path path : paths) {
			for (Place place : path) {
				crossed[place.coordinate.x][place.coordinate.y] = true;
			}
		}
	
		for (int i = 0; i < crossed.length; i++) {
			for (int j = 0; j < crossed[i].length; j++) {
				if(!crossed[i][j] && !(grid[i][j] instanceof ProhibitedPlace)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Creates ine grid 
	 * @param line
	 * @return
	 * @throws FileBadFormatException
	 */
	private static Grid loadLevelAndSize(String line) throws FileBadFormatException{
		int init = line.indexOf('#');
		if(init == -1) throw new FileBadFormatException("# not found on line 1");
		int fin = line.indexOf(' ', init);
		if(fin == -1)  throw new FileBadFormatException("<space> after # not found on line 1");
		
		int level = Integer.parseInt(line.substring(init+1, fin));
		
		init = fin;
		fin = line.indexOf('x', init);
		if(fin == -1)  throw new FileBadFormatException("x of size not found on line 1");
		
		int width = Integer.parseInt(line.substring(init+1, fin));
		
		int height = Integer.parseInt(line.substring(fin+1));
		
		return new Grid(level, width, height);
	}
	
	private static Place createPlace(int line, int column, String prmtr) throws FileBadFormatException{
		if(prmtr.equals(".")){
			return new Connector(Coordinate.get(line, column));
		}
		if(prmtr.equals("|")){
			return new OneWayConnector(Coordinate.get(line, column), Orientation.VERTICAL);
		}
		if(prmtr.equals("-")){
			return new OneWayConnector(Coordinate.get(line, column), Orientation.HORIZONTAL);
		}
		if(prmtr.equals("*")){
			return new ProhibitedPlace(Coordinate.get(line, column));
		}
		if(prmtr.length() == 1 && (prmtr.charAt(0) >= 'A' && prmtr.charAt(0) <= 'Z'
				|| prmtr.charAt(0) >= 'a' && prmtr.charAt(0) <= 'z')){
			return new FinalTerminal(Coordinate.get(line, column), prmtr.toUpperCase().charAt(0));
		}
		throw new FileBadFormatException("line: " + line+1 + " unsupported parameter: " + prmtr);
	}
	
	private static void loadLineGrid(Grid grid, int lineLoading, String line) throws FileBadFormatException{
		String[] prmtrs = line.split(" ");
		if(prmtrs.length != grid.width){
			throw new FileBadFormatException("line: " + lineLoading+1 + " less parameters then width");
		}
		int prmtrIdx = 0;
		for (int i = 0; i < prmtrs.length; i++) {
			grid.grid[lineLoading][prmtrIdx] = createPlace(lineLoading, prmtrIdx, prmtrs[i]);
			prmtrIdx++;
		}
	}
	
	public static Grid loadGrid(BufferedReader bufReader) throws IOException, FileBadFormatException{
		Grid grid = null;

		String line=null;
		if((line=bufReader.readLine()) == null){
			return null;
		}
		
		grid = loadLevelAndSize(line);
		
		int nLines = 0;
		
		while( (line=bufReader.readLine()) != null )
		{
			if(nLines >= grid.height){
				throw new FileBadFormatException("More lines on file than height");
			}
			loadLineGrid(grid, nLines++, line);
		}
		if(nLines < grid.width){
			throw new FileBadFormatException("Less lines on file than height");
		}
		
		return grid;
	}
	public static class FileBadFormatException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public FileBadFormatException(String str){
			super(str);
		}
	}
}
