package radikalchess;

public interface Heuristic {

    public static class HeuristicList extends ReadonlyList<Heuristic>{};
    
    public float getFor(Board board);
    
}
