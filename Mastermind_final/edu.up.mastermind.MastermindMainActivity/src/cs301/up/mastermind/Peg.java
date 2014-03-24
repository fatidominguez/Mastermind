package cs301.up.mastermind;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

/** 
 * This is the object Peg for the GUI. It is a circle 
 * that is able to cycle through a set number of colors
 * when clicked.
 * 
 * @author Lupita Carabes
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez
 * 
 * @Date Fall 2012
 */
public class Peg {

	private int color;
	public static final int RADIUS = 27;
	public static final int TAP_MARGIN = 10;
	private int x;
	private int y;
	Paint[] palletColors = new Paint[8];


	/**
	 * This is the constructor method for Peg class
	 * Pegs are circular objects that has a color.
	 * @int x
	 * @int y
	 */
	public Peg(int x, int y) {
		this.x = x;
		this.y = y;
		color = 8;
		for(int i = 0; i < palletColors.length; i++){
			palletColors[i] = new Paint();
		}
		palletColors[0].setColor(Color.rgb(1,32,96)); // Dark Blue
		palletColors[1].setColor(Color.rgb(192,0,0)); //Red
		palletColors[2].setColor(Color.rgb(111,49,160)); //Dark Purple
		palletColors[3].setColor(Color.rgb(234,34,205)); //Dark Pink
		palletColors[4].setColor(Color.rgb(253,192,3)); //Yellow Orange
		palletColors[5].setColor(Color.rgb(146,209,78)); //Lime Green
		palletColors[6].setColor(Color.rgb(196,189,153)); //Brownish Gray
		palletColors[7].setColor(Color.rgb(3,135,141)); //Teal
	}


	/**
	 * This method sets color of peg object to the new color inputed
	 * changed from setColor because the copy paste method will be
	 * later implemented
	 */
	public void incrementColor() {
			if(color == 8){
				color = 0;
			}else{
				color = (color + 1) % 8;
			}
	}

	
	/**
	 * This method sets the color of the peg object to the new color inputed
	 * @param int colorNum
	 */
	public void setColor(int colorNum){
			color = colorNum;
	}
	
	
	/**
	 * This method checks if an object contains the points with the inputed x,y values 
	 * @param int x
	 * @param int y
	 * @return boolean true if given point is contained, false otherwise 
	 */
	public boolean containsPoint(int x, int y) {
		int fudge = (RADIUS + TAP_MARGIN);
		int left = this.x - fudge;
		int top = this.y - fudge;
		int right = this.x + fudge;
		int bottom = this.y + fudge;
		Rect r = new Rect(left, top, right, bottom);

		return r.contains(x, y);
	}


	/**
	 * This returns the current color of the peg object
	 * @return int color
	 */
	public int getColor() {
		return color;
	}
	

	/**
	 * This is the drawing method of the Peg class
	 * @param Canvas canvas
	 */
	public void draw(Canvas canvas) {
		Paint defaultColor = new Paint();
		defaultColor.setColor(Color.rgb(57,93,141));

		Paint darkTxtColor = new Paint();
		Paint lightTxtColor = new Paint();

		darkTxtColor.setColor(Color.BLACK);
		lightTxtColor.setColor(Color.WHITE);

		darkTxtColor.setTextSize(20);
		lightTxtColor.setTextSize(20);

		darkTxtColor.setTypeface(Typeface.DEFAULT_BOLD);
		lightTxtColor.setTypeface(Typeface.DEFAULT_BOLD);

		if(color == 8){
			canvas.drawCircle(x, y, RADIUS, defaultColor);
		} else {
			int colorTxt = color + 1;
			canvas.drawCircle(x, y, RADIUS, palletColors[color]);
			if(color < 4){
				canvas.drawText(""+ colorTxt, x-5, y+3, lightTxtColor);
			}else{
				canvas.drawText(""+ colorTxt, x-5, y+3, darkTxtColor);
			}
		}
	}


}
