package radikalchess;

public class Statistics {
    
    public Position king;
    public long attacks;
    public long pieces;
    public int value;

    private Statistics(Position king, long attacks, long pieces, int value)
    {
        this.king = king;
        this.attacks = attacks;
        this.pieces = pieces;
        this.value = value;
    }
    
    public Statistics()
    {
        this(null,0L,0L,0);
    }

    @Override
    public Statistics clone() {
        return new Statistics(this.king.clone(),this.attacks,this.pieces,this.value);
    }
    
}
