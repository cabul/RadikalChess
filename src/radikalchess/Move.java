package radikalchess;

public final class Move
{

    public static final int MOVE_NORMAL = 0;
    public static final int MOVE_CAPTURE = 1;
    public static final int MOVE_STALEMATE = 2;
    public static final int MOVE_PROMOTION = 3;
    
    public static String[] MoveTypes;
    
    public static final int NULL_MOVE = -1;
    
    public static final int EVALTYPE_ACCURATE = 0;
    public static final int EVALTYPE_UPPERBOUND = 1;
    public static final int EVALTYPE_LOWERBOUND = 2;
    
    static {
        MoveTypes = new String[4];
        MoveTypes[0] = "Normal";
        MoveTypes[1] = "Capture";
        MoveTypes[2] = "Stalemate";
        MoveTypes[3] = "Promotion";
    }
    
    public int movingPiece;
    public int capturedPiece;
    public int sourceSquare, destinationSquare;
    
    public int moveType;
    
    public int moveEvaluation;
    public int moveEvaluationType;
    public int searchDepth;
    
    public Move()
    {
        reset();
    }
    
    public void clone(Move target)
    {
        movingPiece = target.movingPiece;
        capturedPiece = target.capturedPiece;
        sourceSquare = target.sourceSquare;
        destinationSquare = target.destinationSquare;
        moveType = target.moveType;
        moveEvaluation = target.moveEvaluation;
        moveEvaluationType = target.moveEvaluationType;
        searchDepth = target.searchDepth;
    }
    
    public boolean equals(Move target)
    {
        return ( movingPiece       == target.movingPiece )
            && ( capturedPiece     == target.capturedPiece )
            && ( sourceSquare      == target.sourceSquare )
            && ( destinationSquare == target.destinationSquare );
    }
    
    public void reset()
    {
        movingPiece = Board.EMPTY_SQUARE;
        capturedPiece = Board.EMPTY_SQUARE;
        sourceSquare = NULL_MOVE;
        destinationSquare = NULL_MOVE;
        moveType = NULL_MOVE;
        moveEvaluation = NULL_MOVE;
        moveEvaluationType = NULL_MOVE;
        searchDepth = NULL_MOVE;
    }
    
    @Override
    public String toString()
    {
        String str = "Move(" + MoveTypes[moveType] + "): ";
        if ( moveType == MOVE_STALEMATE )
            str += "Stalemate!";
        else
        {
            str = str + Board.PieceStrings[movingPiece] 
                    + " " + Board.index(sourceSquare)
                    + " > " + Board.index(destinationSquare);
        }
        return str;
    }
    
}
