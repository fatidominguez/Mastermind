package cs301.up.mastermind;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

/**
 * This is the HumanSurfaceView class. This class also contains the view for Human Player.
 *
 * @author Lupita Carabes (main)
 * @author Fatima Dominguez (main)
 * 
 * @author Jessica Lazatin
 * @author Frank Phillips
 * 
 * @Date Fall 2012
 */
public class HumanSurfaceView extends SurfaceView implements OnGestureListener{

    private Bitmap arrow;
    private int savedColor;
    //Spencer, at first, used a deprecated constructor. So I, Fati, fixed it. HAH.
    //SPENCER:  IT WAS FOR DEMONSTRATIVE PURPOSES, DANG IT. Geez, Fati.
    
    GestureDetector gestureDetector = new GestureDetector(this.getContext(),this);
    // A list of where the pegs are
    Row[] rows = new Row[10];
    private int roundNum;
    private int[][] previousGuesses = new int[MastermindGame.NUM_OF_ROUNDS][MastermindGame.NUM_OF_PEGS];
    private int[][] previousFeedback = new int[MastermindGame.NUM_OF_ROUNDS][MastermindGame.NUM_OF_PEGS];


    /** called by the ctors to initialize the variables in class*/
    private void myInitializationStuff()
    {
        // Will allow everything to draw 
        setWillNotDraw(false);
        for(int i = 0; i < rows.length; i++) {
            int y = 55 + 67*i;
            rows[i] = new Row(y);
        }
    }

    
    /**
     * This method sets the active guessing row.
     * @param int round
     */
    public void setRoundNum(int round) {
        roundNum = round;
        if(roundNum < 10){
            rows[9-roundNum].changeActive();
        }
    }
    
    
    /**
     * This method gives the arrow bitmap to the rows.
     * @param Bitmap img
     */
    public void setArrowImg(Bitmap img){
        this.arrow = img;
        for(int i = 0; i < rows.length; i++) {
            rows[i].setArrowImg(this.arrow);
        }
        invalidate();
    }
    
    
    /**
     * This method sets a color to be used by the view
     * @param int color
     */
    public void setSavedColor(int color){
        savedColor = color;
    }


    /**
     * The following HumanSurfaceView methods overload the constructor method of this class
     */
    
    
    /**
     * @param Context context
     */
    //extending surface view
    public HumanSurfaceView(Context context) {
        super(context);
        myInitializationStuff();
    }
    
    
    /**
     * @param Context context
     * @param AttributeSet set
     */
    //extending surface view
    public  HumanSurfaceView(Context context, AttributeSet set) {
        super(context, set);
        myInitializationStuff();
    }

    
    /**
     * @param Context context
     * @param AttributeSet attrs
     * @param int defStyle
     */
    //extending surface view
    public  HumanSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        myInitializationStuff();
    }

    
    /** 
     * Draw the elements
     * @param Canvas canvas
     * */
    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < rows.length; i++) {
            rows[i].draw(canvas);
        }

    }

    
    /**
     * This method gets the player's guess from the current round
     * @param int currentRound
     * @return int[] guess
     */
    public int[] getGuess(int currentRound){
        Peg[] temp = rows[currentRound].getPegs().clone();
        int[] guess = new int[temp.length];
        for(int i = 0; i < guess.length; i++){
            guess[i]= temp[i].getColor();
        }
        return guess;
    }

    
    /** 
     * Sets the text
     * @param TextView v
     * */
    public void setTextView(TextView v) {

    }

    
    /**
     * This method sets the previous guess and puts it in a double array of ints
     * @param int[][] guesses
     */
    public void setPreviousGuess(int[][] guesses){
        previousGuesses = guesses;
        for(int i = 0; i < rows.length; i++){
            Peg[] temp = rows[i].getPegs().clone();
            for(int j = 0; j < 6; j++){
                temp[j].setColor(previousGuesses[9-i][j]);
            }

        }
        this.invalidate();
    }
    
    
    /**
     *This method sets the Previous feedback and puts it in a double array of ints
     * @param int[][] feedback
     */
    public void setPreviousFeedBack(int[][] feedback){
        previousFeedback = feedback;
        for(int i = 0; i < rows.length; i++){
            FeedBackPeg[] temp = rows[i].getFeedback().clone();
            for(int j = 0; j < 6; j++){
                temp[j].setCondition(previousFeedback[9-i][j]);
            }
        }
        this.invalidate();
    }


    /**
     * @param View v, MotionEvent event
     * */
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    
    /**
     * This method helps detect if a Touch event occurs
     * @ return boolean  true if a Touch happened, false otherwise
     */
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    
    /**
     * For an onDown event, the peg will go through a pallet of colors per one touch.
     * Used over onTouch to avoid the cycling of colors.
     * @author Spencer Fleetwood Dale (because he made me)
     * @param MotionEvent e
     */
    public boolean onDown(MotionEvent e) {
        // Get the x any y locations of where the user touched
        int x = (int) e.getX();
        int y = (int) e.getY();

        // Run through row array and if the location matches 
        // change the color and display the name.
        for (int i = 0; i < rows.length; i++) {
            Peg[] temp = rows[i].getPegs().clone();
            for(int j = 0; j < 6; j++){
                if(temp[j].containsPoint(x,y) && rows[i].activeGuess){
                    rows[i].changePegColor(j);
                    break;
                }
            }
        }
        //force the surface to redraw itself 
        this.invalidate();
        return true;
    }

  
    /**
     * By long-pressing, a user can change the color of a certain peg.
     * This method gets the saved color retrieved from the  most recently
     * pressed color button, then sets the touched peg to that color.
     * 
     * @param MotionEvent e
     */
    public void onLongPress(MotionEvent e) {
        // Get the x any y locations of where the user touched
        int x = (int) e.getX();
        int y = (int) e.getY();
        
        // Run through row array and if the location matches 
        // change the color and display the name.
        for (int i = 0; i < rows.length; i++) {
            Peg[] temp = rows[i].getPegs().clone();
            for(int j = 0; j < 6; j++){
                if(temp[j].containsPoint(x,y) && rows[i].activeGuess){
                    rows[i].setPegColor(j,savedColor);
                    invalidate();
                    break;
                }
            }
        }
    }//onLongPress
    
    
    /**
     * This method helps detect if a fling event occurs with the initial on down 
     * Motion Event that triggered it
     * @param MotionEvent e1
     * @param MotionEvent e2
     * @param float velocityX how fast the fling is horizontally
     * @param float velocityY how fast the fling is vertically
     * NOT USED
     */
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) { return false; }
    
    
    /**
     * This method detects for scroll events
     * NOT USED
     */
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) { return false; }
    
    
    /**
     * This method detects for Show Press events
     * NOT USED
     */
    public void onShowPress(MotionEvent e) {}

    
    /**
     * This method detects for single tap up events
     * NOT USED
     */
    public boolean onSingleTapUp(MotionEvent e) { return false; }


}