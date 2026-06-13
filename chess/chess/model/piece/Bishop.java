package chess.model.piece;

import chess.model.Board;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color, Type.BISHOP);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dRow = toRow - fromRow;
        int dCol = toCol - fromCol;
        if (Math.abs(dRow) == Math.abs(dCol)) {
            return isPathClear(board, fromRow, fromCol, toRow, toCol);
        }
        return false;
    }
}