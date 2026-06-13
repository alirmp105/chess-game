package chess.utils;

public class ConsoleColors {

    public static final String RESET = "\033[0m";

    public static final String WHITE = "\033[0;37m";
    public static final String BLUE = "\033[0;34m";
    public static final String YELLOW = "\033[0;33m";
    public static final String CYAN = "\033[0;36m";

    public static final String WHITE_BOLD = "\033[1;37m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String YELLOW_BOLD = "\033[1;33m";

    public static final String BG_LIGHT_GRAY = "\033[48;2;140;140;140m";
    public static final String BG_DARK_GRAY  = "\033[48;2;60;60;60m";


    public static String whitePiece() { return WHITE_BOLD; }
    public static String blackPiece() { return BLUE_BOLD; }
    public static String coordText() { return YELLOW_BOLD; }
    public static String lightSquareBg() { return BG_LIGHT_GRAY; }
    public static String darkSquareBg() { return BG_DARK_GRAY; }
}