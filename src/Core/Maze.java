package Core;

import Core.Square.*;

import java.util.Random;


public class Maze {

    private Square[][] maze;                   //maze [width][height]

    public int getHeight() {
        return this.maze[0].length;
    }

    public int getWidth() {
        return this.maze.length;
    }

    public void setSquareAtIndex(int w, int h, Square square) {
        maze[w][h] = square;
    }

    public Square[][] getMaze() {
        return maze;
    }

    public void generateEmptyMaze(int width, int height) {
        maze = new Square[width][height];
    }

    public void generateMaze(int width, int height) {
        generateEmptyMaze(width * 2 + 1, height * 2 + 1);
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (i == 0 || i == getHeight() - 1 || j == 0 || j == getWidth() - 1)
                    maze[j][i] = new Wall();
                else
                    maze[j][i] = new Path();
            }
        }

        makeEntrances(width, height);
        divide(1, getWidth() - 2, 1, getHeight() - 2);
    }


    private void divide(int startWidth, int endWidth, int startHeight, int endHeight) {
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
                maze[x + startWidth - 1][y + startHeight - 1] = new Path();
                divide(startWidth, startWidth - 1 + x - 1, startHeight, endHeight);
                divide(startWidth - 1 + x + 1, endWidth, startHeight, endHeight);
            } else {
                x = r.nextInt(h - 1) * 2 + 2;
                y = r.nextInt(w) * 2 + 1;
                for (int i = startWidth; i <= endWidth; i++)
                    maze[i][x + startHeight - 1] = new Wall();
                maze[y + startWidth - 1][x + startHeight - 1] = new Path();
                divide(startWidth, endWidth, startHeight, startHeight - 1 + x - 1);
                divide(startWidth, endWidth, startHeight - 1 + x + 1, endHeight);
            }
        }
    }

    private void makeEntrances(int width, int height) {
        Random r = new Random(1);
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
}

