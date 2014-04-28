package rc.model;

public final class Position 
{
    
    public static int fromString(String str)
    {
        if(str.length() != 2 ) return -1;
        final int row = Integer.parseInt( str.substring(1) ) - 1;
        if( row < 0 || row >= Board.ROWS ) return -1;
        final int col = ( (int) Character.toUpperCase( str.charAt(0) ) ) - 65 ;
        if( col < 0 || col >= Board.COLUMNS ) return -1;
        return row * Board.COLUMNS + col;
    }
    
    public static String toString(int pos)
    {
        String str = "";
        str += (char)( 65 + ( pos % Board.COLUMNS ));
        str += 1 + ( pos / Board.COLUMNS );
        return str;
    }
    
    public static boolean isValid(int pos)
    {
        return pos >= 0  && pos < Board.ALL_SQUARES;
    }
    
}
