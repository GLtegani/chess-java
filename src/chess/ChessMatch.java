package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
   private int turn;
   private Color currentPlayer;
   private final Board board;
   private boolean check;
   private boolean checkMate;
   private final List<Piece> piecesOnTheBoard = new ArrayList<>();
   private final List<Piece> capturedPieces = new ArrayList<>();

   public ChessMatch() {
      this.board = new Board(8, 8);
      this.turn = 1;
      this.currentPlayer = Color.WHITE;
      this.check = false;
      this.initialSetup();
   }

   public int getTurn() {
      return this.turn;
   }

   public Color getCurrentPlayer() {
      return this.currentPlayer;
   }

   public boolean getCheck() {
      return this.check;
   }

   public boolean getCheckMate() {
      return this.checkMate;
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

   public boolean[][] possibleMoves(ChessPosition sourcePosition) {
      Position position = sourcePosition.toPosition();
      validateSourcePosition(position);
      return this.board.piece(position).possibleMoves();
   }

   public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
      Position source = sourcePosition.toPosition();
      Position target = targetPosition.toPosition();
      validateSourcePosition(source);
      validateTargetPosition(source, target);
      Piece capturedPiece = makeMove(source, target);
      if(testCheck(this.currentPlayer)) {
         undoMove(source, target, capturedPiece);
         throw new ChessException("You can't put yourself in check");
      }

      this.check = (testCheck(opponent(this.currentPlayer))) ? true : false;

      if(testCheckMate(opponent(this.currentPlayer))) {
         this.checkMate = true;
      } else {
         nextTurn();
      }
      return (ChessPiece) capturedPiece;
   }

   private Piece makeMove(Position source, Position target) {
      ChessPiece piece = (ChessPiece) this.board.removePiece(source);
      piece.increaseMoveCount();
      Piece capturedPiece = this.board.removePiece(target);
      this.board.placePiece(piece, target);

      if(capturedPiece != null) {
         this.piecesOnTheBoard.remove(capturedPiece);
         this.capturedPieces.add(capturedPiece);
      }
      return capturedPiece;
   }

   private void undoMove(Position source, Position target, Piece capturedPiece) {
      ChessPiece p = (ChessPiece) this.board.removePiece(target);
      p.decreaseMoveCount();
      this.board.placePiece(p, source);

      if(capturedPiece != null) {
         this.board.placePiece(capturedPiece, target);
         this.capturedPieces.remove(capturedPiece);
         this.piecesOnTheBoard.add(capturedPiece);
      }
   }

   private void validateSourcePosition(Position position) {
      if(!this.board.thereIsAPiece(position)) {
         throw new ChessException("There is no piece on source position");
      }
      if(this.currentPlayer != ((ChessPiece) this.board.piece(position)).getColor()) {
         throw new ChessException("The chosen piece is not yours");
      }
      if(!this.board.piece(position).isThereAnyPossibleMove()) {
         throw new ChessException("There is no possible moves for the chosen piece");
      }
   }

   private void validateTargetPosition(Position source, Position target) {
      if(!this.board.piece(source).possibleMove(target)) {
         throw new ChessException("The chosen piece can't move to target position");
      }
   }

   private void nextTurn() {
      this.turn++;
      this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
   }

   private Color opponent(Color color) {
      return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
   }

   private ChessPiece king(Color color) {
      List<Piece> list = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();
      for(Piece piece : list) {
         if(piece instanceof King) {
            return (ChessPiece) piece;
         }
      }
      throw new IllegalStateException("There is no " + color + " king on the board");
   }

   private boolean testCheck(Color color) {
      Position kingPosition = king(color).getChessPosition().toPosition();
      List<Piece> opponentPieces = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).toList();

      for(Piece piece : opponentPieces) {
         boolean[][] mat = piece.possibleMoves();
         if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
            return true;
         }
      }
      return false;
   }

   private boolean testCheckMate(Color color) {
      if(!testCheck(color)) {
         return false;
      }
      List<Piece> list = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();

      for (Piece p : list) {
         boolean[][] mat = p.possibleMoves();
         for(int row = 0; row < this.board.getRows(); row++) {
            for(int column = 0; column < this.board.getColumns(); column++) {
               if(mat[row][column]) {
                  Position source = ((ChessPiece)p).getChessPosition().toPosition();
                  Position target = new Position(row, column);
                  Piece capturedPiece = makeMove(source, target);
                  boolean testCheck = testCheck(color);
                  undoMove(source, target, capturedPiece);
                  if(!testCheck) {
                     return false;
                  }
               }
            }
         }
      }
      return true;
   }

   private void placeNewPiece(char column, int row, ChessPiece piece) {
      this.board.placePiece(piece, new ChessPosition(column, row).toPosition());
      this.piecesOnTheBoard.add(piece);
   }

   private void initialSetup() {
      placeNewPiece('a', 1, new Rook(board, Color.WHITE));
      placeNewPiece('b', 1, new Knight(board, Color.WHITE));
      placeNewPiece('g', 1, new Knight(board, Color.WHITE));
      placeNewPiece('d', 1, new Queen(board, Color.WHITE));
      placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
      placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
      placeNewPiece('e', 1, new King(board, Color.WHITE));
      placeNewPiece('h', 1, new Rook(board, Color.WHITE));
      placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
      placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
      placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
      placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
      placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
      placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
      placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
      placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

      placeNewPiece('a', 8, new Rook(board, Color.BLACK));
      placeNewPiece('b', 8, new Knight(board, Color.BLACK));
      placeNewPiece('g', 8, new Knight(board, Color.BLACK));
      placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
      placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
      placeNewPiece('d', 8, new Queen(board, Color.BLACK));
      placeNewPiece('e', 8, new King(board, Color.BLACK));
      placeNewPiece('h', 8, new Rook(board, Color.BLACK));
      placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
      placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
      placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
      placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
      placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
      placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
      placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
      placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
   }
}
