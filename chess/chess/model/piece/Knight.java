package chess.model.piece;

import chess.model.Board;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color, Type.KNIGHT);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dRow = Math.abs(toRow - fromRow);
        int dCol = Math.abs(toCol - fromCol);
        return (dRow == 2 && dCol == 1) || (dRow == 1 && dCol == 2);
    }
}