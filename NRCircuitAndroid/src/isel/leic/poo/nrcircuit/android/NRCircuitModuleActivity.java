package isel.leic.poo.nrcircuit.android;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

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
	private List<String> progress;
	
	private LinearLayout getLayout(){
		if(layout == null){
			layout = (LinearLayout)findViewById(R.id.container);
		}
		return layout;
	}
	
	private List<String> getProgress() throws IOException{
		if(progress == null){
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
		return progress;
	}
	
	private void saveProgressModule(String module){
		try {
			if(!getProgress().contains(module)){
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit_module);
		
		if(getIntent().getExtras() != null 
				&& getIntent().getExtras().containsKey(StaticValues.KEY_MODULE_FINISHED)
				&& getIntent().getExtras().getBoolean(StaticValues.KEY_MODULE_FINISHED))
			createModuleFinishedView();
		else{
			createNormalModuleView();
		}
	}
	
	private void createNormalModuleView(){
		try {
			createButtons();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createModuleFinishedView(){
		String module = getIntent().getExtras().getString(StaticValues.KEY_MODULE);
		saveProgressModule(module);
		finish();
	}
	
	private void createButtons() throws IOException{
		
		BufferedReader modules_reader = new BufferedReader(
				new InputStreamReader(getAssets().open(StaticValues.MODULES_FILE)));
		
		addTextView("Modules");
		
		String line = null;
		boolean moduleEnabled = true;
		while((line = modules_reader.readLine()) != null){
			createModuleButton(line, moduleEnabled);
			moduleEnabled = getProgress().contains(line);
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
				BufferedReader modules_reader;
				try {
					modules_reader = new BufferedReader(
							new InputStreamReader(getAssets().open(StaticValues.MODULES_FILE)));
					String module;
					while((module = modules_reader.readLine()) != null){
						try {
							openFileOutput(StaticValues.MODULES_PROGRESS_FILE, 0).close();
							openFileOutput(StaticValues.getModuleProgressFile(module), 0).close();
						} catch (IOException e) {
							System.out.println("Error rewriting file for module: " + module );
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					System.out.println("Error with modules file");
					e.printStackTrace();
					return;
				}
				
			}
		});
		getLayout().addView(bt);
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
	}
	
	private void launchModule(String module){
		Intent msg = new Intent(NRCircuitModuleActivity.this, isel.leic.poo.nrcircuit.android.NRCircuitLevelActivity.class);
		msg.putExtra(StaticValues.KEY_MODULE, module);
		startActivity(msg);
	}
	
}