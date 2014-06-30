package isel.leic.poo.nrcircuit.model.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import isel.leic.poo.nrcircuit.model.MockPlace;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.ProhibitedPlace;

import org.junit.Test;

public class ProhibitedPlaceTests {

	ProhibitedPlace prohibitedPlace = new ProhibitedPlace(Position.get(2, 2));
	
	@Test
	public void test_link(){
		assertFalse(prohibitedPlace.canBeLinkedTo(new MockPlace(Position.get(2, 2))));
		assertFalse(prohibitedPlace.canBeLinkedTo(new MockPlace(Position.get(0, 2))));
		assertFalse(prohibitedPlace.canBeLinkedTo(new MockPlace(Position.get(2, 0))));
		assertFalse(prohibitedPlace.canBeLinkedTo(new MockPlace(Position.get(3, 2))));
		assertFalse(prohibitedPlace.canBeLinkedTo(new MockPlace(Position.get(2, 3))));
	}

	@Test
	public void testEquals(){
		assertTrue(prohibitedPlace.equals(prohibitedPlace));
		assertTrue(prohibitedPlace.equals(new ProhibitedPlace(Position.get(2, 2))));
		assertFalse(prohibitedPlace.equals(new MockPlace(Position.get(2, 2))));
	}
	
}
