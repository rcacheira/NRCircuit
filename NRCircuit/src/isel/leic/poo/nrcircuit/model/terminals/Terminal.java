package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Place;
import isel.leic.poo.nrcircuit.model.Position;

/**
 * class whose instance represents the initial or final position of a path
 * 
 * @author rcacheira & nreis
 *
 */
public abstract class Terminal extends Place {
	
	private boolean endOfPath;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position The terminal position
	 */
	public Terminal(Position position, int nLinks) {
		super(position, nLinks);
		endOfPath = false;
	}
	
	@Override
	protected void especificClearWork(){
		endOfPath = false;
	}
	
	public void setEndOfPath(boolean endOfPath){
		this.endOfPath = endOfPath;
	}
	
	public boolean isEndOrBeginOfPath() {
		return endOfPath || links[0] != null;
	}
	
}
