package boardgame;

public abstract class Piece {
   protected Position position;
   private final Board board;

   public Piece(Board board) {
      this.board = board;
      this.position = null;
   }

   public Position getPosition() {
      return position;
   }

   public void setPosition(Position position) {
      this.position = position;
   }

   protected Board getBoard() {
      return board;
   }

   public abstract boolean[][] possibleMoves();

   public boolean possibleMove(Position position) {
      return this.possibleMoves()[position.getRow()][position.getColumn()];
   }

   public boolean isThereAnyPossibleMove() {
      boolean[][] mat = this.possibleMoves();

      for(int row = 0; row < mat.length; row++) {
         for(int column = 0; column < mat.length; column++) {
            if(mat[row][column]) {
               return true;
            }
         }
      }
      return false;
   }

}
