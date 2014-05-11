// Generated on Sat May 10 13:58:57 WEST 2014
package radikalchess;

import java.util.ArrayList;

public enum Position {

    a1(0, 0),
    b1(0, 1),
    c1(0, 2),
    d1(0, 3),
    a2(1, 0),
    b2(1, 1),
    c2(1, 2),
    d2(1, 3),
    a3(2, 0),
    b3(2, 1),
    c3(2, 2),
    d3(2, 3),
    a4(3, 0),
    b4(3, 1),
    c4(3, 2),
    d4(3, 3),
    a5(4, 0),
    b5(4, 1),
    c5(4, 2),
    d5(4, 3),
    a6(5, 0),
    b6(5, 1),
    c6(5, 2),
    d6(5, 3);

    public final int row, col;

    private Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Position fromRowColumn(int row, int col) {
        String str = "";
        str += (char) (97 + col);
        str += 1 + row;
        return fromString(str);
    }

    public static Position fromString(String str) {
        try {
            return valueOf(str.toLowerCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public int distance(Position other) {
        return euclidean2(this, other);
    }

    private static int euclidean2(Position a, Position b) {
        return ((b.row - a.row) * (b.row - a.row)) + ((b.col - a.col) * (b.col - a.col));
    }
    
}
