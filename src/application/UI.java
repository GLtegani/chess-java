package application;

import chess.ChessPiece;

public class UI {
   public void printBoard(ChessPiece[][] pieces) {
      for(int row = 0; row < pieces.length; row++) {
         System.out.print((8 - row) + " ");
         for(int column = 0; column < pieces.length; column++) {
            this.printPiece(pieces[row][column]);
         }
         System.out.println();
      }
      System.out.println("  a b c d e f g h");
   }

   private void printPiece(ChessPiece piece) {
      if(piece == null) {
         System.out.print("-");
      } else {
         System.out.print(piece);
      }
      System.out.print(" ");

   }
}
