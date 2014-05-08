package radikalchess;

public class Move
{
    public final Position sourceSquare;
    public final Position destinationSquare;
    public final Piece movingPiece;
    public final Piece capturedPiece;
    
    private Move(Position sourceSquare, Position destinationSquare, Piece movingPiece, Piece capturedPiece) {
        this.sourceSquare = sourceSquare;
        this.destinationSquare = destinationSquare;
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
    }

    @Override
    public String toString()
    {
        return movingPiece+" " +sourceSquare.toString() + " " + destinationSquare.toString();
    }
    
    public boolean isCapture()
    {
        return capturedPiece != null;
    }
    
    public boolean isPromotion()
    {
        return destinationSquare.atBottom() || destinationSquare.atTop();
    }
    
    public static class Builder 
    {
        private Position sourceSquare;
        private Position destinationSquare;
        private Piece movingPiece;
        private Piece capturedPiece;
        private final Board board;
        
        public Builder(Board board)
        {
            this.board = board;
        }
        
        public Builder from(Position sourceSquare)
        {
            this.sourceSquare = sourceSquare;
            movingPiece = board.at( sourceSquare );
            if( movingPiece == null ) return null;
            return this;
        }
        
        public Builder to(Position destinationSquare)
        {
            this.destinationSquare = destinationSquare;
            this.capturedPiece = board.at( destinationSquare );
            return this;
        }
        
        public Move build()
        {
            return new Move(sourceSquare, destinationSquare, movingPiece, capturedPiece);
        }
        
    }
    

}
