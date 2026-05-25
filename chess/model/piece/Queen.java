package chess.model.piece;

import chess.model.Board;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color, Type.QUEEN);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dRow = toRow - fromRow;
        int dCol = toCol - fromCol;
        if ((dRow == 0 || dCol == 0) || Math.abs(dRow) == Math.abs(dCol)) {
            return isPathClear(board, fromRow, fromCol, toRow, toCol);
        }
        return false;
    }
}