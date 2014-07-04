package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.terminals.Terminal;

import java.util.ArrayList;
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
		if(initialT == null)
			throw new IllegalArgumentException("initialT can't be null");
		this.initialT = initialT;
		places = new LinkedList<Place>();
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
	
	public ArrayList<Place> clear(Place p){
		ArrayList<Place> placesToRemove = new ArrayList<Place>();
		if(initialT.equals(p) || finalT != null && finalT.equals(p)){
			return placesToRemove;
		}
//		if((isFull() && finalT.equals(p))){
//			return placesToRemove;
//		}
//		placesToRemove.add(finalT);
//		finalT = null;
		finalT = null;
		int finalIdx = places.size();
		int initialIdx = places.indexOf(p);
		if(initialIdx < finalIdx){
			placesToRemove.addAll(places.subList(initialIdx, finalIdx));
			places.removeAll(placesToRemove);
		}
		return placesToRemove;
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
	
	public int getSize(){
		return places.size();
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
