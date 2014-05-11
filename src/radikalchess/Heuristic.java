package radikalchess;

import java.util.ArrayList;

public abstract class Heuristic {

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
    
    public abstract int eval(Board board);
    
}
