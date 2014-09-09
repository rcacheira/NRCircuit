package isel.leic.poo.nrcircuit.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class NRCircuitLevelActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit_level);
		
		int nfiles = R.raw.class.getFields().length;
		
		Button [] buttons = new Button[R.raw.class.getFields().length];
		
		for (int i = 0; i < nfiles; i++) {
			buttons[i] = new Button(this);
			buttons[i].setText("Level: "+(i+1));
		}

	}
}
