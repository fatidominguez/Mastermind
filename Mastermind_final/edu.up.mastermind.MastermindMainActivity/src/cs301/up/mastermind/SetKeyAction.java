package cs301.up.mastermind;

import edu.up.game.GameAction;

/** 
 * This is the GameAction class for confirming the player's key. This key will 
 * be what each of the other player's guess will be compared to.
 * 
 * @author Lupita Carabes
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez
 * 
 * @Date Fall 2012
 */
public class SetKeyAction extends GameAction{

	private static final long serialVersionUID = -4056622047518895719L;
	
	//This is an array of ints that stores the key that the player made
	private int[] patternSet = new int[6];
	protected boolean isEasyFeedback;
	protected boolean isComputer;

	
	/**
	 * This is the constructor for the action to set keys
	 * 
	 * @param int source 	the player
	 * @param int[] pegs		the array of ints that the player set
	 * @param boolean isEasyFeedback
	 */
	public SetKeyAction(int source, int[] pegs, boolean isComputer, boolean isEasyFeedback) {
		super(source);
		patternSet= pegs.clone();
		this.isEasyFeedback = isEasyFeedback;
		this.isComputer = isComputer;
	}//constructor

	
	/**
	 * This is the getter method for the key
	 * @return int[]
	 */
	public int[] getPatternSet() {
		return patternSet;
	}//getPatternSet

}
