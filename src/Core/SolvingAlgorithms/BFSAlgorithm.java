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

    public void solveBFS(Square[][] maze) {
        makeList(maze);
        makeConnections(maze);
        setEntranceExit(maze);
        Node exit = queue.element();
        boolean solve = false;
        if (queue.element().isExit)
            solve = true;
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

        for (Node n : solvePath) {
            System.out.print(n.number + " ");
            if (!n.isEntrance) {
                System.out.print("-> ");
            }
        }
    }

    private void addToQueue(Node n) {
        if (n.up != null && n.up != n.creator) {
            queue.add(n.up);
            n.up.creator = n;
        }
        if (n.right != null && n.right != n.creator) {
            queue.add(n.right);
            n.right.creator = n;
        }
        if (n.down != null && n.down != n.creator) {
            queue.add(n.down);
            n.down.creator = n;
        }
        if (n.left != null && n.left != n.creator) {
            queue.add(n.left);
            n.left.creator = n;
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
