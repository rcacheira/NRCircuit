package isel.leic.poo.nrcircuit.model.terminals.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.MockPlace;
import isel.leic.poo.nrcircuit.model.terminals.Tunnel;

import org.junit.Test;

public class TunnelTests {
	
	Tunnel tunnel = new Tunnel(Position.get(2, 2));
	
	@Test
	public void testEquals(){
		assertTrue(tunnel.equals(tunnel));
		assertTrue(tunnel.equals(new Tunnel(Position.get(2, 2))));
		assertFalse(tunnel.equals(new MockPlace(Position.get(2, 2))));
	}
	
	@Test
	public void check_twin(){
		Tunnel t2 = new Tunnel(Position.get(1, 1));
		tunnel.setTwin(t2);
		assertTrue(tunnel.getTwin() == t2);
		assertTrue(t2.getTwin() == tunnel);
		assertTrue(tunnel.getTwin().getTwin() == tunnel);
		assertTrue(t2.getTwin().getTwin() == t2);
		
	}
}
