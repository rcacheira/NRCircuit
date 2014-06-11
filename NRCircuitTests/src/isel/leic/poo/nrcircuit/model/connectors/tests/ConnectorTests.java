package isel.leic.poo.nrcircuit.model.connectors.tests;

import static org.junit.Assert.*;
import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;
import isel.leic.poo.nrcircuit.model.connectors.Connector;

import org.junit.Test;

public class ConnectorTests {

	@Test
	public void check_good_initialization() {
		Connector c1 = new Connector(new Coordinate(0, 0));
		assertTrue(c1.canBeCrossed(new Direction(Position.UP, Position.DOWN)));
		assertTrue(c1.canBeCrossed(new Direction(Position.UP, Position.LEFT)));
		assertTrue(c1.canBeCrossed(new Direction(Position.UP, Position.RIGHT)));
		assertTrue(c1.canBeCrossed(new Direction(Position.DOWN, Position.UP)));
		assertTrue(c1.canBeCrossed(new Direction(Position.DOWN, Position.LEFT)));
		assertTrue(c1.canBeCrossed(new Direction(Position.DOWN, Position.RIGHT)));
		assertTrue(c1.canBeCrossed(new Direction(Position.LEFT, Position.UP)));
		assertTrue(c1.canBeCrossed(new Direction(Position.LEFT, Position.DOWN)));
		assertTrue(c1.canBeCrossed(new Direction(Position.LEFT, Position.RIGHT)));
		assertTrue(c1.canBeCrossed(new Direction(Position.RIGHT, Position.UP)));
		assertTrue(c1.canBeCrossed(new Direction(Position.RIGHT, Position.DOWN)));
		assertTrue(c1.canBeCrossed(new Direction(Position.RIGHT, Position.LEFT)));
	}
	
	@Test
	public void check_bad_crossing(){
		Connector c1 = new Connector(new Coordinate(0, 0));
		assertFalse(c1.canBeCrossed(new Direction(Position.UP, Position.CENTER)));
		assertFalse(c1.canBeCrossed(new Direction(Position.DOWN, Position.CENTER)));
		assertFalse(c1.canBeCrossed(new Direction(Position.LEFT, Position.CENTER)));
		assertFalse(c1.canBeCrossed(new Direction(Position.RIGHT, Position.CENTER)));
		assertFalse(c1.canBeCrossed(new Direction(Position.CENTER, Position.UP)));
		assertFalse(c1.canBeCrossed(new Direction(Position.CENTER, Position.DOWN)));
		assertFalse(c1.canBeCrossed(new Direction(Position.CENTER, Position.LEFT)));
		assertFalse(c1.canBeCrossed(new Direction(Position.CENTER, Position.RIGHT)));
	}

}
