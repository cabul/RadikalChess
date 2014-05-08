package radikalchess;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

public class Board implements Iterable<Position>
{
    private HashMap<Position,Piece> map;
    
    private EnumMap<Color,Statistics> stats;
    
    private Move[] moves;
    
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
        map = new HashMap<>();
        stats = new EnumMap( Color.class );
        clean();
    }
    
    @Override
    public Board clone()
    {
        Board b = new Board();
        b.map = (HashMap)map.clone();
        b.stats = stats.clone();
        b.turn = turn;
        return b;
    }
    
    private void clean()
    {
        map.clear();
        stats.clear();
        stats.put(Color.WHITE,new Statistics());
        stats.put(Color.BLACK,new Statistics());
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
        return map.get(pos);
    }
    
    public Board apply(Move move)
    {
        if( move.isCapture() )
            delete( move.capturedPiece, move.destinationSquare );
        delete( move.movingPiece, move.sourceSquare );
        if( move.isPromotion() )
            add( move.movingPiece.promote(), move.destinationSquare );
        else
            add( move.movingPiece, move.destinationSquare );
        
        turn++;
        
        generateMoves();
        return this;
    }
    
    public Move[] moves()
    {
        if( moves == null ) generateMoves();
        return moves;
    }
    
    public Statistics stats(Color color)
    {
        return stats.get(color).clone();
    }
    
    public Iterable<Position> all(Color color)
    {
        return BitBoard.traverse(stats.get(color).pieces);
    }
    
    private void add(Piece piece, Position pos)
    {
        map.put(pos, piece);
        Statistics stat = stats.get(piece.color);
        stat.value += piece.value();
        stat.pieces |= pos.index();
        if( piece.type == Piece.Type.KING ) stats.get(piece.color).king = pos;
    }
    
    private void delete(Piece piece, Position pos)
    {
        map.remove(pos);
        Statistics stat = stats.get(piece.color);
        stat.value -= piece.value();
        stat.pieces ^= pos.index();
    }
    
    private void generateMoves() {
        
    }

    @Override
    public Iterator<Position> iterator() {
        return map.keySet().iterator();
    }

    public String save()
    {
        String str = "";
        for( Position pos : this )
        {
            str += at(pos)
             +" "+ pos +"\n";
        }
        return str.substring(0, str.length()-1);
    }
    
    public void load(String str)
    {
        clean();
        String[] lines = str.split("\n");
        for( int i = 0; i < lines.length; i++ )
        {
            String[] words = lines[i].split(" ");
            String piece = words[0].toUpperCase()+"_"+words[1].toUpperCase();
            add( Piece.valueOf(piece), Position.fromString(words[2]));
        } 
    }
    
}
