package isel.leic.poo.nrcircuit.android.views.tiles;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

/**
 * Class whose instance visually represent a prohibited place.
 * @author rcacheira & nreis
 *
 */
public class TileProhibitedPlace  extends Tile{

	/**
	 * Creates an instance with the given arguments.
	 *  
	 * @param parent The tile's parent control
	 * @param bounds The tile's initial bounds
	 */
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
