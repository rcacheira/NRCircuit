package isel.leic.poo.nrcircuit.model.tests;

import static org.junit.Assert.*;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.MockPlace;
import isel.leic.poo.nrcircuit.model.Place;

import org.junit.Test;

public class PlaceTests {

	Place place;
	
	public PlaceTests() {
		place = new MockPlace(Position.get(2, 2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_initialization_with_null_coordinate(){
		new MockPlace(null);
	}
	
	@Test
	public void test_equals() {
		assertTrue(place.equals(place));
		assertTrue(place.equals(new MockPlace(Position.get(2, 2))));
		assertFalse(place.equals(new MockPlace(Position.get(0, 0))));
		assertFalse(place.equals(new MockPlace(Position.get(2, 0))));
		assertFalse(place.equals(new MockPlace(Position.get(0, 2))));
	}

	@Test
	public void test_link(){
		assertTrue(place.canBeLinkedTo(new MockPlace(Position.get(1, 2))));
		assertTrue(place.canBeLinkedTo(new MockPlace(Position.get(2, 1))));
		assertTrue(place.canBeLinkedTo(new MockPlace(Position.get(3, 2))));
		assertTrue(place.canBeLinkedTo(new MockPlace(Position.get(2, 3))));
		assertFalse(place.canBeLinkedTo(new MockPlace(Position.get(3, 3))));
		assertFalse(place.canBeLinkedTo(new MockPlace(Position.get(0, 0))));
		assertFalse(place.canBeLinkedTo(new MockPlace(Position.get(0, 3))));
		assertFalse(place.canBeLinkedTo(new MockPlace(Position.get(3, 0))));
		assertFalse(place.canBeLinkedTo(new MockPlace(Position.get(4, 2))));
		assertFalse(place.canBeLinkedTo(new MockPlace(Position.get(2, 4))));
		assertFalse(place.canBeLinkedTo(new MockPlace(Position.get(0, 2))));
		assertFalse(place.canBeLinkedTo(new MockPlace(Position.get(2, 0))));
	}
	
}
