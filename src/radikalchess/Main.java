package radikalchess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    
    private static BufferedWriter cout;
    private static BufferedWriter cerr;
    private static BufferedReader cin;
    
    private static HashMap<String,Command> commands;
    
    private static enum Return { VOID, PRINT, EXIT, ERROR };
    
    private static interface Command
    {
        Return execute(String[] args) throws IOException;
    }
    
    private static Command DEFAULT;
    
    private static void println(Object msg) throws IOException
    {
        // mapped to cout
        cout.write(msg.toString()); cout.newLine();
    }
    
    private static Return errorln(Object msg) throws IOException
    {
        // mapped to cerr
        cerr.write(msg.toString()); cerr.newLine();
        return Return.ERROR;
    }
    
    private static void repl() throws IOException
    {
        Command c = commands.get("new");
        String[] args = null;
        Return rv;
        while( (rv = c.execute(args)) != Return.EXIT )
        {
            if( rv == Return.PRINT) 
                commands.get("print").execute(null);
            cout.flush();
            cerr.flush();
            args = cin.readLine().split(" ");
            c = commands.get( args[0].toLowerCase() );
            if( c == null ) c = DEFAULT;
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        cin = new BufferedReader(new InputStreamReader(System.in));
        cout = new BufferedWriter(new OutputStreamWriter(System.out));
        cerr = new BufferedWriter(new OutputStreamWriter(System.err));
        
        commands = new HashMap();
        
        final Game game = new Game();
        final PrettyPrinter printer = new PrettyPrinter();
        final List<Move> moveBuffer = new ArrayList();
        final List<Position> positionBuffer = new ArrayList();
        
        final Heuristic hValue = new Heuristic(){

            @Override
            public int eval(Board board) {
                return board.info(board.player()).value();
            }
            
        };
        
        final AISearch ai = new AISearch(hValue);
        
        DEFAULT = new Command(){
            @Override
            public Return execute(String[] args) throws IOException {
                Move move = Move.fromString(args[0]);
                if( move != null ){
                    if( game.board().valid(move) ){
                        game.advance(move);
                    } else {
                        return errorln("No valid Move: '"+move+"'");
                    }
                }
                else return errorln("I don't understand '" + args[0]+"'");
                return Return.PRINT;
            }
        };
        
        commands.put("exit", new Command(){
            @Override
            public Return execute(String[] args) throws IOException {
                cin.close();
                cout.close();
                cerr.close();
                return Return.EXIT;
            } 
        });
        
        commands.put("new", new Command(){
            @Override
            public Return execute(String[] args) throws IOException {
                println("Starting new Game");
                game.reset();
                return Return.PRINT;
            } 
        });
        
        commands.put("help", new Command(){
            @Override
            public Return execute(String[] args) throws IOException {
                for( String c : commands.keySet() )
                    println(c);
                return Return.VOID;
            } 
        });
        
        commands.put("print", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                println("Turn " + game.turn());
                printer.clear().load(game.board()).print(cout);
                println(game.player()+" to move");
                return Return.VOID;
            }
        });
        
        commands.put("at", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                for( int i = 1; i < args.length; i++ )
                {
                    Position pos = Position.fromString(args[i]);
                    if( pos == null ) errorln("Wrong Position Format: '"+args[i]+"'");
                    else println(pos + ": " + game.board().at(pos));
                }
                return Return.VOID;
            }
        });
        
        commands.put("undo", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if( !game.undo() ) return errorln("No moves to undo");
                return Return.PRINT;
            }
        });
        
        commands.put("redo", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if( !game.redo() ) return errorln("No moves to redo");
                return Return.PRINT;
            }
        });
        
        commands.put("save", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if(args.length == 1) game.save(cout);
                else{
                    try (BufferedWriter file = new BufferedWriter(new FileWriter(args[1]))) {
                        game.save(file);
                    } catch(Exception ex)
                    {
                        return errorln("Saving error");
                    }
                    println("Saving complete");
                }
                return Return.VOID;
            }
        });
        
        commands.put("load", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if(args.length == 1) game.load(cin);
                else{
                    try (BufferedReader file = new BufferedReader(new FileReader(args[1]))) {
                        game.load(file);
                    } catch(Exception ex)
                    {
                        return errorln("Loading error");
                    }
                    println("Loading complete");
                }
                return Return.PRINT;
            }
        });
        
        commands.put("mark", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                positionBuffer.clear();
                for( int i = 1; i < args.length; i++ )
                {
                    positionBuffer.add( Position.fromString(args[i]) );
                }
                printer.clear().load(game.board()).positions(positionBuffer).print(cout);
                return Return.VOID;
            }
        });
        
        commands.put("color", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if( args.length < 2 ) return errorln("Which Color?");
                Color color = Color.fromString(args[1]);
                if( color == null ) return errorln("'" + args[1] + "' is not a Color");
                positionBuffer.clear();
                for( Position pos : game.board().info(color).pieces())
                    positionBuffer.add(pos);
                printer.clear().load(game.board()).positions(positionBuffer).print(cout);
                return Return.VOID;
            }
        });
        
        commands.put("attacks", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if( args.length < 2 ) return errorln("Which Position?");
                Color color  =  Color.fromString(args[1]);
                Position pos = Position.fromString(args[1]);
                positionBuffer.clear();
                if( color != null ) {
                    Generator.genAllAttacks(positionBuffer, game.board(), color);
                } 
                else if( pos != null ) {
                    Piece piece = game.board().at(pos);
                    if( piece == null ) return errorln("No piece at "+pos);
                    Generator.genAttacks(positionBuffer, game.board(), piece.color, pos);
                }
                else {
                    return errorln("Wrong argument: '"+args[1]+"'");
                }
                printer.clear().load(game.board()).positions(positionBuffer).print(cout);
                return Return.VOID;
            }
        });
        
        commands.put("test", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                Generator.test(cerr,args[1]);
                return Return.VOID;
            }
        });
        
        commands.put("moves", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                Color color;
                Position pos = null;
                if( args.length < 2 ){
                    color = game.board().player();
                } else {
                    color = Color.fromString(args[1]);
                    pos = Position.fromString(args[1]);
                }
                moveBuffer.clear();
                if( color != null ) {
                    Generator.genAllMoves(moveBuffer, game.board(), color);
                    printer.clear().load(game.board()).moves(moveBuffer).print(cout);
                }
                else if( pos != null ) {
                    Piece piece = game.board().at(pos);
                    if( piece == null ) return errorln("No piece at "+pos);
                    Generator.genMoves(moveBuffer, game.board(), piece.color, pos);
                    printer.clear().load(game.board()).moves(moveBuffer).print(cout);
                }
                return Return.VOID;
            }
        });
        
        commands.put("decide", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                Move move = ai.decide(game.board());
                return Return.VOID;
            }
        });
       
        repl();
        
    }

}
