package isel.leic.poo.nrcircuit.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import isel.leic.poo.nrcircuit.android.common.TileActionEvent;
import isel.leic.poo.nrcircuit.android.common.TileActionEvent.TileEvent;
import isel.leic.poo.nrcircuit.android.views.CircuitTileFactory;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.CircuitView.OnTileActionListener;
import isel.leic.poo.nrcircuit.android.views.MessageView;
import isel.leic.poo.nrcircuit.android.viewstate.GridSurrogate;
import isel.leic.poo.nrcircuit.model.Circuit;
import isel.leic.poo.nrcircuit.model.Circuit.OnCircuitActionListener;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.model.Path;
import isel.leic.poo.nrcircuit.model.Place;
import isel.leic.poo.nrcircuit.model.Position;
import android.os.Bundle;

public class NRCircuitController {
	
	private static final String VIEW_STATE_KEY = "isel.leic.poo.nrcircuit.android.viewstate.Paths";
	
	public interface OnLevelFinishedListener{
		public void levelFinished();
	}
	
	private final CircuitView circuitView;
	private final Circuit model;
	private OnLevelFinishedListener levelFinishedListener;
	
	private NRCircuitController(CircuitView circuitView, MessageView messageView, BufferedReader gridFile) throws IOException, FileBadFormatException {
		if(circuitView == null || messageView == null || gridFile == null)
			throw new IllegalArgumentException("Any argument can be null");
		
		model = new Circuit(gridFile);
		model.setCircuitActionListener(new OnCircuitActionListener(){
			@Override
			public void onLinkClear(int row, int column) {
				NRCircuitController.this.circuitView.clearLink(row, column);
			}
		});
		
		messageView.setLevel( model.getLevel());
		
		this.circuitView = circuitView;
		this.circuitView.setTileActionListener(new OnTileActionListener() {
			
			private int lastRow = 0;
			private int lastColumn = 0;
			
			private boolean setWorkingPath(TileActionEvent evt){
				if(model.setWorkingPath(Position.get(evt.row, evt.column))){
					lastRow = model.getLastPlacePosition().row;
					lastColumn = model.getLastPlacePosition().column;
					return true;
				}
				return false;
			}
			
			private boolean doLink(TileActionEvent evt){
				if((evt.row != lastRow || evt.column != lastColumn) && model.doLink(Position.get(evt.row, evt.column))){
					if(evt.event == TileEvent.TILE_LINK){
						NRCircuitController.this.circuitView.setLink(lastRow, lastColumn, evt.row, evt.column, model.getCurrentLetter());
					}
					else{
						NRCircuitController.this.circuitView.setSingleLink(evt.row, evt.column, lastRow, lastColumn, model.getCurrentLetter());
					}
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
					case LINKED_TILE_TOUCH:
						if(setWorkingPath(evt)){
							doLink(evt);
						}
						return;
					case TILE_LINK:
						doLink(evt);
						return;
				}
			}

			@Override
			public void loadAllLinks() {
				for (Path path : model.getGrid()) {
					Iterator<Place> it = path.iterator();
					if(it.hasNext()){
						Position from = it.next().position;
						Position to;
						for (;it.hasNext();) {
							to = it.next().position;
							NRCircuitController.this.circuitView.setLink(from.row, from.column, to.row, to.column, path.getLetter());
							from = to;
						}
					}
				}
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
	
	public void setOnLevelFinishedListener(OnLevelFinishedListener levelFinishedListener){
		this.levelFinishedListener = levelFinishedListener;
	}
	
	public static NRCircuitController createController(CircuitView circuitView, MessageView messageView, BufferedReader gridFile) throws IOException, FileBadFormatException{
		return new NRCircuitController(circuitView, messageView, gridFile);
	}
	
	public static NRCircuitController createController(CircuitView circuitView, MessageView messageView, BufferedReader gridFile, Bundle savedInstanceState) throws IOException, FileBadFormatException{
		NRCircuitController controller = new NRCircuitController(circuitView, messageView, gridFile);
		
		controller.model.getGrid().setPaths(((GridSurrogate) savedInstanceState.getParcelable(VIEW_STATE_KEY)).getPaths(controller.model.getGrid()));
		
		return controller;
	}
	
}
