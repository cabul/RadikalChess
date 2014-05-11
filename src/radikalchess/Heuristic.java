package radikalchess;

import java.util.ArrayList;

public interface Heuristic {

    public static class HeuristicList extends ArrayList<Heuristic>
    {
        public int eval(Board board)
        {
            int val = 0;
            for( Heuristic heur : this )
                val += heur.eval(board);
            return val;
        }
    };
    
    public int eval(Board board);
    
}
