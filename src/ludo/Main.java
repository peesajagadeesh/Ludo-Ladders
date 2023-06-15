package ludo;

import ludo.config.GameConfig;
import ludo.managers.GameManager;
import ludo.managers.ScoreManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: Main
 * The Main class serves as the entry point of the Ludo-Ladders game. It initializes the necessary components and starts and ends the game.
 */
public class Main {

    /**
     * This is the main method that gets executed when the program is run. It initializes the game components, such as
     * the dice, board, players, score manager, and game manager. It then starts the game by calling
     * gameManager.startGame(), and finally, ends the game by calling gameManager.endGame().
     * Prints "Game over. Thank you for playing!" when the game is finished.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Initialising Ludo and Snake-ladders");

        Dice dice = new Dice(GameConfig.NUM_DICE_SIDES);
        Board board = new Board(Board.BOARD_SIZE);
        List<Player> players = initializePlayers();
        ScoreManager scoreManager = new ScoreManager();

        GameManager gameManager = new GameManager(players, dice, board, scoreManager);
        gameManager.startGame();
        gameManager.endGame();

        System.out.println("Game over. Thank you for playing!");
    }

    /**
     * This method initializes the players for the game. It creates a list of players and adds the specified number of
     * players to it, based on the GameConfig.NUM_PLAYERS value. Each player is assigned a unique name
     * ("Player 1", "Player 2", etc.) and pawns are initialized for each player by calling initializePawns(player).
     * @return Returns the list of initialized players.
     */
    private static List<Player> initializePlayers() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < GameConfig.NUM_PLAYERS; i++) {
            Player player = new Player("Player " + (i + 1));
            players.add(player);
            initializePawns(player);
        }
        return players;
    }

    /**
     * This method initializes the pawns for a given player. It creates the specified number of pawns per player, based
     * on the GameConfig.NUM_PAWNS value. Each pawn is assigned a player ID and a unique pawn number. The initialized
     * pawn is added to the player's pawn collection.
     * @param player
     */
    private static void initializePawns(Player player) {
        for (int i = 0; i < GameConfig.NUM_PAWNS; i++) {
            Pawn pawn = new Pawn(player.getPlayerId(), i + 1);
            player.addPawn(pawn);
        }
    }
}