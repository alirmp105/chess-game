package chess.model;

import chess.model.piece.*;
import chess.utils.ConsoleColors;

import java.util.Scanner;

public class Board {
    private final Piece[][] squares = new Piece[8][8];

    private int[] enPassantTarget = null;

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
        for (int c = 0; c < 8; c++) squares[1][c] = new Pawn(Piece.Color.BLACK);

        squares[7][0] = new Rook(Piece.Color.WHITE);
        squares[7][1] = new Knight(Piece.Color.WHITE);
        squares[7][2] = new Bishop(Piece.Color.WHITE);
        squares[7][3] = new Queen(Piece.Color.WHITE);
        squares[7][4] = new King(Piece.Color.WHITE);
        squares[7][5] = new Bishop(Piece.Color.WHITE);
        squares[7][6] = new Knight(Piece.Color.WHITE);
        squares[7][7] = new Rook(Piece.Color.WHITE);
        for (int c = 0; c < 8; c++) squares[6][c] = new Pawn(Piece.Color.WHITE);
    }

    public Piece getPiece(int row, int col) {
        if (row < 0 || row > 7 || col < 0 || col > 7) return null;
        return squares[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        squares[row][col] = piece;
    }

    public int[] getEnPassantTarget() { return enPassantTarget; }

    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol,
                             Piece.Color currentTurn, Scanner scanner) {
        Piece piece = getPiece(fromRow, fromCol);

        if (piece == null) {
            System.out.println(ConsoleColors.YELLOW + "Error: Source square is empty!" + ConsoleColors.RESET);
            return false;
        }
        if (piece.getColor() != currentTurn) {
            System.out.println(ConsoleColors.YELLOW + "Error: It's " +
                (currentTurn == Piece.Color.WHITE ? "White" : "Black") + "'s turn!" + ConsoleColors.RESET);
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

        if (moveLeavesKingInCheck(fromRow, fromCol, toRow, toCol, currentTurn)) {
            System.out.println(ConsoleColors.YELLOW + "Error: This move leaves your King in check!" + ConsoleColors.RESET);
            return false;
        }

        executeMove(fromRow, fromCol, toRow, toCol, piece, scanner);
        return true;
    }

    private void executeMove(int fromRow, int fromCol, int toRow, int toCol,
                             Piece piece, Scanner scanner) {

        if (piece.getType() == Piece.Type.PAWN && enPassantTarget != null
                && toRow == enPassantTarget[0] && toCol == enPassantTarget[1]) {
            squares[fromRow][toCol] = null;
        }

        enPassantTarget = null;
        if (piece.getType() == Piece.Type.PAWN) {
            int dRow = toRow - fromRow;
            if (Math.abs(dRow) == 2) {
                enPassantTarget = new int[]{fromRow + dRow / 2, fromCol};
            }
        }

        if (piece.getType() == Piece.Type.KING && Math.abs(toCol - fromCol) == 2) {
            if (toCol > fromCol) {
                squares[fromRow][5] = squares[fromRow][7];
                squares[fromRow][7] = null;
                if (squares[fromRow][5] != null) squares[fromRow][5].setMoved();
            } else {
                squares[fromRow][3] = squares[fromRow][0];
                squares[fromRow][0] = null;
                if (squares[fromRow][3] != null) squares[fromRow][3].setMoved();
            }
        }

        squares[toRow][toCol] = piece;
        squares[fromRow][fromCol] = null;
        piece.setMoved();

        if (piece.getType() == Piece.Type.PAWN) {
            int promotionRow = (piece.getColor() == Piece.Color.WHITE) ? 0 : 7;
            if (toRow == promotionRow) {
                Piece promoted = askPromotion(piece.getColor(), scanner);
                squares[toRow][toCol] = promoted;
                promoted.setMoved();
            }
        }
    }

    private Piece askPromotion(Piece.Color color, Scanner scanner) {
        System.out.println(ConsoleColors.CYAN +
            "Pawn Promotion! Choose: Q=Queen  R=Rook  B=Bishop  N=Knight" +
            ConsoleColors.RESET);
        while (true) {
            System.out.print("Your choice (Q/R/B/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "Q": return new Queen(color);
                case "R": return new Rook(color);
                case "B": return new Bishop(color);
                case "N": return new Knight(color);
                default:
                    System.out.println(ConsoleColors.YELLOW + "Invalid choice. Please enter Q, R, B or N." + ConsoleColors.RESET);
            }
        }
    }

    public boolean isInCheck(Piece.Color color) {
        int[] kingPos = findKing(color);
        if (kingPos == null) return false;
        return isSquareUnderAttack(kingPos[0], kingPos[1], color);
    }

    private int[] findKing(Piece.Color color) {
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++) {
                Piece p = squares[r][c];
                if (p != null && p.getType() == Piece.Type.KING && p.getColor() == color)
                    return new int[]{r, c};
            }
        return null;
    }

    public boolean isSquareUnderAttack(int row, int col, Piece.Color friendlyColor) {
        Piece.Color enemy = (friendlyColor == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++) {
                Piece p = squares[r][c];
                if (p != null && p.getColor() == enemy) {
                    if (p.getType() == Piece.Type.KING) {
                        int dRow = Math.abs(row - r);
                        int dCol = Math.abs(col - c);
                        if (dRow <= 1 && dCol <= 1 && !(dRow == 0 && dCol == 0)) return true;
                    } else if (p.isValidMove(this, r, c, row, col)) {
                        return true;
                    }
                }
            }
        return false;
    }

    private boolean moveLeavesKingInCheck(int fromRow, int fromCol, int toRow, int toCol,
                                          Piece.Color color) {
        Piece moving = squares[fromRow][fromCol];
        Piece captured = squares[toRow][toCol];
        int[] savedEP = enPassantTarget;

        int epCaptureRow = -1, epCaptureCol = -1;
        boolean isEP = moving.getType() == Piece.Type.PAWN && enPassantTarget != null
                && toRow == enPassantTarget[0] && toCol == enPassantTarget[1];
        if (isEP) {
            epCaptureRow = fromRow;
            epCaptureCol = toCol;
            squares[epCaptureRow][epCaptureCol] = null;
        }

        squares[toRow][toCol] = moving;
        squares[fromRow][fromCol] = null;

        boolean inCheck = isInCheck(color);

        squares[fromRow][fromCol] = moving;
        squares[toRow][toCol] = captured;
        if (isEP) {
            Piece.Color enemyColor = (color == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
            squares[epCaptureRow][epCaptureCol] = new Pawn(enemyColor);
        }
        enPassantTarget = savedEP;

        return inCheck;
    }

    public boolean hasAnyLegalMove(Piece.Color color) {
        for (int fr = 0; fr < 8; fr++)
            for (int fc = 0; fc < 8; fc++) {
                Piece p = squares[fr][fc];
                if (p == null || p.getColor() != color) continue;
                for (int tr = 0; tr < 8; tr++)
                    for (int tc = 0; tc < 8; tc++) {
                        Piece dest = squares[tr][tc];
                        if (dest != null && dest.getColor() == color) continue;
                        if (p.isValidMove(this, fr, fc, tr, tc)) {
                            if (!moveLeavesKingInCheck(fr, fc, tr, tc, color))
                                return true;
                        }
                    }
            }
        return false;
    }

    public void display() {
        System.out.print("     ");
        for (char c = 'A'; c <= 'H'; c++)
            System.out.print(ConsoleColors.coordText() + c + "   " + ConsoleColors.RESET);
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
                    String pc = (p.getColor() == Piece.Color.WHITE)
                            ? ConsoleColors.whitePiece() : ConsoleColors.blackPiece();
                    System.out.print(bg + pc + " " + p.getSymbol() + " " + ConsoleColors.RESET);
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
        for (char c = 'A'; c <= 'H'; c++)
            System.out.print(ConsoleColors.coordText() + c + "   " + ConsoleColors.RESET);
        System.out.println();
    }
}
