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
		Tunnel t = new Tunnel(new Coordinate(0, 0));
		assertTrue(t.canBeCrossed(new Direction(Position.UP, Position.CENTER)));
		assertTrue(t.canBeCrossed(new Direction(Position.DOWN, Position.CENTER)));
		assertTrue(t.canBeCrossed(new Direction(Position.LEFT, Position.CENTER)));
		assertTrue(t.canBeCrossed(new Direction(Position.RIGHT, Position.CENTER)));
		assertTrue(t.canBeCrossed(new Direction(Position.CENTER, Position.UP)));
		assertTrue(t.canBeCrossed(new Direction(Position.CENTER, Position.DOWN)));
		assertTrue(t.canBeCrossed(new Direction(Position.CENTER, Position.LEFT)));
		assertTrue(t.canBeCrossed(new Direction(Position.CENTER, Position.RIGHT)));
	}
	
	@Test
	public void check_bad_crossing(){
		Tunnel t = new Tunnel(new Coordinate(0, 0));
		assertFalse(t.canBeCrossed(new Direction(Position.UP, Position.DOWN)));
		assertFalse(t.canBeCrossed(new Direction(Position.UP, Position.LEFT)));
		assertFalse(t.canBeCrossed(new Direction(Position.UP, Position.RIGHT)));
		assertFalse(t.canBeCrossed(new Direction(Position.DOWN, Position.UP)));
		assertFalse(t.canBeCrossed(new Direction(Position.DOWN, Position.LEFT)));
		assertFalse(t.canBeCrossed(new Direction(Position.DOWN, Position.RIGHT)));
		assertFalse(t.canBeCrossed(new Direction(Position.LEFT, Position.UP)));
		assertFalse(t.canBeCrossed(new Direction(Position.LEFT, Position.DOWN)));
		assertFalse(t.canBeCrossed(new Direction(Position.LEFT, Position.RIGHT)));
		assertFalse(t.canBeCrossed(new Direction(Position.RIGHT, Position.UP)));
		assertFalse(t.canBeCrossed(new Direction(Position.RIGHT, Position.DOWN)));
		assertFalse(t.canBeCrossed(new Direction(Position.RIGHT, Position.LEFT)));
	}
	
	@Test
	public void check_twin(){
		Tunnel t1 = new Tunnel(new Coordinate(0, 0));
		Tunnel t2 = new Tunnel(new Coordinate(1, 1));
		t1.setTwin(t2);
		assertTrue(t1.getTwin() == t2);
		assertTrue(t2.getTwin() == t1);
		assertTrue(t1.getTwin().getTwin() == t1);
		assertTrue(t2.getTwin().getTwin() == t2);
		
	}
}
