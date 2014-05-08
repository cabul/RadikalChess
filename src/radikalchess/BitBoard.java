package radikalchess;

import java.util.Iterator;

public class BitBoard {
    
    public static final long MASK_INVALID;
    
    static
    {
        long invalid = 0;
        for( int i = 64; i >= Config.ALL_SQUARES; i-- )
        {
            invalid |= 1L << i;
        }
        MASK_INVALID = invalid;
    }
    
    public static String toString(long bitboard)
    {
        if( (bitboard & MASK_INVALID) != 0 )
            return "Invalid";
        
        String str = "";
        for( int row = Config.ROWS - 1; row >= 0; row-- )
        {
            for( int col = 0; col < Config.COLUMNS; col++ )
            {
                Position pos = new Position(row,col);
                if( (bitboard & pos.index()) == 0 )
                    str += " o";
                else str += " x";
            }
            str += "\n";
        }
        return str;
    }

    public static int count(long bitboard)
    {
        return Long.bitCount(bitboard);
    }
    
    public static Iterable<Position> traverse(final long bitboard)
    {
        return new Iterable<Position>() {

            private final long bitboard_mask = bitboard;
            
            @Override
            public Iterator<Position> iterator() {
                return new Iterator() {

                    private long mask = bitboard_mask;
            
                    @Override
                    public boolean hasNext() {
                        return mask != 0;
                    }

                    @Override
                    public Position next() {
                        int hash = Long.numberOfTrailingZeros(mask);
                        mask ^= 1L << hash;
                        return Position.fromHash(hash);
                    }

                    @Override
                    public void remove() {
                    }

                };
    
            }
        };
        
    }
    
    public static long mask(Iterable<Position> ps)
    {
        long mask = 0;
        for( Position p : ps )
        {
            mask |= p.index();
        }
        return mask;
    }
    
}
