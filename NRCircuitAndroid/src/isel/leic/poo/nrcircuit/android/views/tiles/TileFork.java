package isel.leic.poo.nrcircuit.android.views.tiles;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import isel.leic.poo.nrcircuit.model.terminals.Fork.Orientation;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

/**
 * Class whose instance visually represent a one way connector.
 * @author rcacheira & nreis
 *
 */
public class TileFork extends Tile {
	
	/**
	 * Tile's orientation value.
	 */
	private Orientation orientation;
	
	/**
	 * Creates an instance with the given arguments.
	 *  
	 * @param parent The tile's parent control
	 * @param bounds The tile's initial bounds
	 * @param orientation The tile's orientation value
	 */
	public TileFork(CircuitView parent, RectF bounds, Orientation orientation) {
		super(parent, bounds);
		this.orientation = orientation;
		
		brush.setColor(Color.GRAY);
		brush.setStrokeWidth(strokeWidth);
	}

	@Override
	public void drawTile(Canvas canvas) {
		drawMainRect(canvas);
		if(isHorizontal())
			drawSecHoriRect(canvas);
		else
			drawSecVertRect(canvas);
	}

	public boolean isHorizontal(){
		return orientation == Orientation.HORIZONTAL_DOWN || orientation == Orientation.HORIZONTAL_UP;
	}
	
	public void drawMainRect(Canvas canvas){
		
		float startX = bounds.left;
		float startY = bounds.top;
		
		startX += isHorizontal() ? bounds.width()/5 : bounds.width()/2;
		float stopX = startX + (isHorizontal() ? bounds.width()/5*3 : 0);
		startY += isHorizontal() ? bounds.height()/2 : bounds.height()/5;
		float stopY = startY + (isHorizontal() ? 0 : bounds.height()/5*3);
		
		canvas.drawLine(startX, startY, stopX, stopY, brush);
	}
	
	public void drawSecHoriRect(Canvas canvas){
		float X = bounds.left + bounds.width()/2;
		float startY = bounds.top + bounds.height()/2;
		float stopY = bounds.top + (orientation == Orientation.HORIZONTAL_UP ? bounds.width()/5: bounds.width()/5*4);
		
		canvas.drawLine(X, startY, X, stopY, brush);
	}
	
	public void drawSecVertRect(Canvas canvas){
		float Y = bounds.top + bounds.height()/2;
		float startX = bounds.left + bounds.width()/2;
		float stopX = bounds.left + (orientation == Orientation.VERTICAL_LEFT ? bounds.height()/5: bounds.height()/5*4);
		
		canvas.drawLine(startX, Y, stopX, Y, brush);
	}
}
