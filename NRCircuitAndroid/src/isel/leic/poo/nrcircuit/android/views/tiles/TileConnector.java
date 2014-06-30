package isel.leic.poo.nrcircuit.android.views.tiles;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

public class TileConnector extends Tile {
	
	public TileConnector(CircuitView parent, RectF bounds) {
		super(parent, bounds);

		brush.setColor(Color.GRAY);
	}

	@Override
	public void drawTile(Canvas canvas) {
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth/2, brush);
	}

}
