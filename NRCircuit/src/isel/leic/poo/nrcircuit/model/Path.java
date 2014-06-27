package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.terminals.Terminal;

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
public class Path {
	private final Terminal initialT;
	private Terminal finalT;
	private LinkedList<Connector> connectors;
	
	public Path(Terminal initialT) {
		this.initialT = initialT;
		connectors = new LinkedList<>();
	}
	
	public void add(Place place){
		if(isFull()){
			throw new PathFullException();
		}
		
		if(place instanceof Terminal){
			finalT = (Terminal)place;
		}
		else{
			connectors.add((Connector)place);
		}
	}
	
	public void clear(Place p){
		if(isFull() && finalT.equals(p)){
			return;
		}
		finalT = null;
		int finalIdx = connectors.size();
		int initialIdx = connectors.indexOf(p);
		if(initialIdx < finalIdx-1){
			connectors.removeAll(connectors.subList(initialIdx, finalIdx));
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
		if(initialT.equals(place))
			return true;
		if(isFull() && finalT.equals(place))
			return true;
		return connectors.contains(place);
	}
	
	public boolean isFull(){
		return finalT != null;
	}
	
	public Place getLastPlace(){
		if( finalT != null)
			return finalT;
		if( connectors.isEmpty())
			return initialT;
		return connectors.getLast();
	}
	
	public class PathFullException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
	
}
