package radikalchess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static radikalchess.Move.MoveBuilder;

public class Main
{
    
    public static void main(String[] args) throws IOException 
    {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        
        repl(br);
        
    }
    
    private static void repl(BufferedReader br) throws IOException
    {
        String[] args;
        
        Stack stack = new Stack();
        
        stack.push( new BitBoard().reset() );
        
        boolean running = true;
        
        System.out.println("Welcome to Radikal Chess");
        
        while ( running )
        {
            args = br.readLine().split(" ");
            switch (args[0].toLowerCase()) 
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
                    MoveBuilder turn = new MoveBuilder( stack.current() );
                    int aux = BitBoard.indexFromString( args[1] );
                    if( aux < 0 )
                    {
                        error("Index not valid");
                        break;
                    }
                    
                    turn.from(aux);
                    aux = BitBoard.indexFromString( args[2] );
                    if( aux < 0 )
                    {
                        error("Index not valid");
                        break;
                    }
                    turn.to(aux);
                    Move m = turn.build();
                    System.out.println(m.toString());
                    stack.apply( m );
                    stack.printTop();
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
                    System.out.println(BitBoard.VERBOSE[stack.current().pieceAt(index)]);
                    break;
                case "print":
                    stack.printTop();
                    break;
                case "save":
                    if( args.length < 2)
                    {
                        error("Not enough arguments");
                        break;
                    }
                    if( !stack.save( args[1] ))
                        error("Saving problem");
                    else
                        System.out.println("Saving complete");
                    break;
                case "load":
                    if( args.length < 2)
                    {
                        error("Not enough arguments");
                        break;
                    }
                    if( !stack.load( args[1] ) )
                        error("Loading problem");
                    else 
                    {
                        System.out.println("Loading complete");
                        stack.printTop();
                    }
                    break;
                case "undo":
                    if( !stack.backwards() )
                        error("No undo moves");
                    else
                        stack.printTop();
                    break;
                case "redo":
                    if( !stack.forwards() )
                        error("No redo moves");
                    else
                        stack.printTop();
                    break;
                case "turn":
                    if( args.length < 2)
                    {
                        System.out.println("Turn "+stack.current().turn());
                        System.out.println(BitBoard.VERBOSE[stack.current().player()] + " to move");
                    }
                    else 
                    {
                        stack.move( Integer.parseInt(args[1]));
                        stack.printTop();
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
    
    private static void error(String msg)
    {
        System.out.println("Error: " + msg);
        System.out.println("Try help");
    }
    
    
    private static class Stack {
        
        private ArrayList<BitBoard> list;
        
        private int head;
        
        public Stack()
        {
            head = 0;
            list = new ArrayList<>();
        }
        
        public BitBoard current()
        {
            return list.get(head-1);
        }
        
        public void push(BitBoard board)
        {
            while( head < list.size() )
                list.remove( list.size() - 1 );
                    
            list.add( board );
            head++;
        }
        
        public void apply(Move move)
        {
            push( current().copy().applyMove(move) );
        }
        
        public boolean forwards()
        {
            if( head == list.size())
                return false;
            head++;
            return true;
        }
        
        public boolean backwards()
        {
            if( head == 1 )
                return false;
            head--;
            return true;
        }
        
        public boolean save(String path)
        {
            try {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                    BitBoard.save( writer, list);
                }
                return true;
            } catch (IOException ex) {
                return false;
            }
        }
        
        public boolean load(String path)
        {
            try {
                try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                    BitBoard.load( reader, list);
                }
                head = list.size();
                return true;
            } catch (IOException ex) {
                return false;
            }
        }
        
        public void printTop()
        {
            System.out.println( current().toString());
        }
        
        public boolean move(int head)
        {
            if( head > 0 && head <= list.size() ) 
            {
                this.head = head;
                return true;
            } else return false;
        }
        
    }
    
}
