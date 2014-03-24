package cs301.up.mastermind;

import edu.up.game.*;

/** 
 * This is the Main Activity that initializes the game.
 * 
 * @author Lupita Carabes
 * @author Jessica Lazatin
 * @author Frank Phillips
 * @author Fatima Dominguez
 * 
 * @Date Fall 2012
 */
public class MastermindMainActivity extends GameMainActivity{
	
	boolean isOnePlayer;

	
	/**
	 * This method creates the main GameConfig
	 * @return GameConfig defaultConfig
	 */
	public GameConfig createDefaultConfig() {
		// Defines the allowed player types
		GamePlayerType[] playerType = new GamePlayerType[4];
		playerType[0] = new GamePlayerType("2-player", false, "cs301.up.mastermind.HumanPlayer", false);
		playerType[1] = new GamePlayerType("1-player: Easy Feedback", false, "cs301.up.mastermind.ComputerPlayer", true);
		playerType[2] = new GamePlayerType("1-player: Easier Code", false, "cs301.up.mastermind.MediumComputerPlayer", true);
		playerType[3] = new GamePlayerType("1-player: THAT DARN CODE!", false, "cs301.up.mastermind.BetterComputerPlayer", true);

		// Create a game configuration class for Mastermind
		GameConfig defaultConfig = new GameConfig(playerType, 2, 2, "Mastermind");
		
	
		// Add default players
		defaultConfig.addPlayer("Human", 0);
		defaultConfig.addPlayer("Computer", 1);

		return defaultConfig;

	}



	/**
	 * This method creates a new Mastermind Game
	 * @param GameConfig config
	 * @return LocalGame
	 */
	public final LocalGame createLocalGame(GameConfig config) {
		GamePlayerType[] temp = config.getSelTypes().clone();
		for(int i =0; i < temp.length; i++){
			if(temp[i].isComputer){
				isOnePlayer = true;
			}else{
				isOnePlayer = false;
			}
		}
		return new MastermindGame(config, 0, isOnePlayer);

	}


	/**
    * not used
    */
	@Override
	public ProxyPlayer createRemotePlayer() {
		return null;
	}


	/**
	 * not used
	 */
	@Override
	public ProxyGame createRemoteGame(String hostName) {
		return null;
	}
}

