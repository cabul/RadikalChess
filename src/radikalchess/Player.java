package radikalchess;

public enum Player {
    
    ai,
    human;
    
    public Decision decision;
    
    public static Player fromString(String str)
    {
        try{
            return Player.valueOf(str);
        }catch( IllegalArgumentException ex )
        {
            return null;
        }
    }
    
    public Move decide(Board board)
    {
        return decision.make(board);
    }
    
    public static interface Decision 
    {
        public Move make(Board board);
    }
    
}
