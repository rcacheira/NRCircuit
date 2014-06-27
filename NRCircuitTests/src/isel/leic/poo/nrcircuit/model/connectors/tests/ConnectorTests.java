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
		Connector c1 = new Connector(Coordinate.get(0, 0));
		assertTrue(c1.canBeCrossed(Direction.get(Position.UP, Position.DOWN)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.UP, Position.LEFT)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.UP, Position.RIGHT)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.DOWN, Position.UP)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.DOWN, Position.LEFT)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.DOWN, Position.RIGHT)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.LEFT, Position.UP)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.LEFT, Position.DOWN)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.LEFT, Position.RIGHT)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.RIGHT, Position.UP)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.RIGHT, Position.DOWN)));
		assertTrue(c1.canBeCrossed(Direction.get(Position.RIGHT, Position.LEFT)));
	}
	
	@Test
	public void check_bad_crossing(){
		Connector c1 = new Connector(Coordinate.get(0, 0));
		assertFalse(c1.canBeCrossed(Direction.get(Position.UP, Position.CENTER)));
		assertFalse(c1.canBeCrossed(Direction.get(Position.DOWN, Position.CENTER)));
		assertFalse(c1.canBeCrossed(Direction.get(Position.LEFT, Position.CENTER)));
		assertFalse(c1.canBeCrossed(Direction.get(Position.RIGHT, Position.CENTER)));
		assertFalse(c1.canBeCrossed(Direction.get(Position.CENTER, Position.UP)));
		assertFalse(c1.canBeCrossed(Direction.get(Position.CENTER, Position.DOWN)));
		assertFalse(c1.canBeCrossed(Direction.get(Position.CENTER, Position.LEFT)));
		assertFalse(c1.canBeCrossed(Direction.get(Position.CENTER, Position.RIGHT)));
	}

}
