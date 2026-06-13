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

        if (dRow <= 1 && dCol <= 1 && !(dRow == 0 && dCol == 0)) {
            return true;
        }

        if (!hasMoved && dRow == 0 && dCol == 2) {
            return canCastle(board, fromRow, fromCol, toCol);
        }

        return false;
    }

    private boolean canCastle(Board board, int row, int fromCol, int toCol) {
        int rookCol = (toCol > fromCol) ? 7 : 0;
        Piece rook = board.getPiece(row, rookCol);

        if (rook == null || rook.getType() != Type.ROOK || rook.getColor() != color || rook.hasMoved()) {
            return false;
        }

        int minCol = Math.min(fromCol, rookCol) + 1;
        int maxCol = Math.max(fromCol, rookCol);
        for (int col = minCol; col < maxCol; col++) {
            if (board.getPiece(row, col) != null) return false;
        }

        int step = (toCol > fromCol) ? 1 : -1;
        for (int col = fromCol; col != toCol + step; col += step) {
            if (board.isSquareUnderAttack(row, col, color)) return false;
        }

        return true;
    }
}
