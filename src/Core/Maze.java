package Core;

import Core.Square.*;

import java.util.Arrays;
import java.util.Random;

public class Maze {

    private SquareInterface[][] maze;                   //maze [szerokosc][wysokosc]

    private void generateMaze(int width, int height) {

        int mazeWidth = width * 2 + 1;
        int mazeHeight = height * 2 + 1;
        maze = new SquareInterface[mazeWidth][mazeHeight];
        for (int i = 0; i < mazeHeight; i++) {
            for (int j = 0; j < mazeWidth; j++) {
                if (i == 0 || i == mazeHeight - 1 || j == 0 || j == mazeWidth - 1)
                    maze[j][i] = new Wall();
                else
                    maze[j][i] = new Path();
            }
        }

        makeEntrances(width, height);
        divide(1, mazeWidth - 2, 1, mazeHeight - 2);

        for (int i = 0; i < height * 2 + 1; i++) {
            for (int j = 0; j < width * 2 + 1; j++) {
                System.out.print(maze[j][i]);
            }
            System.out.println(" ");
        }
    }

    private void divide(int startWidth, int endWidth, int startHeight, int endHeight) {
        System.out.println(startWidth + " " + endWidth + " " + startHeight + " " + endHeight);
        Random r;
        int x, y;
        boolean z = false;
        int w = (endWidth - startWidth + 2) / 2;
        int h = (endHeight - startHeight + 2) / 2;
        if (w > 1 && h > 1) {
            r = new Random();
            if (w == h)
                z = r.nextBoolean();
            if (w > h || z) {
                x = r.nextInt(w - 1) * 2 + 2;
                y = r.nextInt(h) * 2 + 1;
                for (int i = startHeight; i <= endHeight; i++)
                    maze[x + startWidth - 1][i] = new Wall();
                maze[x + startWidth - 1][y + startHeight -1] = new Path();
                divide(startWidth, startWidth - 1 + x - 1, startHeight, endHeight);
                divide(startWidth - 1 + x + 1, endWidth, startHeight, endHeight);
            } else {
                x = r.nextInt(h - 1) * 2 + 2;
                y = r.nextInt(w) * 2 + 1;
                for (int i = startWidth; i <= endWidth; i++)
                    maze[i][x + startHeight - 1] = new Wall();
                maze[y + startWidth - 1][x + startHeight - 1] = new Path();
                divide(startWidth, endWidth, startHeight, startHeight -1 + x - 1);
                divide(startWidth, endWidth, startHeight -1 + x + 1, endHeight);
            }
        }
    }

    private void makeEntrances(int width, int height) {
        Random r = new Random();
        int x;
        int y;
        boolean a = r.nextBoolean();
        boolean b = r.nextBoolean();
        if (b) {
            x = r.nextInt(width) * 2 + 1;
            y = r.nextInt(width) * 2 + 1;
            if (a) {
                maze[x][0] = new Entrance();
                maze[y][height * 2] = new Exit();
            } else {
                maze[x][0] = new Exit();
                maze[y][height * 2] = new Entrance();
            }
        } else {
            x = r.nextInt(height) * 2 + 1;
            y = r.nextInt(height) * 2 + 1;
            if (a) {
                maze[0][x] = new Entrance();
                maze[width * 2][y] = new Exit();
            } else {
                maze[0][x] = new Exit();
                maze[width * 2][y] = new Entrance();
            }
        }
    }


    public static void main(String[] args) {
        Maze m = new Maze();
        m.generateMaze(20, 20);


    }
}
