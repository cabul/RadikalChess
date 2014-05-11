package radikalchess;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import radikalchess.Heuristic.HeuristicList;

public class AISearch {
    
    private final HeuristicList heuristics;
    private BufferedWriter debug;
    private int maxDepth = Config.SEARCH_DEPTH;
    private int timeout = Config.SEARCH_TIMEOUT * 1000;
    private Algorithm algorithm = Algorithm.minimax;
    private Metrics metrics;

    public static enum Algorithm{ minimax, alphabeta, iterative };
    
    public AISearch(HeuristicList heuristics)
    {
        this.heuristics = heuristics;
    }
    
    public AISearch(Heuristic... heuristics)
    {
        this.heuristics = new HeuristicList();
        this.heuristics.addAll(Arrays.asList(heuristics));
    }
    
    public AISearch debug(BufferedWriter debug)
    {
        this.debug = debug;
        return this;
    }
    
    public AISearch depth(int depth)
    {
        maxDepth = depth;
        return this;
    }
    
    public AISearch timeout(int timeout)
    {
        this.timeout = timeout * 1000;
        return this;
    }
    
    public AISearch use(Algorithm algorithm)
    {
        this.algorithm = algorithm;
        return this;
    }
    
    public static final class Metrics
    {
        public final int expanded;
        public final int depth;
        private Metrics(int expanded, int depth)
        {
            this.expanded = expanded;
            this.depth = depth;
        }
    }
    
    public Metrics metrics() {
        return metrics;
    }
    
    public Move decide(Board root)
    {
        List<Move> results = new ArrayList();
        float rv = Float.NEGATIVE_INFINITY;
        Color player = root.player();
        int expandedNodes = 0;
        int depth = 0;
        long maxTime = System.currentTimeMillis() + timeout;
        
        
        
        metrics = new Metrics(expandedNodes,depth);
        return null;
    }
    
    public int negamax(Board board, int depth, int alpha, int beta, int color)
    {
        return 0;
    }
    
    

}
