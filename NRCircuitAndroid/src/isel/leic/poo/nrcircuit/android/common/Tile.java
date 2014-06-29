package isel.leic.poo.nrcircuit.android.common;

import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Tile {

	static public int NR_OF_COLORS = 5;
	
	static public int getLetterColor(char letter){
		int factor = (letter-'A');
		int idxColor = 255/NR_OF_COLORS * factor;
		return Color.rgb(
				(factor & 0x01) == 0 && (factor & 0x02) == 0 ||
				(factor & 0x01) != 0 && (factor & 0x02) != 0 ? 255 : factor < 3 ? 0 : idxColor, 
				(factor & 0x01) != 0 && (factor & 0x02) == 0 ? 255 : factor < 3 ? 0 : idxColor, 
				(factor & 0x01) == 0 && (factor & 0x02) != 0 ? 255 : factor < 3 ? 0 : idxColor);
	}
	
	protected final CircuitView parent;
	
	protected final RectF bounds;
	
	protected final Paint boundsBrush;
	protected final Paint backgroundBrush;
	protected Paint brush;
	
	protected final float strokeWidth;
	
	public Tile(CircuitView parent, RectF bounds) {
		this.parent = parent;
		this.bounds = bounds;
		
		boundsBrush = new Paint();
		boundsBrush.setStyle(Paint.Style.STROKE);
		boundsBrush.setColor(Color.GRAY);
		
		backgroundBrush = new Paint();
		backgroundBrush.setStyle(Paint.Style.FILL);
		backgroundBrush.setColor(Color.BLACK);
		
		brush = new Paint();
		
		strokeWidth = Math.min(bounds.width(), bounds.height()) / 5;
	}
	
	public void doDraw(Canvas canvas){
		canvas.drawRect(bounds, boundsBrush);
		canvas.drawRect(bounds, backgroundBrush);
	}
}
