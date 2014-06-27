package isel.leic.poo.nrcircuit.model.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import isel.leic.poo.nrcircuit.model.Coordinate;
import isel.leic.poo.nrcircuit.model.Direction;
import isel.leic.poo.nrcircuit.model.Direction.Position;
import isel.leic.poo.nrcircuit.model.Grid;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.model.Place;
import isel.leic.poo.nrcircuit.model.ProhibitedPlace;
import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector.Orientation;
import isel.leic.poo.nrcircuit.model.terminals.FinalTerminal;

public class GridTests {
	
	String goodStr;
	
	public GridTests(){
		goodStr = "#1 5x5";
		goodStr+="\nA - - - .";
		goodStr+="\nB - - . |";
		goodStr+="\nC C * | |";
		goodStr+="\nB - - . |";
		goodStr+="\nA - - - .";
	}
	
	@Test
	public void test_good_load() throws IOException, FileBadFormatException {
		
		Grid grid = Grid.loadGrid(new BufferedReader(new StringReader(goodStr)));
		
		assertThat(grid.getLevel(), is(equalTo(1)));
		assertThat(grid.getWidth(), is(equalTo(5)));
		assertThat(grid.getHeight(), is(equalTo(5)));		
		assertThat(grid.getSize(), is(equalTo(5*5)));
		
		assertThat(grid.getPlaceAtPosition(Coordinate.get(0,0)), is(equalTo((Place)new FinalTerminal(Coordinate.get(0, 0), 'A'))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(0,1)), is(equalTo((Place)new OneWayConnector(Coordinate.get(0, 1), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(0,2)), is(equalTo((Place)new OneWayConnector(Coordinate.get(0, 2), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(0,3)), is(equalTo((Place)new OneWayConnector(Coordinate.get(0, 3), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(0,4)), is(equalTo((Place)new Connector(Coordinate.get(0, 4)))));
		
		assertThat(grid.getPlaceAtPosition(Coordinate.get(1,0)), is(equalTo((Place)new FinalTerminal(Coordinate.get(1, 0), 'B'))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(1,1)), is(equalTo((Place)new OneWayConnector(Coordinate.get(1, 1), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(1,2)), is(equalTo((Place)new OneWayConnector(Coordinate.get(1, 2), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(1,3)), is(equalTo((Place)new Connector(Coordinate.get(1, 3)))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(1,4)), is(equalTo((Place)new OneWayConnector(Coordinate.get(1, 4), Orientation.VERTICAL))));
		
		assertThat(grid.getPlaceAtPosition(Coordinate.get(2,0)), is(equalTo((Place)new FinalTerminal(Coordinate.get(2, 0), 'C'))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(2,1)), is(equalTo((Place)new FinalTerminal(Coordinate.get(2, 1), 'C'))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(2,2)), is(equalTo((Place)new ProhibitedPlace(Coordinate.get(2, 2)))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(2,3)), is(equalTo((Place)new OneWayConnector(Coordinate.get(2, 3), Orientation.VERTICAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(2,4)), is(equalTo((Place)new OneWayConnector(Coordinate.get(2, 4), Orientation.VERTICAL))));
		
		assertThat(grid.getPlaceAtPosition(Coordinate.get(3,0)), is(equalTo((Place)new FinalTerminal(Coordinate.get(3, 0), 'B'))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(3,1)), is(equalTo((Place)new OneWayConnector(Coordinate.get(3, 1), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(3,2)), is(equalTo((Place)new OneWayConnector(Coordinate.get(3, 2), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(3,3)), is(equalTo((Place)new Connector(Coordinate.get(3, 3)))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(3,4)), is(equalTo((Place)new OneWayConnector(Coordinate.get(3, 4), Orientation.VERTICAL))));
		
		assertThat(grid.getPlaceAtPosition(Coordinate.get(4,0)), is(equalTo((Place)new FinalTerminal(Coordinate.get(4, 0), 'A'))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(4,1)), is(equalTo((Place)new OneWayConnector(Coordinate.get(4, 1), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(4,2)), is(equalTo((Place)new OneWayConnector(Coordinate.get(4, 2), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(4,3)), is(equalTo((Place)new OneWayConnector(Coordinate.get(4, 3), Orientation.HORIZONTAL))));
		assertThat(grid.getPlaceAtPosition(Coordinate.get(4,4)), is(equalTo((Place)new Connector(Coordinate.get(4, 4)))));
	}
	
	@Test
	public void test_cross() throws IOException, FileBadFormatException {
		
		Grid grid = Grid.loadGrid(new BufferedReader(new StringReader(goodStr)));
		
		assertFalse(grid.setWorkingPath(Coordinate.get(0, 1), Direction.get(Position.LEFT, Position.RIGHT)));
		assertFalse(grid.doCross(Coordinate.get(0, 1), Direction.get(Position.LEFT, Position.RIGHT)));
		assertFalse(grid.isComplete());
		
		assertTrue(grid.setWorkingPath(Coordinate.get(0, 0), Direction.get(Position.CENTER, Position.RIGHT)));
		assertTrue(grid.doCross(Coordinate.get(0, 1), Direction.get(Position.LEFT, Position.RIGHT)));
		assertTrue(grid.doCross(Coordinate.get(0, 2), Direction.get(Position.LEFT, Position.RIGHT)));
		assertTrue(grid.doCross(Coordinate.get(0, 3), Direction.get(Position.LEFT, Position.RIGHT)));
		assertTrue(grid.doCross(Coordinate.get(0, 4), Direction.get(Position.LEFT, Position.DOWN)));
		assertTrue(grid.doCross(Coordinate.get(1, 4), Direction.get(Position.UP, Position.DOWN)));
		assertTrue(grid.doCross(Coordinate.get(2, 4), Direction.get(Position.UP, Position.DOWN)));
		assertTrue(grid.doCross(Coordinate.get(3, 4), Direction.get(Position.UP, Position.DOWN)));
		assertTrue(grid.doCross(Coordinate.get(4, 4), Direction.get(Position.UP, Position.LEFT)));
		assertTrue(grid.doCross(Coordinate.get(4, 3), Direction.get(Position.RIGHT, Position.LEFT)));
		assertTrue(grid.doCross(Coordinate.get(4, 2), Direction.get(Position.RIGHT, Position.LEFT)));
		assertTrue(grid.doCross(Coordinate.get(4, 1), Direction.get(Position.RIGHT, Position.LEFT)));
		assertTrue(grid.doCross(Coordinate.get(4, 0), Direction.get(Position.RIGHT, Position.CENTER)));
		
		assertFalse(grid.isComplete());
		
		assertTrue(grid.setWorkingPath(Coordinate.get(1, 0), Direction.get(Position.CENTER, Position.RIGHT)));
		assertTrue(grid.doCross(Coordinate.get(1, 1), Direction.get(Position.LEFT, Position.RIGHT)));
		assertTrue(grid.doCross(Coordinate.get(1, 2), Direction.get(Position.LEFT, Position.RIGHT)));
		assertTrue(grid.doCross(Coordinate.get(1, 3), Direction.get(Position.LEFT, Position.DOWN)));
		assertTrue(grid.doCross(Coordinate.get(2, 3), Direction.get(Position.UP, Position.DOWN)));
		assertTrue(grid.doCross(Coordinate.get(3, 3), Direction.get(Position.UP, Position.LEFT)));
		assertTrue(grid.doCross(Coordinate.get(3, 2), Direction.get(Position.RIGHT, Position.LEFT)));
		assertTrue(grid.doCross(Coordinate.get(3, 1), Direction.get(Position.RIGHT, Position.LEFT)));
		assertTrue(grid.doCross(Coordinate.get(3, 0), Direction.get(Position.RIGHT, Position.CENTER)));
		
		assertFalse(grid.isComplete());
		
		assertTrue(grid.setWorkingPath(Coordinate.get(2, 0), Direction.get(Position.CENTER, Position.RIGHT)));
		assertTrue(grid.doCross(Coordinate.get(2, 1), Direction.get(Position.LEFT, Position.CENTER)));
	
		assertTrue(grid.isComplete());
	}
	
	@Test (expected = IllegalStateException.class)
	public void test_illegalStateException_on_doCross() throws IOException, FileBadFormatException{
		
		Grid grid = Grid.loadGrid(new BufferedReader(new StringReader(goodStr)));
		
		assertTrue(grid.setWorkingPath(Coordinate.get(0, 0), Direction.get(Position.CENTER, Position.RIGHT)));
		grid.doCross(Coordinate.get(0, 2), Direction.get(Position.LEFT, Position.RIGHT));
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_illegalArgumentException_on_doCross1() throws IOException, FileBadFormatException{
		
		Grid grid = Grid.loadGrid(new BufferedReader(new StringReader(goodStr)));
		
		assertTrue(grid.setWorkingPath(Coordinate.get(0, 0), Direction.get(Position.CENTER, Position.RIGHT)));
		grid.doCross(Coordinate.get(5, 2), Direction.get(Position.LEFT, Position.RIGHT));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_illegalArgumentException_on_doCross2() throws IOException, FileBadFormatException{
		
		Grid grid = Grid.loadGrid(new BufferedReader(new StringReader(goodStr)));
		
		assertTrue(grid.setWorkingPath(Coordinate.get(0, 0), Direction.get(Position.CENTER, Position.RIGHT)));
		grid.doCross(Coordinate.get(2, 5), Direction.get(Position.LEFT, Position.RIGHT));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_illegalArgumentException_on_doCross3() throws IOException, FileBadFormatException{
		
		Grid grid = Grid.loadGrid(new BufferedReader(new StringReader(goodStr)));
		
		assertTrue(grid.setWorkingPath(Coordinate.get(0, 0), Direction.get(Position.CENTER, Position.RIGHT)));
		grid.doCross(Coordinate.get(5, 5), Direction.get(Position.LEFT, Position.RIGHT));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_illegalArgumentException_on_setWorkingPath1() throws IOException, FileBadFormatException{
		
		Grid grid = Grid.loadGrid(new BufferedReader(new StringReader(goodStr)));
		
		assertTrue(grid.setWorkingPath(Coordinate.get(5, 0), Direction.get(Position.CENTER, Position.RIGHT)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_illegalArgumentException_on_setWorkingPath2() throws IOException, FileBadFormatException{
		
		Grid grid = Grid.loadGrid(new BufferedReader(new StringReader(goodStr)));
		
		assertTrue(grid.setWorkingPath(Coordinate.get(0, 5), Direction.get(Position.CENTER, Position.RIGHT)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_illegalArgumentException_on_setWorkingPath3() throws IOException, FileBadFormatException{
		
		Grid grid = Grid.loadGrid(new BufferedReader(new StringReader(goodStr)));
		
		assertTrue(grid.setWorkingPath(Coordinate.get(5, 5), Direction.get(Position.CENTER, Position.RIGHT)));
	}
	
	@Test (expected = NumberFormatException.class)
	public void test_letter_on_level() throws IOException, FileBadFormatException {
		String str = "#a 5x5";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
	@Test (expected = NumberFormatException.class)
	public void test_letter_on_width() throws IOException, FileBadFormatException {
		String str = "#1 ax5";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
	@Test (expected = NumberFormatException.class)
	public void test_letter_on_height() throws IOException, FileBadFormatException {
		String str = "#1 5xa";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
	@Test (expected = FileBadFormatException.class)
	public void test_withou_cardinal() throws IOException, FileBadFormatException {
		String str = "1 5x5";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
	@Test (expected = FileBadFormatException.class)
	public void test_no_space_after_cardinal() throws IOException, FileBadFormatException {
		String str = "#15x5";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
	@Test (expected = FileBadFormatException.class)
	public void test_no_x_on_Size() throws IOException, FileBadFormatException {
		String str = "#1 55";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
	@Test (expected = FileBadFormatException.class)
	public void test_less_lines_then_height() throws IOException, FileBadFormatException {
		String str = "#1 5x5";
		str+="\nA . . . .";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
	@Test (expected = FileBadFormatException.class)
	public void test_more_lines_then_height() throws IOException, FileBadFormatException {
		String str = "#1 5x5";
		str+="\nA - - - .";
		str+="\nB - - . |";
		str+="\nC C * | |";
		str+="\nB - - . |";
		str+="\nA - - - .";
		str+="\nD . . . .";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
	@Test (expected = FileBadFormatException.class)
	public void test_less_columns_then_width() throws IOException, FileBadFormatException {
		String str = "#1 5x5";
		str+="\nA - - - ";
		str+="\nB - - . |";
		str+="\nC C * | |";
		str+="\nB - - . |";
		str+="\nA - - - .";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
	@Test (expected = FileBadFormatException.class)
	public void test_more_columns_then_width() throws IOException, FileBadFormatException {
		String str = "#1 5x5";
		str+="\nB - - . | .";
		str+="\nC C * | |";
		str+="\nB - - . |";
		str+="\nA - - - .";
		
		Grid.loadGrid(new BufferedReader(new StringReader(str)));
	}
	
}
