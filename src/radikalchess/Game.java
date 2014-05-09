package radikalchess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import radikalchess.Move.MoveList;

public final class Game {
    
    private MoveList history;
    private Board init;
    private Board current;
    private int head;

    public Game()
    {
        init = Board.init();
        history = new MoveList();
        reset();
    }
    
    public Board board()
    {
        return current;
    }
    
    public Color player()
    {
        return board().player();
    }
    
    public int turn()
    {
        return board().turn();
    }
    
    public void advance(Move move)
    {
        while( head < history.size() )
            history.remove(history.get(head));
        history.add(move);
        head++;
        move_head();
    }
    
    public boolean undo()
    {
        if( head <= 0 ) return false;
        head--;
        move_head();
        return true;
    }
    
    public boolean redo()
    {
        if( head > history.size() - 1) return false;
        head++;
        move_head();
        return true;
    }
    
    private void move_head()
    {
        current = init.clone();
        for( int i = 0; i < head; i++ )
        {
            current.apply(history.get(i));
        }
    }
    
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(init.save());
        bw.newLine();
        bw.write("/");
        bw.newLine();
        for(Move move : history) {
            bw.write(move.toString());
            bw.newLine();
        }
        bw.write("/");
        bw.newLine();
    }
    
    public void load(BufferedReader br) throws IOException
    {
        reset();
        String board = "";
        String line;
        while(!"/".equals(line = br.readLine()))
            board+=line+"\n";
        init.load(board);
        while(!"/".equals(line = br.readLine()))
        {
            history.add(Move.fromString(line));
        }
        head = history.size();
        move_head();
    }

    public void reset() {
        head = 0;
        history.clear();
        current = init.clone();
    }
    
}
