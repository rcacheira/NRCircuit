package isel.leic.poo.nrcircuit.android;

import isel.leic.poo.nrcircuit.android.NRCircuitGameController.OnLevelFinishedListener;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.MessageView;
import isel.leic.poo.nrcircuit.model.Grid.FileBadFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NRCircuitGameActivity extends Activity {
	
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
	private int level;
	
	/**
	 * The current game module
	 */
	private String module;

	/**
	 * 
	 * @param savedInstanceState
	 */
	private void createController(Bundle savedInstanceState){
		String moduleLevel = module + "/" + String.format("%02d", level);
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
							Intent msg = new Intent();
							msg.putExtra(StaticValues.KEY_MODULE, module);
							msg.putExtra(StaticValues.KEY_LEVEL, level);
							setResult(RESULT_OK, msg);
							finish();
						}
					});
				}
					
				@Override
				public void resetLevelFinished() {
					levelFinished.setVisibility(View.GONE);
					levelFinished.setEnabled(false);
				}
			});
		} catch (IOException | FileBadFormatException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			setContentView(R.layout.activity_nrcircuit_game_vertical);
		else
			setContentView(R.layout.activity_nrcircuit_game_horizontal);
		
		if(savedInstanceState != null){
			module = savedInstanceState.getString(StaticValues.KEY_MODULE);
			level = savedInstanceState.getInt(StaticValues.KEY_LEVEL);
		}
		else{
			Bundle extras = this.getIntent().getExtras();
			if ( extras == null || !extras.containsKey(StaticValues.KEY_MODULE) 
					|| !extras.containsKey(StaticValues.KEY_LEVEL) )
				throw new IllegalStateException("Necessary keys not found");
			module = extras.getString(StaticValues.KEY_MODULE);
			level = extras.getInt(StaticValues.KEY_LEVEL);
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
		outState.putString(StaticValues.KEY_MODULE, module);
		outState.putInt(StaticValues.KEY_LEVEL, level);
		nrCircuitController.saveState(outState);
	}

}
