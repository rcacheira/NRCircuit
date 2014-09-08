package isel.leic.poo.nrcircuit.model.terminals;

import isel.leic.poo.nrcircuit.model.Position;

/**
 * class whose instance represents a color terminal
 *  
 * @author rcacheira & nreis
 *
 */
public class FinalTerminal extends Terminal {
	
	/**
	 * Initiates an instance with the given parameters
	 * 
	 * @param position The FinalTerminal position
	 */
	public FinalTerminal(Position position, char letter) {
		super(position, 1);
		super.setLetter(letter);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj) && obj instanceof FinalTerminal)
			return ((FinalTerminal)obj).position == position
				&& ((FinalTerminal)obj).getLetter() == getLetter();
		
		return false;
	}
	
	@Override
	public void setLetter(char letter) {
		throw new IllegalStateException("Letter of finalTerminal can't be changed");
	}
	
	@Override
	public boolean isFullLinked() {
		return isEndOrBeginOfPath();
	}

}
