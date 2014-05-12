package radikalchess;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Board implements Iterable<Position>
{
    private EnumMap<Position,Piece> map;
    
    private EnumMap<Color,Info> details;
    
    private int moves;
    
    private int turn;
    
    private Stack<Move> history;
    private Stack<Piece> captures;
    
    public static Board init()
    {
        Board b = new Board();
        
        b.clear();
        
        b.add( Piece.white_pawn, Position.a2 );
        b.add( Piece.white_pawn, Position.b2  );
        b.add( Piece.white_pawn, Position.c2 );
        b.add( Piece.white_pawn, Position.d2 );
        b.add( Piece.white_rook, Position.a1 );
        b.add( Piece.white_bishop, Position.b1 );
        b.add( Piece.white_queen, Position.c1 );
        b.add( Piece.white_king, Position.d1 );
        
        b.add( Piece.black_pawn, Position.a5 );
        b.add( Piece.black_pawn, Position.b5 );
        b.add( Piece.black_pawn, Position.c5 );
        b.add( Piece.black_pawn, Position.d5 );
        b.add( Piece.black_king, Position.a6 );
        b.add( Piece.black_queen, Position.b6 );
        b.add( Piece.black_bishop, Position.c6 );
        b.add( Piece.black_rook, Position.d6 );
        
        b.turn = 1;
        
        b.moves = -1;
        
        return b;
    }
    
    public Board() {
        map = new EnumMap( Position.class );
        details = new EnumMap( Color.class );
        history = new Stack();
        captures = new Stack();
    }
    
    @Override
    public Board clone()
    {
        Board b = new Board();
        b.map = map.clone();
        b.details = details.clone();
        b.turn = turn;
        return b;
    }
    
    private void clear()
    {
        turn = 1;
        moves = -1;
        map.clear();
        details.clear();
        details.put(Color.white,new Info());
        details.put(Color.black,new Info());
        history.clear();
        captures.clear();
    }
    
    public Color player()
    {
        return ( turn % 2 == 1 )?Color.white:Color.black;
    }
    
    public int turn()
    {
        return turn;
    }

    public List<Move> genMoves()
    {
        List<Move> list = new ArrayList();
        Generator.genAllMoves(list, this, player());
        moves = list.size();
        return list;
    }
    
    public int numMoves()
    {
        if( moves < 0 )
            genMoves();
        return moves;
    }
    
    public Piece at(Position pos)
    {
        return map.get(pos);
    }
    
    
    public boolean make(Move move)
    {
        Piece mov = at(move.from);
        Piece cap = at(move.to);
        if( mov == null ) return false;
        if( cap != null )
            delete( cap, move.to );
        delete( mov, move.from );
        if( mov.type == Piece.Type.pawn && move.from.atEndRow(mov.color))
            add( mov.toQueen(), move.to );
        else add( mov, move.to );
        
        turn++;
        
        history.push(move);
        captures.push(cap);
        
        moves = -1;
        
        return true;
    }
    
    public Move unmake()
    {
        if( history.empty() ) return null;
        if( captures.empty() ) return null;
        Move move = history.pop();
        Piece cap = captures.pop();
        
        Piece mov = at(move.to);
        if( mov == null ) return null;
        delete( mov, move.to );
        if( move.isPromotion() && mov.type == Piece.Type.queen )
            add( mov.toPawn(), move.from );
        else add( mov, move.from );
        if( cap != null )
            add( cap, move.to );
        
        turn--;
        
        moves = -1;
            
        return move;
        
    }
            
    public Info info(Color color)
    {
        return details.get(color);
    }
    
    private void add(Piece piece, Position pos)
    {
        map.put(pos,piece);
        Info info = details.get(piece.color);
        info.value += piece.value();
        info.pieces.add(pos);
        if( piece.type == Piece.Type.king ) info.king = pos;
    }
    
    private void delete(Piece piece, Position pos)
    {
        map.remove(pos);
        Info info = details.get(piece.color);
        info.value -= piece.value();
        info.pieces.remove(pos);
        if( piece.type == Piece.Type.king ) info.king = null;
    }
    
    @Override
    public Iterator<Position> iterator() {
        return map.keySet().iterator();
    }

    public static Board load(EnumMap<Position,Piece> map, int turn)
    {
        Board b = new Board();
        b.clear();
        for( Position pos : map.keySet() )
        {
            b.add(map.get(pos), pos);
        }
        b.turn = turn;
        return b;
    }
    
    public static class Info
    {
        private Position king;
        private EnumSet<Position> pieces;
        private int value;
        
        private Info()
        {
            pieces = EnumSet.noneOf(Position.class);
            value = 0;
        }
        
        public Iterable<Position> pieces()
        {
            return pieces;
        }
        
        public int count()
        {
            return pieces.size();
        }
        
        public int value()
        {
            return value;
        }
        
        public Position king()
        {
            return king;
        }
        
        @Override
        public Info clone()
        {
            Info info = new Info();
            info.pieces = pieces.clone();
            info.king = king;
            info.value = value;
            return info;
        }
    }
    
}
