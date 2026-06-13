package chess.model.piece;

import chess.model.Board;

public abstract class Piece {
    public enum Color { WHITE, BLACK }
    public enum Type { KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN }

    protected final Color color;
    protected final Type type;
    protected boolean hasMoved = false;

    public Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public Color getColor() { return color; }
    public Type getType() { return type; }
    public boolean hasMoved() { return hasMoved; }
    public void setMoved() { this.hasMoved = true; }

    public char getSymbol() {
        return switch (type) {
            case KING   -> 'K';
            case QUEEN  -> 'Q';
            case ROOK   -> 'R';
            case BISHOP -> 'B';
            case KNIGHT -> 'N';
            case PAWN   -> 'P';
        };
    }

    public abstract boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol);

    protected static boolean isPathClear(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dRow = Integer.signum(toRow - fromRow);
        int dCol = Integer.signum(toCol - fromCol);
        int row = fromRow + dRow;
        int col = fromCol + dCol;
        while (row != toRow || col != toCol) {
            if (board.getPiece(row, col) != null) return false;
            row += dRow;
            col += dCol;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }
}
