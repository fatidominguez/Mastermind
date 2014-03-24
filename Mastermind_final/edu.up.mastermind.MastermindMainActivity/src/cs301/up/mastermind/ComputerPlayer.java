package cs301.up.mastermind;

import edu.up.game.GameAction;
import edu.up.game.GameComputerPlayer;

/**
 * An computer game player. This player creates easy-to-break patterns for users.
 * 6 out of the 8 colors will always be used.
 * 
 * @author Jessica Lazatin (main)
 * @author Fatima Dominguez (main)
 * @author Lupita Carabes
 * @author Frank Phillips
 * 
 * @Date Fall 2012
 * @version November 2012
 */
public class ComputerPlayer extends GameComputerPlayer{

	protected int[] pattern = new int[MastermindGame.NUM_OF_PEGS]; //an array of six integers
	

	/**
	 * The constructor method for ComputerPlayer class 
	 * a computer player sets a key for a one-player to guess
	 */
	public ComputerPlayer() {
		
	}
	
	/**
	 * This method creates and calculates a simple pattern
	 * Disregards any repeat of numbers
	 * @return GameAction
	 */
	 protected GameAction calculateMove() {
		 MastermindGame gameCopy = (MastermindGame) this.game;
		 
		 if(!gameCopy.isKeySet[game.whoseTurn()]){
			 for(int i = 0; i < pattern.length; i++){
				 int rand = (int) (8*Math.random()); //creates a random positive integer from 0-7
				 pattern[i] =  rand;
			 }//for loop
			 return new SetKeyAction(this.game.whoseTurn(), pattern, true, true); //last param sends in that it is in easy mode
		 }else{
			 return new SkipTurnAction(this.game.whoseTurn(), false);
		 }
		 

	}//calculate move

}//ComputerPlayer
