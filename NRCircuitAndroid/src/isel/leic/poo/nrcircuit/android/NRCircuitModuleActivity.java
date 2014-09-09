package isel.leic.poo.nrcircuit.android;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class NRCircuitModuleActivity extends Activity {

	private LinearLayout layout;
	
	private LinearLayout getLayout(){
		if(layout == null){
			layout = (LinearLayout)findViewById(R.id.container);
		}
		return layout;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit_module);
		try {
			createButtons();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createButtons() throws IOException{
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(getAssets().open("modules")));
		
		String line = null;
		while((line = reader.readLine()) != null)
			createModuleButton(line);
	}
	
	private void createModuleButton(final String module){
		Button bt = new Button(this);
		bt.setText(module);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent msg = new Intent(NRCircuitModuleActivity.this, isel.leic.poo.nrcircuit.android.NRCircuitLevelActivity.class);
				msg.putExtra("Module", module);
				startActivity(msg);
			}
		});
		getLayout().addView(bt);
	}
	
}