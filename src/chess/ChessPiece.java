package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
   private final Color color;
   private int moveCount;

   public ChessPiece(Board board, Color color) {
      super(board);
      this.color = color;
   }

   public Color getColor() {
      return color;
   }

   public int getMoveCount() {
      return this.moveCount;
   }

   public void increaseMoveCount() {
      this.moveCount++;
   }

   public void decreaseMoveCount() {
      this.moveCount--;
   }

   protected boolean isThereOpponentPiece(Position position) {
      ChessPiece piece = (ChessPiece) getBoard().piece(position);
      return piece != null && piece.getColor() != this.color;
   }

   public ChessPosition getChessPosition() {
      ChessPosition chessPosition = new ChessPosition();
      return chessPosition.fromPosition(this.position);
   }
}
