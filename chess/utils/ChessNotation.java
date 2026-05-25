package chess.utils;

public class ChessNotation {
    public static int[] parseSquare(String square) {
        if (square == null || square.length() != 2) return null;
        char file = square.charAt(0);
        char rank = square.charAt(1);

        if (file < 'a' || file > 'h' || rank < '1' || rank > '8') return null;
        int col = file - 'a';
        int row = 8 - (rank - '0');
        return new int[]{row, col};
    }
}