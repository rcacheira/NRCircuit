package isel.leic.poo.nrcircuit.android.views.tiles;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

/**
 * Class whose instance visually represent a connector.
 * @author rcacheira & nreis
 *
 */
public class TileConnector extends Tile {
	
	/**
	 * Creates an instance with the given arguments.
	 *  
	 * @param parent The tile's parent control
	 * @param bounds The tile's initial bounds
	 */
	public TileConnector(CircuitView parent, RectF bounds) {
		super(parent, bounds);

		brush.setColor(Color.GRAY);
	}

	@Override
	public void drawTile(Canvas canvas) {
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth/2, brush);
	}

}
