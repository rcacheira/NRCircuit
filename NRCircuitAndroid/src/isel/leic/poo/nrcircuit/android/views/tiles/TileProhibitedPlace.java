package isel.leic.poo.nrcircuit.android.views.tiles;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

public class TileProhibitedPlace  extends Tile{

	public TileProhibitedPlace(CircuitView parent, RectF bounds) {
		super(parent, bounds);
		linkedBackgroundBrush.setColor(Color.WHITE);
		linkedBackgroundBrush.setAlpha(50);
	}

	@Override
	public void drawTile(Canvas canvas) {
		canvas.drawRect(backgroundBounds, linkedBackgroundBrush);
	}
	
}
