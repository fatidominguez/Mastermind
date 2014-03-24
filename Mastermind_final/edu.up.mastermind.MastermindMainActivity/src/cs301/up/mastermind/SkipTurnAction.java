package cs301.up.mastermind;

import edu.up.game.GameAction;

/**
 *This is the GameAction class that skips the player's turn.
 * @author Lupita Carabes
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez
 * 
 * @Date Fall 2012
 */
public class SkipTurnAction extends GameAction{
	private static final long serialVersionUID = 103542939255223826L;
	protected boolean giveUp;
	
	
	/**
	 * The constructor for the SkipTurnAction Class. 
	 * @param source 	the player
	 * @boolean giveUp
	 */
	public SkipTurnAction(int source, boolean giveUp) {
		super(source);
		 this.giveUp = giveUp;
	}

}
