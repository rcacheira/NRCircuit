package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Position;

/**
 * class whose instance represents a tunnel to other side of board.
 * Tunnels is always connected to a brother. So a grid should have always a even
 * number of tunnels
 * 
 * @author rcacheira & nreis
 *
 */
public class Tunnel extends Terminal {
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position the Tunnel position
	 */
	public Tunnel(Position position) {
		super(position, 1);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof Tunnel)
			return ((Tunnel)obj).position == position;
		
		return false;
	}
	
	@Override
	protected void especificClearWork() {
		//do nothing
	}
	
	@Override
	public boolean isFullLinked() {
		return super.isFullLinked() || previousLink != null;
	}
}
