package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.terminals.Terminal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class whose instance represents a circuit path
 * 
 * A path is composed by an initial and final Terminal, linked by a set of 
 * connectors
 * 
 * @author rcacheira & nreis
 *
 */
public class Path implements Iterable<Place>{
	private final Terminal initialT;
	private Terminal finalT;
	private LinkedList<Place> places;
	
	/**
	 * Creates a new instance with the given parameters
	 * 
	 * @param initialT Path's initiak terminal
	 */
	public Path(Terminal initialT) {
		if(initialT == null)
			throw new IllegalArgumentException("initialT can't be null");
		this.initialT = initialT;
		places = new LinkedList<Place>();
		places.add(initialT);
	}
	
	/**
	 * adds a place to path
	 * 
	 * @param place place to add
	 */
	public void add(Place place){
		if(isFull()){
			throw new PathFullException();
		}
		
		if(place instanceof Terminal){
			finalT = (Terminal)place;
		}
		places.add(place);
	}
	
	/**
	 * remove from path all the places linked after given Place, given place 
	 * will be removed too 
	 * 
	 * @param p place to break path
	 * @return the list of places removed from path
	 */
	public List<Place> breakPath(Place p){
		ArrayList<Place> placesToRemove = new ArrayList<Place>();
		if(initialT.equals(p) || finalT != null && finalT.equals(p)){
			return placesToRemove;
		}
		finalT = null;
		int finalIdx = places.size();
		int initialIdx = places.indexOf(p);
		if(initialIdx < finalIdx){
			placesToRemove.addAll(places.subList(initialIdx, finalIdx));
			places.removeAll(placesToRemove);
		}
		return placesToRemove;
	}
	
	/**
	 * Gets the path initial terminal
	 * 
	 * @return the initial Terminal
	 */
	public Terminal getInitialT() {
		return initialT;
	}
	
	/**
	 * Gets the path letter
	 * 
	 * @return letter of path
	 */
	public char getLetter(){
		return initialT.getLetter();
	}
	
	/**
	 * Verifies if place is part or path
	 * 
	 * @param place place to verify
	 * @return {@code true} if place is part of path, {@code false} otherwise
	 */
	public boolean hasPlace(Place place){
		return places.contains(place);
	}
	
	/**
	 * Verifies if path id full
	 * 
	 * @return {@code true} if path is full, {@code false} otherwise
	 */
	public boolean isFull(){
		return finalT != null;
	}
	
	/**
	 * Gets the last pace added to path
	 * 
	 * @return the last place added to path 
	 */
	public Place getLastPlace(){
		return places.getLast();
	}
	
	/**
	 * Gets the number of places in path
	 * @return
	 */
	public int size(){
		return places.size();
	}
	
	@Override
	public Iterator<Place> iterator() {
		return places.iterator();
	}
	
	/**
	 * Exception that will be thrown when path is full and a try to add place
	 * occurs
	 * 
	 * @author rcacheira & nreis
	 *
	 */
	public class PathFullException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
	
}
