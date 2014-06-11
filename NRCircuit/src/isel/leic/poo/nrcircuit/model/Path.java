package isel.leic.poo.nrcircuit.model;

import java.util.LinkedList;
import java.util.List;

import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.terminals.Terminal;

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
	private List<Connector> connectors;
	
	public Path(Terminal initialT) {
		this.initialT = initialT;
		connectors = new LinkedList<>();
	}
	
	public void add(Place place) throws PathFullException{
		if(finalT != null){
			throw new PathFullException();
		}
		if(place instanceof Terminal){
			finalT = (Terminal)place;
		}
		else{
			connectors.add((Connector)place);
		}
	}
	
	public Terminal getInitialT() {
		return initialT;
	}
	
	public class PathFullException extends Exception{

		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;}
	
}
