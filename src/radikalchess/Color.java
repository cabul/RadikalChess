package radikalchess;

// Color

public enum Color 
{ 
    white,
    black;
    
    public Color enemy()
    {
        return (this == white)?black:white;
    }
    
    public static Color fromString(String color)
    {
        try{ 
            return Color.valueOf(color.toLowerCase());
        }catch(IllegalArgumentException ex){
            return null;
        }
    }
    
}
