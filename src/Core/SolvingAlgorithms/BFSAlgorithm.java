package Core.SolvingAlgorithms;

import Core.Square.Entrance;
import Core.Square.Exit;
import Core.Square.Path;
import Core.Square.Square;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSAlgorithm {

    private Queue<Node> queue = new LinkedList<>();
    private List<Node> nodeList = new ArrayList<>();
    private boolean print;

    public BFSAlgorithm() {
    }

    public BFSAlgorithm(boolean print) {
        this.print = print;
    }

    public void solveBFS(Square[][] maze) {
        makeList(maze);
        makeConnections(maze);
        setEntranceExit(maze);
        Node exit = queue.element();
        boolean solve = false;
        while (!solve) {
            Node n = queue.remove();
            if (n.isExit) {
                exit = n;
                solve = true;
            } else
                addToQueue(n);
        }
        List<Node> solvePath = new ArrayList<>();
        solvePath.add(exit);
        while (exit.creator != null) {
            exit = exit.creator;
            solvePath.add(exit);
        }

        if (print) {
            for (Node n : solvePath) {
                System.out.print(n.number + " ");
                if (!n.isEntrance) {
                    System.out.print("-> ");
                }
            }
            showNumeration();
        }
    }

    private void addToQueue(Node n) {
        if (n.up != null) {
            queue.add(n.up);
            if (n.up.creator == null)
                n.up.creator = n;
            n.up.down = null;
        }
        if (n.right != null) {
            queue.add(n.right);
            if (n.right.creator == null)
                n.right.creator = n;
            n.right.left = null;
        }
        if (n.down != null) {
            queue.add(n.down);
            if (n.down.creator == null)
                n.down.creator = n;
            n.down.up = null;
        }
        if (n.left != null) {
            queue.add(n.left);
            if (n.left.creator == null)
                n.left.creator = n;
            n.left.right = null;
        }
    }

    private void makeList(Square[][] maze) {
        int height = maze[0].length;
        int width = maze.length;

        int counter = 1;
        for (int i = 1; i < height; i = i + 2) {
            for (int j = 1; j < width; j = j + 2) {
                Node n = new Node(counter++);
                nodeList.add(n);
            }
        }

    }

    private void makeConnections(Square[][] maze) {
        int height = maze[0].length;
        int width = maze.length;
        int w = (width - 1) / 2;
        int numNode = 0;
        for (int i = 1; i < height; i = i + 2) {
            for (int j = 1; j < width; j = j + 2) {
                if (maze[j][i - 1] instanceof Path)
                    nodeList.get(numNode).up = nodeList.get(numNode - w);
                if (maze[j][i + 1] instanceof Path)
                    nodeList.get(numNode).down = nodeList.get(numNode + w);
                if (maze[j - 1][i] instanceof Path)
                    nodeList.get(numNode).left = nodeList.get(numNode - 1);
                if (maze[j + 1][i] instanceof Path)
                    nodeList.get(numNode).right = nodeList.get(numNode + 1);

                numNode++;
            }
        }

    }

    private void setEntranceExit(Square[][] maze) {
        int height = maze[0].length;
        int width = maze.length;
        checkHorizontal(maze, width, 0);
        checkHorizontal(maze, width, height - 1);
        checkVertical(maze, width, height, 0);
        checkVertical(maze, width, height, width - 1);
    }

    private void checkHorizontal(Square[][] maze, int width, int i) {
        int w = (width - 1) / 2;
        for (int j = 1; j < width; j = j + 2) {
            if (maze[j][i] instanceof Entrance) {
                nodeList.get(((j - 1) / 2) + (i / 2) * (w - 1)).isEntrance = true;
                queue.add(nodeList.get(((j - 1) / 2) + (i / 2) * (w - 1)));
            } else if (maze[j][i] instanceof Exit) {
                nodeList.get(((j - 1) / 2) + (i / 2) * (w - 1)).isExit = true;
            }
        }
    }

    private void checkVertical(Square[][] maze, int width, int height, int j) {
        int x;
        if (j == 0)
            x = 0;
        else
            x = width / 2 - 1;
        for (int i = 1; i < height; i = i + 2) {
            if (maze[j][i] instanceof Entrance) {
                nodeList.get(((i - 1) / 2) * ((width - 1) / 2) + x).isEntrance = true;
                queue.add(nodeList.get(((i - 1) / 2) * ((width - 1) / 2) + x));
            } else if (maze[j][i] instanceof Exit) {
                nodeList.get(((i - 1) / 2) * ((width - 1) / 2) + x).isExit = true;
            }
        }
    }

    private void showNumeration() {
        System.out.println("\nNumeracja węzłów w labiryncie:");
        System.out.println("-------");
        System.out.println("|1|2|3|");
        System.out.println("-------");
        System.out.println("|4|5|6|");
        System.out.println("-------");
        System.out.println("|7|8|9|");
        System.out.println("-------");
    }

    private class Node extends Path {
        Node up;
        Node down;
        Node left;
        Node right;
        Node creator;
        int number;
        boolean isEntrance;
        boolean isExit;

        Node(int number) {
            this.number = number;
        }
    }
}
