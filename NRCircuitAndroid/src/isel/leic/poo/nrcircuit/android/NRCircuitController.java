package isel.leic.poo.nrcircuit.android;

import java.io.BufferedReader;
import java.io.IOException;

import isel.leic.poo.nrcircuit.android.common.TileActionEvent;
import isel.leic.poo.nrcircuit.android.views.CircuitTileFactory;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.CircuitView.OnTileActionListener;
import isel.leic.poo.nrcircuit.model.Circuit;
import isel.leic.poo.nrcircuit.model.Circuit.OnCircuitFinishedListener;
import isel.leic.poo.nrcircuit.model.Circuit.OnLinkClearListener;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.model.Position;
import android.os.Bundle;

public class NRCircuitController {

	private CircuitView view;
	private Circuit model;
	
	private int lastRow;
	private int lastColumn;
	
	private OnTileActionListener tileActionListener = new OnTileActionListener() {
		@Override
		public void onTileAction(TileActionEvent evt) {
//			System.out.println("evt on row: " + evt.row + " column:" + evt.column);
			switch(evt.event){
				case TILE_TOUCH:
					if(model.setWorkingPath(Position.get(evt.row, evt.column))){
						lastRow = evt.row;
						lastColumn = evt.column;
						System.out.println("working path ok");
					}
					return;
				case TILE_LINK:
					if((evt.row != lastRow || evt.column != lastColumn) &&
						model.doLink(Position.get(evt.row, evt.column))){
							if(view != null){
								view.setLink(lastRow, lastColumn, evt.row, evt.column, model.getCurrentLetter());
							}
							lastRow = evt.row;
							lastColumn = evt.column;
							System.out.println("link ok");
						}
					return;
			}
		}
	};
	
	private OnCircuitFinishedListener circuitFinishedListener = new OnCircuitFinishedListener() {
		@Override
		public void onCircuitFinished() {
			System.out.println("Circuit finished");
		}
	};
	
	private OnLinkClearListener linkClearListener = new OnLinkClearListener() {
		
		@Override
		public void onLinkClear(int row, int column) {
			if(view != null){
				System.out.println("clearLink row:" + row + " column:" + column);
				view.clearLink(row, column);
			}
		}
	};
	
	private NRCircuitController(CircuitView view, BufferedReader gridFile) throws IOException, FileBadFormatException {
		model = new Circuit(gridFile);
		model.setCircuitFinishedListener(circuitFinishedListener);
		model.setLinkClearListener(linkClearListener);
		
		this.view = view;
		
		lastRow = 0;
		lastColumn = 0;
		
		if(view != null){
			view.setTileProvider(new CircuitTileFactory(model));
			view.setTileActionListener(tileActionListener);
		}
	}

	public static NRCircuitController createController(CircuitView view, BufferedReader gridFile) throws IOException, FileBadFormatException{
		return new NRCircuitController(view, gridFile);
	}
	
	public static NRCircuitController createController(CircuitView view, BufferedReader gridFile, Bundle savedInstanceState) throws IOException, FileBadFormatException{
		//TODO: do something with savedInstanceState
		return new NRCircuitController(view, gridFile);
	}
	
}
