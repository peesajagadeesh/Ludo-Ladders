import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    public static final int BOARD_SIZE = 20;
    public static final int HOME_POSITION = 20;
    private int size;
    private List<Integer> safePositions;
    private List<Player> players;

    private Map<Integer, Integer> snakePositions;   // Map head position to tail position
    private Map<Integer, Integer> ladderPositions;  // Map bottom position to top position


    public Board(int size, List<Player> players) {
        this.size = size;
        this.safePositions = new ArrayList<>();
        initializeSafePositions();
        this.players = players;
        this.snakePositions = new HashMap<>();
        this.ladderPositions = new HashMap<>();
        initializeSnakesAndLadders();
    }


    private void initializeSafePositions() {
        // Add safe positions to the list
        safePositions.add(8);
        safePositions.add(15);
        safePositions.add(18);
    }

    private void initializeSnakesAndLadders() {
        // Add snake positions
        snakePositions.put(17, 7);
        snakePositions.put(19, 2);
        // ...

        // Add ladder positions
        ladderPositions.put(4, 14);
        ladderPositions.put(9, 12);
        // ...
    }

    public int getTailPositionForSnake(int headPosition) {
        return snakePositions.getOrDefault(headPosition, -1);
    }

    public int getTopPositionForLadder(int bottomPosition) {
        return ladderPositions.getOrDefault(bottomPosition, -1);
    }

    public boolean isValidPosition(int position) {
        return position > 0 && position <= size;
    }

    public boolean isSafePosition(int position) {
        return safePositions.contains(position);
    }

    public boolean isOccupiedByOtherPlayer(int position, int currentPlayerId) {
        for (Player player : players) {
            if (player.getPlayerId() != currentPlayerId) {
                List<Pawn> pawns = player.getPawnsAtPosition(position);
                if (!pawns.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Player getOpponentPlayerAtPosition(int position, Player currentPlayer) {
        for (Player player : players) {
            if (player != currentPlayer) {
                List<Pawn> pawns = player.getPawns();
                for (Pawn pawn : pawns) {
                    if (pawn.getPosition() == position) {
                        return player;
                    }
                }
            }
        }
        return null;
    }
}