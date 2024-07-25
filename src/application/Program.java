package application;

import chess.ChessMatch;

public class Program {
   public static void main(String[] args) {
      ChessMatch chessMatch = new ChessMatch();
      UI ui = new UI();
      ui.printBoard(chessMatch.getPieces());
   }
}