package rc.model;

public final class Move
{

    public static final int MOVE_NORMAL = 0;
    public static final int MOVE_CAPTURE = 1;
    public static final int MOVE_PROMOTION = 2;
    public static final int NULL_MOVE = -1;
    
    public final int movingPiece;
    public final int capturedPiece;
    public final int sourceSquare, destinationSquare;
    
    public final int moveType;

    private Move( int movingPiece 
                , int capturedPiece
                , int sourceSquare
                , int destinationSquare 
                , int moveType ) 
    {
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
        this.sourceSquare = sourceSquare;
        this.destinationSquare = destinationSquare;
        this.moveType = moveType;
    }
    
    public Move( Move clone )
    {
        this( clone.movingPiece 
            , clone.capturedPiece
            , clone.sourceSquare
            , clone.destinationSquare
            , clone.moveType );
    }
    
    @Override
    public String toString()
    {
        
        String str = "Move( ";
        if ( (moveType & MOVE_CAPTURE) != 0 )
            str += "Capture ";
        else 
            str += "Normal ";
        if ( (moveType & MOVE_PROMOTION) != 0 )
            str += "Promotion ";
        
        return str + "): " 
                   + Board.PieceStrings[movingPiece] 
                   + " from " + Board.index(sourceSquare)
                   + " to " + Board.index(destinationSquare);
        
    }
    
    public boolean equals(Move other)
    {
        return other.movingPiece == movingPiece
            && other.capturedPiece == capturedPiece
            && other.sourceSquare == sourceSquare
            && other.destinationSquare == destinationSquare
            && other.moveType == moveType;
    }
    
    
    public final static class Builder 
    {
        private int movingPiece;
        private int capturedPiece;
        private int sourceSquare;
        private int destinationSquare;
        private int moveType;

        public void move(int movingPiece) {
            this.movingPiece = movingPiece;
        }

        public void capture(int capturedPiece) {
            if ( capturedPiece != Board.EMPTY_SQUARE )
                moveType
            this.capturedPiece = capturedPiece;
        }

        public void from(int sourceSquare) {
            this.sourceSquare = sourceSquare;
        }

        public void to(int destinationSquare) {
            this.destinationSquare = destinationSquare;
        }
        
        public Move build()
        
    }
    
}
