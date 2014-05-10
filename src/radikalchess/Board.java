package radikalchess;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import radikalchess.Position.PositionList;

public class Board implements Iterable<Position>
{
    private EnumMap<Position,Piece> map;
    
    private EnumMap<Color,Info> details;
    
    private int turn;
    
    public static Board init()
    {
        Board b = new Board();
        
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
        
        return b;
    }
    
    public Board() {
        map = new EnumMap( Position.class );
        details = new EnumMap( Color.class );
        clear();
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
        map.clear();
        details.clear();
        details.put(Color.white,new Info());
        details.put(Color.black,new Info());
    }
    
    public Color player()
    {
        return ( turn % 2 == 1 )?Color.white:Color.black;
    }
    
    public int turn()
    {
        return turn;
    }
    
    public Piece at(Position pos)
    {
        return map.get(pos);
    }
    
    public Board apply(Move move)
    {
        Piece mov = at(move.from);
        Piece cap = at(move.to);
        if( mov == null ) return null;
        if( cap != null )
            delete( cap, move.to );
        delete( mov, move.from );
        if( move.isPromotion() && mov.type == Piece.Type.pawn ) 
            add( mov.promote(), move.to );
        else add( mov, move.to );
        
        turn++;
        
        return this;
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

    public String save()
    {
        String str = "";
        for( Position pos : Position.values() )
        {
            if( at(pos) != null )
            {
                str += at(pos)
                +" "+ pos +"\n";
            }
        }
        return str.substring(0, str.length()-1);
    }
    
    public void load(String str)
    {
        clear();
        String[] lines = str.split("\n");
        for( int i = 0; i < lines.length; i++ )
        {
            String[] words = lines[i].split(" ");
            add( Piece.fromString(lines[i]), Position.fromString(words[2]));
        } 
    }
    
    public static class Info
    {
        private Position king;
        private PositionList pieces;
        private EnumSet<Position> ps;
        private int value;
        
        private Info()
        {
            pieces = new PositionList();
            value = 0;
        }
        
        public Iterable<Position> pieces()
        {
            return pieces.ro;
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
            info.pieces = (PositionList) pieces.clone();
            info.king = king;
            info.value = value;
            return info;
        }
    }
    
}
