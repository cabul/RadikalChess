package radikalchess;

import java.util.Iterator;

// Position

public class Position {

    public static class PositionList extends ReadonlyList<Position>{}

    public static Iterable<Position> ALL;
    
    static 
    {
        ALL = new Iterable<Position>() {
            @Override
            public Iterator<Position> iterator() {
                return new Iterator() {
                    private int head;

                    @Override
                    public boolean hasNext() {
                        return head < Config.ALL_SQUARES;
                    }

                    @Override
                    public Position next() {
                        return new Position( head/Config.COLUMNS , head++%Config.COLUMNS );
                    }

                    @Override
                    public void remove() {
                    }
                };
            }
        };
    }
    
    
    public final int row;
    public final int col;

    public Position(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    @Override
    public String toString()
    {
        String str = "";
        str += (char)( 97 + ( col ));
        str += 1 + ( row );
        return str;
    }

    @Override
    public Position clone()
    {
        return new Position(this.row,this.col);
    }
    
    public static Position fromString(String str)
    {
        try{
            if(str.length() != 2 ) return null;
            final int row = Integer.parseInt( str.substring(1) ) - 1;
            if( row < 0 || row >= Config.ROWS ) return null;
            final int col = ( (int) Character.toLowerCase( str.charAt(0) ) ) - 97 ;
            if( col < 0 || col >= Config.COLUMNS ) return null;
            return new Position(row,col);
        } catch(Exception ex) {
            return null;
        }
    }
    
    public int distance(Position other)
    {
        return euclidean2(this,other);
    }

    @Override
    public int hashCode() {
        return row * Config.COLUMNS + col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if( !(obj instanceof Position) ) return false;
        final Position other = (Position) obj;
        return (row == other.row) && (col == other.col);
    }
    
    public boolean atTop()
    {
        return row == Config.ROWS - 1;
    }
    
    public boolean atBottom()
    {
        return row == 0;
    }
    
    public boolean atLeft()
    {
        return col == 0;
    }
    
    public boolean atRight()
    {
        return col == Config.COLUMNS - 1;
    }
    
    private static int euclidean2(Position a, Position b)
    {
        return ((b.row - a.row) * (b.row - a.row))
             + ((b.col - a.col) * (b.col - a.col));
    }
    
    private static int manhattan(Position a, Position b)
    {
        return Math.abs(b.row - a.row)
             + Math.abs(b.col - a.col);
    }
    
}
