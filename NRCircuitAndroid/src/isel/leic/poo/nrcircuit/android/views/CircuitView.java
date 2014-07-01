package isel.leic.poo.nrcircuit.android.views;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.common.Tile.Link;
import isel.leic.poo.nrcircuit.android.common.TileActionEvent;
import isel.leic.poo.nrcircuit.android.common.TileActionEvent.TileEvent;
import isel.leic.poo.nrcircuit.android.common.TileFactory;
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
	
	public static interface OnTileActionListener {
		public void onTileAction(TileActionEvent evt);
	}
	
	private OnTileActionListener tileActionListener;
	
	private static final int DEFAULT_TILE_COUNT = 5;
	
	private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
	private static final String INITIAL_HORIZONTAL_TILE_COUNT_ATTR = "initialHorizontalTileCount";
	private static final String INITIAL_VERTICAL_TILE_COUNT_ATTR = "initialVerticalTileCount";
	
	private int horizontalTileCount;
	private int verticalTileCount;
	
	private Tile[][] tiles;
	private TileFactory tileFactory;
	
	private Paint backgroundBrush;
	
	private int tileWidth;
	private int tileHeight;
	
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
						if(tiles[row][column].hasLinks()){
							fireOnTileTouchEvent(new TileActionEvent(TileEvent.LINKED_TILE_TOUCH, row, column));
						}else{
							fireOnTileTouchEvent(new TileActionEvent(TileEvent.TILE_TOUCH, row, column));
						}
						return true;
					case MotionEvent.ACTION_MOVE:
						fireOnTileTouchEvent(new TileActionEvent(TileEvent.TILE_LINK, row, column));
						return true;
				}
				return false;
			}
		});
		
		horizontalTileCount = attrs.getAttributeIntValue(NAMESPACE, INITIAL_HORIZONTAL_TILE_COUNT_ATTR, DEFAULT_TILE_COUNT);
		verticalTileCount = attrs.getAttributeIntValue(NAMESPACE, INITIAL_VERTICAL_TILE_COUNT_ATTR, DEFAULT_TILE_COUNT);
		tileFactory = null;
	}
	
	private int getColumn(float x){
		return (int)(x/tileWidth);
	}
	
	private int getRow(float y){
		return (int)(y/tileHeight);
	}
	
	private boolean isCoordsWithinBounds(float x, float y){
		return x>=0 && x<=horizontalTileCount*tileWidth && y>=0 && y<=verticalTileCount*tileHeight;
	}
	
	private void initBrushes(Context context, AttributeSet attrs) 
	{
		backgroundBrush = new Paint();
		backgroundBrush.setColor(Color.BLACK);
		backgroundBrush.setStyle(Paint.Style.FILL);
		backgroundBrush.setAlpha(100);

		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}
	
	private Rect getTileRect(int row, int column){
		float left = column * tileWidth;
		float top = row * tileHeight;
		return new Rect((int)left, (int)top, (int)left+tileWidth, (int)top+tileHeight);
	}
	
	private RectF getTileBounds(int row, int column){
		float left = column * tileWidth;
		float top = row * tileHeight;
		return new RectF((int)left, (int)top, (int)left+tileWidth, (int)top+tileHeight);
	}
	
	public void setSingleLink(int row, int column, int lastRow, int lastColumn, char letter){
		int cDelta = lastColumn - column;
		int rDelta = lastRow - row;
		
		tiles[row][column].setLink(cDelta != 0 ? 
				cDelta < 1 ? Link.LEFT : Link.RIGHT : 
				rDelta < 1 ? Link.TOP : Link.BOTTOM , 
				letter);
		
		invalidate(getTileRect(row, column));
	}
	
	public void setLink(int startRow, int startColumn, int stopRow, int stopColumn, char letter){
		setSingleLink(startRow, startColumn, stopRow, stopColumn, letter);
		setSingleLink(stopRow, stopColumn, startRow, startColumn, letter);
	}
	
	public void clearLink(int row, int column){
		if(tiles[row][column].clearLinks()){
			invalidate(getTileRect(row, column));
		}
	}
	
	public void setTileCount(int horizontalTileCount, int verticalTileCount){
		this.horizontalTileCount = horizontalTileCount;
		this.verticalTileCount = verticalTileCount;
	}
	
	private void initTiles() 
	{
		if(tileFactory == null)
			return;
		
		tiles = new Tile[horizontalTileCount][verticalTileCount];
		
		for(int row = 0; row < verticalTileCount; ++row)
		{
			for(int column = 0; column < horizontalTileCount; ++column)
			{
				tiles[row][column] = tileFactory.createTile(row, column, this, 
						getTileBounds(row, column));
			}
		}
	}

	public void setTileActionListener(OnTileActionListener tileActionListener){
		this.tileActionListener = tileActionListener;
	}
	
	private void fireOnTileTouchEvent(TileActionEvent evt)
	{
		if(tileActionListener != null)
			tileActionListener.onTileAction(evt);
	}
	
	public void setTileProvider(TileFactory tileFactory)
	{
		if(tileFactory == null)
			throw new IllegalArgumentException();
		
		this.tileFactory = tileFactory;
		initTiles();
		invalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		int squareSize = Math.min(MeasureSpec.getSize(widthMeasureSpec), 
				MeasureSpec.getSize(heightMeasureSpec));
		
		tileWidth = squareSize / horizontalTileCount;
		tileHeight = squareSize / verticalTileCount;
		
		setMeasuredDimension(tileWidth * horizontalTileCount, tileHeight * verticalTileCount);
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
}
