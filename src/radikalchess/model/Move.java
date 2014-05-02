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
                   + " from " + Board.indexToString(sourceSquare)
                   + " to " + Board.indexToString(destinationSquare);
        
    }
    
    public boolean equals(Move other)
    {
        return other.movingPiece == movingPiece
            && other.capturedPiece == capturedPiece
            && other.sourceSquare == sourceSquare
            && other.destinationSquare == destinationSquare
            && other.moveType == moveType;
    }
    
    
    public final static class MoveBuilder 
    {
        private int movingPiece;
        private int capturedPiece;
        private int sourceSquare;
        private int destinationSquare;
        
        public MoveBuilder move(int movingPiece) {
            this.movingPiece = movingPiece;
            return this;
        }

        public MoveBuilder capture(int capturedPiece) {
            this.capturedPiece = capturedPiece;
            return this;
        }

        public MoveBuilder from(int sourceSquare) {
            this.sourceSquare = sourceSquare;
            return this;
        }

        public MoveBuilder to(int destinationSquare) {
            this.destinationSquare = destinationSquare;
            return this;
        }
        
        public Move build() {
            int moveType = MOVE_NORMAL;
            if ( capturedPiece != Board.EMPTY_SQUARE )
                moveType |= MOVE_CAPTURE;
            if ( Board.indexAtEndRow( destinationSquare ) 
              && movingPiece < Board.BISHOP )
                moveType |= MOVE_PROMOTION;
            
            return new Move( movingPiece
                           , capturedPiece
                           , sourceSquare
                           , destinationSquare 
                           , moveType );
        }
        
        public MoveBuilder with(Board board, String from, String to) {
            from( Board.indexFromString(from) );
            to( Board.indexFromString(to) );
            move( board.findPiece(sourceSquare) );
            capture( board.findPiece(destinationSquare) );
            return this;
        }
        
    }
    
}
