package isel.leic.poo.nrcircuit.android.views.tiles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;

public class TileConnector extends Tile {
	
	public TileConnector(CircuitView parent, RectF bounds) {
		super(parent, bounds);

		brush.setColor(Color.GRAY);
		brush.setStyle(Paint.Style.FILL);
	}

	@Override
	public void doDraw(Canvas canvas) {
		super.doDraw(canvas);
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth/2, brush);
	}

}
