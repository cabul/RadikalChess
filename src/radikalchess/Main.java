package radikalchess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static radikalchess.Move.MoveBuilder;

public class Main
{

    private static final MoveBuilder turn = new MoveBuilder();
    
    private static BitBoard board = new BitBoard();
    
    public static void main(String[] args) throws IOException 
    {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        
        board.reset();
        
        repl(br);
        
    }
    
    private static void repl(BufferedReader br) throws IOException
    {
        String[] args;
        
        boolean running = true;
        
        System.out.println("Welcome to Radikal Chess");
        
        print( board );
        
        while ( running )
        {
            args = br.readLine().split(" ");
            switch (args[0]) 
            {
                case "exit":
                    running = false;
                    break;
                case "move":
                    if( args.length < 3 )
                    {
                        error("Not enough arguments");
                        break;
                    }
                    int aux = BitBoard.indexFromString( args[1] );
                    if( aux < 0 )
                    {
                        error("Index not valid");
                        break;
                    }
                    turn.from(aux);
                    turn.move( board.pieceAt(aux) );
                    aux = BitBoard.indexFromString( args[2] );
                    if( aux < 0 )
                    {
                        error("Index not valid");
                        break;
                    }
                    turn.to(aux);
                    turn.capture( board.pieceAt(aux) );
                    Move m = turn.build();
                    System.out.println(m.toString());
                    board.applyMove(m);
                    print( board );
                    break;
                case "at":
                    if( args.length < 2)
                    {
                        error("Not enough arguments");
                        break;
                    }
                    int index = BitBoard.indexFromString(args[1]);
                    if( index < 0 )
                    {
                        error("Index not valid");
                        break;
                    }
                    System.out.println(BitBoard.VERBOSE[board.pieceAt(index)]);
                    break;
                case "print":
                    print( board );
                    break;
                case "reset":
                    board.reset();
                    break;
                case "save":
                    if( args.length < 2)
                    {
                        error("Not enough arguments");
                        break;
                    }
                    try 
                    {
                        board.save( args[1] );
                    } catch(IOException e) {
                        error("Saving: " + e.getMessage() );
                    }
                    break;
                case "load":
                    if( args.length < 2)
                    {
                        error("Not enough arguments");
                        break;
                    }
                    try 
                    {
                        board.load( args[1] );
                    } catch(IOException e) {
                        error("Saving: " + e.getMessage() );
                    }
                    break;
                case "help":
                    System.out.println("There is no help...You are all alone");
                    break;
                case "hint":
                    System.out.println("Not implemented");
                    break;
                case "level":
                    System.out.println("Not implemented");
                    break;
                case "white":
                    System.out.println("Not implemented");
                    break;
                case "black":
                    System.out.println("Not implemented");
                default:
                    error("I don't understand");
                    break;
            }
            
        }
        
    }
    
    private static void print(BitBoard board)
    {
        System.out.println( board.toString() );
    }
    
    private static void error(String msg)
    {
        System.out.println("Error: " + msg);
        System.out.println("Try help");
    }
    
}
