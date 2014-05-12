package radikalchess;

// Config

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;


public class Config {
    
    public static boolean DEBUG = false;
    
    public static final int ROWS = 6;
    public static final int COLUMNS = 4;
    
    public static final int ALL_SQUARES = ROWS * COLUMNS;
 
    public static final int SEARCH_DEPTH = 20;
    public static final int SEARCH_TIMEOUT = 10;
    
    public static void main(String[] args) throws IOException {
        
        //generatePosition("Position");
        System.out.println("Nothing to config");
        
    }

    private static void generatePosition(String pos) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/radikalchess/Position.java"));
        bw.write("// Generated on " + new Date()); bw.newLine();
        bw.write("package radikalchess;"); bw.newLine();
        bw.write("public enum "+pos+"{"); bw.newLine();
        for( int row = 0; row < ROWS; row++ )
        for( int col = 0; col < COLUMNS; col++ ) {
            bw.write( (char)( 97 + ( col )) );
            bw.write( (row+1) + "(" + row +", "+ col + ")");
            if( row != ROWS-1 || col != COLUMNS-1) bw.write(",");
            else bw.write(";");
            bw.newLine();
        }
        bw.write("public static class "+pos+"List extends ReadonlyList<"+pos+">{};"); bw.newLine();
        bw.write("public final int row,col;"); bw.newLine();
        bw.write("private "+pos+"(int row, int col){"); bw.newLine();
        bw.write("this.row = row;");bw.newLine();
        bw.write("this.col = col;");bw.newLine();
        bw.write("}");bw.newLine();
        bw.write("public static "+pos+" fromRowColumn(int row, int col){");bw.newLine();
        bw.write("String str = \"\";"); bw.newLine();
        bw.write("str += (char)(97+col);"); bw.newLine();
        bw.write("str += 1+row;");bw.newLine();
        bw.write("return fromString(str);");bw.newLine();
        bw.write("}");bw.newLine();
        bw.write("public static "+pos+" fromString(String str){");bw.newLine();
        bw.write("try{ return valueOf(str.toLowerCase()); }"); bw.newLine();
        bw.write("catch( IllegalArgumentException e ){ return null; }");bw.newLine();
        bw.write("}"); bw.newLine();
        bw.write("public int distance("+pos+" other){");bw.newLine();
        bw.write("return euclidean2( this,other );");bw.newLine();
        bw.write("}");bw.newLine();
        bw.write("private static int euclidean2("+pos+" a, "+pos+" b){");bw.newLine();
        bw.write("return ((b.row-a.row)*(b.row-a.row))+((b.col-a.col)*(b.col-a.col));");bw.newLine();
        bw.write("}");bw.newLine();
        bw.write("}");bw.newLine();
        bw.close();
    }
    
}
