package isel.leic.poo.nrcircuit.android.views;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.common.Tile.LinkDirection;
import isel.leic.poo.nrcircuit.android.common.TileActionEvent;
import isel.leic.poo.nrcircuit.android.common.TileActionEvent.TileEvent;
import isel.leic.poo.nrcircuit.android.common.TileFactory;
import isel.leic.poo.nrcircuit.android.views.tiles.TileTunnel;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CircuitView extends View {
	
	/**
	 * Contract to be supported by listeners of tile actions (e.g. touch, drag)
	 */
	public static interface OnTileActionListener {
		
		public void onTileAction(TileActionEvent evt);
		
		public void loadAllLinks();
		
		public void setGridSize();
	}
	
	/**
	 * Holds the reference to the registered tile action listener.
	 */
	private OnTileActionListener tileActionListener;
	
	/**
	 * Number of Tiles in one line.
	 */
	private int columns;
	
	/**
	 * Number of Tiles in one column.
	 */
	private int rows;
	
	/**
	 * Holds the view's Tiles.
	 */
	private Tile[][] tiles;
	
	private TileFactory tileFactory;
	
	/**
	 * Brush to paint the background of the interface.
	 */
	private Paint backgroundBrush;
	
	/**
	 * The Height of every Tile.
	 */
	private int tileHeight;
	
	/**
	 * The Width of every Tile.
	 */
	private int tileWidth;
	
	/**
	 * 
	 */
	private int squareSize;
	
	public CircuitView(Context context) {
		this(context, null, 0);
	}

	public CircuitView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public CircuitView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		initBrushes(context, attrs);
		
		setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getPointerId(0) != 0 || event.getActionIndex() != 0
						|| !isCoordsWithinBounds(event.getX(0), event.getY(0))){
					return false;
				}
				int row = getRow(event.getY(0));
				int column = getColumn(event.getX(0));
				switch(event.getAction()){
					case MotionEvent.ACTION_DOWN:
						fireOnTileTouchEvent(new TileActionEvent(TileEvent.TILE_TOUCH, row, column));
						return true;
					case MotionEvent.ACTION_MOVE:
						fireOnTileTouchEvent(new TileActionEvent(TileEvent.TILE_LINK, row, column));
						return true;
				}
				return false;
			}
		});
		
		rows = 5;
		columns = 5;
		tileFactory = null;
	}
	
	/**
	 * Gets the row on where the tile is.
	 * 
	 * @param y
	 * @return	the row of the tile.
	 */
	private int getRow(float y){
		return (int)(y/tileHeight);
	}
	
	/**
	 * Gets the column on where the tile is.
	 * @param x
	 * @return	the column of the tile.
	 */
	private int getColumn(float x){
		return (int)(x/tileWidth);
	}
	
	/**
	 * Sets the size of the grid.
	 * 
	 * @param rows	Number of rows of the grid.
	 * @param columns	Number of columns of the grid.
	 */
	public void setGridSize(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}
	
	/**
	 * Checks if the touch is within bounds.
	 * @param x	coordinate x
	 * @param y	coordinate y
	 * @return	{@code true} if the coordinates are within their bounds, {@code false} otherwise.
	 */
	private boolean isCoordsWithinBounds(float x, float y){
		return x>=0 && x<=columns*tileWidth && y>=0 && y<=rows*tileHeight;
	}
	
	/**
	 * Helper method that initializes all brushes used to paint the circuit view.
	 * 
	 * @param context	The context in use
	 */
	private void initBrushes(Context context, AttributeSet attrs) 
	{
		backgroundBrush = new Paint();
		backgroundBrush.setColor(Color.BLACK);
		backgroundBrush.setStyle(Paint.Style.FILL);
		backgroundBrush.setAlpha(100);

		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}
	
	/**
	 * 
	 * @param row
	 * @param column
	 * @return The Tile's rect touched.
	 */
	private Rect getTileRect(int row, int column){
		float left = column * tileWidth;
		float top = row * tileHeight;
		return new Rect((int)left, (int)top, (int)left+tileWidth, (int)top+tileHeight);
	}
	
	/**
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	private RectF getTileBounds(int row, int column){
		float left = column * tileWidth;
		float top = row * tileHeight;
		return new RectF((int)left, (int)top, (int)left+tileWidth, (int)top+tileHeight);
	}
	
	/**
	 * Calculates a link direction
	 * 
	 * @param row initial row
	 * @param column initial column
	 * @param lastRow final row
	 * @param lastColumn final column
	 * @return
	 */
	private LinkDirection calcLinkDirection(int row, int column, int lastRow, int lastColumn){
		int cDelta = lastColumn - column;
		int rDelta = lastRow - row;
		
		if(cDelta == 0 && rDelta == 0)
			throw new IllegalStateException("No link direction");
		
		return cDelta != 0 ? cDelta < 1 ? LinkDirection.LEFT : LinkDirection.RIGHT : rDelta < 1 ? LinkDirection.TOP : LinkDirection.BOTTOM;
	}
	
	/**
	 * 
	 * @param row
	 * @param column
	 * @param lastRow
	 * @param lastColumn
	 * @param letter
	 */
	public void setSingleLink(int row, int column, int lastRow, int lastColumn, char letter){
		tiles[row][column].setLink(calcLinkDirection(row, column, lastRow, lastColumn), letter);
		invalidate(getTileRect(row, column));
	}
	
	/**
	 * Sets a link between coordinate's tiles
	 * 
	 * @param startRow
	 * @param startColumn
	 * @param stopRow
	 * @param stopColumn
	 * @param letter
	 */
	public void setLink(int startRow, int startColumn, int stopRow, int stopColumn, char letter){
		setSingleLink(startRow, startColumn, stopRow, stopColumn, letter);
		setSingleLink(stopRow, stopColumn, startRow, startColumn, letter);
	}
	
	/**
	 * Clear link between coordinate's tiles
	 * 
	 * @param startRow
	 * @param startColumn
	 * @param stopRow
	 * @param stopColumn
	 */
	public void clearLink(int startRow, int startColumn, int stopRow, int stopColumn){
		if(tiles[startRow][startColumn].clearLink(calcLinkDirection(startRow, startColumn, stopRow, stopColumn))){
			invalidate(getTileRect(startRow, startColumn));
		}
		if(tiles[stopRow][stopColumn].clearLink(calcLinkDirection(stopRow, stopColumn, startRow, startColumn))){
			invalidate(getTileRect(stopRow, stopColumn));
		}
	}
	
	/**
	 * Sets the maximum number of Tiles.
	 * 
	 * @param horizontalTileCount
	 * @param verticalTileCount
	 */
	public void setTileCount(int horizontalTileCount, int verticalTileCount){
		this.columns = horizontalTileCount;
		this.rows = verticalTileCount;
	}
	
	/**
	 * Helper method that initializes the view's tiles
	 */
	private void initTiles() 
	{
		if(tileFactory == null || tileActionListener == null)
			return;
		
		tileActionListener.setGridSize();
		
		tileHeight = squareSize / rows;
		tileWidth = squareSize / columns;
		
		tiles = new Tile[rows][columns];
		
		for(int row = 0; row < rows; ++row)
		{
			for(int column = 0; column < columns; ++column)
			{
				tiles[row][column] = tileFactory.createTile(row, column, this, 
						getTileBounds(row, column));
			}
		}
		
		fireSetLinksEvent();
	}

	/**
	 * Registers the given listener has a receiver of tile action events.
	 * 
	 * @param listener the listener to be registered, or {@code null}, to disable notifications.
	 */
	public void setTileActionListener(OnTileActionListener tileActionListener){
		this.tileActionListener = tileActionListener;
	}
	
	/**
	 * Dispatches the given event to the registered listener, if there is one.
	 *  
	 * @param evt the event instance to be dispatched
	 */
	private void fireOnTileTouchEvent(TileActionEvent evt)
	{
		if(tileActionListener != null)
			tileActionListener.onTileAction(evt);
	}
	
	private void fireSetLinksEvent()
	{
		if(tileActionListener != null)
			tileActionListener.loadAllLinks();
	}
	
	/**
	 * Sets the instance's concrete {@link Tile} instances factory. 
	 *  
	 * @param tileFactory The factory instance
	 * @throws IllegalArgumentException if the argument is {@code null}
	 */
	public void setTileProvider(TileFactory tileFactory)
	{
		if(tileFactory == null)
			throw new IllegalArgumentException();
		
		this.tileFactory = tileFactory;
		if(tileWidth != 0 && tileHeight != 0){
			initTiles();
			invalidate();
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		squareSize = Math.min(MeasureSpec.getSize(widthMeasureSpec), 
				MeasureSpec.getSize(heightMeasureSpec));
		
		setMeasuredDimension(squareSize, squareSize);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) 
	{
		if(changed){
			initTiles();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundBrush);
		if(tileFactory != null)
		{
			for(Tile[] row : tiles){
				for(Tile tile : row){
					tile.doDraw(canvas);
				}
			}
		}
	}

	public void setTunnelsLetter(char letter) {
		for (int i = 0; i<rows ; i++) {
			for (int j = 0; j< columns; j++) {
				if(tiles[i][j] instanceof TileTunnel){
					((TileTunnel)tiles[i][j]).setTunnelLetter(letter);
					invalidate(getTileRect( i, j));
				}
			}
		}
	}
}
