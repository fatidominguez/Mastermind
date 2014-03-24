package cs301.up.mastermind;


import edu.up.game.R;

import edu.up.game.GameHumanPlayer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;


/**
 *This is the HumanPlayer class. This class also contains the GUI.
 *
 * @author Lupita Carabes (Main)
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez (Main)
 * 
 * @Date Fall 2012
 */


public class HumanPlayer extends GameHumanPlayer implements OnClickListener{

	private int roundNum;
	private boolean isHelpOn;

	private HumanSurfaceView hsv;
	private SetKeySurfaceView skv;

	private ImageButton giveUpButton;
	private ImageButton guessButton;
	private Button setKeyButton;
	private ImageButton endGameButton;
	private ImageButton skipButton;
	private Button okButton;
	private Button yesButton;
	private Button noButton;

	private ImageButton colorButton1;
	private ImageButton colorButton2;
	private ImageButton colorButton3;
	private ImageButton colorButton4;
	private ImageButton colorButton5;
	private ImageButton colorButton6;
	private ImageButton colorButton7;
	private ImageButton colorButton8;
	private ImageButton helpButton;

	private TextView layoutTitle;
	private TextView errorTxt;

	private FrameLayout setKeyLayout;
	private FrameLayout errorLayout;
	private FrameLayout helpLayout;
	private Bitmap arrowImg;

	private int[][] previousGuesses = new int[MastermindGame.NUM_OF_ROUNDS][MastermindGame.NUM_OF_PEGS];
	private int[][] previousFeedback = new int[MastermindGame.NUM_OF_ROUNDS][MastermindGame.NUM_OF_PEGS];
	private int[] revealedKey = new int[MastermindGame.NUM_OF_PEGS];

	/**
	 * display the MasterMind gui and begin listening for button clicks from it
	 *  */
	@Override
	protected final void initializeGUI() {


		setContentView(R.layout.humanplayer);
		hsv = new HumanSurfaceView(this);
		skv = new SetKeySurfaceView(this);
		
		//this layout contains the SurfaceView relating to anything about the key
		LinearLayout container2 = (LinearLayout) this.findViewById(R.id.setKeySurface);
		
		//this layout contains the SurfaceView relating to the board
		LinearLayout container = (LinearLayout) this.findViewById(R.id.SurfaceViewContainer);
		
		container.addView(hsv);
		container2.addView(skv);
		
		//this holds the popup screen that shows the key things
		setKeyLayout = (FrameLayout) this.findViewById(R.id.frame);
		
		//this holds the popup that gives you messages
		errorLayout = (FrameLayout) this.findViewById(R.id.frame2);
		
		//this holds the popups that give help images
		helpLayout = (FrameLayout) this.findViewById(R.id.helpframe);
		
		//set the to invisible at first
		errorLayout.setVisibility(4);
		helpLayout.setVisibility(4);

		// init the GUI control instance variables
		giveUpButton = (ImageButton) findViewById(R.id.giveUp);
		setKeyButton = (Button) findViewById(R.id.setKeyButton);
		guessButton = (ImageButton) findViewById(R.id.guessButton);
		endGameButton = (ImageButton) findViewById(R.id.endGameButton);
		skipButton = (ImageButton) findViewById(R.id.skipButton);
		okButton = (Button) findViewById(R.id.okButton);
		yesButton = (Button) findViewById(R.id.yesButton);
		noButton = (Button) findViewById(R.id.noButton);
		
		//hide these til needed
		yesButton.setVisibility(4);
		noButton.setVisibility(4);

		//all the color buttons
		colorButton1 = (ImageButton) findViewById(R.id.colorButton1);
		colorButton2 = (ImageButton) findViewById(R.id.colorButton2);
		colorButton3 = (ImageButton) findViewById(R.id.colorButton3);
		colorButton4 = (ImageButton) findViewById(R.id.colorButton4);
		colorButton5 = (ImageButton) findViewById(R.id.colorButton5);
		colorButton6 = (ImageButton) findViewById(R.id.colorButton6);
		colorButton7 = (ImageButton) findViewById(R.id.colorButton7);
		colorButton8 = (ImageButton) findViewById(R.id.colorButton8);
		helpButton = (ImageButton) findViewById(R.id.helpButton);

		//text views
		layoutTitle = (TextView)findViewById(R.id.frameTitle);
		errorTxt = (TextView)findViewById(R.id.errorTxt);
		
		//current round arrow
		arrowImg = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
		hsv.setArrowImg(arrowImg);

		//setup listeners
		giveUpButton.setOnClickListener(this);
		guessButton.setOnClickListener(this);
		endGameButton.setOnClickListener(this);
		skipButton.setOnClickListener(this);
		setKeyButton.setOnClickListener(this);
		okButton.setOnClickListener(this);
		yesButton.setOnClickListener(this);
		noButton.setOnClickListener(this);
		
		colorButton1.setOnClickListener(this);
		colorButton2.setOnClickListener(this);
		colorButton3.setOnClickListener(this);
		colorButton4.setOnClickListener(this);
		colorButton5.setOnClickListener(this);
		colorButton6.setOnClickListener(this);
		colorButton7.setOnClickListener(this);
		colorButton8.setOnClickListener(this);
		helpButton.setOnClickListener(this);

		updateDisplay();

	}//initializeGUI


	/**
	 * This method collects the needed information from the game state and 
	 * updates the GUI accordingly	
	 */
	protected final void updateDisplay() {
		
		//create a game copy
		MastermindGame game = (MastermindGame) this.game;
		
		//important things to know
		int myId = game.whoseTurn();
		roundNum = game.getRoundNumber(myId);
		int nextPlayer = 1 - myId;
		revealedKey = game.getKey(nextPlayer);
		
		//do not show the SetKey round if it is a one player game
		if(game.isOnePlayerGame() && roundNum == 11){
			takeAction(new SkipTurnAction(myId, false));
		}
		//asks if a player wants to continue guessing for guessing sake
		if(game.isKeyGuessed[nextPlayer] && (roundNum  + 1 > game.getRoundNumber(nextPlayer)) 
				&& !game.isKeyGuessed[myId]){
			errorTxt.setText("Your opponent has already won. \n Do you want to continue guessing?");
			errorLayout.setVisibility(2);
			yesButton.setVisibility(2);
			noButton.setVisibility(2);
			okButton.setVisibility(4);
		}
		
		
		//If the other player is still guessing, this player will automatically skip their turn
		if(game.isGameOver[myId]){
			takeAction(new SkipTurnAction(myId, false));
		}
		
		//During guessing rounds, hide the set key layout
		if(game.isKeySet[myId] && roundNum != 11){
			setKeyLayout.setVisibility(4);
		}
		
		//Set the Key Round
		if(roundNum == 11){
			endGameButton.setEnabled(false);
			setThingsInvisible();
		}
		
		//Reveal Code Round
		if(roundNum == 10 || game.isKeyGuessed[myId]){
			skv.setColors(revealedKey);
			skv.setAsDisplay();
			if(game.isKeyGuessed[myId]){
				layoutTitle.setTextSize(30);
				String tries;
				if(roundNum == 1) {
					tries = " try.";
				}else{
					tries = " tries.";
				}
				layoutTitle.setText("You guessed right! It only took you " + roundNum + tries);
			}else{
				layoutTitle.setText("Sorry! This is the code:"); 
			}

			setKeyLayout.setVisibility(2); 
			setThingsInvisible();
		}

		//update the board display
		hsv.setRoundNum(roundNum);
		for(int i = 0; i < MastermindGame.NUM_OF_ROUNDS; i++){
			for(int j = 0; j < MastermindGame.NUM_OF_PEGS; j++){
				this.previousGuesses[i][j] = game.allGuessedPatterns[myId][i][j];
				this.previousFeedback[i][j] = game.feedback[myId][i][j];
			}
		}
		hsv.setPreviousGuess(this.previousGuesses);
		hsv.setPreviousFeedBack(this.previousFeedback);

	}//updateDisplay

	
	/**
	 * This method sends actions depending on which button is clicked
	 * @param View
	 */
	public final void onClick(View v) {
		MastermindGame game = (MastermindGame) this.game;
		int myId = game.whoseTurn();

		//Create the action associated with each button

		//Skip your turn
		if (v == skipButton){
			takeAction(new SkipTurnAction(myId, false)); //skip turn
		//Give up your turn
		}else if(v == giveUpButton){
			takeAction(new SkipTurnAction(myId, true));
		//Guess Button
		}else if (v == guessButton){

			//if none of the pegs are empty, then carry on
			if(hasNoEmptyPegs(1)){
					//Retrieves the guess from the surfaceView then takes a new guess action
					takeAction(new GuessAction(myId, hsv.getGuess(9-roundNum)));
			//if there are empty pegs, show the error	
			}else{
				errorLayout.setVisibility(2);
				setThingsInvisible();
			}
		//closes the game
		}else if (v == endGameButton){
			takeAction(new EndGameAction(myId));
			
		//Sets the Key
		} else if (v == setKeyButton){
			if(hasNoEmptyPegs(0)){
				if(game.isKeySet[myId]){
					takeAction(new SkipTurnAction(myId, false));
				}else{
					takeAction(new SetKeyAction(myId, skv.getKey(), false, false));
				}
			}else{
				errorLayout.setVisibility(2);
				setThingsInvisible();
			}
		
		//Closes the error view
		}else if(v == okButton){
			errorLayout.setVisibility(4);
			if(game.isKeySet[myId]){
				setThingsVisible();
			}
		
		//allows a player to continue guessing
		}else if(v == yesButton){
			errorLayout.setVisibility(4);
			setThingsVisible();	
		//stops the player from guessing if they already lost
		}else if(v == noButton){
			errorLayout.setVisibility(4);
			skv.setColors(revealedKey);
			skv.setAsDisplay();
			setKeyLayout.setVisibility(2); 
			setThingsInvisible();
			takeAction(new SkipTurnAction(myId, true));
		//The help button makes the helpFrame appear/disappear	
		}else if(v == helpButton){
			if(isHelpOn){
				helpLayout.setVisibility(4);
				isHelpOn = false;
			}else{
				helpLayout.setVisibility(2);
				isHelpOn = true;
			}
		//All the color buttons
		}else if (v == colorButton1){
			hsv.setSavedColor(0);
			skv.saveColor(0);
		}else if (v == colorButton2){
			hsv.setSavedColor(1);
			skv.saveColor(1);
		}else if (v == colorButton3){
			hsv.setSavedColor(2);
			skv.saveColor(2);
		}else if (v == colorButton4){
			hsv.setSavedColor(3); 
			skv.saveColor(3);
		}else if (v == colorButton5){
			hsv.setSavedColor(4);
			skv.saveColor(4);
		}else if (v == colorButton6){
			hsv.setSavedColor(5);
			skv.saveColor(5);
		}else if (v == colorButton7){
			hsv.setSavedColor(6);
			skv.saveColor(6);
		}else if (v == colorButton8){
			hsv.setSavedColor(7);
			skv.saveColor(7);
		}
	}

	
	/**
	 * This method checks if there are no empty pegs in their guess or their key
	 * @param int x
	 * @return boolean true if there are no empty pegs, false otherwise 
	 */
	public boolean hasNoEmptyPegs(int x) {
		int[] temp = new int[MastermindGame.NUM_OF_PEGS];
		if(x==1){
			temp = hsv.getGuess(9-roundNum);
		}else{
			temp = skv.getKey();
		}
		boolean hasNoEmptyPegs = true;
		for(int i = 0; i < temp.length; i++){
			if(temp[i] == 8){
				hasNoEmptyPegs = false;
			}
		}
		return hasNoEmptyPegs;
	}

	
	/**
	 * This method sets the visibility of the buttons to 4, which is invisible.
	 */
	public void setThingsInvisible() {
		endGameButton.setVisibility(4);
		guessButton.setVisibility(4);
		skipButton.setVisibility(4);
		giveUpButton.setVisibility(4);
	}
	
	
	/**
	 * This method sets the visibility of the buttons to 2, which is visible.
	 */
	public void setThingsVisible(){
		endGameButton.setVisibility(2);
		guessButton.setVisibility(2);
		skipButton.setVisibility(2);
		giveUpButton.setVisibility(2);
	}

}