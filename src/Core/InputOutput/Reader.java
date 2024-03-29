package Core.InputOutput;

import Core.Maze;
import Core.Square.Entrance;
import Core.Square.Exit;
import Core.Square.Path;
import Core.Square.Wall;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Reader {
    public void readFile(Maze m, File f) throws IOException {
        List<String> lines = Files.readAllLines(f.toPath());
        m.generateEmptyMaze(lines.get(0).length(), lines.size());
        for (int i = 0; i < m.getHeight(); i++) {
            for (int j = 0; j < m.getWidth(); j++)
                switch (lines.get(i).charAt(j)) {
                    case '+': {
                        m.setSquareAtIndex(j, i, new Wall());
                        break;
                    }
                    case '0': {
                        m.setSquareAtIndex(j, i, new Path());
                        break;
                    }
                    case '#': {
                        m.setSquareAtIndex(j, i, new Entrance());
                        break;
                    }
                    case '*': {
                        m.setSquareAtIndex(j, i, new Exit());
                        break;
                    }
                }
        }


    }
}
