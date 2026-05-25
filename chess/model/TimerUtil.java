package chess.model;

import chess.model.piece.Piece;

public class TimerUtil {
    private long whiteSeconds;
    private long blackSeconds;
    private long turnStartMillis;

    public TimerUtil() {
        turnStartMillis = System.currentTimeMillis();
    }

    public long endTurn(Piece.Color currentTurn) {
        long now = System.currentTimeMillis();
        long thinkingSeconds = (now - turnStartMillis) / 1000;
        if (currentTurn == Piece.Color.WHITE) {
            whiteSeconds += thinkingSeconds;
        } else {
            blackSeconds += thinkingSeconds;
        }
        turnStartMillis = System.currentTimeMillis();
        return thinkingSeconds;
    }

    public long getWhiteSeconds() { return whiteSeconds; }
    public long getBlackSeconds() { return blackSeconds; }

    public String getTimeDisplay() {
        return String.format("White: %ds | Black: %ds", whiteSeconds, blackSeconds);
    }
}