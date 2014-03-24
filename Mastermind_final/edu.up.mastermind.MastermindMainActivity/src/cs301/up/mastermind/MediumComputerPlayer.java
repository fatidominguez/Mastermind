package cs301.up.mastermind;

import edu.up.game.GameAction;
import edu.up.game.GameComputerPlayer;


/**
 *An abstract computerized game player. This player creates easy-to-break patterns for users.
 * 
 * @author Lupita Carabes
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez
 * 
 * @Date Fall 2012
 * @version November 2012
 */
public class MediumComputerPlayer extends GameComputerPlayer{

		protected int[] pattern = new int[MastermindGame.NUM_OF_PEGS]; //an array of six integers
		protected boolean[] count = {false, false, false, false, false, false, false, false}; // an array of six integers used to see if color has been used (TRUE if used)
		

		/**
		 * The constructor method for MediumComputerPlayer class 
		 * a computer player sets a key for a one-player to guess
		 */
		public MediumComputerPlayer() {
			
		}
		
		
		/**
		 * This method creates and calculates a pattern with no repeat in colors
		 * @return GameAction
		 */
		 protected GameAction calculateMove() {
			 MastermindGame gameCopy = (MastermindGame) this.game;
			 
			 if(!gameCopy.isKeySet[game.whoseTurn()]){
				 for(int i = 0; i < pattern.length; i++){
					 int rand = (int) (8*Math.random()); //creates a random positive integer from 0-7
					 while(count[rand]){
						 rand = (int)(8*Math.random()); //keep creating a random positive interger from 0-7
					 }
					 count[rand] = true;
					 pattern[i] =  rand;
				 }
				 return new SetKeyAction(this.game.whoseTurn(), pattern, true, false);
			 }else{
				 return new SkipTurnAction(this.game.whoseTurn(), false);
			 }
			 

		}//calculate move

	}//ComputerPlayerpackage cs301.up.mastermind;
