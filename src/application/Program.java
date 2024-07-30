package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      ChessMatch chessMatch = new ChessMatch();
      UI ui = new UI();
      List<ChessPiece> captured = new ArrayList<>();
      while (true) {
         try {
            ui.clearScreen();
            ui.printMatch(chessMatch, captured);
            System.out.println();
            System.out.print("Source: ");
            ChessPosition source = ui.readChessPosition(sc);
            boolean[][] possibleMoves = chessMatch.possibleMoves(source);
            ui.clearScreen();
            ui.printBoard(chessMatch.getPieces(), possibleMoves);
            System.out.println();
            System.out.print("Target: ");
            ChessPosition target = ui.readChessPosition(sc);
            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
            if(capturedPiece != null) {
               captured.add(capturedPiece);
            }
         } catch (ChessException | InputMismatchException e) {
            System.out.println(e.getMessage());
            sc.nextLine();
         }
      }
   }
}