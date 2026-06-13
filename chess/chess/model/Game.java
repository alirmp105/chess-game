package chess.model;

import chess.utils.ChessNotation;
import chess.utils.ConsoleColors;
import chess.model.piece.Piece;

import java.util.Scanner;

public class Game {
    private final Board board;
    private Piece.Color currentTurn;
    private final Scanner scanner;
    private final TimerUtil timer;

    public Game() {
        board = new Board();
        currentTurn = Piece.Color.WHITE;
        scanner = new Scanner(System.in);
        timer = new TimerUtil();
    }

    public void start() {
        System.out.println("Chess Game - Phase 2");
        System.out.println("Input format: e2-e4  or  e2 e4");
        System.out.println("Castling: e1-g1 (short) or e1-c1 (long)");
        System.out.println("Type 'quit' to exit.\n");

        while (true) {
            board.display();

            boolean inCheck = board.isInCheck(currentTurn);
            boolean hasLegal = board.hasAnyLegalMove(currentTurn);

            if (!hasLegal) {
                if (inCheck) {
                    String winner = (currentTurn == Piece.Color.WHITE) ? "Black" : "White";
                    System.out.println(ConsoleColors.YELLOW_BOLD +
                        "*** CHECKMATE! " + winner + " wins! ***" + ConsoleColors.RESET);
                } else {
                    System.out.println(ConsoleColors.YELLOW_BOLD +
                        "*** STALEMATE! The game is a draw. ***" + ConsoleColors.RESET);
                }
                break;
            }

            if (inCheck) {
                System.out.println(ConsoleColors.YELLOW +
                    "⚠  " + (currentTurn == Piece.Color.WHITE ? "White" : "Black") +
                    " is in CHECK!" + ConsoleColors.RESET);
            }

            System.out.println("--- " + timer.getTimeDisplay() + " ---");
            System.out.print((currentTurn == Piece.Color.WHITE ? "White" : "Black") + "'s turn: ");

            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Game ended.");
                break;
            }

            if (!processMove(input)) continue;

            long thinking = timer.endTurn(currentTurn);
            System.out.println((currentTurn == Piece.Color.WHITE ? "White" : "Black") +
                               " thought for " + thinking + " seconds.");

            currentTurn = (currentTurn == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        }
        scanner.close();
    }

    private boolean processMove(String input) {
        String normalized = input.replace('-', ' ');
        String[] parts = normalized.split("\\s+");
        if (parts.length != 2) {
            System.out.println(ConsoleColors.YELLOW + "Error: Wrong format. Example: e2 e4" + ConsoleColors.RESET);
            return false;
        }
        int[] from = ChessNotation.parseSquare(parts[0]);
        int[] to   = ChessNotation.parseSquare(parts[1]);
        if (from == null || to == null) {
            System.out.println(ConsoleColors.YELLOW + "Error: Invalid coordinates." + ConsoleColors.RESET);
            return false;
        }
        return board.movePiece(from[0], from[1], to[0], to[1], currentTurn, scanner);
    }
}
