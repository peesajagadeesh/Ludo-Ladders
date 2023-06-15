import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private List<Pawn> pawns;
    private int score;


    public Player(String name) {
        this.name = name;
        pawns = new ArrayList<>();
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public void addPawn(Pawn pawn) {
        pawns.add(pawn);
    }

    public int getPlayerId() {
        return name.hashCode(); // Generate a unique ID for each player
    }

    public List<Pawn> getMovablePawns(int diceRoll) {
        List<Pawn> movablePawns = new ArrayList<>();
        for (Pawn pawn : pawns) {
            int currentPosition = pawn.getPosition();
            int newPosition = currentPosition + diceRoll;
            if (newPosition <= Board.BOARD_SIZE) {
                movablePawns.add(pawn);
            }
        }
        return movablePawns;
    }
    public boolean hasWon() {
        for (Pawn pawn : pawns) {
            if (pawn.getPosition() != Board.HOME_POSITION) {
                return false;
            }
        }
        return true;
    }
    public void resetPawn(Pawn pawn) {
        pawn.setPosition(0);
        pawns.remove(pawn);
    }
    public void captureOpponentPawns(Player opponentPlayer, int position) {
        List<Pawn> capturedPawns = opponentPlayer.getPawnsAtPosition(position);
        for (Pawn capturedPawn : capturedPawns) {
            resetPawn(capturedPawn);
            System.out.println("Captured " + opponentPlayer.getName() + "'s pawn at position " + position);
        }
    }

    public List<Pawn> getPawnsAtPosition(int position) {
        List<Pawn> pawnsAtPosition = new ArrayList<>();
        for (Pawn pawn : pawns) {
            if (pawn.getPosition() == position) {
                pawnsAtPosition.add(pawn);
            }
        }
        return pawnsAtPosition;
    }

    public void updateScoreOnPawnReachHome() {
        score += 10;
    }

    public void updateScoreOnPawnCapture() {
        score += 5;
    }

    public void updateScoreOnPawnCaptured() {
        score -= 2;
    }
}
