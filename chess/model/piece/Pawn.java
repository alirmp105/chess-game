package chess.model.piece;

import chess.model.Board;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(color, Type.PAWN);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dRow = toRow - fromRow;
        int dCol = toCol - fromCol;
        int direction = (color == Color.WHITE) ? -1 : 1;
        int startRow  = (color == Color.WHITE) ? 6 : 1;

        if (dCol == 0) {
            if (dRow == direction) {
                return board.getPiece(toRow, toCol) == null;
            }
            if (dRow == 2 * direction && fromRow == startRow) {
                int middleRow = fromRow + direction;
                return board.getPiece(middleRow, fromCol) == null &&
                       board.getPiece(toRow, toCol) == null;
            }
            return false;
        }

        if (Math.abs(dCol) == 1 && dRow == direction) {
            Piece target = board.getPiece(toRow, toCol);
            if (target != null && target.getColor() != color) {
                return true;
            }
            int[] ep = board.getEnPassantTarget();
            if (ep != null && ep[0] == toRow && ep[1] == toCol) {
                return true;
            }
        }

        return false;
    }
}
