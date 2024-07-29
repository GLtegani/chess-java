package boardgame;

public class Position {
   private int row;
   private int column;

   public Position(int row, int column) {
      this.row = row;
      this.column = column;
   }

   public final int getColumn() {
      return column;
   }

   public final void setColumn(int column) {
      this.column = column;
   }

   public final int getRow() {
      return row;
   }

   public final void setRow(int row) {
      this.row = row;
   }

   public void setValues(int row, int column) {
      this.row = row;
      this.column = column;
   }

   @Override
   public final String toString() {
      return this.row + ", " + this.column;
   }
}
