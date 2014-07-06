package isel.leic.poo.nrcircuit.android.common;

import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Tile {

	private static final int MAX_LINKS = 3;
	
	public static enum Link{
		TOP,
		LEFT,
		RIGHT,
		BOTTOM
	}
	
	public static int NR_OF_COLORS = 5;
	
	public static int getLetterColor(char letter){
		int factor = (letter-'A');
		int idxColor = 255/NR_OF_COLORS * factor;
		return Color.rgb(
				(factor & 0x01) == 0 && (factor & 0x02) == 0 ||
				(factor & 0x01) != 0 && (factor & 0x02) != 0 ? 255 : factor < 3 ? 0 : idxColor, 
				(factor & 0x01) != 0 && (factor & 0x02) == 0 ? 255 : factor < 3 ? 0 : idxColor, 
				(factor & 0x01) == 0 && (factor & 0x02) != 0 ? 255 : factor < 3 ? 0 : idxColor);
	}
	
	protected final CircuitView parent;
	
	protected final RectF bounds;
	protected final RectF backgroundBounds;
	
	protected final Paint boundsBrush;
	protected final Paint backgroundBrush;
	protected final Paint linkedBackgroundBrush;
	protected final Paint brush;
	protected Paint linkedBrush;
	
	protected final float strokeWidth;

	protected Link[] links;
	protected int nLinks;
	private char letter;
	
	public Tile(CircuitView parent, RectF bounds) {
		this.parent = parent;
		this.bounds = bounds;
		
		links = new Link[MAX_LINKS];
		nLinks = 0;
		
		backgroundBounds = bounds;
		backgroundBounds.inset(1, 1);
		
		boundsBrush = new Paint();
		boundsBrush.setStyle(Paint.Style.STROKE);
		boundsBrush.setColor(Color.DKGRAY);
		boundsBrush.setStrokeWidth(1);
		
		backgroundBrush = new Paint();
		backgroundBrush.setStyle(Paint.Style.FILL);
		backgroundBrush.setColor(Color.BLACK);
		
		linkedBackgroundBrush = new Paint();
		linkedBackgroundBrush.setStyle(Paint.Style.FILL);
		
		brush = new Paint();
		brush.setStyle(Paint.Style.FILL);
		
		linkedBrush = new Paint();
		linkedBrush.setStyle(Paint.Style.FILL);
		
		strokeWidth = Math.min(bounds.width(), bounds.height()) / 5;
	}
	
	public boolean hasLinks(){
		return nLinks > 0;
	}
	
	public void setLink(Link link, char letter){
		if (nLinks == MAX_LINKS)
			throw new IllegalStateException("Can't set more than " + MAX_LINKS + " links");
		if (!hasLinks())
			this.letter = letter;
		else
			if(this.letter != letter)
				throw new IllegalStateException("Link letter different of previous links letter");
		
		links[nLinks++] = link;
	}
	
	public boolean clearLinks(){
		if(!hasLinks()){
			return false;
		}
		nLinks = 0;
		return true;
	}
	
	public final void doDraw(Canvas canvas){
		canvas.drawRect(bounds, boundsBrush);
		canvas.drawRect(backgroundBounds, backgroundBrush);
		if(nLinks > 0){
			linkedBrush.setColor(Tile.getLetterColor(letter));
			linkedBrush.setStrokeWidth(strokeWidth);
			linkedBackgroundBrush.setColor(Tile.getLetterColor(letter));
			linkedBackgroundBrush.setAlpha(75);
			canvas.drawRect(backgroundBounds, linkedBackgroundBrush);
		}
		drawTile(canvas);
		drawLinks(canvas);
	}
	
	public abstract void drawTile(Canvas canvas);
	
	public void drawLinks(Canvas canvas){
		for(int i = 0; i<nLinks; i++){
			drawLink(links[i], canvas);
		}
	}
	
	private void drawLink(Link link, Canvas canvas){
		linkedBrush.setColor(Tile.getLetterColor(letter));
		
		float startX = 0, stopX = 0;
		float startY = 0, stopY = 0;
		switch(link){
			case TOP:
				startX = stopX = bounds.left + bounds.width()/2;
				startY = bounds.top;
				stopY = bounds.top + bounds.height()/2 + strokeWidth/2;
				break;
			case LEFT:
				startY = stopY = bounds.top + bounds.height()/2;
				startX = bounds.left;
				stopX = bounds.left + bounds.width()/2 + strokeWidth/2;
				break;
			case RIGHT:
				startY = stopY = bounds.top + bounds.height()/2;
				startX = bounds.left + bounds.width()/2 - strokeWidth/2;
				stopX = bounds.right;
				break;
			case BOTTOM:
				startX = stopX = bounds.left + bounds.width()/2;
				startY = bounds.top + bounds.height()/2 - strokeWidth/2;
				stopY = bounds.bottom;
				break;
		}
		
		canvas.drawLine(startX, startY, stopX, stopY, linkedBrush);
	}
}
