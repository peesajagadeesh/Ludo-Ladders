package ludo;

import ludo.Interfaces.BoardInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Board class represents the game board for the Ludo-Ladders game. It manages the size of the board, safe positions,
 * snake positions, and ladder positions. The class provides methods to interact with the board and handle snake and
 * ladder movements.
 */
public class Board implements BoardInterface {
    public static final int BOARD_SIZE = 20;
    public static final int HOME_POSITION = 20;
    private int size;
    private List<Integer> safePositions;

    private Map<Integer, Integer> snakePositions;   // Map head position to tail position
    private Map<Integer, Integer> ladderPositions;  // Map bottom position to top position


    /**
     * Constructs a Board object with the specified size.
     *
     * @param size the size of the board
     */
    public Board(int size) {
        this.size = size;
        this.safePositions = new ArrayList<>();
        this.snakePositions = new HashMap<>();
        this.ladderPositions = new HashMap<>();
        initializeSafePositions();
        initializeSnakesAndLadders();
    }


    /**
     * Initializes the safe positions on the board.
     */
    private void initializeSafePositions() {
        // Add safe positions to the list
        safePositions.add(8);
        safePositions.add(15);
        safePositions.add(18);
    }

    /**
     * Initializes the snake and ladder positions on the board.
     */
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

    /**
     * Retrieves the tail position of a snake given its head position.
     *
     * @param headPosition the head position of the snake
     * @return the tail position of the snake, or -1 if not found
     */
    public int getTailPositionForSnake(int headPosition) {
        return snakePositions.getOrDefault(headPosition, -1);
    }

    /**
     * Retrieves the top position of a ladder given its bottom position.
     *
     * @param bottomPosition the bottom position of the ladder
     * @return the top position of the ladder, or -1 if not found
     */
    public int getTopPositionForLadder(int bottomPosition) {
        return ladderPositions.getOrDefault(bottomPosition, -1);
    }

    /**
     * Checks if a given position is a valid position on the board.
     *
     * @param position the position to check
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int position) {
        return position > 0 && position <= size;
    }

    /**
     * Checks if a given position is a safe position on the board.
     *
     * @param position the position to check
     * @return true if the position is safe, false otherwise
     */
    public boolean isSafePosition(int position) {
        return safePositions.contains(position);
    }

    /**
     * Checks if a given position is occupied by another player's pawn.
     *
     * @param position       the position to check
     * @param currentPlayerId the ID of the current player
     * @param players        the list of all players
     * @return true if the position is occupied by another player's pawn, false otherwise
     */
    public boolean isOccupiedByOtherPlayer(int position, int currentPlayerId, List<Player> players) {
        for (Player player : players) {
            if (player.getPlayerId() != currentPlayerId && player.getPawnsAtPosition(position).size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the opponent player at a given position.
     *
     * @param position       the position to check
     * @param currentPlayer  the current player
     * @param players        the list of all players
     * @return the opponent player at the position, or null if not found
     */
    public Player getOpponentPlayerAtPosition(int position, Player currentPlayer, List<Player> players) {
        for (Player player : players) {
            if (player != currentPlayer && player.getPawnsAtPosition(position).size() > 0) {
                return player;
            }
        }
        return null;
    }

    /**
     * Handles the movement of a pawn when it encounters a snake or ladder position.
     *
     * @param pawn the pawn to handle
     */
    public void handleSnakeOrLadderPosition(Pawn pawn) {
        int newPosition = pawn.getPosition();

        // Check if the new position is a snake position
        int tailPosition = getTailPositionForSnake(newPosition);
        if (tailPosition != -1) {
            pawn.setPosition(tailPosition);
            System.out.println("Pawn " + pawn.getPawnId() + " encountered a snake! Moved to position " + tailPosition);
            return;
        }

        // Check if the new position is a ladder position
        int topPosition = getTopPositionForLadder(newPosition);
        if (topPosition != -1) {
            if (topPosition <= Board.HOME_POSITION) {
                pawn.setPosition(topPosition);
                System.out.println("Pawn " + pawn.getPawnId() + " climbed a ladder! Moved to position " + topPosition);
            }
        }
    }
}
