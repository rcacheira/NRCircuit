package isel.leic.poo.nrcircuit.android.views;

import isel.leic.poo.nrcircuit.android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ModuleView extends View {

	public ModuleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (changed){
			addView(firstModule);
		}
	}
}
