package radikalchess;

import java.util.ArrayList;
import java.util.Objects;

public class Move
{
    
    
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (! (obj instanceof Move) ) {
            return false;
        }
        final Move other = (Move) obj;
        return from == other.from
            && to == other.to;
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.from);
        hash = 67 * hash + Objects.hashCode(this.to);
        return hash;
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
 
    
    
}
