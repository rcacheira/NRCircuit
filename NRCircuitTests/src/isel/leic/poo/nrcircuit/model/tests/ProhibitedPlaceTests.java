package isel.leic.poo.nrcircuit.model.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;
import isel.leic.poo.nrcircuit.model.ProhibitedPlace;

public class ProhibitedPlaceTests {
	
	@Test
	public void check_that_cannot_be_crossed(){
		ProhibitedPlace p1 = new ProhibitedPlace(new Coordinate(0, 0));
		assertFalse(p1.canBeCrossed(new Direction(Position.UP, Position.DOWN)));
		assertFalse(p1.canBeCrossed(new Direction(Position.UP, Position.LEFT)));
		assertFalse(p1.canBeCrossed(new Direction(Position.UP, Position.RIGHT)));
		assertFalse(p1.canBeCrossed(new Direction(Position.UP, Position.CENTER)));
		assertFalse(p1.canBeCrossed(new Direction(Position.DOWN, Position.UP)));
		assertFalse(p1.canBeCrossed(new Direction(Position.DOWN, Position.LEFT)));
		assertFalse(p1.canBeCrossed(new Direction(Position.DOWN, Position.RIGHT)));
		assertFalse(p1.canBeCrossed(new Direction(Position.DOWN, Position.CENTER)));
		assertFalse(p1.canBeCrossed(new Direction(Position.LEFT, Position.UP)));
		assertFalse(p1.canBeCrossed(new Direction(Position.LEFT, Position.DOWN)));
		assertFalse(p1.canBeCrossed(new Direction(Position.LEFT, Position.RIGHT)));
		assertFalse(p1.canBeCrossed(new Direction(Position.LEFT, Position.CENTER)));
		assertFalse(p1.canBeCrossed(new Direction(Position.RIGHT, Position.UP)));
		assertFalse(p1.canBeCrossed(new Direction(Position.RIGHT, Position.DOWN)));
		assertFalse(p1.canBeCrossed(new Direction(Position.RIGHT, Position.LEFT)));
		assertFalse(p1.canBeCrossed(new Direction(Position.RIGHT, Position.CENTER)));
		assertFalse(p1.canBeCrossed(new Direction(Position.CENTER, Position.UP)));
		assertFalse(p1.canBeCrossed(new Direction(Position.CENTER, Position.DOWN)));
		assertFalse(p1.canBeCrossed(new Direction(Position.CENTER, Position.LEFT)));
		assertFalse(p1.canBeCrossed(new Direction(Position.CENTER, Position.RIGHT)));
	}

}
