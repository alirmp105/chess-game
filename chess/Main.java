package chess;

import chess.model.Game;

public class Main {
    public static void main(String[] args) {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "chcp 65001 > nul && echo").inheritIO().start().waitFor();
                System.out.print("\033[?25h");
            }
        } catch (Exception ignored) { }

        Game game = new Game();
        game.start();
    }
}
