package radikalchess;

public enum Color 
{ 
    WHITE,
    BLACK;
    
    public Color enemy()
    {
        return (this == WHITE)?BLACK:WHITE;
    }
    
}
