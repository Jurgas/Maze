package Core.InputOutput;

import Core.Maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static Core.InputOutput.ArgsChooser.fileChooser;

public class Writer {
    public static void writeToFile(String[] args, Maze m) {
        File f;
        try {
            f = fileChooser(args, "-w");
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
        } catch (FileNotGivenException e) {
            System.out.println(e.getMessage());
        }
    }
}
