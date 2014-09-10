package isel.leic.poo.nrcircuit.android;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;

public class NRCircuitLevelActivity extends Activity {
	
	private GridLayout layout;
	private List<Integer> levels;
	private int lastLevel;
	private int progress;
	private LinkedList<Button> buttons;
	
	private GridLayout getLayout(){
		if(layout == null){
			layout = (GridLayout)findViewById(R.id.gridLayout);
			if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
				layout.setColumnCount(5);
			else
				layout.setColumnCount(8);
		}
		return layout;
	}
	
	private String module;
	
	private void loadProgress() throws IOException{
		BufferedReader level_progress_reader;
		try {
			level_progress_reader = new BufferedReader(
					new InputStreamReader(openFileInput(StaticValues.getModuleProgressFile(module))));
			String line = level_progress_reader.readLine();
			if(line != null){
				progress = Integer.valueOf(line);
			}
		} catch( FileNotFoundException e){
			System.out.println("Progress file for module: " + module + " not found");
		}
	}
	
	private void loadLevels() throws IOException{
		levels = new LinkedList<Integer>();
		int i = 0;
		for(String file: getAssets().list(module)){
			try{
				Integer level = Integer.valueOf(file);
				if(level > lastLevel)
					lastLevel = level;
				levels.add(level);
				if(++i >= 100){
					System.out.println("One module can't have more than 100 levels");
					break;
				}
			}
			catch (NumberFormatException e) {
				//Not a level file
				continue;
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nrcircuit_grid_list);
		
		levels = null;
		lastLevel = 0;
		
		if(getIntent().getExtras() == null || !getIntent().getExtras().containsKey(StaticValues.KEY_MODULE))
			throw new IllegalStateException("No module key found");
		module = getIntent().getExtras().getString(StaticValues.KEY_MODULE);
		setTitle(module);
		
		try {
			loadProgress();
			loadLevels();
			createButtons();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == StaticValues.LEVEL_REQUEST) {
	        if(resultCode == RESULT_OK){
	        	int level = data.getExtras().getInt(StaticValues.KEY_LEVEL);
	        	saveProgressLevel(level);
	        	if(level < lastLevel){
	    			launchLevel(level + 1);
	    			enableButtons();
	    		}
	    		else{
	    			Intent msg = new Intent();
					msg.putExtra(StaticValues.KEY_MODULE, module);
					setResult(RESULT_OK, msg);
					finish();
	    		}
	        }
	    }
	}
	
	private void saveProgressLevel(int level){
		if(level > progress){
			try {
				PrintStream out = new PrintStream(openFileOutput(StaticValues.getModuleProgressFile(module), 0));
				out.print(level);
				out.close();
				System.out.println("writed progress for " + module + ":" + level);
				progress = level;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void createButtons() throws IOException{
		buttons = new LinkedList<Button>();
		boolean nextLevelIsEnabled = true;
		for(Integer level: levels){
			createLevelButton(level, nextLevelIsEnabled);
			nextLevelIsEnabled = level <= progress;
		}
	}
	
	private void launchLevel(int level){
		Intent msg = new Intent(NRCircuitLevelActivity.this, 
				isel.leic.poo.nrcircuit.android.NRCircuitGameActivity.class);
		msg.putExtra(StaticValues.KEY_MODULE, module);
		msg.putExtra(StaticValues.KEY_LEVEL, level);
		startActivityForResult(msg, StaticValues.LEVEL_REQUEST);
	}
	
	private void createLevelButton(final int level, boolean enabled){
		Button bt = new Button(this);
		bt.setText(String.valueOf(level));
		bt.setEnabled(enabled);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchLevel(level);
			}
		});
		getLayout().addView(bt);
		buttons.add(bt);
	}
	
	private void enableButtons(){
		boolean nextButtonEnabled = true;
		for (Button bt : buttons) {
			bt.setEnabled(nextButtonEnabled);
			nextButtonEnabled = Integer.valueOf(bt.getText().toString()) <= progress;
		}
	}
}
