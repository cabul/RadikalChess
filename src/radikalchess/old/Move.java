package radikalchess.old;

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
                   + BitBoard.VERBOSE[movingPiece] 
                   + " from " + BitBoard.indexToString(sourceSquare)
                   + " to " + BitBoard.indexToString(destinationSquare);
        
    }
    
    @Override
    public boolean equals(Object o)
    {
        if( !(o instanceof Move) ) return false;
        Move other = (Move) o;
        return other.movingPiece == movingPiece
            && other.capturedPiece == capturedPiece
            && other.sourceSquare == sourceSquare
            && other.destinationSquare == destinationSquare
            && other.moveType == moveType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.movingPiece;
        hash = 97 * hash + this.capturedPiece;
        hash = 97 * hash + this.sourceSquare;
        hash = 97 * hash + this.destinationSquare;
        return hash;
    }
    
    
    public final static class MoveBuilder 
    {
        private int movingPiece;
        private int capturedPiece;
        private int sourceSquare;
        private int destinationSquare;
        private BitBoard board;
        
        public MoveBuilder(BitBoard board) {
            this.board = board;
        }

        public MoveBuilder from(int sourceSquare) {
            this.sourceSquare = sourceSquare;
            this.movingPiece = board.pieceAt(sourceSquare);
            return this;
        }

        public MoveBuilder to(int destinationSquare) {
            this.destinationSquare = destinationSquare;
            this.capturedPiece = board.pieceAt(destinationSquare);
            return this;
        }
        
        public Move build() {
            int moveType = MOVE_NORMAL;
            if ( capturedPiece != BitBoard.EMPTY_SQUARE )
                moveType |= MOVE_CAPTURE;
            if ( BitBoard.indexAtEndRow( destinationSquare ) 
              && movingPiece < BitBoard.BISHOP )
                moveType |= MOVE_PROMOTION;
            
            return new Move( movingPiece
                           , capturedPiece
                           , sourceSquare
                           , destinationSquare 
                           , moveType );
        }
        
    }
    
}
