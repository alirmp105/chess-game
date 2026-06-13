# ♟️ Chess Game — Java Console

A fully functional two-player chess game playable in the terminal, written in Java.  
Supports all standard chess rules including special moves.

---

## Features

### Phase 1 — Core Rules
- 8×8 board rendered in the console with Unicode-style piece symbols
- White and Black pieces visually distinguished by color
- Standard chess notation input (e2-e4 or e2 e4)
- Move validation for all pieces (King, Queen, Rook, Bishop, Knight, Pawn)
- Turn enforcement — only the active player can move their own pieces
- Illegal move detection with error messages
- Action timer — tracks how long each player spends per turn

### Phase 2 — Advanced Rules
- ♟ En Passant — pawn captures en passant when conditions are met
- ♛ Pawn Promotion — choose Queen, Rook, Bishop, or Knight upon reaching the last rank
- 🏰 Castling — kingside and queenside, with full condition checks:
  - King and Rook must not have moved
  - Path between them must be clear
  - King must not be in check, pass through check, or land in check
- 🛡 Self-check prevention — any move that leaves your own King in check is rejected
- 👑 Check detection — warns the active player when their King is in check
- Checkmate detection — ends the game and announces the winner
- Stalemate detection — ends the game as a draw

---

## How to Run

### Requirements
- Java 11 or higher

### Compile
bash
javac -encoding UTF-8 -d out $(find src -name "*.java")


### Run
bash
java -cp out chess.Main


---

## How to Play

Enter moves using standard algebraic notation:

| Action | Example Input |
|---|---|
| Normal move | e2-e4 or e2 e4 |
| Capture | d4-e5 |
| Castling (kingside) | e1-g1 (White) / e8-g8 (Black) |
| Castling (queenside) | e1-c1 (White) / e8-c8 (Black) |
| Pawn promotion | Move pawn to last rank, then choose Q / R / B / N |
| Quit | quit |

> Input is case-insensitive — E2-E4 and e2-e4 both work.

---

## Project Structure


src/
└── chess/
    ├── Main.java
    ├── model/
    │   ├── Game.java         # Game loop, turn management, end conditions
    │   ├── Board.java        # Board state, move execution, check/mate/stalemate logic
    │   ├── TimerUtil.java    # Per-player action timer
    │   └── piece/
    │       ├── Piece.java    # Abstract base class
    │       ├── King.java     # Includes castling logic
    │       ├── Queen.java
    │       ├── Rook.java
    │       ├── Bishop.java
    │       ├── Knight.java
    │       └── Pawn.java     # Includes en passant & promotion trigger
    └── utils/
        ├── ChessNotation.java   # Parses algebraic notation (e.g. e2 → row/col)
        └── ConsoleColors.java   # ANSI color codes for terminal display


---

## License

This project was developed as a university programming assignment.  
Feel free to use or modify it for educational purposes.
