package ludo.managers;

import ludo.Player;

public class ScoreManager {
    private static final int SCORE_ON_PAWN_REACH_HOME = 10;
    private static final int SCORE_ON_PAWN_CAPTURE = 5;
    private static final int SCORE_ON_PAWN_CAPTURED = -2;

    public void updateScoreOnPawnReachHome(Player player) {
        player.incrementScore(SCORE_ON_PAWN_REACH_HOME);
    }

    public void updateScoreOnPawnCapture(Player player) {
        player.incrementScore(SCORE_ON_PAWN_CAPTURE);
    }

    public void updateScoreOnPawnCaptured(Player player) {
        player.incrementScore(SCORE_ON_PAWN_CAPTURED);
    }
    public static void displayScore(Player player){
        System.out.println(player.getName() +" - "+ player.getScore());
    }
}
