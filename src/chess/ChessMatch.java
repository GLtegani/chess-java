package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
   private Board board;

   public ChessMatch() {
      this.board = new Board(8, 8);
      this.initialSetup();
   }

   public ChessPiece[][] getPieces() {
      ChessPiece[][] mat = new ChessPiece[this.board.getRows()][this.board.getColumns()];

      for(int row = 0; row < this.board.getRows(); row++) {
         for(int column = 0; column < this.board.getColumns(); column++) {
            mat[row][column] = (ChessPiece) this.board.piece(row, column);
         }
      }
      return mat;
   }

   private void initialSetup() {
      this.board.placePiece(new Rook(this.board, Color.WHITE), new Position(2, 1));
      this.board.placePiece(new King(this.board, Color.BLACK), new Position(0, 4));
   }
}
