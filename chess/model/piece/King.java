package chess.model.piece;

import chess.model.Board;

public class King extends Piece {
    public King(Color color) {
        super(color, Type.KING);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dRow = Math.abs(toRow - fromRow);
        int dCol = Math.abs(toCol - fromCol);
        return dRow <= 1 && dCol <= 1 && !(dRow == 0 && dCol == 0);
    }
}