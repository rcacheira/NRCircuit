package isel.leic.poo.nrcircuit.model.connectors.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.MockPlace;
import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.connectors.NumberedConnector;

import org.junit.Test;

public class NumberedConnectorTests {
	
	NumberedConnector connector = new NumberedConnector(Position.get(2, 2), 1);
	
	@Test  (expected = IllegalArgumentException.class)
	public void test_bad_initialization(){
		new NumberedConnector(Position.get(2, 2), 0);
	}
	
	@Test
	public void testEquals(){
		assertTrue(connector.equals(connector));
		assertTrue(connector.equals(new NumberedConnector(Position.get(2, 2), 1)));
		assertFalse(connector.equals(new NumberedConnector(Position.get(2, 2), 2)));
		assertFalse(connector.equals(new MockPlace(Position.get(2, 2))));
	}

}
