package isel.leic.poo.nrcircuit.android.views.tiles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector.Orientation;

public class TileOneWayConnector extends Tile {

	private Orientation orientation;
	
	public TileOneWayConnector(CircuitView parent, RectF bounds, Orientation orientation) {
		super(parent, bounds);
		this.orientation = orientation;
		
		brush.setColor(Color.GRAY);
		brush.setStyle(Paint.Style.FILL);
		brush.setStrokeWidth(strokeWidth);
	}

	@Override
	public void doDraw(Canvas canvas) {
		super.doDraw(canvas);
		
		float startX = bounds.left;
		float startY = bounds.top;
		
		startX += (orientation == Orientation.HORIZONTAL) ? 
				bounds.width()/5 : bounds.width()/2;
		float stopX = startX + ((orientation == Orientation.HORIZONTAL) ? 
				bounds.width()/5*3 : 0);
		startY += (orientation == Orientation.HORIZONTAL) ? 
				bounds.height()/2 : bounds.height()/5;
		float stopY = startY + ((orientation == Orientation.HORIZONTAL) ? 
				0 : bounds.height()/5*3);
		
		canvas.drawLine(startX, startY, stopX, stopY, brush);
	}
}
