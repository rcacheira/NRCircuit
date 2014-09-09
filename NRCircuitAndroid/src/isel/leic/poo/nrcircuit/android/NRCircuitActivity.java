package isel.leic.poo.nrcircuit.android;

import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.MessageView;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;
import isel.leic.poo.nrcircuit.android.NRCircuitController;
import isel.leic.poo.nrcircuit.android.NRCircuitController.OnLevelFinishedListener;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;

public class NRCircuitActivity extends Activity {

	private final static String LEVEL_PROGRESS_FILE_NAME = "d001";
	
	/**
	 * The associated controller instance
	 */
	private NRCircuitController nrCircuitController;
	
	/**
	 * The application view instance
	 */
	private CircuitView circuitView;
	
	/**
	 * The application view instance
	 */
	private MessageView messageView;
	
	/**
	 * The current game level
	 */
	int level;

	/**
	 * 
	 * @param savedInstanceState
	 */
	private void createController(Bundle savedInstanceState){
		
		int fileId = getResources().getIdentifier("raw/level"+level, null, this.getPackageName());
		
		if(fileId == 0){
			System.out.println("You have finished all levels!");
			level = 1;
			createController(savedInstanceState);
			return;
		}
		
		System.out.println("Loading level " + level + " ... ");
		
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
	
	/**
	 * Saves the level in which the user is currently.
	 * 
	 * @param level	Level to be saved.
	 * @throws IOException
	 */
	private void saveLevelProgress(int level) throws IOException{
		DataOutputStream dos = new DataOutputStream(openFileOutput(LEVEL_PROGRESS_FILE_NAME, 0));
		dos.writeInt(level);
		dos.close();
	}
	
	/**
	 * Loads the level which the user saved, or stopped.
	 * If there is no level saved it loads level 1.
	 * 
	 * @return	The level saved. If none, loads level 1.
	 */
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
		setContentView(R.layout.activity_nrcircuit_game);
		
		level = loadLevelProgress();
		
		level = 1;
		
//		TODO: Intent message.
//		Bundle extras = this.getIntent().getExtras();
//		if ( extras != null ) {
//		  if ( extras.containsKey("Level") ) {
//			  level = extras.getInt("Level");
//		  }
//		}
		
		System.out.println("Load level progress: " + level);
		
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
