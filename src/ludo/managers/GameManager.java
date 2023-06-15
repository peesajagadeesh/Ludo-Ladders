package ludo.managers;

import ludo.Board;
import ludo.Dice;
import ludo.Interfaces.BoardInterface;
import ludo.Interfaces.DiceInterface;
import ludo.Pawn;
import ludo.Player;
import ludo.config.GameConfig;

import java.util.List;
import java.util.Scanner;

public class GameManager {
    private List<Player> players;
    private int currentPlayerIndex;
    private final Dice dice;
    private final Board board;
    private boolean gameEnd;

    private ScoreManager scoreManager;

    public GameManager(List<Player> players, Dice dice, Board board, ScoreManager scoreManager) {
        this.players = players;
        this.dice = dice;
        this.board = board;
        this.gameEnd = false;
        this.currentPlayerIndex = 0;
        this.scoreManager = scoreManager;
    }

    public void startGame() {
        // Game initialization logic
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
            board.handleSnakeOrLadderPosition(selectedPawn);


            if (isAtHome && currentPlayer.hasWon()) {
                System.out.println(playerName + " wins!");
                gameEnd = true;
                scoreManager.updateScoreOnPawnReachHome(currentPlayer);
                return;
            }

            boolean isOccupiedByOpponent = board.isOccupiedByOtherPlayer(newPosition, currentPlayer.getPlayerId(), players);
            boolean isSafePosition = board.isSafePosition(newPosition);

            if (isOccupiedByOpponent && !(isSafePosition || isAtHome)) {
                Player opponentPlayer = board.getOpponentPlayerAtPosition(newPosition, currentPlayer, players);
                currentPlayer.captureOpponentPawns(opponentPlayer, currentPlayer, newPosition, scoreManager);
            }

            // Perform additional logic for special positions, capturing opponents' pawns, etc.
        } else {
            System.out.println("Invalid move. Try again!");
        }

        if (isAtHome) {
            scoreManager.updateScoreOnPawnReachHome(currentPlayer);
            System.out.println(playerName + " reached home but hasn't won yet.");
        }
        printPlayersPawnsPositions();
        currentPlayerIndex = (currentPlayerIndex + 1) % GameConfig.NUM_PLAYERS; // Move to the next player
    }


    public void endGame() {
        // Score calculation and winner determination
        calculatePlayerScores();
    }

    private void calculatePlayerScores() {
        for (Player player : players) {
            ScoreManager.displayScore(player);
        }
    }


    private void printWinner(Player winner) {
        System.out.println("Game over.");
        if (winner != null) {
            System.out.println("The winner is: " + winner.getName());
        } else {
            System.out.println("No winner. It's a tie!");
        }
    }

    private Player determineWinner() {
        Player winner = null;
        int maxScore = Integer.MIN_VALUE;

        for (Player player : players) {
            int score = player.getScore();
            if (score > maxScore) {
                maxScore = score;
                winner = player;
            } else if (score == maxScore) {
                // It's a tie
                winner = null;
            }
        }

        return winner;
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

    public void printPlayersScores() {
        for (Player player : players) {
            System.out.println(player.getName() + "'s score: " + player.getScore());
        }
    }

    // Other methods and helpers related to game management
}
