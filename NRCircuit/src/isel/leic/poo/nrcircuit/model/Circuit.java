package isel.leic.poo.nrcircuit.model;

import  isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.model.Grid.OnGridActionListener;

import java.io.BufferedReader;
import java.io.IOException;

public class Circuit {

	public static interface OnCircuitActionListener{
		public void onLinkDone(Position origin, Position destiny, char letter);
		public void onLinkClear(Position origin, Position destiny);
		public void onSetTunnelsLetter(char letter);
	}
	
	private OnCircuitActionListener circuitActionListener;
	
	private Grid grid;
	
	public Circuit(BufferedReader bufReader) throws IOException, FileBadFormatException {
		grid = Grid.loadGrid(bufReader);
		grid.setLinkListener(new OnGridActionListener() {
			@Override
			public void onLinkClear(Link link) {
				if(circuitActionListener != null){
					circuitActionListener.onLinkClear(link.origin, link.destiny);
				}
			}

			@Override
			public void onLinkDone(Link link, char letter) {
				if(circuitActionListener != null){
					circuitActionListener.onLinkDone(link.origin, link.destiny, letter);
				}
			}

			@Override
			public void onSetTunnelsLetter(char letter) {
				if(circuitActionListener != null){
					circuitActionListener.onSetTunnelsLetter(letter);
				}
			}
		});
	}

	public boolean setWorkingPlace(Position position){
		return grid.setWorkingPlace(position);
	}
	
	public boolean doLink(Position position){
		return grid.doLink(position);
	}
	
	public boolean isCircuitFinished(){
		return grid.isComplete();
	}
	
	public Position getLastPlacePosition(){
		return grid.getWorkingPlace().position;
	}
	
	public char getCurrentLetter(){
		return grid.getWorkingPlace().getLetter();
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

	public int getRows() {
		return grid.getRows();
	}

	public int getColumns() {
		return grid.getColumns();
	}
	
	public Grid getGrid(){
		return grid;
	}
	
	public void askForLinkEvents(){
		grid.fireLinkEvents();
	}
}
