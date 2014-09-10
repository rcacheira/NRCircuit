package isel.leic.poo.nrcircuit.android;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NRCircuitModuleActivity extends Activity {
	private LinearLayout layout;
	private LinkedList<String> progress;
	private LinkedList<Button> buttons;
	
	private LinearLayout getLayout(){
		if(layout == null){
			layout = (LinearLayout)findViewById(R.id.linearLayout);
		}
		return layout;
	}
	
	private void loadProgress() throws IOException{
		progress = new LinkedList<String>();
		BufferedReader progress_reader;
		try {
			progress_reader = new BufferedReader(
					new InputStreamReader(openFileInput(StaticValues.MODULES_PROGRESS_FILE)));
			String line;
			while((line = progress_reader.readLine()) != null){
				progress.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("no progress file for modules");
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nrcircuit_linear_list);
		
		try {
			loadProgress();
			createButtons();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == StaticValues.LEVEL_REQUEST) {
	        if(resultCode == RESULT_OK){
	    		saveProgressModule(data.getExtras().getString(StaticValues.KEY_MODULE));
	    		enableButtons();
	        }
		}
	}
	
	private void saveProgressModule(String module){
		try {
			if(!progress.contains(module)){
				PrintStream out = new PrintStream(openFileOutput(StaticValues.MODULES_PROGRESS_FILE, MODE_APPEND));
				out.println(module);
				out.close();
				System.out.println("writed progress for " + module);
				progress.add(module);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createButtons() throws IOException{
		
		buttons = new LinkedList<Button>();
		
		BufferedReader modules_reader = new BufferedReader(
				new InputStreamReader(getAssets().open(StaticValues.MODULES_FILE)));
		
		addTextView("Modules");
		
		String line = null;
		boolean moduleEnabled = true;
		while((line = modules_reader.readLine()) != null){
			createModuleButton(line, moduleEnabled);
			moduleEnabled = progress.contains(line);
		}
		
		addTextView("Options");
		
		createResetProgressButton();
	}
	
	private void addTextView(String text){
		TextView tv = new TextView(this);
		tv.setText(text);
		tv.setTextColor(Color.WHITE);
		getLayout().addView(tv);
	}
	
	private void createResetProgressButton(){
		Button bt = new Button(this);
		bt.setText("Reset Level Progress");
		bt.setTextColor(Color.WHITE);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetGameProgress();
			}
		});
		getLayout().addView(bt);
	}
	
	private void resetGameProgress(){
		BufferedReader modules_reader;
		try {
			openFileOutput(StaticValues.MODULES_PROGRESS_FILE, 0).close();
			modules_reader = new BufferedReader(
					new InputStreamReader(getAssets().open(StaticValues.MODULES_FILE)));
			String module;
			while((module = modules_reader.readLine()) != null){
				openFileOutput(StaticValues.getModuleProgressFile(module), 0).close();
			}
			loadProgress();
			enableButtons();
		} catch (IOException e) {
			System.out.println("Error rewriting modules file");
			e.printStackTrace();
			return;
		}
	}
	
	private void createModuleButton(final String module, boolean enabled){
		Button bt = new Button(this);
		bt.setText(module);
		bt.setTextColor(Color.WHITE);
		bt.setEnabled(enabled);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchModule(module);
			}
		});
		getLayout().addView(bt);
		buttons.add(bt);
	}
	
	private void enableButtons(){
		boolean nextButtonEnabled = true;
		for (Button bt : buttons) {
			bt.setEnabled(nextButtonEnabled);
			nextButtonEnabled = progress.contains(bt.getText());
		}
	}
	
	private void launchModule(String module){
		Intent msg = new Intent(NRCircuitModuleActivity.this, 
				isel.leic.poo.nrcircuit.android.NRCircuitLevelActivity.class);
		msg.putExtra(StaticValues.KEY_MODULE, module);
		startActivityForResult(msg, StaticValues.MODULE_REQUEST);
	}
	
}