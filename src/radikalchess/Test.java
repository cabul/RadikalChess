package radikalchess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import radikalchess.Move.MoveList;
import radikalchess.Position.PositionList;

public class Test {
    
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
        
        DEFAULT = new Command(){
            @Override
            public Return execute(String[] args) throws IOException {
                Move move = Move.fromString(args[0]);
                MoveList moves = new MoveList();
                Generator.genAllMoves(moves,game.board(), game.player());
                if( move != null ){
                    if( moves.contains(move) ){
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
                    }
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
                    }
                }
                return Return.PRINT;
            }
        });
        
        commands.put("mark", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                PositionList marks = new PositionList();
                for( int i = 1; i < args.length; i++ )
                {
                    marks.add( Position.fromString(args[i]) );
                }
                printer.clear().load(game.board()).highlight(marks).print(cout);
                return Return.VOID;
            }
        });
        
        commands.put("color", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if( args.length < 2 ) return errorln("Which Color?");
                Color color = Color.fromString(args[1]);
                if( color == null ) return errorln("'" + args[1] + "' is not a Color");
                PositionList marks = new PositionList();
                for( Position pos : game.board().info(color).pieces())
                    marks.add(pos);
                printer.clear().load(game.board()).highlight(marks).print(cout);
                return Return.VOID;
            }
        });
        
        commands.put("attacks", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if( args.length < 2 ) return errorln("Which Position?");
                Color color  =  Color.fromString(args[1]);
                Position pos = Position.fromString(args[1]);
                PositionList marks = new PositionList();
                if( color != null ) {
                    Generator.genAllAttacks(marks, game.board(), color);
                } 
                else if( pos != null ) {
                    Piece piece = game.board().at(pos);
                    if( piece == null ) return errorln("No piece at "+pos);
                    Generator.genAttacks(marks, game.board(), piece.color, pos);
                }
                else {
                    return errorln("Wrong argument: '"+args[1]+"'");
                }
                printer.clear().load(game.board()).highlight(marks).print(cout);
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
        
        commands.put("reset", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                game.reset();
                return Return.PRINT;
            }
        });
        commands.put("moves", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                MoveList moves = new MoveList();
                Color color = Color.fromString(args[1]);
                Position pos = Position.fromString(args[1]);
                if( color != null ) {
                    Generator.genAllMoves(moves, game.board(), color);
                    printer.clear().load(game.board()).highlight(moves.from()).print(cout);
                }
                else if( pos != null ) {
                    Piece piece = game.board().at(pos);
                    if( piece == null ) return errorln("No piece at "+pos);
                    Generator.genMoves(moves, game.board(), piece.color, pos);
                    printer.clear().load(game.board()).highlight(moves.to()).print(cout);
                }
                return Return.VOID;
            }
        });
       
        repl();
        
    }

}
