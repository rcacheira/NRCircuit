package isel.leic.poo.nrcircuit.android;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class NRCircuitLevelActivity extends Activity {
	
	private LinearLayout layout;
	
	private LinearLayout getLayout(){
		if(layout == null){
			layout = (LinearLayout)findViewById(R.id.container);
		}
		return layout;
	}
	
	private String module;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit_level);
		module = getIntent().getExtras().getString("Module");
		setTitle(module);
		try {
			createButtons();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createButtons() throws IOException{
		for(String level: getAssets().list(module))
			createLevelButton(level);
	}
	
	private void createLevelButton(final String level){
		Button bt = new Button(this);
		bt.setText(level);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent msg = new Intent(NRCircuitLevelActivity.this, 
						isel.leic.poo.nrcircuit.android.NRCircuitGameActivity.class);
				msg.putExtra("Module", module);
				msg.putExtra("Level", level);
				startActivity(msg);
			}
		});
		getLayout().addView(bt);
	}
}
