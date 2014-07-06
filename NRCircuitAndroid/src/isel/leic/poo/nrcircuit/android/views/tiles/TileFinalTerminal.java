package isel.leic.poo.nrcircuit.android.views.tiles;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Class whose instance visually represent a terminal.
 * @author rcacheira & nreis
 *
 */
public class TileFinalTerminal extends Tile {
	
	/**
	 * Creates an instance with the given arguments.
	 *  
	 * @param parent The tile's parent control
	 * @param bounds The tile's initial bounds
	 * @param letter The tile's letter
	 */
	public TileFinalTerminal(CircuitView parent, RectF bounds, char letter) {
		super(parent, bounds);
		brush.setColor(Tile.getLetterColor(letter));
	}

	@Override
	public void drawTile(Canvas canvas) {
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth, brush);
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth/2, backgroundBrush);
	}
	
}
