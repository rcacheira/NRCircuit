package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;

import java.io.BufferedReader;
import java.io.IOException;

public class Circuit {

	Grid grid;
	
	public Circuit(BufferedReader bufReader) throws IOException, FileBadFormatException {
		grid = Grid.loadGrid(bufReader);
	}

	public Place getPlaceAtPosition(Position position){
		return grid.getPlaceAtPosition(position);
	}
	
	public Place getPlaceAtPosition(int row, int column){
		return getPlaceAtPosition(Position.get(row, column));
	}
}
