package cs301.up.mastermind;

import edu.up.game.GameAction;

/**
 *This is the GameAction class that is used to confirm a player's guess for the round
 * @author Lupita Carabes 
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez (Main)
 * 
 * @Date Fall 2012
 */

public class GuessAction extends GameAction{
	private static final long serialVersionUID = -2816695678920660501L;
	// An array to store the player's guess
	private int[] guessPattern = new int[6];

	/**
	 * The constructor for the GuessAction Class. 
	 * @param source 	the player
	 * @param pegs		the int array that contains the player's guess
	 */
	public GuessAction(int source, int[] pegs) {
		super(source);
		for(int i = 0; i < pegs.length; i++){
			guessPattern[i] = pegs[i];
		}
	}

	
	/**
	 * This method returns the pattern that the player guessed.
	 * @return		int[] that contains the pattern guessed
	 */
	public int[] getGuessPattern() {
		return guessPattern;
	}


}
