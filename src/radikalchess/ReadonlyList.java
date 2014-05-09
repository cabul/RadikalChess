package radikalchess;

import java.util.ArrayList;
import java.util.Iterator;


public class ReadonlyList<T> extends ArrayList<T>
{
    
    public final Iterable<Position> ro;
    
    private ReadonlyList( int size)
    {
        super( size );
        ro = new Iterable() {

            @Override
            public Iterator iterator() {
                return new Iterator<T>() {
                    private int head = 0;
                    @Override
                    public boolean hasNext() {
                        return head < size();
                    }

                    @Override
                    public T next() {
                        return get(head++);
                    }

                    @Override
                    public void remove() {
                    }
                };
            }
        };
    }
    
    public ReadonlyList()
    {
        this( Config.LIST_SIZE );
    }

    @Override
    public void clear() {
        super.clear();
        trimToSize();
        ensureCapacity( Config.LIST_SIZE );
    }
    
    @Override
    public ReadonlyList<T> clone()
    {
        ReadonlyList<T> l = new ReadonlyList(size());
        l.addAll(this);
        return l;
    }
            
    
}
