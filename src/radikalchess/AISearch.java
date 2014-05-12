package radikalchess;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import radikalchess.Heuristic.HeuristicList;

public class AISearch{
    
    private static int NEGATIVE_INFINITY = -9999999;
    private static int POSITIVE_INFINITY = 9999999;
    
    private final HeuristicList heuristics;
    private BufferedWriter debug;
    private int maxDepth = Config.SEARCH_DEPTH;
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
    
    public Move negamax(Board root)
    {
        int expandedNodes = 0;
        int depth = 0;
        long maxTime = System.currentTimeMillis() + timeout;
        
        List<Move> moves = root.genMoves();
        Move bestMove = null;
        int best = NEGATIVE_INFINITY;
        
        dbg("Starting new Search");
        dbg(root);
        
        for( Move move : moves )
        {
            root.make(move);
            int val = negamax( root, 1, NEGATIVE_INFINITY, POSITIVE_INFINITY, 1);
            if( val > best ){
                bestMove = move;
            }
            root.unmake();
        }
        
        metrics = new Metrics(expandedNodes,depth);
        return bestMove;
    }
    
    private int negamax(Board board, int depth, int alpha, int beta, int color)
    {
        List<Move> moves = board.genMoves();
        
        dbg("Depth " + depth);
        dbg(board);
        
        if( moves.isEmpty() || depth == 0 ) {
            
            return color * heuristics.eval( board );
            
        }
        
        int best = NEGATIVE_INFINITY;
        
        for( Move move : moves )
        {
            board.make(move);
            int val = -negamax( board, depth - 1, -beta, -alpha, -color );
            best = Math.max( val, best );
            alpha = Math.max( alpha, val );
            board.unmake();
            if( alpha >= beta ) {
                break;
            }
        }
        
        return best;
    }
    
    
    private void dbg(String msg)
    {
        if( debug != null && Config.DEBUG )
        {
            try{
                debug.write(msg);
                debug.newLine();
                debug.flush();
            }catch(IOException ex)
            {
                
            }
        }
    }
    
    private void dbg(Board board)
    {
        if( debug != null )
        {
            try{
                printer.load(board).print(debug);
            }catch(IOException ex)
            {
                
            }
        }
    }
    
    
}
