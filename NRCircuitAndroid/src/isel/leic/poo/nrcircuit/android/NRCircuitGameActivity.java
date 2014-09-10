package isel.leic.poo.nrcircuit.android;

import isel.leic.poo.nrcircuit.android.NRCircuitGameController.OnLevelFinishedListener;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.MessageView;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
	
	private Button levelFinished;
	
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
					levelFinished.setVisibility(View.VISIBLE);
					levelFinished.setEnabled(true);
					levelFinished.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							int levell = (level.split("level")[1].toCharArray()[0]-'0')+1;
							//TODO: Error in final level of module
							level="level"+levell;
							createController(null);
							resetLevelFinished();							
						}
					});
				}
					
					@Override
					public void resetLevelFinished() {
						levelFinished.setVisibility(View.GONE);
						levelFinished.setEnabled(false);
					}
					
//					System.out.println("Level " + NRCircuitGameActivity.this.level + " Finished !!");
//					try {
//						saveLevelProgress(level+1);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					System.out.println("Level progress saved !!!");
//					NRCircuitGameActivity.this.level+=1;
//					createController(null);
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
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			setContentView(R.layout.activity_nrcircuit_game_vertical);
		else
			setContentView(R.layout.activity_nrcircuit_game_horizontal);
		
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
		levelFinished = (Button) findViewById(R.id.button1);
		
		createController(savedInstanceState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mif = getMenuInflater();
		mif.inflate(R.menu.nrcircuit_game, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.exit)
			finish();
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("Module", module);
		outState.putString("Level", level);
		nrCircuitController.saveState(outState);
	}

}
