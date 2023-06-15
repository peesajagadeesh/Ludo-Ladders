package ludo.managers;

import ludo.Board;
import ludo.Pawn;

import java.util.ArrayList;
import java.util.List;

public class PawnManager {
    public List<Pawn> getMovablePawns(List<Pawn> pawns, int diceRoll) {
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

    public boolean allPawnsReachedHome(List<Pawn> pawns) {
        for (Pawn pawn : pawns) {
            if (pawn.getPosition() != Board.HOME_POSITION) {
                return false;
            }
        }
        return true;
    }

    public void resetPawn(Pawn pawn) {
        pawn.setPosition(0);
    }

    public List<Pawn> getPawnsAtPosition(List<Pawn> pawns, int position) {
        List<Pawn> pawnsAtPosition = new ArrayList<>();
        for (Pawn pawn : pawns) {
            if (pawn.getPosition() == position) {
                pawnsAtPosition.add(pawn);
            }
        }
        return pawnsAtPosition;
    }
}
