package radikalchess;

public class Position {
    
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
    
    public static Position fromIndex(long index)
    {
        int hash = Long.numberOfTrailingZeros(index);
        return fromHash( hash );
    }
    
    public static Position fromHash(int hash)
    {
        return new Position( hash / Config.COLUMNS , hash % Config.COLUMNS );
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
        return hashCode() == ((Position)obj).hashCode();
    }
    
    public long index()
    {
        return 1L << hashCode();
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
