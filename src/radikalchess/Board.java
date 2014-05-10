package radikalchess;

import java.util.EnumMap;
import java.util.Iterator;
import radikalchess.Position.PositionList;

public class Board implements Iterable<Piece>
{
    private Piece[] map;
    
    private EnumMap<Color,Info> details;
    
    private int turn;
    
    public static Board init()
    {
        Board b = new Board();
        
        b.add(Piece.WHITE_PAWN,   new Position(1,0) );
        b.add(Piece.WHITE_PAWN,   new Position(1,1) );
        b.add(Piece.WHITE_PAWN,   new Position(1,2) );
        b.add(Piece.WHITE_PAWN,   new Position(1,3) );
        b.add(Piece.WHITE_ROOK,   new Position(0,0) );
        b.add(Piece.WHITE_BISHOP, new Position(0,1) );
        b.add(Piece.WHITE_QUEEN,  new Position(0,2) );
        b.add(Piece.WHITE_KING,   new Position(0,3) );
        
        b.add(Piece.BLACK_PAWN,   new Position(4,0) );
        b.add(Piece.BLACK_PAWN,   new Position(4,1) );
        b.add(Piece.BLACK_PAWN,   new Position(4,2) );
        b.add(Piece.BLACK_PAWN,   new Position(4,3) );
        b.add(Piece.BLACK_ROOK,   new Position(5,3) );
        b.add(Piece.BLACK_BISHOP, new Position(5,2) );
        b.add(Piece.BLACK_QUEEN,  new Position(5,1) );
        b.add(Piece.BLACK_KING,   new Position(5,0) );
        
        b.turn = 1;
        
        return b;
    }
    
    public Board() {
        map = new Piece[Config.ALL_SQUARES];
        details = new EnumMap( Color.class );
        clear();
    }
    
    @Override
    public Board clone()
    {
        Board b = new Board();
        System.arraycopy(map, 0, b.map, 0, Config.ALL_SQUARES);
        b.details = details.clone();
        b.turn = turn;
        return b;
    }
    
    private void clear()
    {
        for (int i = 0; i < map.length; i++) {
            map[i] = null;
        }
        details.clear();
        details.put(Color.WHITE,new Info());
        details.put(Color.BLACK,new Info());
    }
    
    public Color player()
    {
        return ( turn % 2 == 1 )?Color.WHITE:Color.BLACK;
    }
    
    public int turn()
    {
        return turn;
    }
    
    public Piece at(Position pos)
    {
        return map[pos.hashCode()];
    }
    
    public Board apply(Move move)
    {
        Piece mov = at(move.from);
        Piece cap = at(move.to);
        if( mov == null ) return null;
        if( cap != null )
            delete( cap, move.to );
        delete( mov, move.from );
        if( move.isPromotion() && mov.type == Piece.Type.PAWN ) 
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
        map[pos.hashCode()] = piece;
        Info info = details.get(piece.color);
        info.value += piece.value();
        info.pieces.add(pos);
        System.out.println("Debug: place "+piece+" at "+pos);
        if( piece.type == Piece.Type.KING ) info.king = pos;
    }
    
    private void delete(Piece piece, Position pos)
    {
        map[pos.hashCode()] = null;
        Info info = details.get(piece.color);
        info.value -= piece.value();
        info.pieces.remove(pos);
        System.out.println("Debug: remove "+piece+" from "+pos);
        if( piece.type == Piece.Type.KING ) info.king = null;
    }
    
    @Override
    public Iterator<Piece> iterator() {
        return new Iterator<Piece>() {
            private int head = 0;
            @Override
            public boolean hasNext() {
                return ( head < map.length );
            }

            @Override
            public Piece next() {
                return map[ head++ ];
            }

            @Override
            public void remove() {
            }

        };
    }

    public String save()
    {
        String str = "";
        for( Position pos : Position.ALL )
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
        private PositionList attacks;
        private int value;
        
        private Info()
        {
            pieces = new PositionList();
            attacks = new PositionList();
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
            info.king = king.clone();
            info.value = value;
            return info;
        }
    }
    
}
