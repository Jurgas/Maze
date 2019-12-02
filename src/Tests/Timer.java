package Tests;

import Core.Maze;
import Core.SolvingAlgorithms.BFSAlgorithm;
import Core.SolvingAlgorithms.TremauxAlgorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Timer {
    public static void main(String[] args) throws IOException {
        BFSAlgorithm bfs;
        TremauxAlgorithm tre;
        Maze m = new Maze();
        Writer output = new BufferedWriter(new FileWriter("results.txt", true));


        long startBFS, endBFS, startTre, endTre;


        m.generateMaze(500, 500, false);                 // warm-up
        tre = new TremauxAlgorithm();
        bfs = new BFSAlgorithm();
        bfs.solveBFS(m.getMaze());
        tre.solveTremaux(m.getMaze());


        output.append("BFS Tremaux");
        for (int i = 10; i <= 500; i = i + 10) {
            bfs = new BFSAlgorithm();
            tre = new TremauxAlgorithm();
            m.generateMaze(i, i, false);
            tre.copyMaze(m.getMaze());


            startBFS = System.nanoTime();
            for (int j = 0; j < 100; j++) {
                bfs.solveBFS(m.getMaze());
                bfs.clearNodeList();
                bfs.clearQueue();
            }
            endBFS = System.nanoTime();

            startTre = System.nanoTime();
            for (int j = 0; j < 100; j++) {
                tre.solveTremaux(m.getMaze());
            }
            endTre = System.nanoTime();

            output.append("\n").append(String.valueOf((endBFS - startBFS) / 100)).append(" ").append(String.valueOf((endTre - startTre) / 100));
            System.err.println(i);
        }

        output.close();

    }
}
