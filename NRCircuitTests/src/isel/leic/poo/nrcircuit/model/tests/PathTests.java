package isel.leic.poo.nrcircuit.model.tests;

import static org.junit.Assert.*;
import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Path;
import isel.leic.poo.nrcircuit.model.Path.PathFullException;
import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.terminals.FinalTerminal;

import org.junit.Test;

public class PathTests {
	
	@Test
	public void test_good_add_sequence_and_final_place(){
		FinalTerminal ft1 = new FinalTerminal(Coordinate.get(0, 0), 'A');
		Path path = new Path(ft1);
		assertTrue(path.getLastPlace().equals(ft1));
		Connector c1 = new Connector(Coordinate.get(0, 1));
		path.add(c1);
		assertFalse(path.getLastPlace().equals(ft1));
		assertTrue(path.getLastPlace().equals(c1));
		Connector c2 = new Connector(Coordinate.get(0, 2));
		path.add(c2);
		assertFalse(path.getLastPlace().equals(ft1));
		assertFalse(path.getLastPlace().equals(c1));
		assertTrue(path.getLastPlace().equals(c2));
		Connector c3 = new Connector(Coordinate.get(1, 2));
		path.add(c3);
		assertFalse(path.getLastPlace().equals(ft1));
		assertFalse(path.getLastPlace().equals(c1));
		assertFalse(path.getLastPlace().equals(c2));
		assertTrue(path.getLastPlace().equals(c3));
		Connector c4 = new Connector(Coordinate.get(1, 3));
		path.add(c4);
		assertFalse(path.getLastPlace().equals(ft1));
		assertFalse(path.getLastPlace().equals(c1));
		assertFalse(path.getLastPlace().equals(c2));
		assertFalse(path.getLastPlace().equals(c3));
		assertTrue(path.getLastPlace().equals(c4));
		FinalTerminal ft2 = new FinalTerminal(Coordinate.get(1, 4), 'A');
		path.add(ft2);
		assertFalse(path.getLastPlace().equals(ft1));
		assertFalse(path.getLastPlace().equals(c1));
		assertFalse(path.getLastPlace().equals(c2));
		assertFalse(path.getLastPlace().equals(c3));
		assertFalse(path.getLastPlace().equals(c4));
		assertTrue(path.getLastPlace().equals(ft2));
	}
	
	@Test(expected = PathFullException.class)
	public void test_bad_add_sequence(){
		Path path = new Path(new FinalTerminal(Coordinate.get(0, 0), 'A'));
		path.add(new Connector(Coordinate.get(0, 1)));
		path.add(new Connector(Coordinate.get(0, 2)));
		path.add(new Connector(Coordinate.get(1, 2)));
		path.add(new FinalTerminal(Coordinate.get(1, 3),'A'));
		path.add(new Connector(Coordinate.get(1, 4)));
	}
}
