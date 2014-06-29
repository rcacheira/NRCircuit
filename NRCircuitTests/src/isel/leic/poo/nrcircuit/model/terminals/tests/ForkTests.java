package isel.leic.poo.nrcircuit.model.terminals.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.MockPlace;
import isel.leic.poo.nrcircuit.model.terminals.Fork;
import isel.leic.poo.nrcircuit.model.terminals.Fork.Orientation;

import org.junit.Test;

public class ForkTests {

	Fork forkHU = new Fork(Position.get(2, 2), Orientation.HORIZONTAL_UP);
	Fork forkHD = new Fork(Position.get(2, 2), Orientation.HORIZONTAL_DOWN);
	Fork forkVR = new Fork(Position.get(2, 2), Orientation.VERTICAL_RIGHT);
	Fork forkVL = new Fork(Position.get(2, 2), Orientation.VERTICAL_LEFT);
	
	@Test
	public void test_link() {
		assertTrue(forkHU.canBeLinkedWith(new MockPlace(Position.get(2, 1))));
		assertTrue(forkHU.canBeLinkedWith(new MockPlace(Position.get(2, 3))));
		assertTrue(forkHU.canBeLinkedWith(new MockPlace(Position.get(1, 2))));
		assertFalse(forkHU.canBeLinkedWith(new MockPlace(Position.get(3, 2))));
		assertFalse(forkHU.canBeLinkedWith(new MockPlace(Position.get(3, 3))));
		assertFalse(forkHU.canBeLinkedWith(new MockPlace(Position.get(0, 0))));
		assertFalse(forkHU.canBeLinkedWith(new MockPlace(Position.get(0, 3))));
		assertFalse(forkHU.canBeLinkedWith(new MockPlace(Position.get(3, 0))));
		assertFalse(forkHU.canBeLinkedWith(new MockPlace(Position.get(4, 2))));
		assertFalse(forkHU.canBeLinkedWith(new MockPlace(Position.get(0, 2))));
		
		assertTrue(forkHD.canBeLinkedWith(new MockPlace(Position.get(2, 1))));
		assertTrue(forkHD.canBeLinkedWith(new MockPlace(Position.get(2, 3))));
		assertTrue(forkHD.canBeLinkedWith(new MockPlace(Position.get(3, 2))));
		assertFalse(forkHD.canBeLinkedWith(new MockPlace(Position.get(1, 2))));
		assertFalse(forkHD.canBeLinkedWith(new MockPlace(Position.get(3, 3))));
		assertFalse(forkHD.canBeLinkedWith(new MockPlace(Position.get(0, 0))));
		assertFalse(forkHD.canBeLinkedWith(new MockPlace(Position.get(0, 3))));
		assertFalse(forkHD.canBeLinkedWith(new MockPlace(Position.get(3, 0))));
		assertFalse(forkHD.canBeLinkedWith(new MockPlace(Position.get(4, 2))));
		assertFalse(forkHD.canBeLinkedWith(new MockPlace(Position.get(0, 2))));
		
		assertTrue(forkVR.canBeLinkedWith(new MockPlace(Position.get(1, 2))));
		assertTrue(forkVR.canBeLinkedWith(new MockPlace(Position.get(3, 2))));
		assertTrue(forkVR.canBeLinkedWith(new MockPlace(Position.get(2, 3))));
		assertFalse(forkVR.canBeLinkedWith(new MockPlace(Position.get(2, 1))));
		assertFalse(forkVR.canBeLinkedWith(new MockPlace(Position.get(3, 3))));
		assertFalse(forkVR.canBeLinkedWith(new MockPlace(Position.get(0, 0))));
		assertFalse(forkVR.canBeLinkedWith(new MockPlace(Position.get(0, 3))));
		assertFalse(forkVR.canBeLinkedWith(new MockPlace(Position.get(3, 0))));
		assertFalse(forkVR.canBeLinkedWith(new MockPlace(Position.get(4, 2))));
		assertFalse(forkVR.canBeLinkedWith(new MockPlace(Position.get(0, 2))));
		
		assertTrue(forkVL.canBeLinkedWith(new MockPlace(Position.get(1, 2))));
		assertTrue(forkVL.canBeLinkedWith(new MockPlace(Position.get(3, 2))));
		assertTrue(forkVL.canBeLinkedWith(new MockPlace(Position.get(2, 1))));
		assertFalse(forkVL.canBeLinkedWith(new MockPlace(Position.get(2, 3))));
		assertFalse(forkVL.canBeLinkedWith(new MockPlace(Position.get(3, 3))));
		assertFalse(forkVL.canBeLinkedWith(new MockPlace(Position.get(0, 0))));
		assertFalse(forkVL.canBeLinkedWith(new MockPlace(Position.get(0, 3))));
		assertFalse(forkVL.canBeLinkedWith(new MockPlace(Position.get(3, 0))));
		assertFalse(forkVL.canBeLinkedWith(new MockPlace(Position.get(4, 2))));
		assertFalse(forkVL.canBeLinkedWith(new MockPlace(Position.get(0, 2))));
	}
	
	@Test
	public void testEquals(){
		assertTrue(forkHU.equals(forkHU));
		assertTrue(forkHU.equals(new Fork(Position.get(2, 2), Orientation.HORIZONTAL_UP)));
		assertFalse(forkHU.equals(new Fork(Position.get(2, 2), Orientation.HORIZONTAL_DOWN)));
		assertFalse(forkHU.equals(new Fork(Position.get(2, 2), Orientation.VERTICAL_LEFT)));
		assertFalse(forkHU.equals(new Fork(Position.get(2, 2), Orientation.VERTICAL_RIGHT)));
		assertFalse(forkHU.equals(new MockPlace(Position.get(2, 2))));
		
		assertTrue(forkHD.equals(forkHD));
		assertTrue(forkHD.equals(new Fork(Position.get(2, 2), Orientation.HORIZONTAL_DOWN)));
		assertFalse(forkHD.equals(new Fork(Position.get(2, 2), Orientation.HORIZONTAL_UP)));
		assertFalse(forkHD.equals(new Fork(Position.get(2, 2), Orientation.VERTICAL_LEFT)));
		assertFalse(forkHD.equals(new Fork(Position.get(2, 2), Orientation.VERTICAL_RIGHT)));
		assertFalse(forkHD.equals(new MockPlace(Position.get(2, 2))));
		
		assertTrue(forkVR.equals(forkVR));
		assertTrue(forkVR.equals(new Fork(Position.get(2, 2), Orientation.VERTICAL_RIGHT)));
		assertFalse(forkVR.equals(new Fork(Position.get(2, 2), Orientation.HORIZONTAL_UP)));
		assertFalse(forkVR.equals(new Fork(Position.get(2, 2), Orientation.HORIZONTAL_DOWN)));
		assertFalse(forkVR.equals(new Fork(Position.get(2, 2), Orientation.VERTICAL_LEFT)));
		assertFalse(forkVR.equals(new MockPlace(Position.get(2, 2))));
		
		assertTrue(forkVL.equals(forkVL));
		assertTrue(forkVL.equals(new Fork(Position.get(2, 2), Orientation.VERTICAL_LEFT)));
		assertFalse(forkVL.equals(new Fork(Position.get(2, 2), Orientation.HORIZONTAL_UP)));
		assertFalse(forkVL.equals(new Fork(Position.get(2, 2), Orientation.HORIZONTAL_DOWN)));
		assertFalse(forkVL.equals(new Fork(Position.get(2, 2), Orientation.VERTICAL_RIGHT)));
		assertFalse(forkVL.equals(new MockPlace(Position.get(2, 2))));
	}
}
