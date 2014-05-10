package radikalchess;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.EnumMap;
import radikalchess.Position.PositionList;

public final class PrettyPrinter {
    
    private static final int ITEM_LENGTH = 2;
    
    private static final EnumMap<Piece,String> CODE;
    
    private static final int LINE_LENGTH = 5 + Config.COLUMNS * ( 3 + ITEM_LENGTH );
    private static final int LINE_COUNT = 3 + Config.ROWS * 2;
    private static final int CHAR_COUNT = LINE_LENGTH * LINE_COUNT;
    
    private static final String MARK = "##";
    private static final String EMPTY = "  ";
    
    static
    {
        CODE = new EnumMap(Piece.class);
        CODE.put(Piece.WHITE_PAWN, " P");
        CODE.put(Piece.WHITE_BISHOP, " B");
        CODE.put(Piece.WHITE_ROOK, " R");
        CODE.put(Piece.WHITE_QUEEN, " Q");
        CODE.put(Piece.WHITE_KING, " K");
        CODE.put(Piece.BLACK_PAWN, "*P");
        CODE.put(Piece.BLACK_BISHOP, "*B");
        CODE.put(Piece.BLACK_ROOK, "*R");
        CODE.put(Piece.BLACK_QUEEN, "*Q");
        CODE.put(Piece.BLACK_KING, "*K");
    }
    
    private StringBuilder builder;
    
    public PrettyPrinter()
    {
        builder = new StringBuilder();
        init( builder );
    }
    
    @Override
    public String toString()
    {
        return builder.toString();
    }
    
    public void print( BufferedWriter bw ) throws IOException
    {
        String[] lines = builder.toString().split("\n");
        for( String str : lines )
        {
            bw.write(str); bw.newLine();
        }
    }
    
    private int offset(Position pos)
    {
        return LINE_LENGTH + 5
             + (Config.ROWS - pos.row - 1 ) * 2 * LINE_LENGTH
             + pos.col * ( 3 + ITEM_LENGTH );
    }
    
    private void put(String str, Position pos)
    {
        int offset = offset(pos);
        builder.replace(offset, offset+ITEM_LENGTH,str);
    }
    
    private void put(Piece piece, Position pos)
    {
        if( piece == null ) put( EMPTY, pos );
        else put( CODE.get( piece ), pos);
    }
    
    public PrettyPrinter highlight(PositionList mask)
    {
        for( Position pos : mask ) put( MARK, pos );
        return this;
    }
    
    public PrettyPrinter load(Board board)
    {
        for( Position pos : Position.ALL ) put( board.at(pos), pos );
        return this;
    }
    
    public PrettyPrinter clear()
    {
        init( builder );
        return this;
    }
    
    private static void init(StringBuilder sb)
    {
        flush( sb );
        grid( sb );
        index( sb );
    }
    
    private static void flush(StringBuilder sb)
    {
        for( int i = 0; i < CHAR_COUNT; i++ )
            sb.append(" ");
        for( int i = LINE_LENGTH-1; i < CHAR_COUNT; i+=LINE_LENGTH )
            sb.setCharAt(i, '\n');
        
    }
    
    private static void grid(StringBuilder sb)
    {
        String rowString = "   +";
        String rowPart = "--+";
        for( int i = 0; i < ITEM_LENGTH; i++ ) rowPart = "-"+rowPart;
        for( int i = 0; i < Config.COLUMNS; i++ ) rowString += rowPart;
        
        String colString = "   |";
        String colPart = "  |";
        for( int i = 0; i < ITEM_LENGTH; i++ ) colPart = " "+colPart;
        for( int i = 0; i < Config.COLUMNS; i++ ) colString += colPart;
        
        sb.replace(0, LINE_LENGTH-1, rowString);
        for( int i = 0, offset = 0
           ; i < Config.ROWS
           ; i++ )
        {
            offset += LINE_LENGTH;
            sb.replace(offset, offset+LINE_LENGTH-1, colString);
            offset += LINE_LENGTH;
            sb.replace(offset, offset+LINE_LENGTH-1, rowString);
        }
        
    }
    
    private static void index(StringBuilder sb)
    {
        int offset = 1;
                
        for( int i = 0; i < Config.ROWS; i++ )
        {
            offset += LINE_LENGTH;
            sb.replace(offset, offset+1, ""+(Config.ROWS - i));
            offset += LINE_LENGTH;
        }
        
        offset += LINE_LENGTH * 2;
        
        for( int i = 0; i < Config.COLUMNS; i++ )
        {
            offset += 3 + ITEM_LENGTH;
            sb.setCharAt(offset, (char)(97+i));
        }
        
    }
    
    
}
