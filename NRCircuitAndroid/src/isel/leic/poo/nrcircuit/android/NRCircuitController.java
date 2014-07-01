package isel.leic.poo.nrcircuit.android;

import java.io.BufferedReader;
import java.io.IOException;

import isel.leic.poo.nrcircuit.android.common.TileActionEvent;
import isel.leic.poo.nrcircuit.android.common.TileActionEvent.TileEvent;
import isel.leic.poo.nrcircuit.android.views.CircuitTileFactory;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.CircuitView.OnTileActionListener;
import isel.leic.poo.nrcircuit.model.Circuit;
import isel.leic.poo.nrcircuit.model.Circuit.OnCircuitActionListener;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.model.Position;
import android.os.Bundle;

public class NRCircuitController {
	
	public interface OnLevelFinishedListener{
		public void levelFinished();
	}
	
	private final CircuitView view;
	private final Circuit model;
	private OnLevelFinishedListener levelFinishedListener;
	
	private NRCircuitController(CircuitView circuitView, BufferedReader gridFile) throws IOException, FileBadFormatException {
		model = new Circuit(gridFile);
		model.setCircuitActionListener(new OnCircuitActionListener(){
			@Override
			public void onLinkClear(int row, int column) {
					view.clearLink(row, column);
			}
		});
		
		this.view = circuitView;
		
		if(this.view != null){
			this.view.setTileProvider(new CircuitTileFactory(model));
			this.view.setTileActionListener(new OnTileActionListener() {
				
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
							view.setLink(lastRow, lastColumn, evt.row, evt.column, model.getCurrentLetter());
						}
						else{
							view.setSingleLink(evt.row, evt.column, lastRow, lastColumn, model.getCurrentLetter());
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
			});
		}
	}

	public void setOnLevelFinishedListener(OnLevelFinishedListener levelFinishedListener){
		this.levelFinishedListener = levelFinishedListener;
	}
	
	public static NRCircuitController createController(CircuitView view, BufferedReader gridFile) throws IOException, FileBadFormatException{
		return new NRCircuitController(view, gridFile);
	}
	
	public static NRCircuitController createController(CircuitView view, BufferedReader gridFile, Bundle savedInstanceState) throws IOException, FileBadFormatException{
		//TODO: do something with savedInstanceState
		return new NRCircuitController(view, gridFile);
	}
	
}
