package radikalchess.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import static radikalchess.BitBoard.*;

public class MoveMaskGenerator {
    
    public static void main(String[] args) throws IOException {
        // BufferedWriter writer = new BufferedWriter( new FileWriter( "res/moveMask" ));
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( System.out ));
        generateMoveMask( writer );
    }
    
    private static void generateMoveMask( BufferedWriter bw ) throws IOException {
        
        bw.write("// Move masks, ordered by Square");
        bw.newLine();
        bw.newLine();
        
        generateKingMask( bw );
        generateRookMask( bw );
        generateBishopMask( bw );
        
        bw.close();
    }

    private static void generateKingMask(BufferedWriter bw) throws IOException {
        bw.write("// King Moves");
        bw.newLine();
        bw.write("public final static int KING_MOVES[][] = new int[64][];");
        String king_moves = "KING_MOVES";
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
            String where = (atCenter)?"center":(atCorner)?"corner":"side";
            bw.newLine();
            bw.write("// "+ indexToString( square ) + " is at " + where);
            bw.newLine();
            bw.write(king_moves+"["+ square +"] = ");
            bw.write("new int[" + ((atCenter)?8:(atCorner)?3:5) + "];" );
            bw.newLine();
            if( atCorner ){
                if( atBottom && atLeft ){
                    bw.write(king_moves+"["+ square +"][0] = " + SQUARE_BITS[ indexTop(square) ]
                             + " // " + indexToString(indexTop(square)));
                    bw.newLine();
                    bw.write(king_moves+"["+ square +"][1] = " + SQUARE_BITS[ indexTop(indexRight(square)) ]
                             + " // " + indexToString(indexTop(indexRight(square))));
                    bw.newLine();
                    bw.write(king_moves+"["+ square +"][2] = " + SQUARE_BITS[ indexRight(square) ]
                             + " // " + indexToString(indexRight(square)));
                    continue;
                }
                if( atTop && atLeft ){
                    bw.write(king_moves+"["+ square +"][0] = " + SQUARE_BITS[ indexBottom(square) ]
                             + " // " + indexToString(indexBottom(square)));
                    bw.newLine();
                    bw.write(king_moves+"["+ square +"][1] = " + SQUARE_BITS[ indexBottom(indexRight(square)) ]
                             + " // " + indexToString(indexBottom(indexRight(square))));
                    bw.newLine();
                    bw.write(king_moves+"["+ square +"][2] = " + SQUARE_BITS[ indexRight(square) ]
                             + " // " + indexToString(indexRight(square)));
                    continue;
                }
                if( atBottom && atRight ){
                    bw.write(king_moves+"["+ square +"][0] = " + SQUARE_BITS[ indexTop(square) ]
                             + " // " + indexToString(indexTop(square)));
                    bw.newLine();
                    bw.write(king_moves+"["+ square +"][1] = " + SQUARE_BITS[ indexTop(indexLeft(square)) ]
                             + " // " + indexToString(indexTop(indexLeft(square))));
                    bw.newLine();
                    bw.write(king_moves+"["+ square +"][2] = " + SQUARE_BITS[ indexLeft(square) ]
                             + " // " + indexToString(indexLeft(square)));
                    continue;
                }
                if( atTop && atRight ){
                    bw.write(king_moves+"["+ square +"][0] = " + SQUARE_BITS[ indexBottom(square) ]
                             + " // " + indexToString(indexBottom(square)));
                    bw.newLine();
                    bw.write(king_moves+"["+ square +"][1] = " + SQUARE_BITS[ indexBottom(indexLeft(square)) ]
                             + " // " + indexToString(indexBottom(indexLeft(square))));
                    bw.newLine();
                    bw.write(king_moves+"["+ square +"][2] = " + SQUARE_BITS[ indexLeft(square) ]
                             + " // " + indexToString(indexLeft(square)));
                    continue;
                }
            }
            if( atTop ){
                bw.write(king_moves+"["+ square +"][0] = " + SQUARE_BITS[ indexRight(square) ]
                         + " // " + indexToString(indexRight(square)));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][1] = " + SQUARE_BITS[ indexBottom(indexRight(square)) ]
                         + " // " + indexToString(indexBottom(indexRight(square))));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][2] = " + SQUARE_BITS[ indexBottom(square) ]
                         + " // " + indexToString(indexBottom(square)));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][3] = " + SQUARE_BITS[ indexBottom(indexLeft(square)) ]
                         + " // " + indexToString(indexBottom(indexLeft(square))));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][4] = " + SQUARE_BITS[ indexLeft(square) ]
                         + " // " + indexToString(indexLeft(square)));
                continue;
            }
            if( atLeft ){
                bw.write(king_moves+"["+ square +"][0] = " + SQUARE_BITS[ indexTop(square) ]
                         + " // " + indexToString(indexTop(square)));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][1] = " + SQUARE_BITS[ indexTop(indexRight(square)) ]
                         + " // " + indexToString(indexTop(indexRight(square))));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][2] = " + SQUARE_BITS[ indexRight(square) ]
                         + " // " + indexToString(indexRight(square)));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][3] = " + SQUARE_BITS[ indexBottom(indexRight(square)) ]
                         + " // " + indexToString(indexBottom(indexRight(square))));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][4] = " + SQUARE_BITS[ indexBottom(square) ]
                         + " // " + indexToString(indexBottom(square)));
                continue;
            }
            if( atBottom ){
                bw.write(king_moves+"["+ square +"][0] = " + SQUARE_BITS[ indexLeft(square) ]
                         + " // " + indexToString(indexLeft(square)));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][1] = " + SQUARE_BITS[ indexTop(indexLeft(square)) ]
                         + " // " + indexToString(indexTop(indexLeft(square))));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][2] = " + SQUARE_BITS[ indexTop(square) ]
                         + " // " + indexToString(indexTop(square)));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][3] = " + SQUARE_BITS[ indexTop(indexRight(square)) ]
                         + " // " + indexToString(indexTop(indexRight(square))));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][4] = " + SQUARE_BITS[ indexRight(square) ]
                         + " // " + indexToString(indexRight(square)));
                continue;
            }
            if( atRight ){
                bw.write(king_moves+"["+ square +"][0] = " + SQUARE_BITS[ indexBottom(square) ]
                         + " // " + indexToString(indexBottom(square)));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][1] = " + SQUARE_BITS[ indexBottom(indexLeft(square)) ]
                         + " // " + indexToString(indexBottom(indexLeft(square))));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][2] = " + SQUARE_BITS[ indexLeft(square) ]
                         + " // " + indexToString(indexLeft(square)));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][3] = " + SQUARE_BITS[ indexTop(indexLeft(square)) ]
                         + " // " + indexToString(indexTop(indexLeft(square))));
                bw.newLine();
                bw.write(king_moves+"["+ square +"][4] = " + SQUARE_BITS[ indexTop(square) ]
                         + " // " + indexToString(indexTop(square)));
                continue;
            }
            bw.write(king_moves+"["+ square +"][0] = " + SQUARE_BITS[ indexTop(square) ]
                     + " // " + indexToString(indexTop(square)));
            bw.newLine();
            bw.write(king_moves+"["+ square +"][1] = " + SQUARE_BITS[ indexTop(indexRight(square)) ]
                     + " // " + indexToString(indexTop(indexRight(square))));
            bw.newLine();
            bw.write(king_moves+"["+ square +"][2] = " + SQUARE_BITS[ indexRight(square) ]
                     + " // " + indexToString(indexRight(square)));
            bw.newLine();
            bw.write(king_moves+"["+ square +"][3] = " + SQUARE_BITS[ indexBottom(indexRight(square)) ]
                     + " // " + indexToString(indexBottom(indexRight(square))));
            bw.newLine();
            bw.write(king_moves+"["+ square +"][4] = " + SQUARE_BITS[ indexBottom(square) ]
                     + " // " + indexToString(indexBottom(square)));
            bw.newLine();
            bw.write(king_moves+"["+ square +"][5] = " + SQUARE_BITS[ indexBottom(indexLeft(square)) ]
                     + " // " + indexToString(indexBottom(indexLeft(square))));
            bw.newLine();
            bw.write(king_moves+"["+ square +"][6] = " + SQUARE_BITS[ indexLeft(square) ]
                     + " // " + indexToString(indexLeft(square)));
            bw.newLine();
            bw.write(king_moves+"["+ square +"][7] = " + SQUARE_BITS[ indexTop(indexLeft(square)) ]
                     + " // " + indexToString(indexTop(indexLeft(square))));
            
        }
        bw.newLine();
    }

    private static void generateRookMask(BufferedWriter bw) {
    }

    private static void generateBishopMask(BufferedWriter bw) {
    }
    
    
    

}
