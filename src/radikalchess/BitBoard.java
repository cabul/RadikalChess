package radikalchess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static radikalchess.Move.*;

public final class BitBoard 
{
    
    // Static utilities, definitions, etc.

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
    
    public static final int MASK_TOP_ROW;
    public static final int MASK_BOTTOM_ROW;
    public static final int MASK_LEFT_COLUMN;
    public static final int MASK_RIGHT_COLUMN;
    public static final int MASK_BORDER;
    
    public static final int MASK_INVALID;
    
    public static final int[] SQUARE_BITS;
    
    public static String[] VERBOSE;
    
    static 
    {
        SQUARE_BITS = new int[ ALL_SQUARES ];
        for ( int i = 0; i < ALL_SQUARES; i++ )
        {
            SQUARE_BITS[ i ] = ( 1 << i );
        }
        
        VERBOSE = new String[ 13 ];
        VERBOSE[ WHITE_PAWN   ] = "p"; //"\u2659";
        VERBOSE[ WHITE_BISHOP ] = "b"; //"\u2657";
        VERBOSE[ WHITE_ROOK   ] = "r"; //"\u2656";
        VERBOSE[ WHITE_QUEEN  ] = "q"; //"\u2655";
        VERBOSE[ WHITE_KING   ] = "k"; //"\u2654";
        VERBOSE[ BLACK_PAWN   ] = "P"; //"\u265F";
        VERBOSE[ BLACK_BISHOP ] = "B"; //"\u265D";
        VERBOSE[ BLACK_ROOK   ] = "R"; //"\u265C";
        VERBOSE[ BLACK_QUEEN  ] = "Q"; //"\u265B";
        VERBOSE[ BLACK_KING   ] = "K"; //"\u265A";
        
        VERBOSE[ EMPTY_SQUARE ] = " ";
        
        VERBOSE[ WHITE ] = "White";
        VERBOSE[ BLACK ] = "Black";
        
        int bottom_row = 0;
        int top_row = 0;
        
        for( int i = 0; i < COLUMNS; i++ )
        {
            bottom_row |= SQUARE_BITS[ i ];
            top_row |= SQUARE_BITS[ ALL_SQUARES - i - 1 ];
        }
        
        MASK_BOTTOM_ROW = bottom_row;
        MASK_TOP_ROW = top_row;
        
        int left_column = 0;
        int right_column = 0;
        
        for( int i = 0; i < ALL_SQUARES; i = indexTop(i))
        {
            left_column |= SQUARE_BITS[ i ];
            right_column |= SQUARE_BITS[ i + COLUMNS - 1 ];
        }
        
        MASK_LEFT_COLUMN = left_column;
        MASK_RIGHT_COLUMN = right_column;
        
        MASK_BORDER = MASK_BOTTOM_ROW | MASK_TOP_ROW
                    | MASK_LEFT_COLUMN | MASK_RIGHT_COLUMN; 
        
        
        int invalid = 0;
        for( int i = 31; i >= ALL_SQUARES; i-- )
        {
            invalid |= ( 1 << i );
        }
        MASK_INVALID = invalid;
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
    
    public static int indexFromRowColumn(int row, int col)
    {
        return row * COLUMNS +col;
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
    
    public static void print(int bitboard)
    {
        if( (bitboard & MASK_INVALID) != 0 ) {
            System.out.println("Invalid Bitboard");
            return;
        }
        String pr = "";
        for( int row = ROWS - 1; row >= 0; row-- )
        {
            for( int col = 0; col < COLUMNS; col++ )
            {
                int i = indexFromRowColumn( row,col );
                if( (bitboard & SQUARE_BITS[i]) == 0 )
                    pr += " o";
                else pr += " x";
            }
            System.out.println(pr);
            pr = "";
        }
    }
    
    // Static part ends
    // Object part 
    
    private int turn;
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
    
    public BitBoard reset()
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
        
        return this;
    }
    
    public int pieceAt( int square )
    {
        return bitSquares[ square ];
    }
    
    public int get( int type )
    {
        return bitBoards[type];
    }
    
    public BitBoard applyMove( Move move )
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
        
        return this;
    }
    
    public static void save(BufferedWriter bw, ArrayList<BitBoard> list) throws IOException
    {
        if( list.isEmpty() ) return;
        bw.write("FIRST " + list.get(0).turn ); bw.newLine();
        bw.write("TURNS " + list.size()); bw.newLine();
        for( BitBoard b : list)
        {
            bw.write("BEGIN"); bw.newLine();
            for( int i = 0; i < ALL_SQUARES; i++ )
            {
                int piece = b.pieceAt( i );
                if( piece != EMPTY_SQUARE )
                {
                    bw.write( i + " " + piece); bw.newLine();
                }
            }
            bw.write("END"); bw.newLine();
        }
    }
    
    public static void load(BufferedReader br, ArrayList<BitBoard> list) throws IOException
    {
        list.clear();
        int turn = Integer.parseInt( br.readLine().split(" ")[1] );
        int size = Integer.parseInt( br.readLine().split(" ")[1] );
        for( int i = 0; i < size; i++ )
        {
            BitBoard b = new BitBoard().empty();
            br.readLine();
            String line;
            while( ! ( line = br.readLine()).equals("END") )
            {
                String[] aux = line.split(" ");
                b.addPiece( Integer.parseInt( aux[0] ), Integer.parseInt( aux[1] ));
            }
            b.turn = turn + i;
            list.add( b );
        }
            
    }
    
    public int player()
    {
        return turn % 2;
    }
    
    public int turn()
    {
        return turn;
    }
    
    // PRIVATE
    
    private BitBoard empty()
    {
        for( int i = 0; i < ALL_BITBOARDS; i++ )
        {
            bitBoards[ i ] = 0;
        }
        for( int i = 0; i < ALL_SQUARES; i++ )
        {
            bitSquares[ i ] = EMPTY_SQUARE;
        }
        return this;
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
