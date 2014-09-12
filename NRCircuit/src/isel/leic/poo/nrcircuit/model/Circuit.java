package isel.leic.poo.nrcircuit.model;

import  isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.model.Grid.OnGridActionListener;

import java.io.BufferedReader;
import java.io.IOException;

public class Circuit {

	/**
	 * class whose instance represents a Listener to pass actions to 
	 * platform dependent implementation
	 * 
	 * @author rcacheira & nreis
	 *
	 */
	public static interface OnCircuitActionListener{
		public void onLinkDone(Position origin, Position destiny, char letter);
		public void onLinkClear(Position origin, Position destiny);
		public void onSetTunnelsLetter(char letter);
	}
	
	private OnCircuitActionListener circuitActionListener;
	
	private Grid grid;
	
	/**
	 * creates a new instance of Circuit with given parameters
	 * 
	 * @param bufReader Bufferedreader to read the level data
	 * @throws IOException 
	 * @throws FileBadFormatException
	 */
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

	/**
	 * Tries to set a new working place with position
	 * 
	 * @param position {@code Place} {@code Position}
	 * @return {@code true} if the place at position could be set as working, 
	 * {@code false} otherwise
	 */
	public boolean setWorkingPlace(Position position){
		return grid.setWorkingPlace(position);
	}
	
	/**
	 * Tries to do a new link from working place to place with given position 
	 * 
	 * @param position next place link position
	 * @return {@code true} if link was made, {@code false} other wise
	 */
	public boolean doLink(Position position){
		return grid.doLink(position);
	}
	
	/**
	 * Verifies if circuit is complete
	 * 
	 * @return {@code true} if circuit is complete, {@code false} otherwise
	 */
	public boolean isCircuitFinished(){
		return grid.isComplete();
	}
	
	/**
	 * gets the last place position
	 * 
	 * @return returns the last {@code Place} {@code Position}
	 */
	public Position getLastPlacePosition(){
		return grid.getWorkingPlace().position;
	}
	
	/**
	 * get a place at given position
	 * 
	 * @param position place's position 
	 * @return {@code Place} at given {@code Position}
	 */
	public Place getPlaceAtPosition(Position position){
		return grid.getPlaceAtPosition(position);
	}
	
	/**
	 * Sets a new circuitActionListener
	 * 
	 * @param circuitActionListener the new circuit action listener
	 */
	public void setCircuitActionListener(OnCircuitActionListener circuitActionListener) {
		this.circuitActionListener = circuitActionListener;
	}
	
	/**
	 * Gets the current level
	 * 
	 * @return the current level
	 */
	public int getLevel(){
		return grid.getLevel();
	}

	/**
	 * gets the circuit number of rows
	 * 
	 * @return the circuit number of rows
	 */
	public int getRows() {
		return grid.getRows();
	}

	/**
	 * gets the circuit number of columns
	 * 
	 * @return the circuit number of columns
	 */
	public int getColumns() {
		return grid.getColumns();
	}
	
	/**
	 * gets the circuit grid
	 * 
	 * @return the circuit {@code Grid}
	 */
	public Grid getGrid(){
		return grid;
	}
	
	/**
	 * asks the circuit to re-launch the link events
	 */
	public void askForLinkEvents(){
		grid.fireLinkEvents();
	}
}
