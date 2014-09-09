package isel.leic.poo.nrcircuit.android;

import isel.leic.poo.nrcircuit.android.NRCircuitGameController.OnLevelFinishedListener;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.MessageView;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;

public class NRCircuitGameActivity extends Activity {

	private final static String LEVEL_PROGRESS_FILE_NAME = "d001";
	
	/**
	 * The associated controller instance
	 */
	private NRCircuitGameController nrCircuitController;
	
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
	private String level;
	
	/**
	 * The current game module
	 */
	private String module;

	/**
	 * 
	 * @param savedInstanceState
	 */
	private void createController(Bundle savedInstanceState){
		String moduleLevel = module + "/" + level;
		System.out.println("Loading level " + moduleLevel + " ... ");
		
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							this.getAssets().open(moduleLevel)));
			
			nrCircuitController = (savedInstanceState != null) ?
					NRCircuitGameController.createController(circuitView, messageView, reader, savedInstanceState) :
					NRCircuitGameController.createController(circuitView, messageView, reader);
					
			nrCircuitController.setOnLevelFinishedListener(new OnLevelFinishedListener(){
				@Override
				public void levelFinished() {
//					System.out.println("Level " + NRCircuitGameActivity.this.level + " Finished !!");
//					try {
//						saveLevelProgress(level+1);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					System.out.println("Level progress saved !!!");
//					NRCircuitGameActivity.this.level+=1;
//					createController(null);
				}
			});
		} catch (IOException | FileBadFormatException e) {
			e.printStackTrace();
		}
	}
	
//	/**
//	 * Saves the level in which the user is currently.
//	 * 
//	 * @param level	Level to be saved.
//	 * @throws IOException
//	 */
//	private void saveLevelProgress(int level) throws IOException{
//		DataOutputStream dos = new DataOutputStream(openFileOutput(LEVEL_PROGRESS_FILE_NAME, 0));
//		dos.writeInt(level);
//		dos.close();
//	}
//	
//	/**
//	 * Loads the level which the user saved, or stopped.
//	 * If there is no level saved it loads level 1.
//	 * 
//	 * @return	The level saved. If none, loads level 1.
//	 */
//	private int loadLevelProgress(){
//		int level = 1;
//		try {
//			DataInputStream dis = new DataInputStream(openFileInput(LEVEL_PROGRESS_FILE_NAME));
//			level = dis.readInt();
//			dis.close();
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
//		return level;
//	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit_game);
		
//		level = loadLevelProgress();
		
		if(savedInstanceState != null){
			module = savedInstanceState.getString("Module");
			level = savedInstanceState.getString("Level");
		}
		else{
			Bundle extras = this.getIntent().getExtras();
			if ( extras != null && extras.containsKey("Module") 
					&& extras.containsKey("Level") )
				module = extras.getString("Module");
				level = extras.getString("Level");
		}
		
		circuitView = (CircuitView) findViewById(R.id.circuitView);
		messageView = (MessageView) findViewById(R.id.messageView);
		
		createController(savedInstanceState);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("Module", module);
		outState.putString("Level", level);
		nrCircuitController.saveState(outState);
	}

}
