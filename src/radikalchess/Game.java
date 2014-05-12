package radikalchess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Stack;

public final class Game {
    
    private Board board;

    private EnumMap<Position,Piece> initMap;
    private Color initColor;
    
    private Stack<Move> undos;
    
    public Game()
    {
        undos = new Stack();
        
        initMap = new EnumMap(Position.class);
        initMap.put( Position.a2, Piece.white_pawn );
        initMap.put( Position.b2, Piece.white_pawn );
        initMap.put( Position.c2, Piece.white_pawn );
        initMap.put( Position.d2, Piece.white_pawn );
        initMap.put( Position.a1, Piece.white_rook );
        initMap.put( Position.b1, Piece.white_bishop );
        initMap.put( Position.c1, Piece.white_queen );
        initMap.put( Position.d1, Piece.white_king );
        initMap.put( Position.a5, Piece.black_pawn );
        initMap.put( Position.b5, Piece.black_pawn );
        initMap.put( Position.c5, Piece.black_pawn );
        initMap.put( Position.d5, Piece.black_pawn );
        initMap.put( Position.d6, Piece.black_rook );
        initMap.put( Position.c6, Piece.black_bishop );
        initMap.put( Position.b6, Piece.black_queen );
        initMap.put( Position.a6, Piece.black_king );
        
        initColor = Color.white;
        
        reset();
    }
    
    public Board board()
    {
        return board;
    }
    
    public Color player()
    {
        return board().player();
    }
    
    public int turn()
    {
        return board().turn();
    }
    
    public void advance(Move move)
    {
        board.make(move);
    }
    
    public boolean undo()
    {
        Move move = board.unmake();
        if( move == null ) return false;
        undos.push(move);
        return true;
    }
    
    public boolean redo()
    {
        if( undos.empty() ) return false;
        board.make( undos.pop() );
        return true;
    }
    
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write("begin");bw.newLine();
        for( Position pos : initMap.keySet() )
        {
            bw.write(initMap.get(pos) + " " + pos); bw.newLine();
        }
        Move move;
        while( (move = board.unmake()) != null )
        {
            bw.write(move.toString());
            bw.newLine();
        }
        bw.write( "turn " + initColor ); bw.newLine();
        bw.write("end"); bw.newLine();
        bw.flush();
    }
    
    @SuppressWarnings("empty-statement")
    public void load(BufferedReader br) throws IOException
    {
        while(!"begin".equals(br.readLine()));
        
        String line;
        int ln = 0;
        initMap = new EnumMap(Position.class);
        List<Move> history = new ArrayList();
        Color turn = Color.white;
        while( !"end".equals(line = br.readLine() ) )
        {
            ln++;
            if( line.equals("") ) continue;
            String[] words = line.split(" ");
            
            switch( words.length ) {
                case 1:
                    Move move = Move.fromString(line);
                    if( move == null) throw new IOException( "["+ln+"] "+line);
                    history.add(move);
                    break;
                case 2:
                    if( !"turn".equals(words[0]) ) throw new IOException( "["+ln+"] "+line);
                    turn = Color.fromString(words[1]);
                    if( turn == null )throw new IOException( "["+ln+"] "+line);
                    break;
                case 3:
                    Position pos = Position.fromString(words[2]);
                    Piece piece = Piece.fromString(line);
                    if( pos == null || piece == null ) throw new IOException( "["+ln+"] "+line);
                    initMap.put(pos, piece);
                    break;
                default:
                    throw new IOException( "["+ln+"] "+line);
            }
            
        }
        
        initColor = turn;
        reset();
        for( Move move : history )
        {
            if( !board.make(move)) throw new IOException("Illegal move "+move);
        }
        
    }
    
    public void reset()
    {
        board = Board.load(initMap, initColor);
        undos.clear();
    }
    
}
