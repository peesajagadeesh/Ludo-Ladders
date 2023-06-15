/**
 * The Pawn class represents a pawn in the game of Ludo.
 */
package ludo;

public class Pawn {
    private int playerId;
    private int position;
    private int pawnId;

    /**
     * Constructs a new Pawn object with the specified player ID and pawn ID.
     *
     * @param playerId the ID of the player the pawn belongs to
     * @param pawnId   the ID of the pawn
     */
    public Pawn(int playerId, int pawnId) {
        this.playerId = playerId;
        this.position = 0; // 0 indicates that the pawn is not on the board
        this.pawnId = pawnId;
    }

    /**
     * Returns the ID of the player the pawn belongs to.
     *
     * @return the player ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns the ID of the pawn.
     *
     * @return the pawn ID
     */
    public int getPawnId() {
        return pawnId;
    }

    /**
     * Returns the current position of the pawn on the board.
     *
     * @return the current position of the pawn
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the pawn on the board.
     *
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }
}
