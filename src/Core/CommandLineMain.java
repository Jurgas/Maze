package Core;

import Core.InputOutput.ArgsChooser;
import Core.InputOutput.FileNotGivenException;
import Core.InputOutput.Reader;
import Core.SolvingAlgorithms.BFSAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static Core.InputOutput.Writer.writeToFile;

public class CommandLineMain {
    public static void main(String[] args) {
        Maze m = new Maze();
        Reader r = new Reader();
        ArgsChooser ac = new ArgsChooser();
        File f;

        if (args.length != 0)
            for (String arg : args) {
                if (arg.equals("-r")) {
                    try {
                        f = ac.fileChooser(args, "-r");
                        try {
                            r.readFile(m, f);
                        } catch (IOException e) {
                            System.out.println("Nie znaleziono podanego pliku: " + f.getAbsolutePath());
                            System.exit(-2);
                        }
                    } catch (FileNotGivenException e) {
                        System.out.println(e.getMessage());
                        System.exit(-3);
                    }
                }
            }
        else {
            Scanner s = new Scanner(System.in);
            System.out.println("Wpisz rozmiary labiryntu (szerokość wysokość) który chcesz wygenerować.");
            int w = s.nextInt();
            int h = s.nextInt();
            m.generateMaze(w, h);
            s.close();
        }

        for (int i = 0; i < m.getHeight(); i++) {
            for (int j = 0; j < m.getWidth(); j++)
                System.out.print(m.getMaze()[j][i]);
                System.out.println();
        }

        BFSAlgorithm b = new BFSAlgorithm();
        b.solveBFS(m.getMaze());

        for (String arg : args) {
            if (arg.equals("-w")) {
                try {
                    f = ac.fileChooser(args, "-w");
                    writeToFile(m, f);
                } catch (FileNotGivenException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
