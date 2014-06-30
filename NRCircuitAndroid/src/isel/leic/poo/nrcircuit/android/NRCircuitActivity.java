package isel.leic.poo.nrcircuit.android;

import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;

public class NRCircuitActivity extends Activity {

	private NRCircuitController nrCircuitController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit);
		
		CircuitView circuitView = (CircuitView) findViewById(R.id.circuitView);
		
		int fileId = getResources().getIdentifier("raw/level1", null, this.getPackageName());
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						this.getResources().openRawResource(fileId)));
		
		try {
			nrCircuitController = (savedInstanceState != null) ?
					NRCircuitController.createController(circuitView, reader, savedInstanceState) :
					NRCircuitController.createController(circuitView, reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileBadFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
