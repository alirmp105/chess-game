package chess.model;

import chess.model.piece.*;
import chess.utils.ConsoleColors;

public class Board {
    private final Piece[][] squares = new Piece[8][8];

    public Board() {
        setupBoard();
    }

    private void setupBoard() {
        squares[0][0] = new Rook(Piece.Color.BLACK);
        squares[0][1] = new Knight(Piece.Color.BLACK);
        squares[0][2] = new Bishop(Piece.Color.BLACK);
        squares[0][3] = new Queen(Piece.Color.BLACK);
        squares[0][4] = new King(Piece.Color.BLACK);
        squares[0][5] = new Bishop(Piece.Color.BLACK);
        squares[0][6] = new Knight(Piece.Color.BLACK);
        squares[0][7] = new Rook(Piece.Color.BLACK);
        for (int c = 0; c < 8; c++) {
            squares[1][c] = new Pawn(Piece.Color.BLACK);
        }

        squares[7][0] = new Rook(Piece.Color.WHITE);
        squares[7][1] = new Knight(Piece.Color.WHITE);
        squares[7][2] = new Bishop(Piece.Color.WHITE);
        squares[7][3] = new Queen(Piece.Color.WHITE);
        squares[7][4] = new King(Piece.Color.WHITE);
        squares[7][5] = new Bishop(Piece.Color.WHITE);
        squares[7][6] = new Knight(Piece.Color.WHITE);
        squares[7][7] = new Rook(Piece.Color.WHITE);
        for (int c = 0; c < 8; c++) {
            squares[6][c] = new Pawn(Piece.Color.WHITE);
        }
    }

    public Piece getPiece(int row, int col) {
        return squares[row][col];
    }

    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol, Piece.Color currentTurn) {
        Piece piece = getPiece(fromRow, fromCol);
        if (piece == null) {
            System.out.println(ConsoleColors.YELLOW + "Error: Source square is empty!" + ConsoleColors.RESET);
            return false;
        }
        if (piece.getColor() != currentTurn) {
            System.out.println(ConsoleColors.YELLOW + "Error: It's " + (currentTurn == Piece.Color.WHITE ? "White" : "Black") + "'s turn!" + ConsoleColors.RESET);
            return false;
        }
        Piece target = getPiece(toRow, toCol);
        if (target != null && target.getColor() == piece.getColor()) {
            System.out.println(ConsoleColors.YELLOW + "Error: Cannot capture your own piece!" + ConsoleColors.RESET);
            return false;
        }
        if (!piece.isValidMove(this, fromRow, fromCol, toRow, toCol)) {
            System.out.println(ConsoleColors.YELLOW + "Error: Illegal move!" + ConsoleColors.RESET);
            return false;
        }

        squares[toRow][toCol] = piece;
        squares[fromRow][fromCol] = null;
        return true;
    }

    public void display() {
        System.out.print("     ");
        for (char c = 'A'; c <= 'H'; c++) {
            System.out.print(ConsoleColors.coordText() + c + "   " + ConsoleColors.RESET);
        }
        System.out.println();

        System.out.print("    ");
        System.out.println(ConsoleColors.coordText() + "--------------------------------" + ConsoleColors.RESET);

        for (int row = 0; row < 8; row++) {
            System.out.print(" " + ConsoleColors.coordText() + (8 - row) + ConsoleColors.RESET + " ");
            System.out.print(ConsoleColors.coordText() + "|" + ConsoleColors.RESET);

            for (int col = 0; col < 8; col++) {
                Piece p = squares[row][col];
                boolean isLightSquare = (row + col) % 2 == 0;
                String bg = isLightSquare ? ConsoleColors.lightSquareBg() : ConsoleColors.darkSquareBg();

                if (p == null) {
                    System.out.print(bg + "   " + ConsoleColors.RESET);
                } else {
                    String pieceColor = (p.getColor() == Piece.Color.WHITE)
                            ? ConsoleColors.whitePiece()
                            : ConsoleColors.blackPiece();
                    System.out.print(bg + pieceColor + " " + p.getSymbol() + " " + ConsoleColors.RESET);
                }
                System.out.print(ConsoleColors.coordText() + "|" + ConsoleColors.RESET);
            }

            System.out.println(" " + ConsoleColors.coordText() + (8 - row) + ConsoleColors.RESET);

            if (row < 7) {
                System.out.print("    ");
                System.out.println(ConsoleColors.coordText() + "--------------------------------" + ConsoleColors.RESET);
            }
        }

        System.out.print("    ");
        System.out.println(ConsoleColors.coordText() + "--------------------------------" + ConsoleColors.RESET);

        System.out.print("     ");
        for (char c = 'A'; c <= 'H'; c++) {
            System.out.print(ConsoleColors.coordText() + c + "   " + ConsoleColors.RESET);
        }
        System.out.println();
    }
}