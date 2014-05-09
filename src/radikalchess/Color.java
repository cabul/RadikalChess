package radikalchess;

// Color

public enum Color 
{ 
    WHITE,
    BLACK;
    
    public Color enemy()
    {
        return (this == WHITE)?BLACK:WHITE;
    }
    
    @Override
    public String toString()
    {
        return super.toString().toLowerCase();
    }
    
    public static Color fromString(String color)
    {
        try{ 
            return Color.valueOf(color.toUpperCase());
        }catch(IllegalArgumentException ex){
            return null;
        }
    }
    
}
