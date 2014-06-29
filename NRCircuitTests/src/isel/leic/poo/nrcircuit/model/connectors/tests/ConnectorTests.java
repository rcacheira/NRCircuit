package isel.leic.poo.nrcircuit.model.connectors.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.MockPlace;
import isel.leic.poo.nrcircuit.model.connectors.Connector;

import org.junit.Test;

public class ConnectorTests {
	
	Connector connector = new Connector(Position.get(2, 2));
	
	@Test
	public void testEquals(){
		assertTrue(connector.equals(connector));
		assertTrue(connector.equals(new Connector(Position.get(2, 2))));
		assertFalse(connector.equals(new MockPlace(Position.get(2, 2))));
	}

}
