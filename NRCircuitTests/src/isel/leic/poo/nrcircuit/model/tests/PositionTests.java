package isel.leic.poo.nrcircuit.model.tests;

import isel.leic.poo.nrcircuit.model.Position;

import org.junit.Test;

public class PositionTests {

	@Test
	public void check_good_initialization() {
		Position.get(0, 0);
		Position.get(0, 1);
		Position.get(1, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initialization_negative_values() {
		Position.get(-1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initialization_x_negative_value() {
		Position.get(-1, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initialization_y_negative_value() {
		Position.get(1, -1);
	}
}
