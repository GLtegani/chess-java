package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
   public King(Board board, Color color) {
      super(board, color);
   }

   @Override
   public String toString() {
      return "K";
   }

   private boolean canMove(Position position) {
      ChessPiece p = (ChessPiece) getBoard().piece(position);
      return p == null || p.getColor() != getColor();
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




      return mat;
   }

}
