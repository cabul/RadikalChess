package radikalchess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import radikalchess.Player.Decision;

public class Main {
    
    private static enum Return { VOID, CHANGE, EXIT, ERROR };
    
    private static interface Command
    {
        Return execute(String[] args) throws IOException;
    }
    
    private static Command DEFAULT;
    
    private void println(Object msg) throws IOException
    {
        // mapped to cout
        cout.write(msg.toString()); cout.newLine(); cout.flush();
    }
    
    private Return errorln(Object msg) throws IOException
    {
        // mapped to cerr
        cerr.write(msg.toString()); cerr.newLine(); cerr.flush();
        return Return.ERROR;
    }
    
    private final BufferedWriter cout;
    private final BufferedWriter cerr;
    private final BufferedReader cin;
    private final PrettyPrinter printer;
    private final List<Move> moveBuffer;
    private final List<Position> positionBuffer;
    private final EnumMap<Color,Player> players;
    private final HashMap<String,Command> commands;
    private AISearch ai;
    private Game game;
    
    private void repl() throws IOException
    {
        Command c;
        String[] args;
        Return rv = Return.VOID;
        println("Welcome to Radikal Chess");
        while( rv != Return.EXIT )
        {
            if( rv == Return.CHANGE ) 
            {
                print();
                Player.Decision decision = players.get( game.player() ).decision;
                if( decision != null ) {
                    Move move = decision.make( game.board() );
                    
                    if( move != null )
                    {
                        game.advance(move);
                        if( Config.DEBUG )
                        {
                            println("Move: " + move);
                            println("Continue?");
                            if( cin.readLine().equals("debug") )
                            {
                                players.put(game.player().enemy(), Player.human);
                                rv = Return.VOID;
                                print();
                            }
                        }
                        continue;
                    }
                    else 
                    {
                        println(game.player() + " cannot move");
                    }
                }
            }
            args = cin.readLine().toLowerCase().split(" ");
            c = commands.get( args[0] );
            if( c == null ) c = DEFAULT;
            rv = c.execute( args );
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        new Main().repl();
    }
    
    private void print() throws IOException
    {
        println("Turn " + game.turn());
        printer.clear().load(game.board()).print(cout);
        println(game.player()+" to move");
    }
    
    private Main()
    {
        cin = new BufferedReader(new InputStreamReader(System.in));
        cout = new BufferedWriter(new OutputStreamWriter(System.out));
        cerr = new BufferedWriter(new OutputStreamWriter(System.err));
        commands = new HashMap();
        game = new Game();
        printer = new PrettyPrinter();
        moveBuffer = new ArrayList();
        positionBuffer = new ArrayList();
        players = new EnumMap( Color.class );
        
        players.put(Color.white, Player.human);
        players.put(Color.black, Player.human);
        
        final Heuristic hValue = new Heuristic(){

            @Override
            public int eval(Board board) {
                return board.info(board.player()).value() 
                     - board.info(board.player().enemy()).value();
            }
            
        };
        ai = new AISearch(hValue);
        try {
            ai.debug(new BufferedWriter(new FileWriter("search.log")));
        } catch (IOException ex) {
            
        }
        Player.human.decision = null;
        
        Player.ai.decision = new Decision(){

            @Override
            public Move make(Board board) {
                return ai.negamax(board);
            }
            
        };
        
        DEFAULT = new Command(){
            @Override
            public Return execute(String[] args) throws IOException {
                Move move = Move.fromString(args[0]);
                if( move != null ){
                    moveBuffer.clear();
                    Generator.genAllMoves(moveBuffer, game.board(), game.player());
                    if( moveBuffer.contains(move) ){
                        game.advance(move);
                    } else {
                        return errorln("No valid Move: '"+move+"'");
                    }
                }
                else return errorln("I don't understand '" + args[0]+"'");
                return Return.CHANGE;
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
                players.clear();
                for( Color color : Color.values() )
                {
                    while( true )
                    {
                        cout.write(color + ": ");
                        cout.flush();
                        String input = cin.readLine();
                        Player p = Player.fromString(input);
                        if( p == null )
                        {
                            errorln("'"+input+"' is no valid player");
                        } else {
                            players.put(color, p);
                            break;
                        }
                    }
                }
                game.reset();
                return Return.CHANGE;
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
                print();
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
                return Return.CHANGE;
            }
        });
        
        commands.put("redo", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if( !game.redo() ) return errorln("No moves to redo");
                return Return.CHANGE;
            }
        });
        
        commands.put("save", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if(args.length == 1)
                    game.save(cout);
                else{
                    try (BufferedWriter file = new BufferedWriter(new FileWriter(args[1]))) {
                        game.save(file);
                    } catch(Exception ex)
                    {
                        return errorln("Loading error: " +ex.getLocalizedMessage());
                    }
                    println("Saving complete");
                }
                return Return.VOID;
            }
        });
        
        commands.put("load", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                if(args.length == 1) 
                try {
                    game.load(cin);
                } catch(IOException ex)
                {
                    return errorln("Loading Error: " +ex.getLocalizedMessage());
                }
                else{
                    try (BufferedReader file = new BufferedReader(new FileReader(args[1]))) {
                        game.load(file);
                    } catch(Exception ex)
                    {
                        return errorln("Loading error: " +ex.getLocalizedMessage());
                    }
                    println("Loading complete");
                }
                return Return.CHANGE;
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
        
        commands.put("hint", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                Move move = ai.negamax(game.board());
                System.out.println("Try " + move);
                return Return.VOID;
            }
        });
     
        commands.put("play", new Command() {
            @Override
            public Return execute(String[] args) throws IOException {
                players.put(game.player(),Player.ai);
                return Return.CHANGE;
            }
        });
        
    }
    

}
