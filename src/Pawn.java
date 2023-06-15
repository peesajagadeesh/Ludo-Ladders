public class Pawn {
    private int playerId;
    private int position;

    private int pawnId;

    public Pawn(int playerId, int pawnId) {
        this.playerId = playerId;
        this.position = 0; // 0 indicates that the pawn is not on the board
        this.pawnId = pawnId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getPawnId() {
        return pawnId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
