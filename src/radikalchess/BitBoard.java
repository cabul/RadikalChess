package radikalchess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static radikalchess.Move.*;

public final class BitBoard 
{
    
    // Static utilities, definitions, etc.

    public static final String MagicString = "Radikal Chess";
    
    public static final int WHITE = 1;
    public static final int BLACK = 0;
    
    public static final int ROWS = 6;
    public static final int COLUMNS = 4;

        // must be less than 32
    public static final int ALL_SQUARES = ROWS * COLUMNS;

    public static final int PAWN = 2;
    public static final int BISHOP = 4;
    public static final int ROOK = 6;
    public static final int QUEEN = 8;
    public static final int KING = 10;
    
    public static final int WHITE_PAWN = WHITE + PAWN;
    public static final int WHITE_BISHOP = WHITE + BISHOP;
    public static final int WHITE_ROOK = WHITE + ROOK;
    public static final int WHITE_QUEEN = WHITE + QUEEN;
    public static final int WHITE_KING = WHITE + KING;
    
    public static final int BLACK_PAWN = BLACK + PAWN;
    public static final int BLACK_BISHOP = BLACK + BISHOP;
    public static final int BLACK_ROOK = BLACK + ROOK;
    public static final int BLACK_QUEEN = BLACK + QUEEN;
    public static final int BLACK_KING = BLACK + KING;
    
    public static final int EMPTY_SQUARE = 12;
    
    public static final int OCCUPIED_SQUARE = 13;
    
    public static final int ALL_BITBOARDS = 14;
    
    public static int SQUARE_BITS[];
    
    public static String VERBOSE[];
    
    static 
    {
        SQUARE_BITS = new int[ ALL_SQUARES ];
        for ( int i = 0; i < ALL_SQUARES; i++ )
        {
            SQUARE_BITS[ i ] = ( 1 << i );
        }
        
        VERBOSE = new String[ 13 ];
        VERBOSE[ WHITE_PAWN   ] = "\u2659";
        VERBOSE[ WHITE_BISHOP ] = "\u2657";
        VERBOSE[ WHITE_ROOK   ] = "\u2656";
        VERBOSE[ WHITE_QUEEN  ] = "\u2655";
        VERBOSE[ WHITE_KING   ] = "\u2654";
        VERBOSE[ BLACK_PAWN   ] = "\u265F";
        VERBOSE[ BLACK_BISHOP ] = "\u265D";
        VERBOSE[ BLACK_ROOK   ] = "\u265C";
        VERBOSE[ BLACK_QUEEN  ] = "\u265B";
        VERBOSE[ BLACK_KING   ] = "\u265A";
        
        VERBOSE[ EMPTY_SQUARE ] = " ";
        
        VERBOSE[ WHITE ] = "White";
        VERBOSE[ BLACK ] = "Black";
        
    }
    
    
    public static int indexFromString(String str)
    {
        if(str.length() != 2 ) return -1;
        final int row = Integer.parseInt( str.substring(1) ) - 1;
        if( row < 0 || row >= ROWS ) return -1;
        final int col = ( (int) Character.toUpperCase( str.charAt(0) ) ) - 65 ;
        if( col < 0 || col >= COLUMNS ) return -1;
        return row * COLUMNS + col;
    }
    
    public static String indexToString(int pos)
    {
        String str = "";
        str += (char)( 65 + ( pos % COLUMNS ));
        str += 1 + ( pos / COLUMNS );
        return str;
    }
    
    public static boolean indexIsValid(int pos)
    {
        return pos >= 0  && pos < ALL_SQUARES;
    }
    
    public static boolean indexAtEndRow(int pos)
    {
        return pos < COLUMNS || pos >= ALL_SQUARES - COLUMNS;
    }
    
    public static int indexTop(int pos)
    {
        return pos + COLUMNS;
    }
    
    public static int indexBottom(int pos)
    {
        return pos - COLUMNS;
    }
    
    public static int indexLeft(int pos)
    {
        return pos - 1;
    }
    
    public static int indexRight(int pos)
    {
        return pos + 1;
    }
    
    public static boolean pieceIs(int piece, int mask)
    {
        return ( (piece & mask) == mask);
    }
    
    public static int pieceColor(int piece)
    {
        return piece % 2;
    }
    
    // Static part ends
    // Object part 
    
    int turn;
    private int bitBoards[];
    private int bitSquares[];
    
    public BitBoard() {
        bitBoards = new int[ ALL_BITBOARDS ];
        bitSquares = new int[ ALL_SQUARES ];
    }
    
    public boolean clone(BitBoard target)
    {
        System.arraycopy( target.bitBoards, 0
                        , this.bitBoards, 0
                        , ALL_BITBOARDS );
        
        System.arraycopy( target.bitSquares, 0
                        , this.bitSquares, 0
                        , ALL_SQUARES );
        
        this.turn = target.turn;
        return true;
    }
    
    public BitBoard copy()
    {
        BitBoard b = new BitBoard();
        b.clone(this);
        return b;
    }
    
    public int[] convert()
    {
        int[] conv = new int[ ALL_SQUARES ];
        for ( int i = 0; i < ALL_SQUARES; i++ )
        {
            conv[ i ] = pieceAt( i );
        }
        return conv;
    }
    
    @Override
    public String toString()
    {
        int lineLength = ( COLUMNS + 1 ) * 4 + 1;
        int length = 2 * ( ROWS + 1 ) * lineLength;
        
        StringBuilder builder = new StringBuilder(length);
        
        for ( int i = 0; i < length; i++ ) 
        {
            builder.append(" ");
        }
        
        String footIndex = "    ";
        for ( int i = 0; i < COLUMNS; i++ )
        {
            footIndex = footIndex + " " + (char)( 65 + i ) + "  ";
        }
        footIndex = footIndex + "\n";
        
        builder.replace( length - lineLength
                       , length
                       , footIndex );
        
        String line = "   +";
        for ( int i = 0; i < COLUMNS; i++ )
        {
            line = line + "---+";
        }
        
        line = line + "\n";
        
        for ( int i = 0; i <= ROWS; i++ )
        {
            int start = i * 2 * lineLength;
            int end = start + lineLength;
            builder.replace( start, end, line);
        }
        
        StringBuilder rowBuilder = new StringBuilder();
        for ( int i = 0; i <= COLUMNS; i++ )
        {
            rowBuilder.append("   |");
        }
        rowBuilder.append("\n");
        
        for ( int i = 1; i <= ROWS; i++ )
        {
            int end = length - ( 2 * i * lineLength );
            int start = end - lineLength;
            rowBuilder.setCharAt( 1, Character.forDigit( i, 10 ) );
            builder.replace( start, end, rowBuilder.toString() );
        }
        
        for ( int row = 0; row < ROWS; row++ )
        {
            for ( int col = 0; col < COLUMNS; col++ )
            {
                int pat = col + row * COLUMNS;
                int sat = length - ( ( row + 1 ) * 2 * lineLength ) - ( ( COLUMNS - col ) * 4 );
                builder.setCharAt( sat, VERBOSE[ pieceAt( pat ) ].charAt(0) );
            }
        }
        
        return "Turn " + turn + "\n" 
             + builder.toString() 
             + VERBOSE[player()] + " to move";
    }
    
    public void reset()
    {
        empty();
        
        addPiece( 0, WHITE_ROOK );
        addPiece( 1, WHITE_BISHOP );
        addPiece( 2, WHITE_QUEEN );
        addPiece( 3, WHITE_KING );
        for ( int i = 4; i < 8; i++)
        {
            addPiece( i, WHITE_PAWN );
        }
        
        addPiece( 23, BLACK_ROOK );
        addPiece( 22, BLACK_BISHOP );
        addPiece( 21, BLACK_QUEEN );
        addPiece( 20, BLACK_KING );
        for ( int i = 16; i < 20; i++)
        {
            addPiece( i, BLACK_PAWN );
        }
        
        turn = 1;
        
    }
    
    public int pieceAt( int square )
    {
        return bitSquares[ square ];
    }
    
    public int get( int type )
    {
        return bitBoards[type];
    }
    
    public void applyMove( Move move )
    {
        final int type = move.moveType;
        removePiece( move.sourceSquare, move.movingPiece );
        if( (type & MOVE_CAPTURE) != 0)
            removePiece( move.destinationSquare, move.capturedPiece );
        if( (type & MOVE_PROMOTION) == 0)
            addPiece( move.destinationSquare, move.movingPiece );
        else 
        {
            final int color = ( move.movingPiece % 2 );
            addPiece( move.destinationSquare, QUEEN + color );
        }
        
        turn++;
        
    }
    
    public void save(String path) throws IOException
    {
        try (BufferedWriter bw = new BufferedWriter( new FileWriter ( path ) )) {
            bw.newLine();
            bw.write(MagicString);
            bw.newLine();
            bw.write(""+turn);
            bw.newLine();
            bw.newLine();
            for ( int i = 0; i < ALL_SQUARES; i++ )
            {
                int piece = pieceAt( i );
                if ( piece != EMPTY_SQUARE )
                {
                    bw.write( i + " " + piece);
                    bw.newLine();
                }
            }
            bw.newLine();
            bw.write(toString());
            bw.newLine();
        }
    }
    
    public void load(String path) throws IOException
    {
        empty();
        try (BufferedReader br = new BufferedReader( new FileReader( path ) )) {
            String line = br.readLine();
            System.out.println(line);
            br.readLine();
            turn = Integer.parseInt( br.readLine() );
            br.readLine();
            String[] aux;
            while( ! ( line = br.readLine() ).equals("") )
            {
                aux = line.split(" ");
                addPiece( Integer.parseInt(aux[0]), Integer.parseInt(aux[1]) );
            }
        }
            
    }
    
    public int player()
    {
        return turn % 2;
    }
    
    // PRIVATE
    
    private void empty()
    {
        for( int i = 0; i < ALL_BITBOARDS; i++ )
        {
            bitBoards[ i ] = 0;
        }
        for( int i = 0; i < ALL_SQUARES; i++ )
        {
            bitSquares[ i ] = EMPTY_SQUARE;
        }
    }
    
    private void addPiece( int square, int piece )
    {
        bitBoards[ piece ] |= SQUARE_BITS[ square ];
        bitBoards[ pieceColor(piece) ] |= SQUARE_BITS[ square ];
        bitBoards[ OCCUPIED_SQUARE ] |= SQUARE_BITS[ square ];
        bitBoards[ EMPTY_SQUARE ] = ~ bitBoards[ OCCUPIED_SQUARE ];
        bitSquares[ square ] = piece;
    }
    
    private void removePiece( int square, int piece )
    {
        bitBoards[ piece ] ^= SQUARE_BITS[ square ];
        bitBoards[ pieceColor(piece) ] ^= SQUARE_BITS[ square ];
        bitBoards[ OCCUPIED_SQUARE ] ^= SQUARE_BITS[ square ];
        bitBoards[ EMPTY_SQUARE ] = ~ bitBoards[ OCCUPIED_SQUARE ];
        bitSquares[ square ] = EMPTY_SQUARE;
    }
    
    
}
