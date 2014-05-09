package radikalchess;

public enum Piece
{
    
    WHITE_PAWN   ( Type.PAWN, Color.WHITE ),
    WHITE_BISHOP ( Type.BISHOP, Color.WHITE ),
    WHITE_ROOK   ( Type.ROOK, Color.WHITE ),
    WHITE_QUEEN  ( Type.QUEEN, Color.WHITE ),
    WHITE_KING   ( Type.KING, Color.WHITE ),
    BLACK_PAWN   ( Type.PAWN, Color.BLACK ),
    BLACK_BISHOP ( Type.BISHOP, Color.BLACK ),
    BLACK_ROOK   ( Type.ROOK, Color.BLACK ),
    BLACK_QUEEN  ( Type.QUEEN, Color.BLACK ),
    BLACK_KING   ( Type.KING, Color.BLACK );
    
    public static enum Type {
        PAWN, BISHOP, ROOK, QUEEN, KING;
        @Override
        public String toString()
        {
            return super.toString().toLowerCase();
        }
    }
    
    public final Type type;
    public final Color color;
    
    Piece(Type type, Color color)
    {
        this.type = type;
        this.color = color;
    }

    @Override
    public String toString() {
        return color.toString()+" "+type.toString();
    }
    
    public static Piece fromString(String str)
    {
        try{
            String[] words = str.split(" ");
            if( words.length < 2 ) return null;
            String piece = words[0].toUpperCase()+"_"+words[1].toUpperCase();
            return Piece.valueOf(piece);
        } catch(Exception ex)
        {
            return null;
        }
    }
    
    public int value()
    {
        switch( type )
        {
            case PAWN:
                return 100;
            case BISHOP:
                return 200;
            case ROOK:
                return 300;
            case QUEEN:
                return 500;
            case KING:
                return 20000;
            default:
                return 0;
        }
    }
    
    public Piece promote()
    {
        return (color == Color.WHITE)?WHITE_QUEEN:BLACK_QUEEN;
    }
    
}
