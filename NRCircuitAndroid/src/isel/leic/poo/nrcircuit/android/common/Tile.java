package isel.leic.poo.nrcircuit.android.common;

import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Abstract Class that represents a part of the tile visual representation.
 * 
 * @author rcacheira & nreis
 *
 */
public abstract class Tile {
	
	protected static final char NO_LETTER = 0; 
	
	/**
	 * Maximum number of links per {@code Tile}
	 */
	private static final int MAX_LINKS = 3;
	
	/**
	 * Enumeration {@code Link} that represents ...
	 * @author Nuno
	 *
	 */
	public static enum LinkDirection{
		TOP,
		LEFT,
		RIGHT,
		BOTTOM
	}
	
	/**
	 * Number of colors. Used to get a color for the given tile, if they have any (Terminals).
	 */
	public static int NR_OF_COLORS = 5;
	
	/**
	 * Get a color for the Final Terminals through the {@code letter}.
	 * 
	 * @param letter	The letter of the FinalTerminal
	 * @return	The color
	 */
	public static int getLetterColor(char letter){
		if( letter == NO_LETTER)
			return Color.GRAY;
		int factor = (letter-'A');
		int idxColor = 255/NR_OF_COLORS * factor;
		return Color.rgb(
				(factor & 0x01) == 0 && (factor & 0x02) == 0 ||
				(factor & 0x01) != 0 && (factor & 0x02) != 0 ? 255 : factor < 3 ? 0 : idxColor, 
				(factor & 0x01) != 0 && (factor & 0x02) == 0 ? 255 : factor < 3 ? 0 : idxColor, 
				(factor & 0x01) == 0 && (factor & 0x02) != 0 ? 255 : factor < 3 ? 0 : idxColor);
	}
	
	/**
	 * The tile's parent View.
	 */
	protected final CircuitView parent;
	
	/**
	 * bounds of the Tile
	 */
	protected final RectF bounds;
	
	/**
	 * 
	 */
	protected final RectF backgroundBounds;
	
	/**
	 * Brush to paint the user interface.
	 */
	protected final Paint backgroundBrush;
	/**
	 * Brush to paint the user interface.
	 */
	protected final Paint linkedBackgroundBrush;
	/**
	 * Brush to paint the user interface.
	 */
	protected final Paint brush;
	/**
	 * Brush to paint the user interface.
	 */
	protected Paint linkedBrush;
	
	protected final float strokeWidth;
	
	/**
	 * The links of a Tile.
	 */
	protected LinkDirection[] links;
	
	/**
	 * Number of links in a Tile.
	 */
	protected int nLinks;
	
	/**
	 * The letter of the Tile.
	 */
	private char letter;
	
	/**
	 * Instantiates a Tile with the given parameters.
	 * 
	 * @param parent	The tile's parent View.
	 * @param bounds	Bounds of the Tile
	 */
	public Tile(CircuitView parent, RectF bounds) {
		this.parent = parent;
		this.bounds = bounds;
		
		links = new LinkDirection[MAX_LINKS];
		nLinks = 0;
		letter = NO_LETTER;
		
		backgroundBounds = bounds;
		
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
	
	/**
	 * get's tile letter
	 * 
	 * @return letter char if tile has letter, {@link NO_LETTER} otherwise
	 */
	public char getLetter() {
		return letter;
	}
	
	/**
	 * Verifies if tile has links
	 * 
	 * @return	{@code true} if Tiles has links, {@code false} otherwise.
	 */
	public boolean hasLinks(){
		return nLinks > 0;
	}
	
	/**
	 * Find a free empty position
	 * 
	 * @return
	 */
	private int findEmptyLinkPosition(){
		for(int i=0; i<MAX_LINKS; i++){
			if(links[i] == null)
				return i;
		}
		throw new IllegalStateException("No free link position");
	}
	
	/**
	 * Find link position
	 * 
	 * @param link
	 * @return
	 */
	private int findLinkPosition(LinkDirection link){
		for(int i=0; i<MAX_LINKS; i++){
			if(links[i] == link)
				return i;
		}
		throw new IllegalStateException("No link");
	}
	
	/**
	 * Set a link in a Tile.
	 * 
	 * @param link The new link to set.
	 * @param letter	The letter of the link (color).
	 */
	public void setLink(LinkDirection link, char letter){
		if (nLinks == MAX_LINKS)
			throw new IllegalStateException("Can't set more than " + MAX_LINKS + " links");
		if (!hasLinks())
			this.letter = letter;
		else
			if(this.letter != letter)
				throw new IllegalStateException("Link letter different of previous links letter");
		
		links[findEmptyLinkPosition()] = link;
		nLinks++;
	}
	
	/**
	 * Clears all links from a Tile.
	 * @return	{@code true} if it had links, {@code false} otherwise.
	 */
	public boolean clearLink(LinkDirection link){
		if(!hasLinks()){
			return false;
		}
		links[findLinkPosition(link)] = null;
		nLinks--;
		if(!hasLinks())
			letter = NO_LETTER;
		return true;
	}
	
	/**
	 * Draws the Tile interface.
	 * 
	 * @param canvas canvas to draw in.
	 */
	public final void doDraw(Canvas canvas){
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
	
	/**
	 * Draws the Tile.
	 * @param canvas
	 */
	public abstract void drawTile(Canvas canvas);
	
	/**
	 * Draws the links of the Tile.
	 * 
	 * @param canvas
	 */
	public void drawLinks(Canvas canvas){
		for(int i = 0; i<nLinks; i++){
			drawLink(links[i], canvas);
		}
	}
	
	/**
	 * Draws a specific link of the Tile.
	 * 
	 * @param link
	 * @param canvas
	 */
	private void drawLink(LinkDirection link, Canvas canvas){
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
	
	/**
	 * Sets letter of Tile
	 * 
	 * @param letter
	 */
	protected void setLetter(char letter){
		this.letter = letter;
	}
}
