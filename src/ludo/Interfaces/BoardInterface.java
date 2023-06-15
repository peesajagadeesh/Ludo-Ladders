/**
 * The BoardInterface interface represents the functionality of a Ludo board.
 * It defines methods for validating positions and handling snake or ladder positions.
 */
package ludo.Interfaces;

import ludo.Pawn;

public interface BoardInterface {
    /**
     * Checks if a given position on the board is valid.
     *
     * @param position the position to validate
     * @return true if the position is valid, false otherwise
     */
    boolean isValidPosition(int position);

    /**
     * Handles the movement of a pawn when it lands on a snake or ladder position.
     *
     * @param pawn the pawn that landed on a snake or ladder position
     */
    void handleSnakeOrLadderPosition(Pawn pawn);

}