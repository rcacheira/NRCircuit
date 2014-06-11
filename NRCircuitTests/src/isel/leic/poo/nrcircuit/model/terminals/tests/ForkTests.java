package isel.leic.poo.nrcircuit.model.terminals.tests;

import static org.junit.Assert.*;
import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;
import isel.leic.poo.nrcircuit.model.terminals.Fork;
import isel.leic.poo.nrcircuit.model.terminals.Fork.Orientation;

import org.junit.Test;

public class ForkTests {

	@Test
	public void check_horizontal_up_good_crossing() {
		Fork f = new Fork(new Coordinate(0, 0), Orientation.HORIZONTAL_UP);
		assertTrue(f.canBeCrossed(new Direction(Position.UP, Position.LEFT)));
		assertTrue(f.canBeCrossed(new Direction(Position.UP, Position.RIGHT)));
		assertTrue(f.canBeCrossed(new Direction(Position.UP, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.LEFT, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.LEFT, Position.RIGHT)));
		assertTrue(f.canBeCrossed(new Direction(Position.LEFT, Position.UP)));
		assertTrue(f.canBeCrossed(new Direction(Position.RIGHT, Position.LEFT)));
		assertTrue(f.canBeCrossed(new Direction(Position.RIGHT, Position.UP)));
		assertTrue(f.canBeCrossed(new Direction(Position.RIGHT, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.UP)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.LEFT)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.RIGHT)));
	}
	
	@Test
	public void check_horizontal_down_good_crossing() {
		Fork f = new Fork(new Coordinate(0, 0), Orientation.HORIZONTAL_DOWN);
		assertTrue(f.canBeCrossed(new Direction(Position.DOWN, Position.LEFT)));
		assertTrue(f.canBeCrossed(new Direction(Position.DOWN, Position.RIGHT)));
		assertTrue(f.canBeCrossed(new Direction(Position.DOWN, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.LEFT, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.LEFT, Position.DOWN)));
		assertTrue(f.canBeCrossed(new Direction(Position.LEFT, Position.RIGHT)));
		assertTrue(f.canBeCrossed(new Direction(Position.RIGHT, Position.DOWN)));
		assertTrue(f.canBeCrossed(new Direction(Position.RIGHT, Position.LEFT)));
		assertTrue(f.canBeCrossed(new Direction(Position.RIGHT, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.DOWN)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.LEFT)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.RIGHT)));
	}
	
	@Test
	public void check_vertical_left_good_crossing() {
		Fork f = new Fork(new Coordinate(0, 0), Orientation.VERTICAL_LEFT);
		assertTrue(f.canBeCrossed(new Direction(Position.UP, Position.DOWN)));
		assertTrue(f.canBeCrossed(new Direction(Position.UP, Position.LEFT)));
		assertTrue(f.canBeCrossed(new Direction(Position.UP, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.DOWN, Position.UP)));
		assertTrue(f.canBeCrossed(new Direction(Position.DOWN, Position.LEFT)));
		assertTrue(f.canBeCrossed(new Direction(Position.DOWN, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.LEFT, Position.UP)));
		assertTrue(f.canBeCrossed(new Direction(Position.LEFT, Position.DOWN)));
		assertTrue(f.canBeCrossed(new Direction(Position.LEFT, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.UP)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.DOWN)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.LEFT)));
	}
	
	@Test
	public void check_vertical_right_good_crossing() {
		Fork f = new Fork(new Coordinate(0, 0), Orientation.VERTICAL_RIGHT);
		assertTrue(f.canBeCrossed(new Direction(Position.UP, Position.DOWN)));
		assertTrue(f.canBeCrossed(new Direction(Position.UP, Position.RIGHT)));
		assertTrue(f.canBeCrossed(new Direction(Position.UP, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.DOWN, Position.UP)));
		assertTrue(f.canBeCrossed(new Direction(Position.DOWN, Position.RIGHT)));
		assertTrue(f.canBeCrossed(new Direction(Position.DOWN, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.RIGHT, Position.UP)));
		assertTrue(f.canBeCrossed(new Direction(Position.RIGHT, Position.DOWN)));
		assertTrue(f.canBeCrossed(new Direction(Position.RIGHT, Position.CENTER)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.UP)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.DOWN)));
		assertTrue(f.canBeCrossed(new Direction(Position.CENTER, Position.RIGHT)));
	}
	
	@Test
	public void check_horizontal_up_bad_crossing(){
		Fork f = new Fork(new Coordinate(0, 0), Orientation.HORIZONTAL_UP);
		assertFalse(f.canBeCrossed(new Direction(Position.UP, Position.DOWN)));
		assertFalse(f.canBeCrossed(new Direction(Position.DOWN, Position.UP)));
		assertFalse(f.canBeCrossed(new Direction(Position.DOWN, Position.LEFT)));
		assertFalse(f.canBeCrossed(new Direction(Position.DOWN, Position.RIGHT)));
		assertFalse(f.canBeCrossed(new Direction(Position.DOWN, Position.CENTER)));
		assertFalse(f.canBeCrossed(new Direction(Position.LEFT, Position.DOWN)));
		assertFalse(f.canBeCrossed(new Direction(Position.RIGHT, Position.DOWN)));
		assertFalse(f.canBeCrossed(new Direction(Position.CENTER, Position.DOWN)));
	}
	
	@Test
	public void check_horizontal_down_bad_crossing(){
		Fork f = new Fork(new Coordinate(0, 0), Orientation.HORIZONTAL_DOWN);
		assertFalse(f.canBeCrossed(new Direction(Position.UP, Position.DOWN)));
		assertFalse(f.canBeCrossed(new Direction(Position.UP, Position.LEFT)));
		assertFalse(f.canBeCrossed(new Direction(Position.UP, Position.RIGHT)));
		assertFalse(f.canBeCrossed(new Direction(Position.UP, Position.CENTER)));
		assertFalse(f.canBeCrossed(new Direction(Position.DOWN, Position.UP)));
		assertFalse(f.canBeCrossed(new Direction(Position.LEFT, Position.UP)));
		assertFalse(f.canBeCrossed(new Direction(Position.RIGHT, Position.UP)));
		assertFalse(f.canBeCrossed(new Direction(Position.CENTER, Position.UP)));
	}
	
	@Test
	public void check_vertical_left_bad_crossing(){
		Fork f = new Fork(new Coordinate(0, 0), Orientation.VERTICAL_LEFT);
		assertFalse(f.canBeCrossed(new Direction(Position.UP, Position.RIGHT)));
		assertFalse(f.canBeCrossed(new Direction(Position.DOWN, Position.RIGHT)));
		assertFalse(f.canBeCrossed(new Direction(Position.LEFT, Position.RIGHT)));
		assertFalse(f.canBeCrossed(new Direction(Position.RIGHT, Position.UP)));
		assertFalse(f.canBeCrossed(new Direction(Position.RIGHT, Position.DOWN)));
		assertFalse(f.canBeCrossed(new Direction(Position.RIGHT, Position.LEFT)));
		assertFalse(f.canBeCrossed(new Direction(Position.RIGHT, Position.CENTER)));
		assertFalse(f.canBeCrossed(new Direction(Position.CENTER, Position.RIGHT)));
	}
	
	@Test
	public void check_vertical_right_bad_crossing(){
		Fork f = new Fork(new Coordinate(0, 0), Orientation.VERTICAL_RIGHT);
		assertFalse(f.canBeCrossed(new Direction(Position.UP, Position.LEFT)));
		assertFalse(f.canBeCrossed(new Direction(Position.DOWN, Position.LEFT)));
		assertFalse(f.canBeCrossed(new Direction(Position.LEFT, Position.UP)));
		assertFalse(f.canBeCrossed(new Direction(Position.LEFT, Position.DOWN)));
		assertFalse(f.canBeCrossed(new Direction(Position.LEFT, Position.RIGHT)));
		assertFalse(f.canBeCrossed(new Direction(Position.LEFT, Position.CENTER)));
		assertFalse(f.canBeCrossed(new Direction(Position.RIGHT, Position.LEFT)));
		assertFalse(f.canBeCrossed(new Direction(Position.CENTER, Position.LEFT)));
	}
}
