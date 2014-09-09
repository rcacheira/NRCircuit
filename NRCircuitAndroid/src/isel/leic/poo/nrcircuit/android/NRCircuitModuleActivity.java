package isel.leic.poo.nrcircuit.android;

import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.android.views.ModuleView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class NRCircuitModuleActivity extends Activity {
	
	private ModuleView moduleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nrcircuit_module);
		
		firstModule.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent msg = new Intent(NRCircuitModuleActivity.this, isel.leic.poo.nrcircuit.android.NRCircuitLevelActivity.class);
				Bundle extras = new Bundle();
				extras.putInt("Module", value);
				startActivity(msg, extras);
			}
		});
				
	}
	
	
}