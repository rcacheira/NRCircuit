package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.model.Grid.OnPlacesRemovedFromPathListener;

import java.io.BufferedReader;
import java.io.IOException;

public class Circuit {

	public static interface OnCircuitActionListener{
		public void onLinkClear(int row, int column);
	}
	
	private OnCircuitActionListener circuitActionListener;
	
	private Grid grid;
	
	public Circuit(BufferedReader bufReader) throws IOException, FileBadFormatException {
		grid = Grid.loadGrid(bufReader);
		grid.setPlacesRemovedFromPathListener(new OnPlacesRemovedFromPathListener() {
			@Override
			public void onPlacesRemovedFromPath(Iterable<Place> placesRemoved) {
				if(circuitActionListener != null){
					for (Place place : placesRemoved) {
						if(place != null)
							circuitActionListener.onLinkClear(place.position.row, place.position.column);
					}
				}
			}
		});
	}

	public boolean setWorkingPath(Position position){
		return grid.setWorkingPath(position);
	}
	
	public boolean doLink(Position position){
		return grid.doLink(position);
	}
	
	public boolean isCircuitFinished(){
		return grid.isComplete();
	}
	
	public Position getLastPlacePosition(){
		return grid.getWorkingPath().getLastPlace().position;
	}
	
	public char getCurrentLetter(){
		return grid.getWorkingPath().getLetter();
	}
	
	public Place getPlaceAtPosition(Position position){
		return grid.getPlaceAtPosition(position);
	}
	
	public void setCircuitActionListener(OnCircuitActionListener circuitActionListener) {
		this.circuitActionListener = circuitActionListener;
	}
	
	public int getLevel(){
		return grid.getLevel();
	}
	
	public Grid getGrid(){
		return grid;
	}
}
