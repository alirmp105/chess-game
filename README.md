# ♟️ Console Chess – Phase 1 (Java)

A clean, object‑oriented chess game for two players, played entirely in the terminal.  
This phase implements all basic piece movements, turn management, and a move timer.

---
🎮 How to play

· At the prompt White's turn: enter a move like e2 e4 or e2-e4.
· Only the player whose turn it is can move their own pieces.
· If a move is illegal, an error is shown and you must try again.
· The game continues indefinitely (no automatic checkmate).
· Type quit to exit.

---

🎨 Customising colours

All colors are defined in chess/utils/ConsoleColors.java.
You can easily change:

· BG_LIGHT_GRAY / BG_DARK_GRAY – square backgrounds
· whitePiece() / blackPiece() – piece colours
· coordText() – coordinate labels

Example: make light squares darker → lower the RGB value in BG_LIGHT_GRAY.

---
