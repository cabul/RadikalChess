package rc.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static rc.model.Move.*;

public final class Board 
{
    
    // Static utilities, definitions, etc.

    public static final String MagicString = "Radikal Chess";
    
    public static final int WHITE = 1;
    public static final int BLACK = 0;
    
    public static final int PAWN = 0;
    public static final int BISHOP = 2;
    public static final int ROOK = 4;
    public static final int QUEEN = 6;
    public static final int KING = 8;
    
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
    
    public static final int EMPTY_SQUARE = 10;
    
    public static final int ROWS = 6;
    public static final int COLUMNS = 4;
    
    public static final int ALL_PIECES = 10;
    // must be less than 32
    public static final int ALL_SQUARES = ROWS * COLUMNS;
    
    public static final int ALL_WHITE_PIECES = ALL_PIECES + WHITE;
    public static final int ALL_BLACK_PIECES = ALL_PIECES + BLACK;
    
    public static final int ALL_BITBOARDS = 12;
    
    public static int SquareBits[];
    
    public static String PlayerStrings[];
    public static String PieceStrings[];
    public static int PieceValues[];
    
    static 
    {
        SquareBits = new int[ ALL_SQUARES ];
        for ( int i = 0; i < ALL_SQUARES; i++ )
        {
            SquareBits[ i ] = ( 1 << i );
        }
        
        PieceStrings = new String[ ALL_PIECES + 1 ];
        PieceStrings[ WHITE_PAWN   ] = "\u2659";
        PieceStrings[ WHITE_BISHOP ] = "\u2657";
        PieceStrings[ WHITE_ROOK   ] = "\u2656";
        PieceStrings[ WHITE_QUEEN  ] = "\u2655";
        PieceStrings[ WHITE_KING   ] = "\u2654";
        PieceStrings[ BLACK_PAWN   ] = "\u265F";
        PieceStrings[ BLACK_BISHOP ] = "\u265D";
        PieceStrings[ BLACK_ROOK   ] = "\u265C";
        PieceStrings[ BLACK_QUEEN  ] = "\u265B";
        PieceStrings[ BLACK_KING   ] = "\u265A";
        PieceStrings[ EMPTY_SQUARE ] = " ";
        
        PieceValues = new int[ ALL_PIECES ];
        PieceValues[ WHITE_PAWN   ] = 100;
        PieceValues[ WHITE_BISHOP ] = 350;
        PieceValues[ WHITE_ROOK   ] = 500;
        PieceValues[ WHITE_QUEEN  ] = 900;
        PieceValues[ WHITE_KING   ] = 2000;
        PieceValues[ BLACK_PAWN   ] = 100;
        PieceValues[ BLACK_BISHOP ] = 350;
        PieceValues[ BLACK_ROOK   ] = 500;
        PieceValues[ BLACK_QUEEN  ] = 900;
        PieceValues[ BLACK_KING   ] = 2000;
        
        PlayerStrings = new String[ 2 ];
        PlayerStrings[ WHITE ] = "White";
        PlayerStrings[ BLACK ] = "Black";
        
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
    
    // Static part ends
    // Object part 
    
    int turn;
    private int bitBoards[];
    
    private int materialValue[];
    private int numPawns[];

    public Board() {
        bitBoards = new int[ ALL_BITBOARDS ];
        numPawns = new int[ 2 ];
        materialValue = new int[ 2 ];
        reset();
    }
    
    public boolean clone(Board target)
    {
        System.arraycopy( target.bitBoards, 0
                        , this.bitBoards, 0
                        , ALL_BITBOARDS );
        
        this.turn = target.turn;
        this.materialValue[ WHITE ] = target.materialValue[ WHITE ];
        this.materialValue[ BLACK ] = target.materialValue[ BLACK ];
        this.numPawns[ WHITE ] = target.numPawns[ WHITE ];
        this.numPawns[ BLACK ] = target.numPawns[ BLACK ];
        return true;
    }
    
    public int[] convert()
    {
        int[] conv = new int[ ALL_SQUARES ];
        for ( int i = 0; i < ALL_SQUARES; i++ )
        {
            conv[ i ] = findPiece( i );
        }
        return conv;
    }
    
    /*
    
       +----+----+----+----+
     6 | 20 | 21 | 22 | 23 |  
       +----+----+----+----+
     5 | 16 | 17 | 18 | 19 |
       +----+----+----+----+
     4 | 12 | 13 | 14 | 15 |
       +----+----+----+----+
     3 |  8 |  9 | 10 | 11 |
       +----+----+----+----+
     2 |  4 |  5 |  6 |  7 |
       +----+----+----+----+
     1 |  0 |  1 |  2 |  3 |
       +----+----+----+----+
          A    B    C    D
    
    */
    
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
                builder.setCharAt( sat, PieceStrings[ findPiece( pat ) ].charAt(0) );
            }
        }
        
        return "Turn " + turn + "\n" 
             + builder.toString() 
             + PlayerStrings[getPlayer()] + " to move";
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
    
    public int findPiece( int square )
    {
        int white = findWhitePiece( square );
        if ( white == EMPTY_SQUARE ) return findBlackPiece( square );
        else return white;
    }
    
    public int findWhitePiece( int square )
    {
        if ( ( bitBoards[ WHITE_KING ] & SquareBits[ square ] ) != 0 )
            return WHITE_KING;
        if ( ( bitBoards[ WHITE_QUEEN ] & SquareBits[ square ] ) != 0 )
            return WHITE_QUEEN;
        if ( ( bitBoards[ WHITE_ROOK ] & SquareBits[ square ] ) != 0 )
            return WHITE_ROOK;
        if ( ( bitBoards[ WHITE_BISHOP ] & SquareBits[ square ] ) != 0 )
            return WHITE_BISHOP;
        if ( ( bitBoards[ WHITE_PAWN ] & SquareBits[ square ] ) != 0 )
            return WHITE_PAWN;
        return EMPTY_SQUARE;
    }
    
    public int findBlackPiece( int square )
    {
        if ( ( bitBoards[ BLACK_KING ] & SquareBits[ square ] ) != 0 )
            return BLACK_KING;
        if ( ( bitBoards[ BLACK_QUEEN ] & SquareBits[ square ] ) != 0 )
            return BLACK_QUEEN;
        if ( ( bitBoards[ BLACK_ROOK ] & SquareBits[ square ] ) != 0 )
            return BLACK_ROOK;
        if ( ( bitBoards[ BLACK_BISHOP ] & SquareBits[ square ] ) != 0 )
            return BLACK_BISHOP;
        if ( ( bitBoards[ BLACK_PAWN ] & SquareBits[ square ] ) != 0 )
            return BLACK_PAWN;
        return EMPTY_SQUARE;
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
        BufferedWriter bw = new BufferedWriter( new FileWriter ( path ) );
        
        bw.newLine();
        bw.write(MagicString);
        bw.newLine();
        bw.write(""+turn);
        bw.newLine();
        bw.newLine();
        for ( int i = 0; i < ALL_SQUARES; i++ )
        {
            int piece = findPiece( i );
            if ( piece != EMPTY_SQUARE )
            {
                bw.write( i + " " + piece);
                bw.newLine();
            }
        }
        bw.newLine();
        bw.write(toString());
        bw.newLine();
        bw.close();
    }
    
    public void load(String path) throws IOException
    {
        empty();
        BufferedReader br = null;
        try {
            br = new BufferedReader( new FileReader( path ) );
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
        catch( IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (br != null) br.close();
        }
            
    }
    
    public int getPlayer()
    {
        return turn % 2;
    }
    
    // PRIVATE
    
    private void empty()
    {
        for ( int i = 0; i < ALL_BITBOARDS; i++ )
        {
            bitBoards[ i ] = 0;
        }
        materialValue[ WHITE ] = 0;
        materialValue[ BLACK ] = 0;
        numPawns[ WHITE ] = 0;
        numPawns[ BLACK ] = 0;
    }
    
    private void addPiece( int square, int piece )
    {
        bitBoards[ piece ] |= SquareBits[ square ];
        bitBoards[ ALL_PIECES + (piece % 2) ] |= SquareBits[ square ];
        materialValue[ piece % 2 ] += PieceValues[ piece ];
        if ( piece == WHITE_PAWN )
            numPawns[ WHITE ]++;
        else if ( piece == BLACK_PAWN )
            numPawns[ BLACK ]++;
    }
    
    private void removePiece( int square, int piece )
    {
        bitBoards[ piece ] ^= SquareBits[ square ];
        bitBoards[ ALL_PIECES + (piece % 2) ] ^= SquareBits[ square ];
        materialValue[ piece % 2 ] -= PieceValues[ piece ];
        if ( piece == WHITE_PAWN )
            numPawns[ WHITE ]--;
        else if ( piece == BLACK_PAWN )
            numPawns[ BLACK ]--;
    }
    
    
}
