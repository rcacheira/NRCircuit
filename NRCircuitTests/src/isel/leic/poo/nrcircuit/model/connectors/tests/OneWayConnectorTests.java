package isel.leic.poo.nrcircuit.model.connectors.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector.Orientation;

import org.junit.Test;

public class OneWayConnectorTests {

	@Test
	public void check_horizontal_good_crossing() {
		OneWayConnector owc = new OneWayConnector(Coordinate.get(0, 0), Orientation.HORIZONTAL);
		assertTrue(owc.canBeCrossed(Direction.get(Position.LEFT, Position.RIGHT)));
		assertTrue(owc.canBeCrossed(Direction.get(Position.RIGHT, Position.LEFT)));
	}
	
	@Test
	public void check_vertical_good_crossing() {
		OneWayConnector owc = new OneWayConnector(Coordinate.get(0, 0), Orientation.VERTICAL);
		assertTrue(owc.canBeCrossed(Direction.get(Position.UP, Position.DOWN)));
		assertTrue(owc.canBeCrossed(Direction.get(Position.DOWN, Position.UP)));
	}
	
	@Test
	public void check_horizontal_bad_crossing(){
		OneWayConnector owc = new OneWayConnector(Coordinate.get(0, 0), Orientation.HORIZONTAL);
		assertFalse(owc.canBeCrossed(Direction.get(Position.UP, Position.DOWN)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.UP, Position.LEFT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.UP, Position.RIGHT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.UP, Position.CENTER)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.DOWN, Position.UP)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.DOWN, Position.LEFT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.DOWN, Position.RIGHT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.DOWN, Position.CENTER)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.LEFT, Position.UP)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.LEFT, Position.DOWN)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.LEFT, Position.CENTER)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.RIGHT, Position.UP)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.RIGHT, Position.DOWN)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.RIGHT, Position.CENTER)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.CENTER, Position.UP)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.CENTER, Position.DOWN)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.CENTER, Position.LEFT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.CENTER, Position.RIGHT)));
	}
	
	@Test
	public void check_vertical_bad_crossing(){
		OneWayConnector owc = new OneWayConnector(Coordinate.get(0, 0), Orientation.VERTICAL);
		assertFalse(owc.canBeCrossed(Direction.get(Position.UP, Position.LEFT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.UP, Position.RIGHT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.UP, Position.CENTER)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.DOWN, Position.LEFT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.DOWN, Position.RIGHT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.DOWN, Position.CENTER)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.LEFT, Position.UP)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.LEFT, Position.DOWN)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.LEFT, Position.RIGHT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.LEFT, Position.CENTER)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.RIGHT, Position.UP)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.RIGHT, Position.DOWN)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.RIGHT, Position.LEFT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.RIGHT, Position.CENTER)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.CENTER, Position.UP)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.CENTER, Position.DOWN)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.CENTER, Position.LEFT)));
		assertFalse(owc.canBeCrossed(Direction.get(Position.CENTER, Position.RIGHT)));
	}

}
