package Core.SolvingAlgorithms;

import Core.Square.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Core.InputOutput.Writer.writeToFile;

public class TremauxAlgorithm {

    private Square[][] markedMaze;
    private MarkedPath entrance;
    private Square position;
    private int curWidth;
    private int curHeight;
    private int lastDirection;
    private String fileName;

    public TremauxAlgorithm() {
    }

    public TremauxAlgorithm(String fileName) {
        this.fileName = fileName;
    }

    public void solveTremaux(Square[][] m) {
        if (markedMaze == null) {
            copyMaze(m);
        }
        makePaths();
        setEntranceExit(markedMaze);

        position = entrance;
        while (!((MarkedPath) position).isExit) {
            choosePath();
        }

        lastDirection = -1;
        while (!position.equals(entrance)) {
            setSolvePath();
        }
        entrance.solvePath = true;

        if (fileName != null)
            writeToFile(markedMaze, new File(fileName));
    }

    private void choosePath() {
        List<Integer> unseenPath = new ArrayList<>();
        List<Integer> seenPath = new ArrayList<>();

        int unseen = 0;

        for (int k = 0; k < 4; k++) {
            if (((MarkedPath) markedMaze[curWidth][curHeight]).way[k] == 0) {
                unseen++;
                unseenPath.add(k);
            }
        }

        if (unseen == 1) {
            move(unseenPath.get(0), false);
        } else if (unseen > 1) {
            chooseMove(unseenPath);
        } else {
            if (!moveBack()) {
                for (int k = 0; k < 4; k++) {
                    if (((MarkedPath) markedMaze[curWidth][curHeight]).way[k] == 1) {
                        seenPath.add(k);
                    }
                }
                chooseMove(seenPath);
            }
        }
    }

    private void chooseMove(List<Integer> l) {
        move(l.get(new Random().nextInt(l.size())), false);
    }

    private void move(int direction, boolean setSolvePath) {
        switch (direction) {
            case 0:
                ((MarkedPath) position).way[0]++;
                moveUp(setSolvePath);
                ((MarkedPath) position).way[1]++;
                lastDirection = 1;
                break;
            case 1:
                ((MarkedPath) position).way[1]++;
                moveDown(setSolvePath);
                ((MarkedPath) position).way[0]++;
                lastDirection = 0;
                break;
            case 2:
                ((MarkedPath) position).way[2]++;
                moveLeft(setSolvePath);
                ((MarkedPath) position).way[3]++;
                lastDirection = 3;
                break;
            case 3:
                ((MarkedPath) position).way[3]++;
                moveRight(setSolvePath);
                ((MarkedPath) position).way[2]++;
                lastDirection = 2;
                break;
        }
    }

    private void moveUp(boolean setSolvePath) {
        do {
            if (setSolvePath) {
                ((MarkedPath) position).solvePath = true;
                ((MarkedPath) markedMaze[curWidth][curHeight - 1]).solvePath = true;
            }
            curHeight = curHeight - 2;
            position = markedMaze[curWidth][curHeight];
        } while (((MarkedPath) position).isTunnel && !((MarkedPath) position).isExit);
    }

    private void moveDown(boolean setSolvePath) {
        do {
            if (setSolvePath) {
                ((MarkedPath) position).solvePath = true;
                ((MarkedPath) markedMaze[curWidth][curHeight + 1]).solvePath = true;
            }
            curHeight = curHeight + 2;
            position = markedMaze[curWidth][curHeight];
        } while (((MarkedPath) position).isTunnel && !((MarkedPath) position).isExit);
    }

    private void moveLeft(boolean setSolvePath) {
        do {
            if (setSolvePath) {
                ((MarkedPath) position).solvePath = true;
                ((MarkedPath) markedMaze[curWidth - 1][curHeight]).solvePath = true;
            }
            curWidth = curWidth - 2;
            position = markedMaze[curWidth][curHeight];
        } while (((MarkedPath) position).isTunnel && !((MarkedPath) position).isExit);
    }

    private void moveRight(boolean setSolvePath) {
        do {
            if (setSolvePath) {
                ((MarkedPath) position).solvePath = true;
                ((MarkedPath) markedMaze[curWidth + 1][curHeight]).solvePath = true;
            }
            curWidth = curWidth + 2;
            position = markedMaze[curWidth][curHeight];
        } while (((MarkedPath) position).isTunnel && !((MarkedPath) position).isExit);
    }

    private boolean moveBack() {
        if (((MarkedPath) position).way[lastDirection] != 2) {
            move(lastDirection, false);
            return true;
        }
        return false;
    }

    private void setSolvePath() {
        for (int k = 0; k < 4; k++) {
            if (((MarkedPath) position).way[k] == 1 && k != lastDirection) {
                move(k, true);
            }
        }
    }

    public void copyMaze(Square[][] maze) {
        markedMaze = new Square[maze.length][maze[0].length];
        for (int i = 0; i < maze[0].length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[j][i] instanceof Path)
                    markedMaze[j][i] = new MarkedPath();
                else if (maze[j][i] instanceof Wall)
                    markedMaze[j][i] = new Wall();
                else if (maze[j][i] instanceof Entrance)
                    markedMaze[j][i] = new Entrance();
                else if (maze[j][i] instanceof Exit)
                    markedMaze[j][i] = new Exit();
            }
        }
    }

    private void makePaths() {
        int height = markedMaze[0].length;
        int width = markedMaze.length;
        int horCounter = 0;
        int verCounter = 0;

        for (int i = 1; i < height; i = i + 2) {
            for (int j = 1; j < width; j = j + 2) {
                if (markedMaze[j][i - 1] instanceof MarkedPath) {
                    ((MarkedPath) markedMaze[j][i]).way[0] = 0;
                    verCounter++;
                }
                if (markedMaze[j][i + 1] instanceof MarkedPath) {
                    ((MarkedPath) markedMaze[j][i]).way[1] = 0;
                    verCounter++;
                }
                if (markedMaze[j - 1][i] instanceof MarkedPath) {
                    ((MarkedPath) markedMaze[j][i]).way[2] = 0;
                    horCounter++;
                }
                if (markedMaze[j + 1][i] instanceof MarkedPath) {
                    ((MarkedPath) markedMaze[j][i]).way[3] = 0;
                    horCounter++;
                }

                if ((verCounter == 2 && horCounter == 0) || (verCounter == 0 && horCounter == 2))
                    ((MarkedPath) markedMaze[j][i]).isTunnel = true;

                horCounter = 0;
                verCounter = 0;
            }
        }

    }

    private void setEntranceExit(Square[][] maze) {
        int h = 0;
        for (int j = 1; j < maze.length; j = j + 2)
            if (maze[j][h] instanceof Entrance) {
                entrance = (MarkedPath) maze[j][h + 1];
                entrance.way[0] = 2;
                curWidth = j;
                curHeight = h + 1;
            } else if (maze[j][h] instanceof Exit) {
                ((MarkedPath) maze[j][h + 1]).isExit = true;
            }

        h = maze[0].length - 1;
        for (int j = 1; j < maze.length; j = j + 2)
            if (maze[j][h] instanceof Entrance) {
                entrance = (MarkedPath) maze[j][h - 1];
                entrance.way[1] = 2;
                curWidth = j;
                curHeight = h - 1;
            } else if (maze[j][h] instanceof Exit) {
                ((MarkedPath) maze[j][h - 1]).isExit = true;
            }

        int w = 0;
        for (int i = 1; i < maze[0].length; i = i + 2)
            if (maze[w][i] instanceof Entrance) {
                entrance = (MarkedPath) maze[w + 1][i];
                entrance.way[2] = 2;
                curWidth = w + 1;
                curHeight = i;
            } else if (maze[w][i] instanceof Exit) {
                ((MarkedPath) maze[w + 1][i]).isExit = true;
            }

        w = maze.length - 1;
        for (int i = 1; i < maze[0].length; i = i + 2)
            if (maze[w][i] instanceof Entrance) {
                entrance = (MarkedPath) maze[w - 1][i];
                entrance.way[3] = 2;
                curWidth = w - 1;
                curHeight = i;
            } else if (maze[w][i] instanceof Exit) {
                ((MarkedPath) maze[w - 1][i]).isExit = true;
            }

        entrance.isTunnel = false;

    }

    private class MarkedPath extends Path {
        int[] way = {-1, -1, -1, -1};       //up, down, left, right
        boolean solvePath;
        boolean isTunnel;
        boolean isExit;

        @Override
        public String toString() {
            if (solvePath)
                return "x";
            else
                return "0";
        }


    }
}
