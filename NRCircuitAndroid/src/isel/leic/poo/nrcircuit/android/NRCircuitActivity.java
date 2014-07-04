package isel.leic.poo.nrcircuit.android;

import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.MessageView;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.android.NRCircuitController;
import isel.leic.poo.nrcircuit.android.NRCircuitController.OnLevelFinishedListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;

public class NRCircuitActivity extends Activity {

	private NRCircuitController nrCircuitController;
	private CircuitView circuitView;
	private MessageView messageView;
	
	int level;

	private void createController(Bundle savedInstanceState){
		
		int fileId = getResources().getIdentifier("raw/level"+level, null, this.getPackageName());
		
		if(fileId == 0){
			System.out.println("You have finished all levels!");
			return;
		}
		
		System.out.println("Loading level " + NRCircuitActivity.this.level + " ... ");
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						this.getResources().openRawResource(fileId)));
		
		try {
			nrCircuitController = (savedInstanceState != null) ?
					NRCircuitController.createController(circuitView, messageView, reader, savedInstanceState) :
					NRCircuitController.createController(circuitView, messageView, reader);
					
			nrCircuitController.setOnLevelFinishedListener(new OnLevelFinishedListener(){
				@Override
				public void levelFinished() {
					System.out.println("Level " + NRCircuitActivity.this.level + " Finished !!");
					NRCircuitActivity.this.level+=1;
					createController(null);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileBadFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit);
		
		level = 1;
		
		circuitView = (CircuitView) findViewById(R.id.circuitView);
		messageView = (MessageView) findViewById(R.id.messageView);
		
		createController(savedInstanceState);
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		nrCircuitController.saveState(outState);
	}

}
