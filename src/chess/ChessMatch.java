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

   private void placeNewPiece(char column, int row, ChessPiece piece) {
      this.board.placePiece(piece, new ChessPosition(column, row).toPosition());
   }

   private void initialSetup() {
      placeNewPiece('c', 1, new Rook(board, Color.WHITE));
      placeNewPiece('c', 2, new Rook(board, Color.WHITE));
      placeNewPiece('d', 2, new Rook(board, Color.WHITE));
      placeNewPiece('e', 2, new Rook(board, Color.WHITE));
      placeNewPiece('e', 1, new Rook(board, Color.WHITE));
      placeNewPiece('d', 1, new King(board, Color.WHITE));

      placeNewPiece('c', 7, new Rook(board, Color.BLACK));
      placeNewPiece('c', 8, new Rook(board, Color.BLACK));
      placeNewPiece('d', 7, new Rook(board, Color.BLACK));
      placeNewPiece('e', 7, new Rook(board, Color.BLACK));
      placeNewPiece('e', 8, new Rook(board, Color.BLACK));
      placeNewPiece('d', 8, new King(board, Color.BLACK));
   }
}
