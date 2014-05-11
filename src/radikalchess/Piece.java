package radikalchess;

public enum Piece
{
    
    white_pawn   ( Type.pawn, Color.white ),
    white_bishop ( Type.bishop, Color.white ),
    white_rook   ( Type.rook, Color.white ),
    white_queen  ( Type.queen, Color.white ),
    white_king   ( Type.king, Color.white ),
    black_pawn   ( Type.pawn, Color.black ),
    black_bishop ( Type.bishop, Color.black ),
    black_rook   ( Type.rook, Color.black ),
    black_queen  ( Type.queen, Color.black ),
    black_king   ( Type.king, Color.black );
    
    public static enum Type {
        pawn, bishop, rook, queen, king;
        @Override
        public String toString()
        {
            return super.toString().toLowerCase();
        }
    }
    
    public final Type type;
    public final Color color;
    
    private Piece(Type type, Color color)
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
            String piece = words[0].toLowerCase()+"_"+words[1].toLowerCase();
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
            case pawn:
                return 100;
            case bishop:
                return 200;
            case rook:
                return 300;
            case queen:
                return 500;
            case king:
                return 20000;
            default:
                return 0;
        }
    }
    
    public Piece toQueen()
    {
        return (color == Color.white)?white_queen:black_queen;
    }
    
    public Piece toPawn()
    {
        return (color == Color.white)?white_pawn:black_pawn;
    }
    
}
