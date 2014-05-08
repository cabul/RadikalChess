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
    private static Command NOT_IMPLEMENTED;
    
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
        
        DEFAULT = new Command(){
            @Override
            public Return execute(String[] args) throws IOException {
                println("I don't understand '" + args[0]+"'");
                println("Try help");
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
                println("TODO: help");
                return Return.VOID;
            } 
        });
        
        commands.put("print", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                println("Turn " + game.turn());
                println(new PrettyPrinter(game.board()));
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
        
        commands.put("move", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if( args.length < 3 ) return errorln("Not enough arguments");
                Move.Builder turn = new Move.Builder(game.board());
                Position pos;
                pos = Position.fromString(args[1]);
                if( pos == null ) return errorln("Wrong Position Format: '"+args[1]+"'");
                turn.from(pos);
                pos = Position.fromString(args[2]);
                if( pos == null ) return errorln("Wrong Position Format: '"+args[2]+"'");
                turn.to(pos);
                Move move = turn.build();
                println(move);
                game.advance(move);
                return Return.PRINT;
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
        
        commands.put("hint", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                return Return.VOID;
            }
        });
        
        commands.put("mark", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                ArrayList<Position> marks = new ArrayList<>();
                for( int i = 1; i < args.length; i++ )
                {
                    marks.add( Position.fromString(args[i]) );
                }
                println(new PrettyPrinter(game.board()).highlight( BitBoard.mask(marks)));
                return Return.VOID;
            }
        }); 
       
        
        repl();
        
    }

}
