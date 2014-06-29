package isel.leic.poo.nrcircuit.android;

import java.io.BufferedReader;
import java.io.IOException;

import isel.leic.poo.nrcircuit.android.views.CircuitTileFactory;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.model.Circuit;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import android.os.Bundle;

public class NRCircuitController {

	CircuitView view;
	Circuit model;
	
	private NRCircuitController(CircuitView view, BufferedReader gridFile) throws IOException, FileBadFormatException {
		model = new Circuit(gridFile);
		this.view = view;
		
		view.setTileProvider(new CircuitTileFactory(model));
	}

	public static NRCircuitController createController(CircuitView view, BufferedReader gridFile) throws IOException, FileBadFormatException{
		return new NRCircuitController(view, gridFile);
	}
	
	public static NRCircuitController createController(CircuitView view, BufferedReader gridFile, Bundle savedInstanceState) throws IOException, FileBadFormatException{
		return createController(view, gridFile);
	}
	
}
