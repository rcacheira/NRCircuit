package isel.leic.poo.nrcircuit.model.terminals.tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;
import isel.leic.poo.nrcircuit.model.terminals.FinalTerminal;

import org.junit.Test;

public class FinalTerminalTests {
	@Test
	public void check(){
		FinalTerminal ft1 = new FinalTerminal(Coordinate.get(0, 0), 'A');
		
		assertThat(ft1.getLetter(), is(equalTo('A')));
		
		assertTrue(ft1.canBeCrossed(Direction.get(Position.UP, Position.CENTER)));
		assertTrue(ft1.canBeCrossed(Direction.get(Position.DOWN, Position.CENTER)));
		assertTrue(ft1.canBeCrossed(Direction.get(Position.LEFT, Position.CENTER)));
		assertTrue(ft1.canBeCrossed(Direction.get(Position.RIGHT, Position.CENTER)));
		assertTrue(ft1.canBeCrossed(Direction.get(Position.CENTER, Position.UP)));
		assertTrue(ft1.canBeCrossed(Direction.get(Position.CENTER, Position.DOWN)));
		assertTrue(ft1.canBeCrossed(Direction.get(Position.CENTER, Position.LEFT)));
		assertTrue(ft1.canBeCrossed(Direction.get(Position.CENTER, Position.RIGHT)));

		assertFalse(ft1.canBeCrossed(Direction.get(Position.UP, Position.DOWN)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.UP, Position.LEFT)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.UP, Position.RIGHT)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.DOWN, Position.UP)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.DOWN, Position.LEFT)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.DOWN, Position.RIGHT)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.LEFT, Position.UP)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.LEFT, Position.DOWN)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.LEFT, Position.RIGHT)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.RIGHT, Position.UP)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.RIGHT, Position.DOWN)));
		assertFalse(ft1.canBeCrossed(Direction.get(Position.RIGHT, Position.LEFT)));
	}
	
}
