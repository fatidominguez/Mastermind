package cs301.up.mastermind;

import edu.up.game.GameAction;
import edu.up.game.GameConfig;
import edu.up.game.LocalGame;

/** 
 * This class is the game state. The game of Mastermind will have the players 
 * set a key in which the other player will try and guess. Each guess will be 
 * stored into an array, feedback based on the accuracy of the guess will be given, 
 * then reported to the players. If the player matches the other player's key, then 
 * they are the winner.
 * 
 * @author Lupita Carabes
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez (Main)
 * 
 * @Date Fall 2012
 */
public class MastermindGame extends LocalGame{
	private static final long serialVersionUID = 8169697642880520706L;

	//This is a 3D array to contain both player's guesses
	protected int[][][] allGuessedPatterns;

	//This array contains both players' keys.
	protected int[][] key;
	protected int nextPlayer;
	protected boolean[] isGameOver = {false, false};
	protected boolean[] isKeySet;
	protected boolean[] isKeyGuessed;
	protected boolean isOnePlayer;


	// This 3D array holds both players' feedbacks
	protected int[][][] feedback;

	private int[] currentRound;
	private boolean isEasyFeedback;
	public static final int NUM_OF_PLAYERS = 2;
	public static final int NUM_OF_PEGS = 6;
	public static final int NUM_OF_ROUNDS = 10;


	/**
	 * Constructor for the MasterMind Game class
	 * @param GameConfig initConfig
	 * @param int initTurn
	 * @param boolean isOnePlayer
	 */
	public MastermindGame(GameConfig initConfig, int initTurn, boolean isOnePlayer) {
		super(initConfig, initTurn);
		//initialize all the arrays
		key = new int[NUM_OF_PLAYERS][NUM_OF_PEGS];
		allGuessedPatterns = new int[NUM_OF_PLAYERS][NUM_OF_ROUNDS][NUM_OF_PEGS];
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < NUM_OF_ROUNDS; j++){
				for(int k = 0; k < NUM_OF_PEGS; k++){
					allGuessedPatterns[i][j][k] = 8;
				}
			}
		}
		nextPlayer = 1;
		feedback = new int[NUM_OF_PLAYERS][NUM_OF_ROUNDS][NUM_OF_PEGS];
		currentRound = new int[NUM_OF_PLAYERS];
		isKeySet = new boolean[NUM_OF_PLAYERS];
		isKeyGuessed  = new boolean[NUM_OF_PLAYERS];
		currentRound[0] = 11;
		currentRound[1] = 11;
		this.isOnePlayer = isOnePlayer;

	}//constructor
	

	/**
	 * This method creates a copy of the GameState
	 * @param MastermindGame orig
	 */
	private MastermindGame(MastermindGame orig) {
		super(orig.getConfig(), orig.whoseTurn());
		//create all the arrays again
		allGuessedPatterns = new int[NUM_OF_PLAYERS][NUM_OF_ROUNDS][NUM_OF_PEGS];
		feedback = new int[NUM_OF_PLAYERS][NUM_OF_ROUNDS][NUM_OF_PEGS];
		currentRound = new int[NUM_OF_PLAYERS];
		isKeySet = new boolean[NUM_OF_PLAYERS];
		isKeyGuessed  = new boolean[NUM_OF_PLAYERS];
		key = new int[NUM_OF_PLAYERS][NUM_OF_PEGS];

		// Copy the values
		allGuessedPatterns = orig.allGuessedPatterns.clone();
		feedback = orig.feedback.clone();
		currentRound = orig.currentRound.clone();
		isKeySet = orig.isKeySet.clone();
		isGameOver = orig.isGameOver.clone();
		isKeyGuessed = orig.isKeyGuessed.clone();
		key = orig.key.clone();
		isOnePlayer = orig.isOnePlayer;
	}//overloads main constructor


	/**
	 * This method applies the action sent to the game state.
	 * @param GameAction action
	 */
	@Override
	public void applyAction(GameAction action) {
		int playerId = action.getSource();
		int temp[] = new int[NUM_OF_PEGS];

		//Setting the key
		if(action instanceof SetKeyAction){
			temp = ((SetKeyAction) action).getPatternSet();
			setKey(playerId, temp); //store the key
			isKeySet[playerId] = true; 
			currentRound[playerId] = 0; //start the guessing rounds
			
			if(((SetKeyAction)action).isComputer){
				//the computer does not guess
				isGameOver[playerId] = true;
				this.isEasyFeedback = ((SetKeyAction)action).isEasyFeedback;
			}
			nextPlayerTurn();

		//Guess Action
		}else if(action instanceof GuessAction){
			temp = ((GuessAction)action).getGuessPattern(); 
			addGuessPattern(playerId,temp); //store into the allGuesses array
			if(isEasyFeedback){
				createFeedback(playerId, temp);
			}else{
				createFeedback(playerId, temp);
			}//creates the feedback
			//increments the guess round
			incrementRound(playerId); 

			//checks to see if the player
			if(getRoundNumber(playerId) < 9){
				nextPlayerTurn(); 
			}

		//Skip Action
		}else if (action instanceof SkipTurnAction){

			if(getRoundNumber(playerId) == 10 || isKeyGuessed[playerId]){
				isGameOver[playerId] = true;
			}else if(getRoundNumber(playerId) == 11){
				isKeySet[playerId] = true;
				currentRound[playerId] = 0;
			}else if (((SkipTurnAction)action).giveUp){
				currentRound[playerId] = 10;
			}
			nextPlayerTurn();

		//End the Game
		}else if (action instanceof EndGameAction){
			System.exit(0);
		}

	} //applyAction


	/**
	 * This method returns the player's State
	 * @param int playerIndex
	 * @return LocalGame
	 */
	@Override
	public LocalGame getPlayerState(int playerIndex) {
		MastermindGame copy = new MastermindGame(this);
		return copy;
	}
	

	/**
	 * This method checks to see if game is over.
	 * @return boolean 		true if both player's have reached their end game
	 */
	@Override
	public boolean isGameOver() {
		if(isGameOver[0] && isGameOver[1]){
			return true;
		}
		return false;
	}

	/**
	 * This method checks if the game is a one-player game or not
	 * @return boolean  isOnePlayer  
	 */
	public boolean isOnePlayerGame(){
		return isOnePlayer;
	}
	
	
	/**
	 * This method returns the winner of the game.
	 * If both players guessed the code, whoever guessed it in fewer rounds.
	 * If guessed in same number of rounds, tie.
	 * If only one, then they are the winner.
	 * If they did not, they both lose.
	 * If isOnePlayer, and did not guess, computer is winner.
	 * 
	 *	@return int winner
	 */
	@Override
	public int getWinnerId() {
		int winner = 0;
		
		//when both players guessed the key
		if(isKeyGuessed[0]&&isKeyGuessed[1]){
			if(currentRound[0] < currentRound[1]){
				winner = 0; //first player wins
			}else if(currentRound[0] > currentRound[1]){
				winner = 1;  //second player wins
			}else if(currentRound[0] == currentRound[1]){
				winner = 2; //its a tie
			}
		}else if (isKeyGuessed[0]){
			winner = 0; //first player wins
		}else if (isKeyGuessed[1]){
			winner = 1; //second player wins
		}else{
			if(isOnePlayer){
				winner = 1; //computer wins
			}else{
				winner = 3; //both players lose
			}
		}
		return winner;
	}//getWinnderID

	
	/**
	 * This method returns the round number current player is in
	 * @param  playerNum 	the current player
	 * @return int the current round number
	 */
	public int getRoundNumber(int playerNum) {
		return currentRound[playerNum];
	}
	
	
	/**
	 * This method is the getter method for the key inputed
	 * @param int playerNum
	 * @return int[] temp
	 */
	public int[] getKey(int playerNum) {

		int[] temp = new int[NUM_OF_PEGS];
		for(int i = 0; i < NUM_OF_PEGS; i++){
			temp[i] = key[playerNum][i];
		}
		return temp;
	}

	
	/**
	 * This method increases currentRound variable by one
	 * @param int playerId
	 */
	private void incrementRound(int playerId) {
		if(currentRound[playerId] < 11){
			currentRound[playerId]++;
		}
	}


	/**
	 * This method changes the current Player to the next player
	 */
	private void nextPlayerTurn() {
		nextPlayer = this.whoseTurn(); //save the current player as the next player
		this.whoseTurn = 1 - this.whoseTurn; //change the current player to the next player
	}


	/**
	 * This method inputs the Guess Pattern into the allGuessedPatterns array
	 * @param int playerNum
	 * @param int[] guessPattern
	 */
	private void addGuessPattern(int playerNum, int[] guessPattern) {
		for(int i = 0; i < guessPattern.length; i++){
			allGuessedPatterns[playerNum][currentRound[playerNum]][i] = guessPattern[i];
		}
	}
	
	
	/**
	 * This method adds a feedback
	 * @param int playerNum
	 * @param int[] newFeedBack
	 */
	private void addFeedBack(int playerNum, int[] newFeedBack){
		boolean matched = true;
		for(int i = 0; i < newFeedBack.length; i++){
			if(newFeedBack[i] != 2){
				matched = false;
			}
			feedback[playerNum][currentRound[playerNum]][i] = newFeedBack[i];
		}
		if(matched){
			isKeyGuessed[playerNum] = true;
		}
	}


	/**
	 * This method adds the key into the key array
	 * @param int playerNum
	 * @param int[] newKey
	 */
	private void setKey(int playerNum, int[] newKey) {
		for(int i = 0; i < newKey.length; i++){
			this.key[playerNum][i] = newKey[i];
		}
	}
	
	
	/**
	 * This method creates a feedback
	 * @param int playerNum
	 * @param int[] input
	 */
	private void createFeedback(int playerNum, int[] input) {
		//initialize an array to store the feedback, 0 value being default color
		int[] newFeedBack = {0, 0, 0, 0, 0, 0}; 
		//initialize a boolean array to keep track of which peg of the key has been accounted for
		boolean[] trackingKeyPegs = {true, true, true, true, true, true};
		//this int keeps track of the next "empty" slot of newFeedback
		int count = 0;

		//Checks for the red pegs: correct color, correct position
		for(int i = 0; i < input.length; i++){
			if(input[i] == key[nextPlayer][i]){ // if it is the correct color in the correct place
				if(trackingKeyPegs[i]){ 
					if(isEasyFeedback){
					newFeedBack[i] = FeedBackPeg.RED;
					}else{
						newFeedBack[count] = FeedBackPeg.RED;
					}
					trackingKeyPegs[i] = false;
					count++;
				}
			}
		}
		//create a new key array for the rest of the unchecked numbers
		if(count != 6){
			int[] tempKey = new int[6-count];
			int[] tempGuess = new int[6-count];
			int[] tempTracking = new int[6-count];
			int tempCount = 0;

			for(int i = 0; i < trackingKeyPegs.length; i++){
				if(trackingKeyPegs[i]){
					tempKey[tempCount] = key[nextPlayer][i];
					tempGuess[tempCount] = input[i];
					tempTracking[tempCount] = i;
					tempCount++;
				}
			}

			//check for white pegs: correct color, wrong position
			for(int i = 0; i < tempGuess.length; i++){
				boolean isColorThere = false;
				for(int j = 0; j < tempKey.length; j++){
					if(tempGuess[i] == tempKey[j] && trackingKeyPegs[tempTracking[j]]){
						if(!isColorThere){
							trackingKeyPegs[tempTracking[i]] = false;
							if(isEasyFeedback){
							newFeedBack[tempTracking[i]] = FeedBackPeg.WHITE;
							}else{
								newFeedBack[count] = FeedBackPeg.WHITE;
								count++;
							}
							isColorThere = true;
						}
					}
				}
			}
		}

		addFeedBack(playerNum, newFeedBack);
	}//createEasyFeedback

}
