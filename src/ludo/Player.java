package ludo;

import ludo.managers.PawnManager;
import ludo.managers.ScoreManager;

import java.util.ArrayList;
import java.util.List;


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

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void addPawn(Pawn pawn) {
        pawns.add(pawn);
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public int getPlayerId() {
        return name.hashCode(); // Generate a unique ID for each player
    }

    public void incrementScore(int points) {
        score += points;
    }


    //==========================
    public List<Pawn> getMovablePawns(int diceRoll) {
        return pawnManager.getMovablePawns(pawns, diceRoll);
    }

    public boolean hasWon() {
        return pawnManager.allPawnsReachedHome(pawns);
    }

    public void captureOpponentPawns(Player opponentPlayer, Player currentPlayer,  int position, ScoreManager scoreManager) {
        List<Pawn> capturedPawns = opponentPlayer.getPawnsAtPosition(position);
        for (Pawn capturedPawn : capturedPawns) {
            pawnManager.resetPawn(capturedPawn);
            scoreManager.updateScoreOnPawnCaptured(opponentPlayer);
            scoreManager.updateScoreOnPawnCapture(currentPlayer);
            System.out.println("Captured " + opponentPlayer.getName() + "'s pawn at position " + position);
        }
    }

    public List<Pawn> getPawnsAtPosition(int position) {
        return pawnManager.getPawnsAtPosition(pawns, position);
    }


}
