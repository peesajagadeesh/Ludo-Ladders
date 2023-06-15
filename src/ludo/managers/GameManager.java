package ludo.managers;

import ludo.Board;
import ludo.Dice;
import ludo.Interfaces.BoardInterface;
import ludo.Interfaces.DiceInterface;
import ludo.Pawn;
import ludo.Player;
import ludo.config.GameConfig;

import java.util.List;
import java.util.Scanner;

/**
 * The GameManager class manages the overall game flow and interactions between players, dice, board, and score manager.
 */
public class GameManager {
    private List<Player> players;
    private int currentPlayerIndex;
    private final Dice dice;
    private final Board board;
    private boolean gameEnd;
    private final ScoreManager scoreManager;

    /**
     * Constructs a GameManager object with the specified players, dice, board, and score manager.
     *
     * @param players      the list of players participating in the game
     * @param dice         the dice used for rolling
     * @param board        the game board
     * @param scoreManager the score manager
     */
    public GameManager(List<Player> players, Dice dice, Board board, ScoreManager scoreManager) {
        this.players = players;
        this.dice = dice;
        this.board = board;
        this.gameEnd = false;
        this.currentPlayerIndex = 0;
        this.scoreManager = scoreManager;
    }

    /**
     * Starts the game by initializing the game board and allowing players to take turns until the game ends.
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ludo Game started!");

        while (!gameEnd) {
            System.out.println("Press Enter to play a turn or type 'exit' to end the game.");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                gameEnd = true;
                System.out.println("Game ended.");
            } else {
                playTurn();
            }
        }

        scanner.close();
    }

    /**
     * Executes a single turn of the game for the current player.
     */
    public void playTurn() {
        // Step 1: Retrieve the current player
        Player currentPlayer = players.get(currentPlayerIndex);

        // Step 2: Roll the dice
        int diceRoll = dice.roll();

        // Step 3: Print the dice roll result
        System.out.println(currentPlayer.getName() + " rolled a " + diceRoll);

        // Step 4: Get movable pawns
        List<Pawn> movablePawns = currentPlayer.getMovablePawns(diceRoll);

        // Step 5: Check if there are no movable pawns
        if (movablePawns.isEmpty()) {
            System.out.println("No movable pawns. Turn skipped!");
            currentPlayerIndex = (currentPlayerIndex + 1) % GameConfig.NUM_PLAYERS;
            return;
        }

        // Step 6: Select the first movable pawn
        Pawn selectedPawn = movablePawns.get(0);
        int currentPosition = selectedPawn.getPosition();
        int newPosition = currentPosition + diceRoll;
        int pawnId = selectedPawn.getPawnId();

        boolean isAtHome = newPosition == Board.HOME_POSITION;
        boolean isValidPosition = board.isValidPosition(newPosition);

        // Step 7: Check if the new position is valid
        if (isValidPosition) {
            selectedPawn.setPosition(newPosition);
            System.out.println("Moved pawn " + pawnId + " to position " + newPosition);

            // Step 8: Check if the new position is a snake or ladder position
            board.handleSnakeOrLadderPosition(selectedPawn);

            // Step 9: Check if the current player has won
            if (isAtHome && currentPlayer.hasWon()) {
                System.out.println(currentPlayer.getName() + " wins!");
                gameEnd = true;
                scoreManager.updateScoreOnPawnReachHome(currentPlayer);
                return;
            }

            // Step 10: Check if the new position is occupied by an opponent player
            boolean isOccupiedByOpponent = board.isOccupiedByOtherPlayer(newPosition, currentPlayer.getPlayerId(), players);
            boolean isSafePosition = board.isSafePosition(newPosition);

            if (isOccupiedByOpponent && !(isSafePosition || isAtHome)) {
                // Step 11: Capture opponent's pawns
                Player opponentPlayer = board.getOpponentPlayerAtPosition(newPosition, currentPlayer, players);
                currentPlayer.captureOpponentPawns(opponentPlayer, currentPlayer, newPosition, scoreManager);
            }

            // Step 12: Perform additional logic for special positions, capturing opponents' pawns, etc.

        } else {
            System.out.println("Invalid move. Try again!");
        }

        // Step 13: Check if the current player reached home
        if (isAtHome) {
            scoreManager.updateScoreOnPawnReachHome(currentPlayer);
            System.out.println(currentPlayer.getName() + " reached home but hasn't won yet.");
        }

        // Step 14: Print players' pawn positions
        printPlayersPawnsPositions();

        // Step 15: Move to the next player
        currentPlayerIndex = (currentPlayerIndex + 1) % GameConfig.NUM_PLAYERS;
    }

    /**
     * Ends the game by calculating player scores and determining the winner.
     */
    public void endGame() {
        calculatePlayerScores();
    }

    /**
     * Calculates and displays the scores of all players.
     */
    private void calculatePlayerScores() {
        for (Player player : players) {
            ScoreManager.displayScore(player);
        }
    }

    /**
     * Prints the winner of the game or a tie message if there is no winner.
     *
     * @param winner the player who has won the game
     */
    private void printWinner(Player winner) {
        System.out.println("Game over.");
        if (winner != null) {
            System.out.println("The winner is: " + winner.getName());
        } else {
            System.out.println("No winner. It's a tie!");
        }
    }

    /**
     * Determines the winner of the game based on the players' scores.
     *
     * @return the player who has the highest score, or null if it's a tie
     */
    private Player determineWinner() {
        Player winner = null;
        int maxScore = Integer.MIN_VALUE;

        for (Player player : players) {
            int score = player.getScore();
            if (score > maxScore) {
                maxScore = score;
                winner = player;
            } else if (score == maxScore) {
                winner = null; // It's a tie
            }
        }

        return winner;
    }

    /**
     * Prints the positions of each player's pawns on the board.
     */
    public void printPlayersPawnsPositions() {
        for (Player player : players) {
            System.out.println(player.getName() + "'s Pawn Positions:");

            List<Pawn> pawns = player.getPawns();
            for (Pawn pawn : pawns) {
                String positionInfo = "Pawn ID: " + pawn.getPawnId() + ", Position: " + pawn.getPosition();
                if (board.isSafePosition(pawn.getPosition())) {
                    positionInfo += " (Safe)";
                }
                System.out.println(positionInfo);
            }

            System.out.println();
        }
    }

    /**
     * Prints the scores of each player.
     */
    public void printPlayersScores() {
        for (Player player : players) {
            System.out.println(player.getName() + "'s score: " + player.getScore());
        }
    }
}
