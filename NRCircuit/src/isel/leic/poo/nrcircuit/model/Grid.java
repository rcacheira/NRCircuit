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
	public static interface OnPlacesRemovedFromPathListener{
		public void onPlacesRemovedFromPath(List<Place> placesRemoved);
	}
	
	/**
	 * grid level
	 */
	private final int level;
	
	/**
	 * Horizontal number of places
	 */
	private final int columns;
	
	/**
	 * Vertical number of places
	 */
	private final int rows;
	
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
	
	private OnPlacesRemovedFromPathListener placesRemovedFromPathListener;
	
	public void setPlacesRemovedFromPathListener(
			OnPlacesRemovedFromPathListener placesRemovedFromPathListener) {
		this.placesRemovedFromPathListener = placesRemovedFromPathListener;
	}
	
	/**
	 * Creates a new instance of grid with the given parameters
	 * 
	 * @param level Grid Level
	 * @param width horizontal number of places 
	 * @param height vertical number of places
	 */
	private Grid(int level, int width, int height) {
		this.level = level;
		this.columns = width;
		this.rows = height;
		
		grid = new Place[height][width];
		paths = new ArrayList<Path>(10);
		
		workingPath = null;
	}
	
	/**
	 * Verifies if coordinate is in bounds
	 * 
	 * @param position Coordinate of Place
	 * @return {@code true} if either x and y is greater or equal to zero 
	 * and x is less then width and y is less than height {@code false} otherwise
	 */
	private boolean isCoordinateWithinBounds(Position position)
	{
		return !(position.column < 0 || position.column >= columns || position.row < 0 || position.row >= rows);
	}
	
	public Place getPlaceAtPosition(Position position){
		if(!isCoordinateWithinBounds(position))
			throw new IllegalArgumentException();
		
		return grid[position.row][position.column];
	}
	
	private void clearPaths(Place place){
		ArrayList<Place> placesRemoved = new ArrayList<Place>();
		for (Path path : paths) {
			if(path.hasPlace(place)){
				workingPath = path;
				placesRemoved.addAll(path.clear(place));
			}
		}

		if(placesRemovedFromPathListener != null){
			placesRemovedFromPathListener.onPlacesRemovedFromPath(placesRemoved);
		}
	}
	
	public boolean setWorkingPath(Position position){
		Place place = getPlaceAtPosition(position);
		if(place instanceof ProhibitedPlace)
			return false;
		clearPaths(place);
		if(place instanceof Terminal){
			workingPath = new Path((Terminal)place);
			paths.add(workingPath);
			return true;
		}
		return false;
	}
	
	public boolean doLink(Position position){
		Place place = getPlaceAtPosition(position);
		
		if(place instanceof ProhibitedPlace || workingPath == null 
				|| workingPath.isFull()
				|| place instanceof Terminal 
					&& ((Terminal) place).getLetter() != workingPath.getLetter())
			return false;
		
		Place lastPlace = workingPath.getLastPlace();
		
		if(!lastPlace.canBeLinkedTo(place) || !place.canBeLinkedTo(lastPlace)){
			return false;
		}

		clearPaths(place);
		workingPath.add(place);
		
		return true;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public int getSize(){
		return columns*rows;
	}

	public Path getWorkingPath() {
		return workingPath;
	}
	
	public boolean isComplete(){
		boolean[][] linked = new boolean[rows][columns];
		
		for (Path path : paths) {
			for (Place place : path) {
				linked[place.position.row][place.position.column] = true;
			}
		}
		
		for (int row = 0; row < linked.length; row++) {
			for (int column = 0; column < linked[row].length; column++) {
				if(!linked[row][column] && !(grid[row][column] instanceof ProhibitedPlace)){
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
	
	private static Place createPlace(int row, int column, String prmtr) throws FileBadFormatException{
		if(prmtr.equals(".")){
			return new Connector(Position.get(row, column));
		}
		if(prmtr.equals("|")){
			return new OneWayConnector(Position.get(row, column), Orientation.VERTICAL);
		}
		if(prmtr.equals("-")){
			return new OneWayConnector(Position.get(row, column), Orientation.HORIZONTAL);
		}
		if(prmtr.equals("*")){
			return new ProhibitedPlace(Position.get(row, column));
		}
		if(prmtr.length() == 1 && (prmtr.charAt(0) >= 'A' && prmtr.charAt(0) <= 'Z'
				|| prmtr.charAt(0) >= 'a' && prmtr.charAt(0) <= 'z')){
			return new FinalTerminal(Position.get(row, column), prmtr.charAt(0));
		}
		throw new FileBadFormatException("line: " + row+1 + " unsupported parameter: " + prmtr);
	}
	
	private static void loadLineGrid(Grid grid, int row, String line) throws FileBadFormatException{
		String[] prmtrs = line.split(" ");
		if(prmtrs.length != grid.columns){
			throw new FileBadFormatException("line: " + row+1 + " less parameters then columns");
		}
		int prmtrIdx = 0;
		for (int i = 0; i < prmtrs.length; i++) {
			grid.grid[row][prmtrIdx] = createPlace(row, prmtrIdx, prmtrs[i]);
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
		
		int nRows = 0;
		
		while( (line=bufReader.readLine()) != null )
		{
			if(nRows >= grid.rows){
				throw new FileBadFormatException("More rows on file than rows size");
			}
			loadLineGrid(grid, nRows++, line);
		}
		if(nRows < grid.columns){
			throw new FileBadFormatException("Less rows on file than rows size");
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
