package isel.leic.poo.nrcircuit.android.views.tiles;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Class whose instance visually represent a terminal.
 * @author rcacheira & nreis
 *
 */
public class TileTunnel extends Tile {
	
	private Paint outBrush;
	
	/**
	 * Creates an instance with the given arguments.
	 *  
	 * @param parent The tile's parent control
	 * @param bounds The tile's initial bounds
	 * @param letter The tile's letter
	 */
	public TileTunnel(CircuitView parent, RectF bounds) {
		super(parent, bounds);
		outBrush = new Paint();
		outBrush.setStyle(Paint.Style.FILL);
		outBrush.setColor(Color.WHITE);
	}

	@Override
	public void drawTile(Canvas canvas) {
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth*1.2F, outBrush);
		brush.setColor(Tile.getLetterColor(getLetter()));
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth, brush);
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth/2, backgroundBrush);
	}
	
	/**
	 * Sets the Tile Tunner letter
	 * 
	 * @param letter
	 */
	public void setTunnelLetter(char letter){
		setLetter(letter);
	}
	
}
