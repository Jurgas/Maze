package Core.InputOutput;

import Core.Square.Square;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Writer {
    public static void writeToFile(Square[][] m, File f) {
        try {
            PrintWriter writer = new PrintWriter(f);
            for (int i = 0; i < m[0].length; i++) {
                for (Square[] squares : m) writer.print(squares[i]);
                if (i + 1 != m[0].length)
                    writer.println();
            }
            writer.close();
        } catch (FileNotFoundException ignored) {
        }
    }
}
