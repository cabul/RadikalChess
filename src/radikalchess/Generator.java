package radikalchess;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

public class Generator {

    private static final int MASK_UP = 1 << 1;
    private static final int MASK_DOWN = 1 << 2;
    private static final int MASK_LEFT = 1 << 3;
    private static final int MASK_RIGHT = 1 << 4;
    
    private enum Direction { 
        UP (MASK_UP),
        UP_RIGHT(MASK_UP+MASK_RIGHT),
        RIGHT(MASK_RIGHT),
        DOWN_RIGHT(MASK_DOWN+MASK_RIGHT),
        DOWN(MASK_DOWN),
        DOWN_LEFT(MASK_DOWN+MASK_LEFT),
        LEFT(MASK_LEFT),
        UP_LEFT(MASK_UP+MASK_LEFT);
        
        private final int mask;
        Direction(int mask){ this.mask = mask; }
        
        private Position move(Position pos)
        {
            int row = pos.row;
            int col = pos.col;
            if((mask&MASK_UP)!=0) row++;
            if((mask&MASK_DOWN)!=0) row--;
            if((mask&MASK_RIGHT)!=0) col++;
            if((mask&MASK_LEFT)!=0) col--;
            Position rv = new Position(row,col);
            return checkBounds(rv)?rv:null;
        }
        
        private boolean checkBounds(Position pos)
        {
            return pos.row >= 0 && pos.row < Config.ROWS
                && pos.col >= 0 && pos.col < Config.COLUMNS;
        }
        
    }
    
    private static class Ray implements Iterable<Position>
    {
        private ArrayList<Position> list;
        private final int length;
        private Ray(Position init, Direction dir)
        {
            list = new ArrayList<>();
            Position pos = init;
            while( ( pos = dir.move(pos) ) != null ) list.add(pos);
            length = list.size();
        }
        @Override
        public Iterator iterator() {
            return list.iterator();
        }
        
    }
    
    private static final HashMap<Position,Ray[]> rook_rays;
    private static final HashMap<Position,Ray[]> bishop_rays;
    private static final HashMap<Position,ArrayList<Position>> king_moves;
    
    static
    {
        rook_rays = new HashMap();
        bishop_rays = new HashMap();
        king_moves = new HashMap();
        
        for( int hash = 0; hash < Config.ALL_SQUARES; hash++ )
        {
            Position pos = Position.fromHash(hash);
            Ray[] rays;
            int count;
            
            Ray up = new Ray(pos,Direction.UP);
            Ray down = new Ray(pos,Direction.DOWN);
            Ray left = new Ray(pos,Direction.LEFT);
            Ray right = new Ray(pos,Direction.RIGHT);
            count = 0;
            if( up.length > 0 ) count++;
            if( down.length > 0 ) count++;
            if( left.length > 0 ) count++;
            if( right.length > 0 ) count++;
            count = 0;
            rays = new Ray[count];
            if( up.length > 0 ) rays[count++] = up;
            if( down.length > 0 ) rays[count++] = down;
            if( left.length > 0 ) rays[count++] = left;
            if( right.length > 0 ) rays[count++] = right;
            rook_rays.put(pos,rays);
            
            Ray ul = new Ray(pos,Direction.UP_LEFT);
            Ray ur = new Ray(pos,Direction.UP_RIGHT);
            Ray dl = new Ray(pos,Direction.DOWN_LEFT);
            Ray dr = new Ray(pos,Direction.DOWN_RIGHT);
            count = 0;
            if( ul.length > 0 ) count++;
            if( ur.length > 0 ) count++;
            if( dl.length > 0 ) count++;
            if( dr.length > 0 ) count++;
            count = 0;
            rays = new Ray[count];
            if( ul.length > 0 ) rays[count++] = ul;
            if( ur.length > 0 ) rays[count++] = ur;
            if( dl.length > 0 ) rays[count++] = dl;
            if( dr.length > 0 ) rays[count++] = dr;
            bishop_rays.put(pos,rays);
            
            
            ArrayList<Position> km = new ArrayList();
            Position to;
            for( Direction dir : Direction.values() )
            {
                if( (to = dir.move(pos)) != null ) km.add(to);
            }
            king_moves.put(pos, km);
            
        }
    }
    
    private Board board;
    
    private EnumMap<Color,ArrayList<Move>> moves;
    
    Generator(Board board)
    {
        this.board = board;
        moves = new EnumMap(Color.class);
    }

    public ArrayList<Move> moves(Color color)
    {
        if( moves.get(color) == null ) calculate(color);
        return moves.get(color);
    }
    
    private void calculate(Color color)
    {
        Statistics my = board.stats(color);
        Statistics enemy = board.stats(color.enemy());
        Move.Builder turn = new Move.Builder(board);
        for( Position pos : board.all(color) )
        {
            turn.from( pos );
            final int distance = enemy.king.distance(pos);
            final Piece.Type type = board.at(pos).type;
            
        }
    }
    
    private long attacks(Position pos)
    {
        return 0;
    }
    
    private ArrayList<Position> moves(Position pos)
    {
        ArrayList<Position> moves = new ArrayList<>();
        Color my = board.at(pos).color;
        Color enemy = my.enemy();
        Piece aux;
        switch( board.at(pos).type )
        {
            case PAWN:
                Direction dir = (my == Color.WHITE)
                              ? Direction.UP
                              : Direction.DOWN;
                break;
            case BISHOP:
                for( Ray ray : bishop_rays.get(pos))
                {
                    for( Position p : ray )
                    {
                        if( (aux = board.at(p)) == null)
                            // Empty
                            moves.add(p);
                        else {
                            if(aux.color == enemy)
                                moves.add(p);
                            break;
                         }
                    }  
                }
                break;
            case ROOK:
                for( Ray ray : rook_rays.get(pos))
                {
                    for( Position p : ray )
                    {
                        if( (aux = board.at(p)) == null)
                            // Empty
                            moves.add(p);
                        else {
                            if(aux.color == enemy)
                                moves.add(p);
                            break;
                         }
                    }  
                }
                break;
            case QUEEN:
                for( Ray ray : rook_rays.get(pos))
                {
                    for( Position p : ray )
                    {
                        if( (aux = board.at(p)) == null)
                            // Empty
                            moves.add(p);
                        else {
                            if(aux.color == enemy)
                                moves.add(p);
                            break;
                         }
                    }  
                }
                for( Ray ray : bishop_rays.get(pos))
                {
                    for( Position p : ray )
                    {
                        if( (aux = board.at(p)) == null)
                            // Empty
                            moves.add(p);
                        else {
                            if(aux.color == enemy)
                                moves.add(p);
                            break;
                         }
                    }  
                }
                break;
            case KING:
                return king_moves.get(pos);
        }
        return moves;
    }
    
}
