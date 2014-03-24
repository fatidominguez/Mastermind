package cs301.up.mastermind;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.view.GestureDetector;
import android.view.SurfaceView;
import android.view.GestureDetector.OnGestureListener;

/** 
 * This is the SetKeySurfaceView class.
 * It handles our GUI pop-up for setting the key in a two player game.
 * 
 * @author Lupita Carabes
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez
 * 
 * @Date Fall 2012
 */
public class SetKeySurfaceView extends SurfaceView implements OnGestureListener{

	// A list of where the pegs are
	Peg[] pegs = new Peg[6];
	boolean cantTouchThis;
	private int savedColor;
	GestureDetector gestureDetector = new GestureDetector(this.getContext(),this);

	/** called by the ctors to initialize the variables I've added to this class */
	private void myInitializationStuff()
	{
		// Will allow everything to draw 
		setWillNotDraw(false);
		for(int i = 0; i < pegs.length; i++){
			int x = 63*i + 100;
			pegs[i] = new Peg(x,100);
		}
	}
	
	
	/**
	 * These methods overload the constructor for this class
	 */
	/**
	 * @param Context context
	 */
	//extending surface view
	public SetKeySurfaceView(Context context) {
		super(context);
		myInitializationStuff();
	}

	/**
	 * @param Context context
	 * @param AttributeSet set
	 */
	//extending surface view
	public  SetKeySurfaceView(Context context, AttributeSet set) {
		super(context, set);
		myInitializationStuff();
	}

	/**
	 * @param Context context
	 * @param AttributeSet attrs
	 * @param int defStyle
	 */
	//extending surface view
	public  SetKeySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		myInitializationStuff();
	}

	
	/** Draw the elements
	 * @param Canvas Canvas
	 * */
	@Override
	public void draw(Canvas canvas) {;
	for(int i = 0; i < MastermindGame.NUM_OF_PEGS; i++) {
		pegs[i].draw(canvas);
	 	}
	}


	/**
	 * This method gets the key and returns it in an array
	 * @return int[] tempInt
	 */
	public int[] getKey(){
		int[] tempInt = new int[pegs.length];
		for(int i = 0; i < pegs.length; i++){
			tempInt[i]= pegs[i].getColor();
		}
		return tempInt;
	}
	
	
	/**
	 * This method saves the color to be used whilst setting the key
	 * @param int color
	 */
	public void saveColor(int color){
		savedColor = color;
	}


	/**
	 * This method sets the colors and goes through a cycle of color options
	 * @param int[] key
	 */
	public void setColors(int[] key){
		for(int i = 0; i < pegs.length; i++) {
			pegs[i].setColor(key[i]);
		}
		invalidate();
	}

	
	/**
	 * This method displays the key
	 */
	public void setAsDisplay(){
		cantTouchThis = true;
	}

	
	/**
	 * This method helps detect if a touch event occurs
	 * @param MotionEven motionEvent
	 */
	public boolean onTouchEvent(MotionEvent motionEvent)
	{
		return this.gestureDetector.onTouchEvent(motionEvent);
	}

	
	/**
	 * This method helps detect if a down event occurs
	 * @return boolean  true if down event happened, false otherwise
	 */
	public boolean onDown(MotionEvent event) {
		if(!cantTouchThis){
			// Get the x any y locations of where the user touched
			int x = (int) event.getX();
			int y = (int) event.getY();

			// Run through row array and if the location matches 
			// change the color and display the name.
			for (int i = 0; i < pegs.length; i++) {
				if(pegs[i].containsPoint(x,y)){
					pegs[i].incrementColor();
				}
			}

			//force the surface to redraw itself 
			this.invalidate();
			return true;
		}
		return false;
	}
	
	
	/**
	 * This method detects a LongPressEvent and saves the color of the image pressed on
	 * @param MotionEvent event
	 */
	public void onLongPress(MotionEvent event) {
		if(!cantTouchThis){
			// Get the x any y locations of where the user touched
			int x = (int) event.getX();
			int y = (int) event.getY();

			// Run through row array and if the location matches 
			// change the color and display the name.
			for (int i = 0; i < pegs.length; i++) {
				if(pegs[i].containsPoint(x,y)){
					pegs[i].setColor(savedColor);
				}
			}

			//force the surface to redraw itself 
			this.invalidate();
		}
	}

	
	/**
	 * the following methods below aren't used
	 */
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) { return false; }

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) { return false; }

	public void onShowPress(MotionEvent e) {}

	public boolean onSingleTapUp(MotionEvent e) { return false; }


}
