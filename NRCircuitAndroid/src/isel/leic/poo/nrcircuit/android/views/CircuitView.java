package isel.leic.poo.nrcircuit.android.views;

import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.common.TileFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircuitView extends View {

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
		
		horizontalTileCount = attrs.getAttributeIntValue(NAMESPACE, INITIAL_HORIZONTAL_TILE_COUNT_ATTR, DEFAULT_TILE_COUNT);
		verticalTileCount = attrs.getAttributeIntValue(NAMESPACE, INITIAL_VERTICAL_TILE_COUNT_ATTR, DEFAULT_TILE_COUNT);
		tileFactory = null;
	}
	
	private void initBrushes(Context context, AttributeSet attrs) 
	{
		backgroundBrush = new Paint();
		backgroundBrush.setColor(Color.BLACK);
		backgroundBrush.setStyle(Paint.Style.FILL);
		backgroundBrush.setAlpha(100);

		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
		
		for(int row = 0; row < horizontalTileCount; ++row)
		{
			for(int column = 0; column < verticalTileCount; ++column)
			{
				int currentLeft = column * tileWidth;
				int currentTop = row * tileHeight;
				tiles[row][column] = tileFactory.createTile(row, column, this, 
						new RectF(currentLeft, currentTop, currentLeft + tileWidth, currentTop + tileHeight));
			}
		}
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
