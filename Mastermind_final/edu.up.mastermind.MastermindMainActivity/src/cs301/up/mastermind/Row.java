package cs301.up.mastermind;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * This class contains the all GUI elements for each round:
 * the guess pegs and the feedback pegs.
 * 
 * @author Fatima Dominguez
 * @author Lupita Carabes
 * @author Jessica Lazatin
 * @author Frank Phillips
 * 
 * 
 * @Date Fall 2012
 */
public class Row {
    protected boolean activeGuess;
    private Peg[] pegs = new Peg[6];
    public FeedBackPeg[] feedback = new FeedBackPeg[6];
    private int y;
    private int x;
    Bitmap arrow;

    
    /**
     * The constructor method for the Row  
     * @param int y
     */
    public Row(int y) {
        x = 50;
        this.y = y;
        for(int i = 0; i < pegs.length; i++){
            x = 63*i + 50;
            pegs[i] = new Peg(x,this.y);
        }
        x = x + 50;
        int x2 = x;
        for(int i = 0; i < feedback.length; i++){
            x = 24*i + x2;
             feedback[i] = new FeedBackPeg(x,this.y);
        }
    }

    
    /**
     *This method sets the arrow image that appears on the GUI 
     * @param Bitmap img
     */
    public void setArrowImg(Bitmap img){
    	arrow = img;
    }

    
    /**
     * This method draws the entire row
     * @param Canvas canvas 
     */
    public void draw(Canvas canvas) {
        for(int i = 0; i < pegs.length; i++){
            pegs[i].draw(canvas);
        }
        for(int i = 0; i < feedback.length; i++){
            feedback[i].draw(canvas);
        }
        if(activeGuess){
        	canvas.drawBitmap(arrow, null, new RectF(x+20,y-20,x+70,y+30), null);
        }
    }


    /**
     * This method changes the current color to the color inputed as the parameter
     * @param int location
      */
    public void changePegColor(int location) {
    	pegs[location].incrementColor();
    }
    
    
    /**
     * This method sets the color of the pegs located in the array
     * @param int location
     * @param int color
     */
    public void setPegColor(int location, int color){
    	pegs[location].setColor(color);
    }
    
    
    /**
     * This method gets and returns the pegs located in the array
     * @return Peg[] pegs
     */
    public Peg[] getPegs(){
    	return pegs;
    }

    
    /**
     * This method changes the Row to an active row
     */
    public void changeActive(){
    	activeGuess = true;
    }


   /**
    * This method gets the feedback for the row
    * @return FeedBackPeg[] feedback
    */
	public FeedBackPeg[] getFeedback() {
		return feedback;
	}

}