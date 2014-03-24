package cs301.up.mastermind;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * This class is the object feedback pegs for
 * drawing on the GUI.
 * 
 * @author Lupita Carabes (main)
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez (main)
 * 
 * @Date Fall 2012
 * @version November 2012
 */
public class FeedBackPeg{

	private int x;
	private int y;
	private int condition;
	static final int RADIUS = 10;
	static final int EMPTY = 0;
	static final int WHITE = 1;
	static final int RED = 2;
	
	
	/**
	 * This is the constructor for the FeedBackPeg
	 * @param int x
	 * @param int y
	 */
	public FeedBackPeg(int x, int y){
		this.x = x;
		this.y = y;
	}


	/**
	 * This method set condition to 0, 1, or 2
	 * A 0 will represent an invisible peg (wrong color wrong spot)
	 * A 1 will represent a white peg (right color, wrong spot)
	 * A 2 will represent a red peg (right color, right spot)
	 * @param int condition
	 */
	public final void setCondition(int condition) {
		this.condition = condition;
	}


	/**
	 * This is the drawing method for the FeedBackPeg class
	 * @param Canvas canvas  the canvas to draw on
	 */
	public void draw(Canvas canvas) {
		Paint defaultPaint = new Paint();
		if(condition == EMPTY){
			defaultPaint.setColor(Color.rgb(79, 130, 188));
		}
		else if(condition == WHITE){
	    	defaultPaint.setColor(Color.WHITE);
	    }
	    else if(condition == RED){
	     	defaultPaint.setColor(Color.RED);
		}
		canvas.drawCircle(x,y,RADIUS,defaultPaint);
	}


}//FeedBackPeg class