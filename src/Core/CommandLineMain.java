package Core;

import Core.InputOutput.ArgsChooser;
import Core.InputOutput.FileNotGivenException;
import Core.InputOutput.Reader;
import Core.SolvingAlgorithms.BFSAlgorithm;
import Core.SolvingAlgorithms.TremauxAlgorithm;

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

        boolean read = false;

        if (args.length != 0) {
            for (String arg : args) {
                if (arg.equals("-r")) {
                    try {
                        f = ac.fileChooser(args, "-r");
                        try {
                            r.readFile(m, f);
                            read = true;
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
        }
        if (!read) {
            Scanner s = new Scanner(System.in);
            System.out.println("Wpisz rozmiary labiryntu (szerokość wysokość), który chcesz wygenerować.");
            System.out.print("Szerokość: ");
            int w = s.nextInt();
            System.out.print("Wysokość: ");
            int h = s.nextInt();
            System.out.println("Czy chcesz wygenerować labirynt z większą liczbą rozwiązań? (t/n)");
            String ans = s.next();
            boolean sol = false;
            boolean enter = false;
            do {
                if (ans.equals("t")) {
                    sol = true;
                    enter = true;
                } else if (ans.equals("n")) {
                    enter = true;
                } else {
                    System.out.println("Wpisano zły znak! Wpisz 't' albo 'n'.");
                    ans = s.next();
                }
            } while (!enter);


            m.generateMaze(w, h, sol);
            s.close();
        }

        System.out.println("---Labirynt---");
        for (int i = 0; i < m.getHeight(); i++) {
            for (int j = 0; j < m.getWidth(); j++)
                System.out.print(m.getMaze()[j][i]);
            System.out.println();
        }

        System.out.println("\nRozwiązanie algorytmem BFS: ");
        BFSAlgorithm b = new BFSAlgorithm(true);
        b.solveBFS(m.getMaze());

        String tremaux = "solveTremaux.txt";
        System.out.println("\nRozwiązanie algorytmem Tremaux zapisane do pliku: " + tremaux);
        TremauxAlgorithm t = new TremauxAlgorithm(tremaux);
        t.solveTremaux(m.getMaze());
        System.out.println("Ścieżka oznaczona jest znakami 'x'");

        for (String arg : args) {
            if (arg.equals("-w")) {
                try {
                    f = ac.fileChooser(args, "-w");
                    writeToFile(m.getMaze(), f);
                } catch (FileNotGivenException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
