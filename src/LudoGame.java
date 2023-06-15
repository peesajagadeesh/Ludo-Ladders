
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LudoGame {


    private List<Player> players;
    private int currentPlayerIndex;
    private Dice dice;
    private Board board;
    private boolean gameEnd;


    public LudoGame() {
        players = new ArrayList<>();
        currentPlayerIndex = 0;
        dice = new Dice(GameConfig.NUM_DICE_SIDES);
        board = new Board(board.BOARD_SIZE, players);
        gameEnd = false;
        initializePlayers();
    }

    private void initializePlayers() {
        for (int i = 0; i < GameConfig.NUM_PLAYERS; i++) {
            Player player = new Player("Player " + (i + 1));
            players.add(player);
            initializePawns(player);
        }
    }

    private void initializePawns(Player player) {
        for (int i = 0; i < GameConfig.NUM_PAWNS; i++) {
            Pawn pawn = new Pawn(player.getPlayerId(), i + 1);
            player.addPawn(pawn);
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ludo Game started!");

        while (!gameEnd) {
            System.out.println("Press Enter to play a turn or type 'exit' to end the game.");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                gameEnd = true;
                System.out.println("Game ended.");
            } else {
                playTurn();
            }
        }

        scanner.close();
    }

    public void playTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        int diceRoll = dice.roll();
        String playerName = currentPlayer.getName();
        System.out.println(currentPlayer.getName() + " rolled a " + diceRoll);

        List<Pawn> movablePawns = currentPlayer.getMovablePawns(diceRoll);

        if (movablePawns.isEmpty()) {
            System.out.println("No movable pawns. Turn skipped!");
            currentPlayerIndex = (currentPlayerIndex + 1) % GameConfig.NUM_PLAYERS; // Move to the next player
            return;
        }

        Pawn selectedPawn = movablePawns.get(0);
        int currentPosition = selectedPawn.getPosition();
        int newPosition = currentPosition + diceRoll;
        int pawnId = selectedPawn.getPawnId();

        boolean isAtHome = newPosition == Board.HOME_POSITION;
        boolean isValidPosition = board.isValidPosition(newPosition);

        if (isValidPosition) {
            selectedPawn.setPosition(newPosition);
            System.out.println("Moved pawn " + pawnId + " to position " + newPosition);

            // Check if the new position is a snake or ladder position
            handleSnakeOrLadderPosition(selectedPawn);


            if (isAtHome && currentPlayer.hasWon()) {
                System.out.println(playerName + " wins!");
                gameEnd = true;
                return;
            }

            boolean isOccupiedByOpponent = board.isOccupiedByOtherPlayer(newPosition, currentPlayer.getPlayerId());
            boolean isSafePosition = board.isSafePosition(newPosition);

            if (isOccupiedByOpponent && !(isSafePosition || isAtHome)) {
                Player opponentPlayer = board.getOpponentPlayerAtPosition(newPosition, currentPlayer);
                currentPlayer.captureOpponentPawns(opponentPlayer, newPosition);
            }

            // Perform additional logic for special positions, capturing opponents' pawns, etc.
        } else {
            System.out.println("Invalid move. Try again!");
        }

        if (isAtHome) {
            System.out.println(playerName + " reached home but hasn't won yet.");
        }
        printPlayersPawnsPositions();
        currentPlayerIndex = (currentPlayerIndex + 1) % GameConfig.NUM_PLAYERS; // Move to the next player
    }

    public void printPlayersPawnsPositions() {
        for (Player player : players) {
            System.out.println(player.getName() + "'s Pawn Positions:");

            List<Pawn> pawns = player.getPawns();
            for (Pawn pawn : pawns) {
                String positionInfo = "Pawn ID: " + pawn.getPawnId() + ", Position: " + pawn.getPosition();
                if (board.isSafePosition(pawn.getPosition())) {
                    positionInfo += " (Safe)";
                }
                System.out.println(positionInfo);
            }

            System.out.println();
        }
    }

    private void handleSnakeOrLadderPosition(Pawn pawn) {
        int newPosition = pawn.getPosition();

        // Check if the new position is a snake position
        int tailPosition = board.getTailPositionForSnake(newPosition);
        if (tailPosition != -1) {
            pawn.setPosition(tailPosition);
            System.out.println("Pawn " + pawn.getPawnId() + " encountered a snake! Moved to position " + tailPosition);
            return;
        }

        // Check if the new position is a ladder position
        int topPosition = board.getTopPositionForLadder(newPosition);
        if (topPosition != -1) {
            if (topPosition <= Board.HOME_POSITION) {
                pawn.setPosition(topPosition);
                System.out.println("Pawn " + pawn.getPawnId() + " climbed a ladder! Moved to position " + topPosition);
                return;
            }
        }
    }


}
