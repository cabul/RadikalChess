package radikalchess.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import radikalchess.BitBoard;
import static radikalchess.BitBoard.*;

public class MoveMaskGenerator {
    
    public static void main(String[] args) throws IOException {
        try (BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( System.out ))) {
        //try (BufferedWriter writer = new BufferedWriter( new FileWriter("res/movemask.java"))) {    
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
        String prefix = "public static final int[][] ";
        String suffix = " = new int["+ALL_SQUARES+"][];";
        bw.write("// " + king + " Square, Step"); bw.newLine();
        bw.write(prefix + king + suffix); bw.newLine();
        bw.write("static"); bw.newLine();
        bw.write("{"); bw.newLine();
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
            int[] masks = new int[8];
            int count = 0;
            if( !atBottom ) masks[count++] = SQUARE_BITS[ indexBottom(square) ];
            if( !atTop ) masks[count++] = SQUARE_BITS[ indexTop(square) ];
            if( !atLeft ) masks[count++] = SQUARE_BITS[ indexLeft(square) ];
            if( !atRight ) masks[count++] = SQUARE_BITS[ indexRight(square) ];
            if( !atBottom && !atRight) masks[count++] = SQUARE_BITS[ indexBottom(indexRight(square))];
            if( !atBottom && !atLeft) masks[count++] = SQUARE_BITS[ indexBottom(indexLeft(square))];
            if( !atTop && !atRight) masks[count++] = SQUARE_BITS[ indexTop(indexRight(square))];
            if( !atTop && !atLeft) masks[count++] = SQUARE_BITS[ indexTop(indexLeft(square))];
            
            bw.write( king+"["+square+"]"+" = new int["+count+"];"); bw.newLine();
            for( int i = 0; i < count; i++ )
            {
                bw.write( king+"["+square+"]["+i+"] = "+masks[i]+";"); bw.newLine();
            } 
        }
        bw.write("}"); bw.newLine();    
    }

    private static void generateRookMask(BufferedWriter bw) throws IOException {
        String rook = "ROOK_MASK";
        String prefix = "public static final int[][][] ";
        String suffix = " = new int["+ALL_SQUARES+"][][];";
        bw.write("// " + rook + " Square, Ray, Step"); bw.newLine();
        bw.write(prefix + rook + suffix); bw.newLine();
        bw.write("static"); bw.newLine();
        bw.write("{"); bw.newLine();
        
        for( int square = 0; square < ALL_SQUARES; square++ )
        {
            int sq_row = square / COLUMNS;
            int sq_col = square % COLUMNS;
            
            int[][] buffer = new int[4][ALL_SQUARES];
            int[] steps = new int[4];
            int ray = 0;
            
            for( int row = sq_row+1, col = sq_col;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row++ )
                buffer[ray][steps[ray]++] = SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            if( steps[ray] > 0 ) ray++;
            
            for( int row = sq_row-1, col = sq_col;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row-- )
                buffer[ray][steps[ray]++] = SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            if( steps[ray] > 0 ) ray++;
            
            for( int row = sq_row, col = sq_col+1;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 col++ )
                buffer[ray][steps[ray]++] = SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            if( steps[ray] > 0 ) ray++;
            
            for( int row = sq_row, col = sq_col-1;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 col-- )
                buffer[ray][steps[ray]++] = SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            if( steps[ray] > 0 ) ray++;
            
            bw.write( rook+"["+square+"]"+" = new int["+ray+"][];"); bw.newLine();
            for( int r = 0; r < ray; r++ )
            {
                bw.write( rook+"["+square+"]["+r+"] = new int["+steps[r]+"];"); bw.newLine();
                for( int s = 0; s < steps[r]; s++ )
                {
                    bw.write( rook+"["+square+"]["+r+"]["+s+"] = "+buffer[r][s]+";"); bw.newLine();
                }
            }
        }
        bw.write("}"); bw.newLine();
    }

    private static void generateBishopMask(BufferedWriter bw) throws IOException {
        String bishop = "BISHOP_MASK";
        String prefix = "public static final int[][][] ";
        String suffix = " = new int["+ALL_SQUARES+"][][];";
        bw.write("// " + bishop + " Square, Ray, Step"); bw.newLine();
        bw.write(prefix + bishop + suffix); bw.newLine();
        bw.write("static"); bw.newLine();
        bw.write("{"); bw.newLine();
        for( int square = 0; square < ALL_SQUARES; square++ )
        {
            int sq_row = square / COLUMNS;
            int sq_col = square % COLUMNS;
            
            int[][] buffer = new int[4][ALL_SQUARES];
            
            int[] steps = new int[4];
            
            int ray = 0;
            
            for( int row = sq_row+1, col = sq_col+1;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row++, col++ )
                buffer[ray][steps[ray]++] = SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            if( steps[ray] > 0 ) ray++;
            
            for( int row = sq_row+1, col = sq_col-1;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row++, col-- )
                buffer[ray][steps[ray]++] = SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            if( steps[ray] > 0 ) ray++;
            
            for( int row = sq_row-1, col = sq_col-1;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row--, col-- )
                buffer[ray][steps[ray]++] = SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            if( steps[ray] > 0 ) ray++;
            
            for( int row = sq_row-1, col = sq_col+1;
                 row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
                 row--, col++ )
                buffer[ray][steps[ray]++] = SQUARE_BITS[ indexFromRowColumn(row,col) ];
            
            if( steps[ray] > 0 ) ray++;
            
            bw.write( bishop+"["+square+"]"+" = new int["+ray+"][];"); bw.newLine();
            for( int r = 0; r < ray; r++ )
            {
                bw.write( bishop+"["+square+"]["+r+"] = new int["+steps[r]+"];"); bw.newLine();
                for( int s = 0; s < steps[r]; s++ )
                {
                    bw.write( bishop+"["+square+"]["+r+"]["+s+"] = "+buffer[r][s]+";"); bw.newLine();
                }
            }
        }
        bw.write("}"); bw.newLine();
    }
    
    private static void generateDistanceMask(BufferedWriter bw) throws IOException
    {
        bw.write("// Distance Map for Pieces"); bw.newLine();
        bw.write("// Ordererd by King position, Piece position"); bw.newLine();
        String distance = "DISTANCE_MAP";
        String prefix = "public static final int[][] ";
        String suffix = " = new int["+ALL_SQUARES+"]["+ALL_SQUARES+"];";
        bw.write(prefix + distance + suffix); bw.newLine();
        bw.write("static"); bw.newLine();
        bw.write("{"); bw.newLine();
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
        bw.write("}"); bw.newLine();
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
