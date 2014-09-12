package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.connectors.NumberedConnector;
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
	
	public static interface OnGridActionListener{
		public void onLinkClear(Link link);
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
	 * Array of links
	 * 
	 * Used to save the state
	 */
	private List<Link> links;
	
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
	
	private OrderControl orderControl;
	
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
		links = new LinkedList<Link>();
		orderControl = new OrderControl(); 
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
	
	/**
	 * get the place at given position
	 * 
	 * @param position {@code Place} {@code Position}
	 * @return {@code Place} at given {@code Position}
	 */
	public Place getPlaceAtPosition(Position position){
		if(!isCoordinateWithinBounds(position))
			throw new IllegalArgumentException();
		
		return grid[position.row][position.column];
	}
	
	/**
	 * Tries to set a new working place with position
	 * 
	 * @param position {@code Place} {@code Position}
	 * @return {@code true} if the place at position could be set as working, 
	 * {@code false} otherwise
	 */
	public boolean setWorkingPlace(Position position){
		Place place = getPlaceAtPosition(position);
		if(place instanceof ProhibitedPlace)
			return false;
		
		if(place.getLetter() != Place.NO_LETTER){
			if((place instanceof FinalTerminal || place instanceof Tunnel)
					&& place.getPrevious() != null)
				clearPreviousLinks(place);
			if(!(place instanceof Fork) || place.isFullLinked()){
				clearFollowedLinks(place);
			}
			workingPlace = place;
			return true;
		}
		
		workingPlace = null;
		return false;
	}
	
	/**
	 * Tries to do a new link from working place to place with given position 
	 * 
	 * @param position next place link position
	 * @return {@code true} if link was made, {@code false} other wise
	 */
	public boolean doLink(Position position){
		Place place = getPlaceAtPosition(position);
		
		if(place instanceof ProhibitedPlace 
				|| workingPlace == null
				|| !workingPlace.canBeLinkedTo(place) 
				|| !place.canBeLinkedTo(workingPlace))
			return false;
		
		//verify if the link is going backwards
		if(place.isLinkedWith(workingPlace))
			return doLinkGoingBackwards(place);
		
		return doLinkGoingForward(place);
	}
	
	/**
	 * Works a link going back
	 * 
	 * @param position next place link position
	 * @return {@code true} if link was made, {@code false} other wise
	 */
	private boolean doLinkGoingBackwards(Place place){
		clearFollowedLinks(place);
		workingPlace = place;
		return true;
	}
	
	/**
	 * Works a link going forward
	 * 
	 * @param place
	 * @return
	 */
	private boolean doLinkGoingForward(Place place){
		if((place instanceof Terminal 
				&& place.getLetter() != Place.NO_LETTER 
				&& place.getLetter() != workingPlace.getLetter())
			|| workingPlace.isFullLinked()
			|| !orderControl.canLinkPlace(workingPlace.getLetter(), place)){
			return false;
		}
		
		clearPlaceLinks(place);
		
		//verification if the order continues to be accepted after clearLinks
		if(!orderControl.canLinkPlace(workingPlace.getLetter(), place)
				|| place instanceof Terminal && place.isFullLinked())
			return false;
	
		newLinkWork(workingPlace, place, true);
		
		Link link = new Link(workingPlace.position, place.position);
		fireOnLinkDoneEvent(link, workingPlace.getLetter());
		links.add(link);
		workingPlace = place;
		return true;
	}
	
	/**
	 * Works to do when createing a new link
	 * 
	 * @param origin origin {@code Place}
	 * @param destiny destiny {@code Place}
	 * @param sendEvents authorization to launch events
	 */
	private void newLinkWork(Place origin, Place destiny, boolean sendEvents){
		origin.addLink(destiny);
		destiny.addPreviousLink(origin);
		
		if(!(destiny instanceof FinalTerminal)){
			if(destiny instanceof Tunnel){
				if(destiny.getLetter() == Place.NO_LETTER){
					//Verification is to set all tunnels letter
					setTunnelsLetter(origin.getLetter());
					if(sendEvents) fireOnSetTunnelsLetter(origin.getLetter());
				}
			}
			else{
				destiny.setLetter(origin.getLetter());
				orderControl.linkedPlace(destiny);
			}
		}
	}
	
	/**
	 * Auxiliar function to remove links
	 * 
	 * @param linksCleared
	 * @param letter
	 */
	private void removeClearedLinks(List<Link> linksCleared, char letter){
		links.removeAll(linksCleared);
		for (Link link : linksCleared) {
			orderControl.unlinkedPlace(letter, getPlaceAtPosition(link.destiny));
			fireOnLinkClearEvent(link);
		}
		checkTunnelsLinks();
	}
	
	/**
	 * Auxiliar function to clear previous links
	 * 
	 * @param place {@code Place} to clear previous links
	 */
	private void clearPreviousLinks(Place place){
		char letter = place.getLetter();
		List<Link> linksCleared = new LinkedList<Link>();
		place.clearPreviousLinks(linksCleared);
		removeClearedLinks(linksCleared, letter);
	}
	
	/**
	 * Auxiliar function to clear followed links
	 * 
	 * @param place {@code Place} to clear previous links
	 */
	private void clearFollowedLinks(Place place){
		char letter = place.getLetter();
		List<Link> linksCleared = new LinkedList<Link>();
		place.clearLinks(linksCleared);
		removeClearedLinks(linksCleared, letter);
	}
	
	/**
	 * Clears given place links
	 * 
	 * @param place
	 */
	private void clearPlaceLinks(Place place){
		if(place.getPrevious() != null)
			clearFollowedLinks(place.getPrevious());
		clearFollowedLinks(place);
	}
	
	private void checkTunnelsLinks(){
		for (Tunnel tunnel : tunnels) {
			if(tunnel.isFullLinked())
				return;
		}
		setTunnelsLetter(Place.NO_LETTER);
		fireOnSetTunnelsLetter(Place.NO_LETTER);
	}
	
	private void setTunnelsLetter(char letter) {
		for (Tunnel tunnel : tunnels) {
			tunnel.setLetter(letter);
		}
	}

	void fireOnLinkClearEvent(Link link){
		if(gridActionListener != null){
			gridActionListener.onLinkClear(link);
		}
	}
	
	void fireOnLinkDoneEvent(Link link, char letter){
		if(gridActionListener != null){
			gridActionListener.onLinkDone(link, letter);
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
	
	public Iterable<Link> getLinks() {
		return links;
	}
	
	public void fireLinkEvents(){
		if(!tunnels.isEmpty()){
			fireOnSetTunnelsLetter(tunnels.get(0).getLetter());
		}
		for (Link link : links) {
			fireOnLinkDoneEvent(link, getPlaceAtPosition(link.origin).getLetter());
		}
	}
	
	public void setLinks(Iterable<Link> links){
		if(!this.links.isEmpty()){
			throw new IllegalStateException("Can't set links with existing links");
		}
		
		for (Link link : links) {
			newLinkWork(getPlaceAtPosition(link.origin), getPlaceAtPosition(link.destiny), false);
			this.links.add(link);
		}
	}
	
	public boolean isComplete(){
		for (Place[] places : grid) {
			for (Place place : places) {
				if(place instanceof ProhibitedPlace)
					continue;
				if(place.getLetter() == Place.NO_LETTER || !place.isFullLinked())
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
		if(prmtr.startsWith("#")){
			if(prmtr.length() > 1){
				return new NumberedConnector(Position.get(row, column), prmtr.charAt(1) - '0');
			}
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
		if(nRows < grid.rows){
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
