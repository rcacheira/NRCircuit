package isel.leic.poo.nrcircuit.model;

import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.model.Grid.OnPlacesRemovedFromPathListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Circuit {

	public static interface OnCircuitFinishedListener{
		public void onCircuitFinished();
	}
	
	public static interface OnLinkClearListener{
		public void onLinkClear(int row, int column);
	}
	
	private OnCircuitFinishedListener circuitFinishedListener;
	
	private OnLinkClearListener linkClearListener;
	
	private Grid grid;
	
	public Circuit(BufferedReader bufReader) throws IOException, FileBadFormatException {
		grid = Grid.loadGrid(bufReader);
		grid.setPlacesRemovedFromPathListener(new OnPlacesRemovedFromPathListener() {
			@Override
			public void onPlacesRemovedFromPath(List<Place> placesRemoved) {
				if(linkClearListener != null){
					for (Place place : placesRemoved) {
						if(place != null)
							linkClearListener.onLinkClear(place.position.row, place.position.column);
					}
				}
			}
		});
	}

	public boolean setWorkingPath(Position position){
		return grid.setWorkingPath(position);
	}
	
	public boolean doLink(Position position){
		if(grid.doLink(position)){
			if(grid.isComplete()){
				if(circuitFinishedListener != null)
					circuitFinishedListener.onCircuitFinished();
			}
			return true;
		}
		return false;
	}
	
	public char getCurrentLetter(){
		return grid.getWorkingPath().getLetter();
	}
	
	public Place getPlaceAtPosition(Position position){
		return grid.getPlaceAtPosition(position);
	}
	
	public void setCircuitFinishedListener(OnCircuitFinishedListener circuitFinishedListener) {
		this.circuitFinishedListener = circuitFinishedListener;
	}
	
	public void setLinkClearListener(OnLinkClearListener linkClearListener) {
		this.linkClearListener = linkClearListener;
	}
}
