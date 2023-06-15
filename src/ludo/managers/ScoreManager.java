package ludo.managers;

import ludo.Player;

/**
 * The ScoreManager class is responsible for managing the scoring system in the Ludo-Ladders game.
 */
public class ScoreManager {
    private static final int SCORE_ON_PAWN_REACH_HOME = 10;
    private static final int SCORE_ON_PAWN_CAPTURE = 5;
    private static final int SCORE_ON_PAWN_CAPTURED = -2;

    /**
     * Updates the score for a player when their pawn reaches the home position.
     *
     * @param player the player whose score needs to be updated
     */
    public void updateScoreOnPawnReachHome(Player player) {
        player.incrementScore(SCORE_ON_PAWN_REACH_HOME);
    }

    /**
     * Updates the score for a player when they capture an opponent's pawn.
     *
     * @param player the player whose score needs to be updated
     */
    public void updateScoreOnPawnCapture(Player player) {
        player.incrementScore(SCORE_ON_PAWN_CAPTURE);
    }

    /**
     * Updates the score for a player when their pawn is captured by an opponent.
     *
     * @param player the player whose score needs to be updated
     */
    public void updateScoreOnPawnCaptured(Player player) {
        player.incrementScore(SCORE_ON_PAWN_CAPTURED);
    }

    /**
     * Displays the score of a player.
     *
     * @param player the player whose score needs to be displayed
     */
    public static void displayScore(Player player) {
        System.out.println(player.getName() + " - " + player.getScore());
    }
}
