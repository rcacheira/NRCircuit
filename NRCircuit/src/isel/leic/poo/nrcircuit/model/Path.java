package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.terminals.Terminal;

import java.util.Iterator;
import java.util.LinkedList;

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
	
	public Path(Terminal initialT) {
		this.initialT = initialT;
		places = new LinkedList<>();
		places.add(initialT);
	}
	
	public void add(Place place){
		if(isFull()){
			throw new PathFullException();
		}
		
		if(place instanceof Terminal){
			finalT = (Terminal)place;
		}
		places.add(place);
	}
	
	public void clear(Place p){
		if(isFull() && finalT.equals(p)){
			return;
		}
		finalT = null;
		int finalIdx = places.size();
		int initialIdx = places.indexOf(p);
		if(initialIdx < finalIdx-1){
			places.removeAll(places.subList(initialIdx, finalIdx));
		}
		return;
	}
	
	public Terminal getInitialT() {
		return initialT;
	}
	
	public char getLetter(){
		return initialT.getLetter();
	}
	
	public boolean hasPlace(Place place){
		return places.contains(place);
	}
	
	public boolean isFull(){
		return finalT != null;
	}
	
	public Place getLastPlace(){
		return places.getLast();
	}
	
	@Override
	public Iterator<Place> iterator() {
		return places.iterator();
	}
	
	public class PathFullException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
	
}
