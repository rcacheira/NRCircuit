package isel.leic.poo.nrcircuit.model.tests;

import isel.leic.poo.nrcircuit.model.Coordinate;

import org.junit.Test;

public class CoordinateTests {

	@Test
	public void check_good_initialization() {
		new Coordinate(0, 0);
		new Coordinate(0, 1);
		new Coordinate(1, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initialization_negative_values() {
		new Coordinate(-1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initialization_x_negative_value() {
		new Coordinate(-1, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void check_bad_initialization_y_negative_value() {
		new Coordinate(1, -1);
	}
}
