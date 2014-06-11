package isel.leic.poo.nrcircuit.model.terminals.tests;

import static org.junit.Assert.*;
import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;
import isel.leic.poo.nrcircuit.model.terminals.FinalTerminal;

import org.junit.Test;

public class FinalTerminalTests {
	@Test
	public void check_good_crossing(){
		FinalTerminal ft1 = new FinalTerminal(new Coordinate(0, 0));
		assertTrue(ft1.canBeCrossed(new Direction(Position.UP, Position.CENTER)));
		assertTrue(ft1.canBeCrossed(new Direction(Position.DOWN, Position.CENTER)));
		assertTrue(ft1.canBeCrossed(new Direction(Position.LEFT, Position.CENTER)));
		assertTrue(ft1.canBeCrossed(new Direction(Position.RIGHT, Position.CENTER)));
		assertTrue(ft1.canBeCrossed(new Direction(Position.CENTER, Position.UP)));
		assertTrue(ft1.canBeCrossed(new Direction(Position.CENTER, Position.DOWN)));
		assertTrue(ft1.canBeCrossed(new Direction(Position.CENTER, Position.LEFT)));
		assertTrue(ft1.canBeCrossed(new Direction(Position.CENTER, Position.RIGHT)));
	}
	
	@Test
	public void check_bad_crossing(){
		FinalTerminal ft1 = new FinalTerminal(new Coordinate(0, 0));
		assertFalse(ft1.canBeCrossed(new Direction(Position.UP, Position.DOWN)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.UP, Position.LEFT)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.UP, Position.RIGHT)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.DOWN, Position.UP)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.DOWN, Position.LEFT)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.DOWN, Position.RIGHT)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.LEFT, Position.UP)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.LEFT, Position.DOWN)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.LEFT, Position.RIGHT)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.RIGHT, Position.UP)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.RIGHT, Position.DOWN)));
		assertFalse(ft1.canBeCrossed(new Direction(Position.RIGHT, Position.LEFT)));
	}
	
}
