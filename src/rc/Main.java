package rc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{

    private static Move move = new Move();
    
    private static Board board = new Board();
    
    public static void main(String[] args) throws IOException 
    {
        System.out.println("Welcome to Radikal Chess");
        
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        
        String line;
        String[] cl = null;
        
        boolean running = true;
        boolean verbose = true;
        
        while ( running )
        {
            if(verbose)
            {
                System.out.println( board.toString() );
            }
            line = br.readLine();
            
            cl = line.split(" ");
            
            if ( cl[0].equals( "end" ) )
            {
                running = false;
            }
            else if ( cl[0].equals( "move" ))
            {
                parseMove(cl[1],cl[2]);
                board.applyMove(move);
                System.out.println(move.toString());
            }
            else if ( cl[0].equals( "at" ))
            {
                int index = parsePosition(cl[1]);
                System.out.println(index + ": " + Board.PieceStrings[board.findPiece(index)]);
            }
            else if ( cl[0].equals( "verbose" ))
            {
                if ( cl[1].equals("on") )
                    verbose = true;
                else if ( cl[1].equals("off") )
                    verbose = false;
                else
                    System.out.println("error");
            }
            else if ( cl[0].equals( "reset" ))
            {
                board.reset();
            }
            else if ( cl[0].equals( "save" ))
            {
                board.save( cl[1] );
            }
            else if ( cl[0].equals( "load" ))
            {
                board.load( cl[1] );
            }
            else 
                System.out.println("Not supported");
            
        }
        
    }
    
    private static void parseMove(String from, String to)
    {
        move.sourceSquare = parsePosition(from);
        
        move.destinationSquare = parsePosition(to);
        
        move.movingPiece = board.findPiece( move.sourceSquare );
        move.capturedPiece = board.findPiece( move.destinationSquare );
        
        if ( ( move.movingPiece < Board.BISHOP ) && board.isEndRow(move.destinationSquare) )
        {
            move.moveType = Move.MOVE_PROMOTION;
        }
        else if ( move.capturedPiece == Board.EMPTY_SQUARE )
        {
            move.moveType = Move.MOVE_NORMAL;
        }
        else
        {
            move.moveType = Move.MOVE_CAPTURE;
        }
        
        
    }
    
    private static int parsePosition(String pos)
    {
        int row = Integer.parseInt( pos.substring(1) ) - 1;
        int col = ( (int) Character.toUpperCase( pos.charAt(0) ) ) - 65 ;
        return row * Board.COLUMNS + col;
    }

}
