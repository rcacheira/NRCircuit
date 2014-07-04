package isel.leic.poo.nrcircuit.android.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class MessageView extends View {

	private final int TEXT_SIZE = 75;
	
	private Paint levelTextBrush;
	private Paint backgroundBrush;
	
	private float level;
	
	public MessageView(Context context) {
		this(context, null, 0);
	}

	public MessageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		level = 0;
		
		levelTextBrush = new Paint();
		levelTextBrush.setColor(Color.WHITE);
		levelTextBrush.setTextSize(TEXT_SIZE);
		levelTextBrush.setTextAlign(Paint.Align.CENTER);
		
		backgroundBrush = new Paint();
		backgroundBrush.setColor(Color.BLACK);
		backgroundBrush.setStyle(Paint.Style.FILL_AND_STROKE);
	}

	public void setLevel(int level){
		this.level = level;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), backgroundBrush);
		if(level > 0){
			canvas.drawText("Level: " + (int)level, getWidth()/2, getHeight()/2 + TEXT_SIZE, levelTextBrush);
		}
	}
	
}
