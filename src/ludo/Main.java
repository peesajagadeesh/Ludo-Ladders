package ludo;

import ludo.config.GameConfig;
import ludo.managers.GameManager;
import ludo.managers.ScoreManager;

import java.util.ArrayList;
import java.util.List;

public class Main {

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

    private static List<Player> initializePlayers() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < GameConfig.NUM_PLAYERS; i++) {
            Player player = new Player("Player " + (i + 1));
            players.add(player);
            initializePawns(player);
        }
        return players;
    }

    private static void initializePawns(Player player) {
        for (int i = 0; i < GameConfig.NUM_PAWNS; i++) {
            Pawn pawn = new Pawn(player.getPlayerId(), i + 1);
            player.addPawn(pawn);
        }
    }
}