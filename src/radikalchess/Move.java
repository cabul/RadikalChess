package radikalchess;

import radikalchess.Position.PositionList;

public class Move
{
    public static class MoveList extends ReadonlyList<Move>
    {
        public PositionList to()
        {
            PositionList list = new PositionList();
            for( Move move : this )
            {
                list.add(move.to);
            }
            return list;
        }
        public PositionList from()
        {
            PositionList list = new PositionList();
            for( Move move : this )
            {
                list.add(move.from);
            }
            return list;
        }
    }
    
    public final Position from;
    public final Position to;
    
    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString()
    {
        return ""+from+to;
    }

    @Override
    public int hashCode() {
        return to.hashCode() + from.hashCode() * Config.ALL_SQUARES;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (! (obj instanceof Move) ) {
            return false;
        }
        final Move other = (Move) obj;
        return from.equals(other.from)
            && to.equals(other.to);
        
    }
    
    public static Move fromString(String str)
    {
        try{
            Position from = Position.fromString(str.substring(0,2));
            Position to = Position.fromString(str.substring(2,4));
            if( from == null ) return null;
            if( to == null ) return null;
            return new Move(from,to);
        } catch( IndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    public boolean isPromotion()
    {
        return to.atBottom() || to.atTop();
    }
    
    

}
