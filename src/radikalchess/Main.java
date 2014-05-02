package radikalchess;

import radikalchess.model.Move;
import radikalchess.model.Board;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static radikalchess.model.Move.MoveBuilder;

public class Main
{

    private static final MoveBuilder turn = new MoveBuilder();
    
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
            switch (cl[0]) 
            {
                case "end":
                    running = false;
                    break;
                case "move":
                    Move m = turn.with(board,cl[1],cl[2]).build();
                    System.out.println(m.toString());
                    board.applyMove(m);
                    break;
                case "at":
                    int index = Board.indexFromString(cl[1]);
                    System.out.println(index + ": " + Board.PieceStrings[board.findPiece(index)]);
                    break;
                case "verbose":
                    switch (cl[1]) 
                    {
                    case "on":
                        verbose = true;
                        break;
                    case "off":
                        verbose = false;
                        break;
                    default:
                        System.out.println("error");
                        break;
                    }
                    break;
                case "reset":
                    board.reset();
                    break;
                case "save":
                    board.save( cl[1] );
                    break;
                case "load":
                    board.load( cl[1] );
                    break;
                case "help":
                    System.out.println("There is no help...You are all alone");
                    break;
                default:
                    System.out.println("Not supported");
                    break;
            }
            
        }
        
    }
    
}
