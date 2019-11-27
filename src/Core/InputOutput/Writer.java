package Core.InputOutput;

import Core.Maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Writer {
    public static void writeToFile(Maze m, File f) {
        try {
            PrintWriter writer = new PrintWriter(f);
            for (int i = 0; i < m.getHeight(); i++) {
                for (int j = 0; j < m.getWidth(); j++)
                    writer.print(m.getMaze()[j][i]);
                if (i + 1 != m.getHeight())
                    writer.println();
            }
            writer.close();
        } catch (FileNotFoundException ignored) {
        }
    }
}
