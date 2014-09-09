package isel.leic.poo.nrcircuit.android.views.tiles;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;

/**
 * Class whose instance visually represent a connector.
 * @author rcacheira & nreis
 *
 */
public class TileNumberedConnector extends Tile {
	
	private final int orderNumber;
	private final Paint textBrush;
	
	private static final int TEXT_SIZE = 36;
	
	/**
	 * Creates an instance with the given arguments.
	 *  
	 * @param parent The tile's parent control
	 * @param bounds The tile's initial bounds
	 */
	public TileNumberedConnector(CircuitView parent, RectF bounds, int orderNumber) {
		super(parent, bounds);
		this.orderNumber = orderNumber;
		brush.setColor(Color.GRAY);

		textBrush = new Paint();
		textBrush.setStyle(Paint.Style.FILL_AND_STROKE);
		textBrush.setColor(Color.WHITE);
		textBrush.setTextSize(TEXT_SIZE);
		textBrush.setTextAlign(Align.CENTER);
		textBrush.setFakeBoldText(true);
	}

	@Override
	public void drawTile(Canvas canvas) {
		canvas.drawCircle(bounds.centerX(), bounds.centerY(), strokeWidth/2, brush);
		canvas.drawText(String.valueOf(orderNumber), bounds.centerX(), bounds.centerY()+13, textBrush);
	}

}
