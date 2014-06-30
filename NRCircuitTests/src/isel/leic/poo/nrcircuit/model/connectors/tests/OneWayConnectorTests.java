package isel.leic.poo.nrcircuit.model.connectors.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.MockPlace;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector.Orientation;

import org.junit.Test;

public class OneWayConnectorTests {

	OneWayConnector oneWayConnectorH = new OneWayConnector(Position.get(2, 2), Orientation.HORIZONTAL);
	OneWayConnector oneWayConnectorV = new OneWayConnector(Position.get(2, 2), Orientation.VERTICAL);
	
	@Test
	public void test_link() {
		assertTrue(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(2, 1))));
		assertTrue(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(2, 3))));
		assertFalse(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(1, 2))));
		assertFalse(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(3, 2))));
		assertFalse(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(3, 3))));
		assertFalse(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(0, 0))));
		assertFalse(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(0, 3))));
		assertFalse(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(3, 0))));
		assertFalse(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(4, 2))));
		assertFalse(oneWayConnectorH.canBeLinkedTo(new MockPlace(Position.get(0, 2))));
		
		assertTrue(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(1, 2))));
		assertTrue(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(3, 2))));
		assertFalse(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(2, 1))));
		assertFalse(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(2, 3))));
		assertFalse(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(3, 3))));
		assertFalse(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(0, 0))));
		assertFalse(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(0, 3))));
		assertFalse(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(3, 0))));
		assertFalse(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(4, 2))));
		assertFalse(oneWayConnectorV.canBeLinkedTo(new MockPlace(Position.get(0, 2))));
	}
	
	@Test
	public void testEquals(){
		assertTrue(oneWayConnectorH.equals(oneWayConnectorH));
		assertTrue(oneWayConnectorH.equals(new OneWayConnector(Position.get(2, 2), Orientation.HORIZONTAL)));
		assertFalse(oneWayConnectorH.equals(new OneWayConnector(Position.get(2, 2), Orientation.VERTICAL)));
		assertFalse(oneWayConnectorH.equals(new MockPlace(Position.get(2, 2))));
		
		assertTrue(oneWayConnectorV.equals(oneWayConnectorV));
		assertTrue(oneWayConnectorV.equals(new OneWayConnector(Position.get(2, 2), Orientation.VERTICAL)));
		assertFalse(oneWayConnectorV.equals(new OneWayConnector(Position.get(2, 2), Orientation.HORIZONTAL)));
		assertFalse(oneWayConnectorV.equals(new MockPlace(Position.get(2, 2))));
	}
}
