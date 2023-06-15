package ludo.managers;

import ludo.Board;
import ludo.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 * The PawnManager class is responsible for managing the movement and status of pawns in the Ludo-Ladders game.
 */
public class PawnManager {
    /**
     * Retrieves the list of movable pawns based on the given dice roll.
     *
     * @param pawns     the list of pawns to check
     * @param diceRoll  the value rolled on the dice
     * @return the list of movable pawns
     */
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

    /**
     * Checks if all the pawns have reached the home position.
     *
     * @param pawns  the list of pawns to check
     * @return true if all pawns have reached the home position, false otherwise
     */
    public boolean allPawnsReachedHome(List<Pawn> pawns) {
        for (Pawn pawn : pawns) {
            if (pawn.getPosition() != Board.HOME_POSITION) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resets the position of a pawn to the starting position.
     *
     * @param pawn  the pawn to reset
     */
    public void resetPawn(Pawn pawn) {
        pawn.setPosition(0);
    }

    /**
     * Retrieves the pawns at a specific position on the board.
     *
     * @param pawns     the list of pawns to check
     * @param position  the position to check
     * @return the list of pawns at the specified position
     */
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
