package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UI {
   // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

   public final String ANSI_RESET = "\u001B[0m";
   public final String ANSI_BLACK = "\u001B[30m";
   public final String ANSI_RED = "\u001B[31m";
   public final String ANSI_GREEN = "\u001B[32m";
   public final String ANSI_YELLOW = "\u001B[33m";
   public final String ANSI_BLUE = "\u001B[34m";
   public final String ANSI_PURPLE = "\u001B[35m";
   public final String ANSI_CYAN = "\u001B[36m";
   public final String ANSI_WHITE = "\u001B[37m";

   public final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
   public final String ANSI_RED_BACKGROUND = "\u001B[41m";
   public final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
   public final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
   public final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
   public final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
   public final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
   public final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

   public void clearScreen() {
      System.out.print("\033[H\033[2J");
      System.out.flush();
   }

   public ChessPosition readChessPosition(Scanner sc) {
      try {
         String s = sc.nextLine();
         char column = s.charAt(0);
         int row = Integer.parseInt(s.substring(1));
         return new ChessPosition(column, row);
      } catch (RuntimeException error) {
         throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
      }
   }

   public void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
      printBoard(chessMatch.getPieces());
      System.out.println();
      printCapturedPieces(captured);
      System.out.println();
      System.out.println("Turn: " + chessMatch.getTurn());
      System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());

      if(chessMatch.getCheck()) {
         System.out.println("CHECK!");
      }
   }

   public void printBoard(ChessPiece[][] pieces) {
      for(int row = 0; row < pieces.length; row++) {
         System.out.print((8 - row) + " ");
         for(int column = 0; column < pieces.length; column++) {
            this.printPiece(pieces[row][column], false);
         }
         System.out.println();
      }
      System.out.println("  a b c d e f g h");
   }

   public void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
      for(int row = 0; row < pieces.length; row++) {
         System.out.print((8 - row) + " ");
         for(int column = 0; column < pieces.length; column++) {
            this.printPiece(pieces[row][column], possibleMoves[row][column]);
         }
         System.out.println();
      }
      System.out.println("  a b c d e f g h");
   }

   private void printPiece(ChessPiece piece, boolean background) {
      if(background) {
         System.out.print(ANSI_RED_BACKGROUND);
      }
      if(piece == null) {
         System.out.print("-" + ANSI_RESET);
      } else if(piece.getColor() == Color.WHITE) {
         System.out.print(ANSI_WHITE + piece + ANSI_RESET);
      } else {
         System.out.print(ANSI_BLACK + piece + ANSI_RESET);
      }
      System.out.print(" ");

   }

   private void printCapturedPieces(List<ChessPiece> captured) {
      List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).toList();
      List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).toList();
      System.out.println("Captured pieces:");
      System.out.println("White: ");
      System.out.print(ANSI_WHITE);
      System.out.println(Arrays.toString(white.toArray()));
      System.out.print(ANSI_RESET);
      System.out.println("Black: ");
      System.out.print(ANSI_BLACK);
      System.out.println(Arrays.toString(black.toArray()));
      System.out.print(ANSI_RESET);

   }
}
