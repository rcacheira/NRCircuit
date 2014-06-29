package isel.leic.poo.nrcircuit.model.terminals.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.MockPlace;
import isel.leic.poo.nrcircuit.model.terminals.FinalTerminal;

import org.junit.Test;

public class FinalTerminalTests {

	FinalTerminal finalTerminal = new FinalTerminal(Position.get(2, 2), 'A');
	
	@Test
	public void testEquals(){
		assertTrue(finalTerminal.equals(finalTerminal));
		assertTrue(finalTerminal.equals(new FinalTerminal(Position.get(2, 2), 'A')));
		assertFalse(finalTerminal.equals(new FinalTerminal(Position.get(2, 2), 'B')));
		assertFalse(finalTerminal.equals(new MockPlace(Position.get(2, 2))));
	}
	
}
