package ludo.Interfaces;

import ludo.Pawn;

public interface BoardInterface {
    boolean isValidPosition(int position);

    void handleSnakeOrLadderPosition(Pawn pawn);
    // Other methods...
}