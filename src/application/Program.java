package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      ChessMatch chessMatch = new ChessMatch();
      UI ui = new UI();
      while (true) {
         try {
            ui.clearScreen();
            ui.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.print("Source: ");
            ChessPosition source = ui.readChessPosition(sc);
            System.out.println();
            System.out.print("Target: ");
            ChessPosition target = ui.readChessPosition(sc);
            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
         } catch (ChessException | InputMismatchException e) {
            System.out.println(e.getMessage());
            sc.nextLine();
         }


      }
   }
}