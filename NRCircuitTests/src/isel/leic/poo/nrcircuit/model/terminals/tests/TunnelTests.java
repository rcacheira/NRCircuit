package isel.leic.poo.nrcircuit.model.terminals.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;
import isel.leic.poo.nrcircuit.model.terminals.Tunnel;

import org.junit.Test;

public class TunnelTests {
	@Test
	public void check_good_crossing(){
		Tunnel t = new Tunnel(Coordinate.get(0, 0));
		assertTrue(t.canBeCrossed(Direction.get(Position.UP, Position.CENTER)));
		assertTrue(t.canBeCrossed(Direction.get(Position.DOWN, Position.CENTER)));
		assertTrue(t.canBeCrossed(Direction.get(Position.LEFT, Position.CENTER)));
		assertTrue(t.canBeCrossed(Direction.get(Position.RIGHT, Position.CENTER)));
		assertTrue(t.canBeCrossed(Direction.get(Position.CENTER, Position.UP)));
		assertTrue(t.canBeCrossed(Direction.get(Position.CENTER, Position.DOWN)));
		assertTrue(t.canBeCrossed(Direction.get(Position.CENTER, Position.LEFT)));
		assertTrue(t.canBeCrossed(Direction.get(Position.CENTER, Position.RIGHT)));
	}
	
	@Test
	public void check_bad_crossing(){
		Tunnel t = new Tunnel(Coordinate.get(0, 0));
		assertFalse(t.canBeCrossed(Direction.get(Position.UP, Position.DOWN)));
		assertFalse(t.canBeCrossed(Direction.get(Position.UP, Position.LEFT)));
		assertFalse(t.canBeCrossed(Direction.get(Position.UP, Position.RIGHT)));
		assertFalse(t.canBeCrossed(Direction.get(Position.DOWN, Position.UP)));
		assertFalse(t.canBeCrossed(Direction.get(Position.DOWN, Position.LEFT)));
		assertFalse(t.canBeCrossed(Direction.get(Position.DOWN, Position.RIGHT)));
		assertFalse(t.canBeCrossed(Direction.get(Position.LEFT, Position.UP)));
		assertFalse(t.canBeCrossed(Direction.get(Position.LEFT, Position.DOWN)));
		assertFalse(t.canBeCrossed(Direction.get(Position.LEFT, Position.RIGHT)));
		assertFalse(t.canBeCrossed(Direction.get(Position.RIGHT, Position.UP)));
		assertFalse(t.canBeCrossed(Direction.get(Position.RIGHT, Position.DOWN)));
		assertFalse(t.canBeCrossed(Direction.get(Position.RIGHT, Position.LEFT)));
	}
	
	@Test
	public void check_twin(){
		Tunnel t1 = new Tunnel(Coordinate.get(0, 0));
		Tunnel t2 = new Tunnel(Coordinate.get(1, 1));
		t1.setTwin(t2);
		assertTrue(t1.getTwin() == t2);
		assertTrue(t2.getTwin() == t1);
		assertTrue(t1.getTwin().getTwin() == t1);
		assertTrue(t2.getTwin().getTwin() == t2);
		
	}
}
