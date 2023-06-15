package ludo;

import ludo.managers.PawnManager;
import ludo.managers.ScoreManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The Player class represents a player in the Ludo-Ladders game. It manages the player's name, pawns, score, and
 * provides methods for interacting with the pawns and score.
 */
public class Player {
    private String name;
    private List<Pawn> pawns;
    private int score;
    private PawnManager pawnManager;

    public Player(String name) {
        this.name = name;
        pawns = new ArrayList<>();
        pawnManager = new PawnManager();
    }

    /**
     * Returns the player's score.
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the player's name.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a pawn to the player's list of pawns.
     * @param pawn
     */
    public void addPawn(Pawn pawn) {
        pawns.add(pawn);
    }

    /**
     * Returns the list of pawns owned by the player
     * @return
     */
    public List<Pawn> getPawns() {
        return pawns;
    }

    /**
     * Generates and returns a unique ID for the player based on the player's name.
     * @return
     */
    public int getPlayerId() {
        return name.hashCode(); // Generate a unique ID for each player
    }

    /**
     * Increments the player's score by the specified number of points.
     * @param points
     */
    public void incrementScore(int points) {
        score += points;
    }

    /**
     * Retrieves the list of pawns that can be moved by the player based on the given dice roll. Delegates to the
     * pawnManager.getMovablePawns() method.
     * @param diceRoll
     * @return
     */
    public List<Pawn> getMovablePawns(int diceRoll) {
        return pawnManager.getMovablePawns(pawns, diceRoll);
    }

    /**
     * Checks if all the player's pawns have reached home. Returns true if all pawns have reached home, false
     * otherwise. Delegates to the pawnManager.allPawnsReachedHome() method
     * @return
     */
    public boolean hasWon() {
        return pawnManager.allPawnsReachedHome(pawns);
    }

    /**
     * Captures the opponent player's pawns at a given position. Resets the captured pawns and updates the scores for
     * both the opponent player and the current player. Prints a message indicating the captured pawn. Delegates to the
     * pawnManager.resetPawn(), scoreManager.updateScoreOnPawnCaptured(), and scoreManager.updateScoreOnPawnCapture()
     * methods.
     * @param opponentPlayer
     * @param currentPlayer
     * @param position
     * @param scoreManager
     */
    public void captureOpponentPawns(Player opponentPlayer, Player currentPlayer,  int position, ScoreManager scoreManager) {
        List<Pawn> capturedPawns = opponentPlayer.getPawnsAtPosition(position);
        for (Pawn capturedPawn : capturedPawns) {
            pawnManager.resetPawn(capturedPawn);
            scoreManager.updateScoreOnPawnCaptured(opponentPlayer);
            scoreManager.updateScoreOnPawnCapture(currentPlayer);
            System.out.println("Captured " + opponentPlayer.getName() + "'s pawn at position " + position);
        }
    }

    /**
     *  Retrieves the list of pawns owned by the player that are at the specified position.
     *  Delegates to the pawnManager.getPawnsAtPosition() method.
     * @param position
     * @return
     */
    public List<Pawn> getPawnsAtPosition(int position) {
        return pawnManager.getPawnsAtPosition(pawns, position);
    }


}
