package isel.leic.poo.nrcircuit.model.tests;

import static org.junit.Assert.*;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;

import org.junit.Test;

public class DirectionTests {

	@Test
	public void check_good_initialization() {
		Direction direction = Direction.get(Position.CENTER, Position.UP);
		assertTrue(direction.from == Position.CENTER);
		assertTrue(direction.to == Position.UP);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initializaton_null_positions(){
		Direction.get(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initialization_null_final_position(){
		Direction.get(Position.CENTER, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initialization_null_initial_position(){
		Direction.get(null, Position.CENTER);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initialization_same_position(){
		Direction.get(Position.DOWN, Position.DOWN);
	}

}
