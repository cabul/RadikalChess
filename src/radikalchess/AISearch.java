package radikalchess;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import radikalchess.Heuristic.HeuristicList;

public class AISearch{
    
    private static int NEGATIVE_INFINITY = -9999999;
    private static int POSITIVE_INFINITY = 9999999;
    
    private final HeuristicList heuristics;
    private int maxDepth = 5;
    private int timeout = Config.SEARCH_TIMEOUT * 1000;
    private Metrics metrics;
    
    private PrettyPrinter printer = new PrettyPrinter();

    public AISearch(HeuristicList heuristics)
    {
        this.heuristics = heuristics;
    }
    
    public AISearch(Heuristic... heuristics)
    {
        this.heuristics = new HeuristicList();
        this.heuristics.addAll(Arrays.asList(heuristics));
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
    
    public static final class Metrics
    {
        public int expanded;
        public int depth;
        public String trace;
        private Metrics()
        {
            trace = "Starting new Search\n";
        }
    }
    
    public Metrics metrics() {
        return metrics;
    }
    
    public Move negamax(Board root)
    {
        
        metrics = new Metrics();
        int expandedNodes = 0;
        int depth = 0;
        long maxTime = System.currentTimeMillis() + timeout;
        
        List<Move> moves = root.genMoves();
        if (moves == null) return null;
        Move bestMove = null;
        int best = NEGATIVE_INFINITY;
        
        for( Move move : moves )
        {
            root.make(move);
            int val = -negamax( root, maxDepth-1, NEGATIVE_INFINITY, POSITIVE_INFINITY);
            metrics.trace += "[0] " + move + " => "+val + "\n";
            if( val > best ){
                bestMove = move;
                best = val;
            }
            root.unmake();
        }
        
        return bestMove;
    }
    
    // Return best value for board and its current player
    private int negamax(Board board, int depth, int alpha, int beta)
    {
        
        List<Move> moves = board.genMoves();
        
        if( depth == 0 || moves.isEmpty ()) { // Depth limit reached or no more moves
            return heuristics.eval(board);
        }
        
        int best = NEGATIVE_INFINITY;
        
        String off = "";
        for( int i = -1; i < maxDepth - depth; i++ ) off += "  ";
        
        for( Move move : moves )
        {
            board.make(move);
            int val = -negamax( board, depth - 1, -beta, -alpha);
            metrics.trace += "["+(maxDepth - depth) + "] " + off + move + " => " + val +"\n";
            best = Math.max( val, best );
            alpha = Math.max( alpha, val );
            board.unmake();
            if( alpha >= beta ) {
                break;
            }
        }
        return best;
        
    }
    
}
