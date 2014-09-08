package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector.Orientation;
import isel.leic.poo.nrcircuit.model.terminals.FinalTerminal;
import isel.leic.poo.nrcircuit.model.terminals.Fork;
import isel.leic.poo.nrcircuit.model.terminals.Terminal;
import isel.leic.poo.nrcircuit.model.terminals.Tunnel;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class whose instance represents a Circuit Grid
 * 
 * Grid has a level, width and a height
 * 
 * @author rcacheira & nreis
 *
 */
public class Grid{
	
	public static class Link{
		public final Place origin;
		public final Place destiny;
		
		public Link(Place origin, Place destiny) {
			this.origin = origin;
			this.destiny = destiny;
		}
	}
	
	public static interface OnGridActionListener{
		public void onLinkClear(Iterable<Link> placesRemoved);
		public void onLinkDone(Link link, char letter);
		public void onSetTunnelsLetter(char letter);
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
	 * List of Tunnels
	 * 
	 * List of tunnel places is used in clear links time, because is needed to 
	 * check if any tunnel remain with link or is end of path, if not is needed 
	 * to remove the letter of tunnel places
	 */
	private List<Tunnel> tunnels;
	
	/**
	 * Path being draw
	 */
	private Place workingPlace;
	
	private OnGridActionListener gridActionListener;
	
	public void setLinkListener(OnGridActionListener gridActionListener) {
		this.gridActionListener = gridActionListener;
	}
	
	/**
	 * Creates a new instance of grid with the given parameters
	 * 
	 * @param level Grid Level
	 * @param columns horizontal number of places 
	 * @param rows vertical number of places
	 */
	private Grid(int level, int rows, int columns) {
		this.level = level;
		this.columns = columns;
		this.rows = rows;
		
		grid = new Place[rows][columns];
		tunnels = new LinkedList<Tunnel>();
		workingPlace = null;
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
	
	public boolean setWorkingPlace(Position position){
		Place place = getPlaceAtPosition(position);
		if(place instanceof ProhibitedPlace)
			return false;
		if(place instanceof FinalTerminal || place.getLetter() != Place.NO_LETTER){
			if(!(place instanceof Fork) || place.isFullLinked())
				clearLinks(place);
			workingPlace = place;
			return true;
		}
		return false;
	}
	
	public boolean doLink(Position position){
		Place place = getPlaceAtPosition(position);
		
		if(place instanceof ProhibitedPlace || workingPlace == null 
				|| place instanceof Terminal && place.getLetter() != Place.NO_LETTER
				&& place.getLetter() != workingPlace.getLetter())
			return false;
		
		//this check is to permit going back
		if(place.isLinkedWith(workingPlace))
			clearLinks(place);
		else{
			if(!workingPlace.canBeLinkedTo(place) || !place.canBeLinkedTo(workingPlace)){
				return false;
			}
			
			clearLinks(place);
		
			workingPlace.addLink(place);
			if(place instanceof Terminal)
				((Terminal)place).setEndOfPath(true);
			
			if(!(place instanceof FinalTerminal)){
				//to set all tunnels letter
				if(place instanceof Tunnel && place.getLetter() == Place.NO_LETTER)
					setTunnelsLetter(workingPlace.getLetter());
				else
					place.setLetter(workingPlace.getLetter());
			}
			fireOnLinkDoneEvent(new Link(workingPlace, place));
		}
		workingPlace = place;
		
		return true;
	}
	
	private void clearLinks(Place place){
		List<Link> linksCleared = new LinkedList<Link>();
		place.clearNextLinks(linksCleared);
		fireOnLinkClearEvent(linksCleared);
		checkTunnelsLinks();
	}
	
	private void checkTunnelsLinks(){
		for (Tunnel tunnel : tunnels) {
			if(tunnel.isEndOrBeginOfPath())
				return;
		}
		setTunnelsLetter(Place.NO_LETTER);
	}
	
	private void setTunnelsLetter(char letter) {
		for (Tunnel tunnel : tunnels) {
			tunnel.setLetter(letter);
		}
		fireOnSetTunnelsLetter(letter);
	}

	void fireOnLinkClearEvent(Iterable<Link> placesRemoved){
		if(gridActionListener != null){
			gridActionListener.onLinkClear(placesRemoved);
		}
	}
	
	void fireOnLinkDoneEvent(Link link){
		if(gridActionListener != null){
			gridActionListener.onLinkDone(link, link.origin.getLetter());
		}
	}
	
	void fireOnSetTunnelsLetter(char letter){
		if(gridActionListener != null){
			gridActionListener.onSetTunnelsLetter(letter);
		}
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

	public Place getWorkingPlace() {
		return workingPlace;
	}
	
	public boolean isComplete(){
		for (Place[] places : grid) {
			for (Place place : places) {
				if(place instanceof ProhibitedPlace)
					continue;
				if(place instanceof Terminal && !((Terminal)place).isEndOrBeginOfPath())
					return false;
				if(!(place instanceof Terminal) && 
						(place.getLetter() == Place.NO_LETTER || !place.isFullLinked()))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Creates line grid 
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
		
		int rows = Integer.parseInt(line.substring(init+1, fin));
		
		int columns = Integer.parseInt(line.substring(fin+1));
		
		return new Grid(level, rows, columns);
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
		if(prmtr.equalsIgnoreCase("$1")){
			return new Fork(Position.get(row, column), isel.leic.poo.nrcircuit.model.terminals.Fork.Orientation.HORIZONTAL_UP);
		}
		if(prmtr.equalsIgnoreCase("$2")){
			return new Fork(Position.get(row, column), isel.leic.poo.nrcircuit.model.terminals.Fork.Orientation.VERTICAL_RIGHT);
		}
		if(prmtr.equalsIgnoreCase("$3")){
			return new Fork(Position.get(row, column), isel.leic.poo.nrcircuit.model.terminals.Fork.Orientation.HORIZONTAL_DOWN);
		}
		if(prmtr.equalsIgnoreCase("$4")){
			return new Fork(Position.get(row, column), isel.leic.poo.nrcircuit.model.terminals.Fork.Orientation.VERTICAL_LEFT);
		}
		if(prmtr.equalsIgnoreCase("$t")){
			return new Tunnel(Position.get(row, column));
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
			Place place = createPlace(row, prmtrIdx, prmtrs[i]);
			grid.grid[row][prmtrIdx] = place;
			if(place instanceof Tunnel)
				grid.tunnels.add((Tunnel)place);
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
