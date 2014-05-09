package radikalchess;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import radikalchess.Move.MoveList;
import radikalchess.Position.PositionList;

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
        private PositionList list;
        private final int length;
        private Ray(Position init, Direction dir)
        {
            list = new PositionList();
            Position pos = init;
            while( ( pos = dir.move(pos) ) != null ) list.add(pos);
            length = list.size();
        }
        @Override
        public Iterator iterator() {
            return list.ro.iterator();
        }
        
    }
    
    private static final HashMap<Position,Ray[]> rook_rays;
    private static final HashMap<Position,Ray[]> bishop_rays;
    private static final HashMap<Position,PositionList> king_moves;
    
    static
    {
        rook_rays = new HashMap();
        bishop_rays = new HashMap();
        king_moves = new HashMap();
        
        for( Position pos : Position.ALL )
        {
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
            rays = new Ray[count];
            count = 0;
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
            rays = new Ray[count];
            count = 0;
            if( ul.length > 0 ) rays[count++] = ul;
            if( ur.length > 0 ) rays[count++] = ur;
            if( dl.length > 0 ) rays[count++] = dl;
            if( dr.length > 0 ) rays[count++] = dr;
            bishop_rays.put(pos,rays);
            
            
            PositionList km = new PositionList();
            Position to;
            for( Direction dir : Direction.values() )
                if( (to = dir.move(pos)) != null ) km.add(to);
            king_moves.put(pos, km);
            
        }
    }
    
    
    public static void test(BufferedWriter bw, String piece) throws IOException
    {
        PrettyPrinter printer = new PrettyPrinter();
        switch( piece ){
            case "king":
                bw.write("Testing king moves"); bw.newLine();
                for( Position pos : Position.ALL )
                {
                    bw.write(pos.toString()); bw.newLine();
                    printer.clear().highlight(king_moves.get(pos)).print(bw);
                }
                break;
            case "bishop":
                bw.write("Testing bishop moves"); bw.newLine();
                for( Position pos : Position.ALL )
                {
                    bw.write(pos.toString()); bw.newLine();
                    printer.clear();
                    for( Ray ray : bishop_rays.get(pos) )
                        printer.highlight(ray.list);
                    printer.print(bw);
                }
                break;
            case "rook":
                bw.write("Testing rook moves"); bw.newLine();
                for( Position pos : Position.ALL )
                {
                    bw.write(pos.toString()); bw.newLine();
                    printer.clear();
                    for( Ray ray : rook_rays.get(pos) )
                        printer.highlight(ray.list);
                    printer.print(bw);
                }
                break;
            default:
                bw.write("No tests for '"+piece+"'"); bw.newLine();
        } 
        
    }
    
    public static void genAllMoves(MoveList list, Board board, Color player)
    {
        for( Position piece : board.info(player).pieces() )
        {
            genMoves(list,board,player,piece);
        }
    }
    
    public static void genMoves(MoveList list, Board board, Color player, Position piece)
    {
        switch( board.at(piece).type ){
            case PAWN:
                pawnMoves( list, board, player, piece );
                break;
            case BISHOP:
                bishopMoves( list, board, player, piece );
                break;
            case ROOK:
                rookMoves( list, board, player, piece );
                break;
            case QUEEN:
                queenMoves( list, board, player, piece );
                break;
            case KING:
                kingMoves( list, board, player, piece );
                break;
        }
    }
    
    public static void genAllAttacks(PositionList list,Board board, Color player)
    {
        for( Position piece : board.info(player).pieces() )
            genAttacks(list,board,player,piece);   
    }
    
    public static void genAttacks(PositionList list, Board board,Color player,Position piece)
    {
        switch( board.at(piece).type ){
            case PAWN:
                pawnAttacks( list, board, player, piece );
                break;
            case BISHOP:
                bishopAttacks( list, board, player, piece );
                break;
            case ROOK:
                rookAttacks( list, board, player, piece );
                break;
            case QUEEN:
                queenAttacks( list, board, player, piece );
                break;
            case KING:
                kingAttacks( list, board, player, piece );
                break;
        }
        
    }
    
    private static void pawnAttacks(PositionList list, Board board, Color player, Position piece)
    {
        Position pos;
        Direction dir = ( player == Color.WHITE)
                                  ? Direction.UP
                                  : Direction.DOWN;
        
        if( (piece = dir.move(piece)) == null) return; // Nothing to do here
        if( (pos = Direction.LEFT.move(piece)) != null) {
            if( board.at(pos) == null)
                list.add(pos);
            else if( board.at(pos).color != player )
                list.add(pos);
        }
        if( (pos = Direction.RIGHT.move(piece)) != null) {
            if( board.at(pos) == null)
                list.add(pos);
            else if( board.at(pos).color != player )
                list.add(pos);
        }   
    }
    
    private static void pawnMoves(MoveList list, Board board, Color player, Position piece)
    {
        Position pos;
        Direction dir = ( player == Color.WHITE)
                                  ? Direction.UP
                                  : Direction.DOWN;
        
        if( (pos = dir.move(piece)) == null) return; // Nothing to do here
        list.add(new Move(piece,pos));
        
        if( (pos = Direction.LEFT.move(piece)) != null) 
        if( board.at(pos) != null)
        if( board.at(pos).color != player )
                list.add(new Move(piece,pos));
        
        if( (pos = Direction.RIGHT.move(piece)) != null) 
        if( board.at(pos) != null)
        if( board.at(pos).color != player )
            list.add(new Move(piece,pos));
        
    }
    
    private static void bishopAttacks(PositionList list, Board board, Color player, Position piece)
    {
        for( Ray ray : bishop_rays.get(piece) )
        for( Position pos : ray)
            
            if( board.at(pos) == null )
                list.add(pos); // Empty square
            else {
                if( board.at(pos).color != player ) 
                    list.add(pos); // Attacking piece
                break; // Collision
            }
            
    }
    
    private static void bishopMoves(MoveList list, Board board, Color player, Position piece)
    {
        final Position enemy_king = board.info(player.enemy()).king();
        final int distance = piece.distance(enemy_king);
        for( Ray ray : bishop_rays.get(piece) )
        for( Position pos : ray) {
            if( pos.distance(enemy_king) > distance) break;
            else {
                if( board.at(pos) == null )
                    list.add(new Move(piece,pos)); // Empty square
                else {
                    if( board.at(pos).color != player ) 
                        list.add(new Move(piece,pos)); // Attacking piece
                    break; // Collision
               }
            }
        }
    }
    
    private static void rookAttacks(PositionList list, Board board, Color player, Position piece)
    {
        for( Ray ray : rook_rays.get(piece) )
        for( Position pos : ray)
            
            if( board.at(pos) == null )
                list.add(pos); // Empty square
            else {
                if( board.at(pos).color != player ) 
                    list.add(pos); // Attacking piece
                break; // Collision
            }
            
    }
    
    private static void rookMoves(MoveList list, Board board, Color player, Position piece)
    {
        final Position enemy_king = board.info(player.enemy()).king();
        final int distance = piece.distance(enemy_king);
        for( Ray ray : rook_rays.get(piece) )
        for( Position pos : ray) {
            if( pos.distance(enemy_king) > distance) break;
            else {
                if( board.at(pos) == null )
                    list.add(new Move(piece,pos)); // Empty square
                else {
                    if( board.at(pos).color != player ) 
                        list.add(new Move(piece,pos)); // Attacking piece
                    break; // Collision
               }
            }
        }
    }
    
    private static void queenAttacks(PositionList list, Board board, Color player, Position piece)
    {
        bishopAttacks( list, board, player, piece );
        rookAttacks( list, board, player, piece );
    }
    
    private static void queenMoves(MoveList list, Board board, Color player, Position piece)
    {
        final Position enemy_king = board.info(player.enemy()).king();
        final int distance = piece.distance(enemy_king);
        for( Ray ray : bishop_rays.get(piece) )
        for( Position pos : ray) {
            if( pos.distance(enemy_king) > distance) {
               PositionList attacks = new PositionList();  // Checks if check
               bishopAttacks(attacks,board,player,piece);
               if( attacks.contains(enemy_king) ) list.add(new Move(piece,pos));
               else break;
            } else {
                if( board.at(pos) == null )
                    list.add(new Move(piece,pos)); // Empty square
                else {
                    if( board.at(pos).color != player ) 
                        list.add(new Move(piece,pos)); // Attacking piece
                    break; // Collision
               }
            }
        }
        for( Ray ray : rook_rays.get(piece) )
        for( Position pos : ray) {
            if( pos.distance(enemy_king) > distance) {
               PositionList attacks = new PositionList();  // Checks if check
               rookAttacks(attacks,board,player,piece);
               if( attacks.contains(enemy_king) ) list.add(new Move(piece,pos));
               else break;
            } else {
                if( board.at(pos) == null )
                    list.add(new Move(piece,pos)); // Empty square
                else {
                    if( board.at(pos).color != player ) 
                        list.add(new Move(piece,pos)); // Attacking piece
                    break; // Collision
               }
            }
        }
    }
    
    private static void kingAttacks(PositionList list, Board board, Color player, Position piece)
    {
        for( Position pos : king_moves.get(piece) )
            if( board.at(pos) != null )
            {
                if( board.at(pos).color != player)
                    list.add( pos );
            } else list.add( pos );
            
    }
    
    private static void kingMoves(MoveList list, Board board, Color player, Position piece)
    {
        for( Position pos : king_moves.get(piece) ) {
            if( board.at(pos) != null )
            {
                if( board.at(pos).color != player)
                    list.add( new Move(piece,pos) );
            } else list.add( new Move(piece,pos) );
        }
            
    }
    
}