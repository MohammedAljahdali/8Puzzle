package com.mohammed;


// class puzzleBoard


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    //create a print board method
    // test
    public static void main(String[] args) {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("To Enter The Boards manually Press 1." +
                "\nTo Enter The Boards by file Press 2.");
        choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("Enter the initial state board");
            PuzzleBoard pInitial = enterBoard();
            System.out.println("Enter the goal board");
            PuzzleBoard pGoal = enterBoard();
            Node node = new Node(pInitial);
            nodeQueue.add(node);
            BFS(pGoal);
        }
        if (choice == 2) {
            System.out.println("Please follow the following format:\n" +
                    "initial state:\n" +
                    "row column\n" +
                    "1 2 3\n" +
                    "4 5 6\n" +
                    "7 8 9\n" +
                    "goal state:\n" +
                    "row column\n" +
                    "1 2 3\n" +
                    "4 5 6\n" +
                    "7 8 9\n");
            System.out.print("Enter the input file path: ");
            String inputPath = scanner.next();
            System.out.print("Enter the output file path: ");
            String outputPath = scanner.next();
            try {
                readWritePuzzleBoard(inputPath, outputPath);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    static Queue<Node> nodeQueue = new LinkedList<Node>();
    static Stack<PuzzleBoard> puzzleBoards = new Stack<>();
    static boolean isFound = false;

    public static void breadthFirstSearch(PuzzleBoard goal) {
        Node initialNode = nodeQueue.poll();
        int row = -3;
        int col = -3;
        if (initialNode.rootNode != null) {
            row = initialNode.rootNode.state.row;
            col = initialNode.rootNode.state.col;
        }
//        if (initialNode == null)
//            return;
        if (goal.equals(initialNode.state)) {
            nodeQueue.clear();
            isFound = true;
            printSteps(initialNode);
        }
        int count = 0;
        if (initialNode.state.col != 0 && initialNode.state.col-1 != col) {
            nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'l'), initialNode));
            count++;
        }
        if (initialNode.state.col != 2 && initialNode.state.col+1 != col) {
            nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'r'), initialNode));
            count++;
        }
        if (initialNode.state.row != 0 && initialNode.state.row-1 != row) {
            nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'u'), initialNode));
            count++;
        }
        if (initialNode.state.row != 2 && initialNode.state.row+1 != row) {
            nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'd'), initialNode));
            count++;
        }
        for (int i = 0; i < count; i++) {
            if (!isFound)
                breadthFirstSearch(goal);
        }
    }

    static Node BFS(PuzzleBoard goal) {
        Node initialNode = nodeQueue.poll();
        int row = -3;
        int col = -3;
        while (!goal.equals(initialNode.state)) {
            if (initialNode.rootNode != null) {
                row = initialNode.rootNode.state.row;
                col = initialNode.rootNode.state.col;
            }
            if (initialNode.state.col != 0 && initialNode.state.col-1 != col) {
                nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'l'), initialNode));
            }
            if (initialNode.state.col != 2 && initialNode.state.col+1 != col) {
                nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'r'), initialNode));
            }
            if (initialNode.state.row != 0 && initialNode.state.row-1 != row) {
                nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'u'), initialNode));
            }
            if (initialNode.state.row != 2 && initialNode.state.row+1 != row) {
                nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'd'), initialNode));
            }
            initialNode = nodeQueue.poll();
        }
        return initialNode;
    }

    public static void printSteps(Node node) {
        while (node.rootNode != null) {
            puzzleBoards.add(node.state);
            node = node.rootNode;
        }
        PuzzleBoard board;
        while (!puzzleBoards.empty()) {
            board = puzzleBoards.pop();
            System.out.println("-------------------------");
            board.print();
        }
    }

    public static PuzzleBoard enterBoard() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the position of the empty tile: ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        System.out.print("Enter the tiles in the following Order:" +
                "\n1 2 3" +
                "\n4 5 6" +
                "\n7 8 9" +
                "\nEnter: ");
        char[][] board = getChars(scanner);
        return new PuzzleBoard(board, row, col);
    }

    public static char[][] getChars(Scanner scanner) {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String element = scanner.next();
                board[i][j] = element.charAt(0);
            }
        }
        return board;
    }

    public static Node setupBFS(PuzzleBoard initialState, PuzzleBoard goalState) {
        Node node = new Node(initialState);
        nodeQueue.add(node);
        Node output = BFS(goalState);
        return output;
    }

    public static void readWritePuzzleBoard(String input, String output) throws FileNotFoundException {
        File file = new File(input);
        Scanner scanner = new Scanner(file);
        FileOutputStream fos = new FileOutputStream(output);
        PrintStream ps = new PrintStream(fos);
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        PuzzleBoard initialBoard = new PuzzleBoard(getChars(scanner), row, col);
        row = scanner.nextInt();
        col = scanner.nextInt();
        PuzzleBoard goalBoard = new PuzzleBoard(getChars(scanner), row, col);
        Node node = setupBFS(initialBoard,goalBoard);
        while (node.rootNode != null) {
            puzzleBoards.add(node.state);
            node = node.rootNode;
        }
        PuzzleBoard board;
        while (!puzzleBoards.empty()) {
            board = puzzleBoards.pop();
            ps.println("-------------------------");
            board.printOnFile(ps);
        }
    }

}
