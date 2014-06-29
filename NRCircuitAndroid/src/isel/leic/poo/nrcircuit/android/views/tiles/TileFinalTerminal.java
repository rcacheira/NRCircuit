package isel.leic.poo.nrcircuit.android.views.tiles;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.RectF;

public class TileFinalTerminal extends Tile {
	
	public TileFinalTerminal(CircuitView parent, RectF bounds, char letter) {
		super(parent, bounds);
		
		brush.setColor(Tile.getLetterColor(letter));
	}

	@Override
	public void doDraw(Canvas canvas) {
		super.doDraw(canvas);
		
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth, brush);
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth/2, backgroundBrush);
	}
	
}
