package chess.model.piece;

import chess.model.Board;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color, Type.ROOK);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dRow = toRow - fromRow;
        int dCol = toCol - fromCol;
        if (dRow == 0 || dCol == 0) {
            return isPathClear(board, fromRow, fromCol, toRow, toCol);
        }
        return false;
    }
}