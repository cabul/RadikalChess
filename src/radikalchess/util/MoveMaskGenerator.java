package radikalchess.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import static radikalchess.BitBoard.*;

public class MoveMaskGenerator {
    
    public static void main(String[] args) throws IOException {
        //try (BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( System.out ))) {
        try (BufferedWriter writer = new BufferedWriter( new FileWriter("res/movemask.java"))) {    
            generateMoveMask( writer );
            generateDistanceMask( writer );
        }   
    }
    
    private static void generateMoveMask( BufferedWriter bw ) throws IOException {
        bw.write("// Move masks, ordered by Square");
        bw.newLine();
        bw.newLine();
        generateKingMask( bw );
        generateRookMask( bw );
        generateBishopMask( bw );
    }

    private static void generateKingMask(BufferedWriter bw) throws IOException {
        String king = "KING_MASK";
        String prefix = "public static final int[] ";
        String suffix = " = new int["+ALL_SQUARES+"];";
        bw.write("// " + king); bw.newLine();
        bw.write(prefix + king + suffix); bw.newLine();
        for( int square = 0; square < ALL_SQUARES; square++ )
        {
            boolean atBottom = square < COLUMNS;
            boolean atTop = square >= ALL_SQUARES - COLUMNS;
            boolean atLeft = square % COLUMNS == 0;
            boolean atRight = ( square + 1 ) % COLUMNS == 0;
            boolean atCenter = !( atBottom || atTop || atLeft || atRight );
            boolean atCorner = atBottom && atLeft 
                            || atBottom && atRight
                            || atTop && atLeft
                            || atTop && atRight;
            int mask = 0;
            if( !atBottom ) mask |= SQUARE_BITS[ indexBottom(square) ];
            if( !atTop ) mask |= SQUARE_BITS[ indexTop(square) ];
            if( !atLeft ) mask |= SQUARE_BITS[ indexLeft(square) ];
            if( !atRight ) mask |= SQUARE_BITS[ indexRight(square) ];
            if( !atBottom && !atRight) mask |= SQUARE_BITS[ indexBottom(indexRight(square))];
            if( !atBottom && !atLeft) mask |= SQUARE_BITS[ indexBottom(indexLeft(square))];
            if( !atTop && !atRight) mask |= SQUARE_BITS[ indexTop(indexRight(square))];
            if( !atTop && !atLeft) mask |= SQUARE_BITS[ indexTop(indexLeft(square))];
            
            bw.write( king+"["+square+"]"+" = "+mask+";"); bw.newLine();
        }
        
            
    }

    private static void generateRookMask(BufferedWriter bw) throws IOException {
        String rook = "ROOK_MASK";
        String prefix = "public static final int[] ";
        String suffix = " = new int["+ALL_SQUARES+"];";
        bw.write("// " + rook); bw.newLine();
        bw.write(prefix + rook + suffix); bw.newLine();
        int complete_row = 0; // 1
        int complete_column = 0; // A
        for( int row = 0; row < ALL_SQUARES; row = indexTop(row) )
        {
            complete_column |= SQUARE_BITS[ row ];
        }
        for( int col = 0; col < COLUMNS; col = indexRight(col) )
        {
            complete_row |= SQUARE_BITS[ col ];
        }
        for( int square = 0; square < ALL_SQUARES; square++ )
        {
            int row = square / COLUMNS;
            int col = square % COLUMNS;
            
            int mask = 0;
            
            mask |= complete_row << ( row * COLUMNS );
            mask ^= complete_column << col;
            
            bw.write( rook+"["+square+"]"+" = "+mask+";"); bw.newLine();
        }
    }

    private static void generateBishopMask(BufferedWriter bw) throws IOException {
        String bishop = "BISHOP_MASK";
        String prefix = "public static final int[] ";
        String suffix = " = new int["+ALL_SQUARES+"];";
        bw.write("// " + bishop); bw.newLine();
        bw.write(prefix + bishop + suffix); bw.newLine();
        for( int square = 0; square < ALL_SQUARES; square++ )
        {
            int sq_row = square / COLUMNS;
            int sq_col = square % COLUMNS;
            
            int mask = 0;
            
            for( int row = sq_row, col = sq_col;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row++, col++ )
                mask |= SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            
            for( int row = sq_row, col = sq_col;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row++, col-- )
                mask |= SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            
            for( int row = sq_row, col = sq_col;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row--, col-- )
                mask |= SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            for( int row = sq_row, col = sq_col;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row--, col++ )
                mask |= SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            mask ^= SQUARE_BITS[ square ];
            
            bw.write( bishop+"["+square+"]"+" = "+mask+";"); bw.newLine();
        }
    }
    
    private static void generateDistanceMask(BufferedWriter bw) throws IOException
    {
        bw.write("// Distance Map for Pieces"); bw.newLine();
        bw.write("// Ordererd by King position, Piece position"); bw.newLine();
        String distance = "DISTANCE_MAP";
        String prefix = "public static final int[][] ";
        String suffix = " = new int["+ALL_SQUARES+"]["+ALL_SQUARES+"];";
        bw.write(prefix + distance + suffix); bw.newLine();
        for( int king = 0; king < ALL_SQUARES; king++ )
        {
            for( int piece = 0; piece < ALL_SQUARES; piece++ )
            {
                int mask = 0;
                int threshold = getEuclideanDistance2( king, piece );
                if( king != piece )
                {
                    for( int square = 0; square < ALL_SQUARES; square++ )
                    {
                        if( getEuclideanDistance2( king, square ) < threshold )
                            mask |= SQUARE_BITS[ square ];
                    }
                }
                
                bw.write( distance + "["+king+"]["+piece+"] = "+ mask + ";"); bw.newLine();
            }
        }
    }
    
    private static int getEuclideanDistance2(int a, int b)
    {
        int arow = a / COLUMNS;
        int acol = a % COLUMNS;
        int brow = b / COLUMNS;
        int bcol = b % COLUMNS;
        
        return (brow-arow)*(brow-arow) + (bcol-acol)*(bcol-acol);
    }

}
