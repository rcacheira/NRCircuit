package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;

/**
 * class whose instance represents a tunnel to other side of board.
 * Tunnels is always connected to a brother. So a grid should have always a even
 * number of tunnels
 * 
 * @author rcacheira & nreis
 *
 */
public class Tunnel extends Terminal {

	char letter;
	
	/**
	 * tunnel's brother
	 */
	private Tunnel twin;
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param coordinate the Tunnel Coordinate
	 */
	public Tunnel(Coordinate coordinate) {
		super(coordinate);
	}
	
	@Override
	public boolean canBeCrossed(Direction direction) {
		return direction.from == Position.CENTER ||
				direction.to == Position.CENTER;
	}
	
	/**
	 * Sets the tunnel twin
	 * 
	 * @param twin tunnel twin
	 */
	public void setTwin(Tunnel twin) {
		if(twin == null) 
			throw new IllegalArgumentException("twin can't be null");
		if(this.twin != null) 
			throw new IllegalStateException("twin can't be set twice");
		
		this.twin = twin;
		if(twin.getTwin() == null) twin.setTwin(this);
	}
	
	/**
	 * Gets the tunnel twin
	 * 
	 * @return tunnel twin
	 */
	public Tunnel getTwin() {
		return twin;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof Tunnel)
			return ((Tunnel)obj).twin == twin;
		
		return false;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	@Override
	public char getLetter() {
		return letter;
	}
}
