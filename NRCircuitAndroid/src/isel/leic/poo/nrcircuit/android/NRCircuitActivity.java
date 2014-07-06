package isel.leic.poo.nrcircuit.android;

import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.MessageView;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.android.NRCircuitController;
import isel.leic.poo.nrcircuit.android.NRCircuitController.OnLevelFinishedListener;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;

public class NRCircuitActivity extends Activity {

	private final static String LEVEL_KEY = "isel.leic.poo.nrcircuit.android.Activity.level";
	private final static String LEVEL_PROGRESS_FILE_NAME = "d001";
	
	private NRCircuitController nrCircuitController;
	private CircuitView circuitView;
	private MessageView messageView;
	
	int level;

	private void createController(Bundle savedInstanceState){
		
		int fileId = getResources().getIdentifier("raw/level"+level, null, this.getPackageName());
		
		if(fileId == 0){
			System.out.println("You have finished all levels!");
			level = 1;
			createController(savedInstanceState);
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
					try {
						saveLevelProgress(level+1);
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Level progress saved !!!");
					NRCircuitActivity.this.level+=1;
					createController(null);
				}
			});
		} catch (IOException | FileBadFormatException e) {
			e.printStackTrace();
		}
	}
	
	private void saveLevelProgress(int level) throws IOException{
		DataOutputStream dos = new DataOutputStream(openFileOutput(LEVEL_PROGRESS_FILE_NAME, 0));
		dos.writeInt(level);
		dos.close();
	}
	
	private int loadLevelProgress(){
		int level = 1;
		try {
			DataInputStream dis = new DataInputStream(openFileInput(LEVEL_PROGRESS_FILE_NAME));
			level = dis.readInt();
			dis.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return level;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit);
	
//		level = (savedInstanceState != null) ? savedInstanceState.getInt(LEVEL_KEY) : loadLevelProgress();
		
		if(savedInstanceState != null){
			level = savedInstanceState.getInt(LEVEL_KEY);
			System.out.println("Load level from savedInstanceState: " + level);
		}
		else{
			level = loadLevelProgress();
			System.out.println("Load level progress: " + level);
		}
		
		circuitView = (CircuitView) findViewById(R.id.circuitView);
		messageView = (MessageView) findViewById(R.id.messageView);
		
		createController(savedInstanceState);
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt(LEVEL_KEY, level);
		
		nrCircuitController.saveState(outState);
	}

}
