package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
   private ChessMatch chessMatch;

   public King(Board board, Color color, ChessMatch chessMatch) {
      super(board, color);
      this.chessMatch = chessMatch;
   }

   @Override
   public String toString() {
      return "K";
   }

   private boolean canMove(Position position) {
      ChessPiece p = (ChessPiece) getBoard().piece(position);
      return p == null || p.getColor() != getColor();
   }

   private boolean testRookCastling(Position position) {
      ChessPiece p = (ChessPiece) getBoard().piece(position);
      return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
   }

   @Override
   public boolean[][] possibleMoves() {
      boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
      Position p = new Position(0, 0);

//      ABOVE
      p.setValues(this.position.getRow() - 1, this.position.getColumn());
      if(getBoard().positionExists(p) && canMove(p)) {
         mat[p.getRow()][p.getColumn()] = true;
      }

      // BELOW
      p.setValues(this.position.getRow() + 1, this.position.getColumn());
      if(getBoard().positionExists(p) && canMove(p)) {
         mat[p.getRow()][p.getColumn()] = true;
      }

      // LEFT
      p.setValues(this.position.getRow(), this.position.getColumn() - 1);
      if(getBoard().positionExists(p) && canMove(p)) {
         mat[p.getRow()][p.getColumn()] = true;
      }

      // RIGHT
      p.setValues(this.position.getRow(), this.position.getColumn() + 1);
      if(getBoard().positionExists(p) && canMove(p)) {
         mat[p.getRow()][p.getColumn()] = true;
      }

      // NW
      p.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
      if(getBoard().positionExists(p) && canMove(p)) {
         mat[p.getRow()][p.getColumn()] = true;
      }

      // NE
      p.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
      if(getBoard().positionExists(p) && canMove(p)) {
         mat[p.getRow()][p.getColumn()] = true;
      }

      // SW
      p.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
      if(getBoard().positionExists(p) && canMove(p)) {
         mat[p.getRow()][p.getColumn()] = true;
      }

      // SE
      p.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
      if(getBoard().positionExists(p) && canMove(p)) {
         mat[p.getRow()][p.getColumn()] = true;
      }

//      #SPECIALMOVE CASTLING
      if(getMoveCount() == 0 && !this.chessMatch.getCheck()) {
//         #SPECIALMOVE CASTLING KINGSIDE ROOK
         Position posT1 = new Position(this.position.getRow(), this.position.getColumn() + 3);
         if(testRookCastling(posT1)) {
            Position p1 = new Position(this.position.getRow(), this.position.getColumn() + 1);
            Position p2 = new Position(this.position.getRow(), this.position.getColumn() + 2);

            if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
               mat[this.position.getRow()][this.position.getColumn() + 2] = true;
            }
         }
         //         #SPECIALMOVE CASTLING QUEENSIDE ROOK
         Position posT2 = new Position(this.position.getRow(), this.position.getColumn() - 4);
         if(testRookCastling(posT2)) {
            Position p1 = new Position(this.position.getRow(), this.position.getColumn() - 1);
            Position p2 = new Position(this.position.getRow(), this.position.getColumn() - 2);
            Position p3 = new Position(this.position.getRow(), this.position.getColumn() - 3);

            if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
               mat[this.position.getRow()][this.position.getColumn() - 2] = true;
            }
         }

      }
      return mat;
   }

}
