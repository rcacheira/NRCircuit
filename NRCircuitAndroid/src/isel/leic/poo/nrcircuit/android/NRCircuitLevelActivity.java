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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class NRCircuitLevelActivity extends Activity {
	
	private LinearLayout layout;
	private List<Integer> levels;
	private int lastLevel;
	private int progress;
	
	private LinearLayout getLayout(){
		if(layout == null){
			layout = (LinearLayout)findViewById(R.id.container);
		}
		return layout;
	}
	
	private String module;
	
	private void loadProgress(){
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
		} catch (IOException e) {
			System.out.println("Error loading progress file for module: " + module);
		}
	}
	
	private List<Integer> getLevels(){
		if(levels == null){
			levels = new LinkedList<Integer>();
			try {
				for(String file: getAssets().list(module)){
					try{
						Integer level = Integer.valueOf(file);
						if(level > lastLevel)
							lastLevel = level;
						levels.add(level);
					}
					catch (NumberFormatException e) {
						//Not a level file
						continue;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return levels;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit_level);
		levels = null;
		lastLevel = 0;
		
		if(getIntent().getExtras() == null || !getIntent().getExtras().containsKey(StaticValues.KEY_MODULE))
			throw new IllegalStateException("No module key found");
		module = getIntent().getExtras().getString(StaticValues.KEY_MODULE);
		setTitle(module);
		
		loadProgress();
		getLevels();
		
		if(getIntent().getExtras().containsKey(StaticValues.KEY_LEVEL_FINISHED)
				&& getIntent().getExtras().getBoolean(StaticValues.KEY_LEVEL_FINISHED))
			createLevelFinishedView();
		else{
			createLevelView();
		}
	}
	
	public void createLevelView(){
		try {
			createButtons();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createLevelFinishedView(){
		if(getIntent().getExtras() == null || !getIntent().getExtras().containsKey(StaticValues.KEY_LEVEL))
			throw new IllegalStateException("No level key found");
		
		int level = getIntent().getExtras().getInt(StaticValues.KEY_LEVEL);
		saveProgressLevel(level);
		if(level < lastLevel){
			launchLevel(level + 1);
		}
		else{
			System.out.println("all module levels completed");
			Intent msg = new Intent(NRCircuitLevelActivity.this, 
					isel.leic.poo.nrcircuit.android.NRCircuitModuleActivity.class);
			msg.putExtra(StaticValues.KEY_MODULE, module);
			msg.putExtra(StaticValues.KEY_MODULE_FINISHED, true);
			startActivity(msg);
		}
		finish();
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
		boolean nextLevelIsEnabled = true;
		for(Integer level: getLevels()){
			createLevelButton(level, nextLevelIsEnabled);
			nextLevelIsEnabled = level <= progress;
		}
	}
	
	private void launchLevel(int level){
		Intent msg = new Intent(NRCircuitLevelActivity.this, 
				isel.leic.poo.nrcircuit.android.NRCircuitGameActivity.class);
		msg.putExtra(StaticValues.KEY_MODULE, module);
		msg.putExtra(StaticValues.KEY_LEVEL, level);
		startActivity(msg);
	}
	
	private void createLevelButton(final int level, boolean enabled){
		Button bt = new Button(this);
		bt.setText("Level " + level);
		bt.setEnabled(enabled);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchLevel(level);
			}
		});
		getLayout().addView(bt);
	}
}
