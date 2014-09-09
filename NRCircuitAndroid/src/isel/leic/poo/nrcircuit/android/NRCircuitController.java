package isel.leic.poo.nrcircuit.android;

import isel.leic.poo.nrcircuit.android.common.TileActionEvent;
import isel.leic.poo.nrcircuit.android.views.CircuitTileFactory;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.CircuitView.OnTileActionListener;
import isel.leic.poo.nrcircuit.android.views.MessageView;
import isel.leic.poo.nrcircuit.android.viewstate.GridSurrogate;
import isel.leic.poo.nrcircuit.model.Circuit;
import isel.leic.poo.nrcircuit.model.Circuit.OnCircuitActionListener;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.model.Position;

import java.io.BufferedReader;
import java.io.IOException;

import android.os.Bundle;

public class NRCircuitController {
	
	private static final String VIEW_STATE_KEY = "isel.leic.poo.nrcircuit.links";
	
	/**
	 * 
	 * @author rcacheira & nreis
	 *
	 */
	public interface OnLevelFinishedListener{
		public void levelFinished();
	}
	
	/**
	 * Holds the current circuit view.
	 */
	private final CircuitView circuitView;
	
	/**
	 * Holds the current circuit model.
	 */
	private final Circuit model;
	
	/**
	 * Listener to event OnLevelFinishedListener.
	 */
	private OnLevelFinishedListener levelFinishedListener;
	
	/**
	 * The controller instance.
	 * 
	 * @param circuitView	The Circuit View
	 * @param messageView	The Message View
	 * @param gridFile		The file of the level
	 * @throws IOException
	 * @throws FileBadFormatException
	 */
	private NRCircuitController(CircuitView circuitView, MessageView messageView, BufferedReader gridFile) throws IOException, FileBadFormatException {
		if(circuitView == null || messageView == null || gridFile == null)
			throw new IllegalArgumentException("Any argument can be null");
		
		model = new Circuit(gridFile);
		model.setCircuitActionListener(new OnCircuitActionListener(){
			@Override
			public void onLinkDone(Position origin, Position destiny, char letter) {
				NRCircuitController.this.circuitView.setLink(origin.row, origin.column, destiny.row, destiny.column, letter);
			}
			
			@Override
			public void onLinkClear(Position origin, Position destiny) {
				NRCircuitController.this.circuitView.clearLink(origin.row, origin.column, destiny.row, destiny.column);
			}
			@Override
			public void onSetTunnelsLetter(char letter) {
				NRCircuitController.this.circuitView.setTunnelsLetter(letter);
			}
		});
		
		messageView.setLevel( model.getLevel());
		
		this.circuitView = circuitView;
		this.circuitView.setTileActionListener(new OnTileActionListener() {
			
			private int lastRow = 0;
			private int lastColumn = 0;
			
			private boolean setWorkingPath(TileActionEvent evt){
				if(model.setWorkingPlace(Position.get(evt.row, evt.column))){
					lastRow = model.getLastPlacePosition().row;
					lastColumn = model.getLastPlacePosition().column;
					return true;
				}
				return false;
			}
			
			private boolean doLink(TileActionEvent evt){
				if((evt.row != lastRow || evt.column != lastColumn) 
						&& model.doLink(Position.get(evt.row, evt.column))){
					lastRow = evt.row;
					lastColumn = evt.column;
					if(model.isCircuitFinished()){
						if(levelFinishedListener != null){
							levelFinishedListener.levelFinished();
						}
					}
					return true;
				}
				return false;
			}
			
			@Override
			public void onTileAction(TileActionEvent evt) {
				switch(evt.event){
					case TILE_TOUCH:
						setWorkingPath(evt);
						return;
					case TILE_LINK:
						doLink(evt);
						return;
				}
			}
			
			@Override
			public void setGridSize() {
				NRCircuitController.this.circuitView.setGridSize(model.getRows(), model.getColumns());
			}

			@Override
			public void getLinkEvents() {
				model.askForLinkEvents();
			}
			
			
		});
		this.circuitView.setTileProvider(new CircuitTileFactory(model));
	}

	/**
	 * Saves the puzzle's state in the given bundle.
	 * 
	 * @param stateBundle The bundle used to store the puzzle's state
	 */
	public void saveState(Bundle stateBundle)
	{
		stateBundle.putParcelable(VIEW_STATE_KEY, new GridSurrogate(model.getGrid()));
	}
	
	/**
	 * Sets levelFinishedListener.
	 * 
	 * @param levelFinishedListener The custom listener to set.
	 */
	public void setOnLevelFinishedListener(OnLevelFinishedListener levelFinishedListener){
		this.levelFinishedListener = levelFinishedListener;
	}
	
	/**
	 * Factory method that creates a controller instance with the given view, a newly 
	 * instantiated model and the file to the given level.
	 * 
	 * @param circuitView	The Circuit view instance
	 * @param messageView	The Message View instance
	 * @param gridFile		The file of the level
	 * @return				The controller instance
	 * @throws IOException	
	 * @throws FileBadFormatException	
	 */
	public static NRCircuitController createController(CircuitView circuitView, MessageView messageView, BufferedReader gridFile) throws IOException, FileBadFormatException{
		return new NRCircuitController(circuitView, messageView, gridFile);
	}
	
	/**
	 * Factory method that creates a controller instance with the given view and the model
	 * retrieved from the received {@link Bundle} instance.
	 * 
	 * @param circuitView			The Circuit view instance
	 * @param messageView			The Message View instance
	 * @param gridFile				The file of the level
	 * @param savedInstanceState	The model state
	 * @return						The controller instance
	 * @throws IOException
	 * @throws FileBadFormatException
	 */
	public static NRCircuitController createController(CircuitView circuitView, MessageView messageView, BufferedReader gridFile, Bundle savedInstanceState) throws IOException, FileBadFormatException{
		NRCircuitController controller = new NRCircuitController(circuitView, messageView, gridFile);
		
		controller.model.getGrid().setLinks(((GridSurrogate) savedInstanceState.getParcelable(VIEW_STATE_KEY)).getLinks());
		
		return controller;
	}
	
}
