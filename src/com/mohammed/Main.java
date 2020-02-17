package com.mohammed;


// class puzzleBoard


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.SQLOutput;
import java.util.*;

public class Main {

    public static int manhattanDistance(PuzzleBoard state, PuzzleBoard goal) {
        ArrayList<Character> statePositions = new ArrayList<>();
        ArrayList<Character> goalPositions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                statePositions.add(state.board[i][j]);
                goalPositions.add(goal.board[i][j]);
            }
        }
        int totalMoves = 0;
        char[] chars = {'1', '2', '3', '4', '5', '6', '7', '8', '-'};
        for (int i = 0; i < 9; i++) {
            int indexOfState = statePositions.indexOf(chars[i]) + 1;
            int indexOfGoal = goalPositions.indexOf(chars[i]) + 1;
            int difference = Math.abs(indexOfState - indexOfGoal);
            int movement = 0;
            while (difference >= 3) {
                movement++;
                difference-= 3;
            }
            movement+= difference;
            totalMoves+= movement;
        }
        return totalMoves;
    }

    public static int numberOfMisplacedTiles(PuzzleBoard state, PuzzleBoard goal) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state.board[i][j] != goal.board[i][j])
                    count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws Exception {
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
            System.out.println(manhattanDistance(pInitial, pGoal));
            aStar(pInitial,pGoal);
//            printSteps(BFS(pGoal, pInitial));
//            twoWayBFS(pGoal,pInitial);
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
        if (choice == 3) {
            File file = new File("C:\\Users\\Mohammed\\Development\\PuzzleInput");
            scanner = new Scanner(file);
//            FileOutputStream fos = new FileOutputStream(output);
//            PrintStream ps = new PrintStream(fos);
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            PuzzleBoard initialBoard = new PuzzleBoard(getChars(scanner), row, col);
            row = scanner.nextInt();
            col = scanner.nextInt();
            PuzzleBoard goalBoard = new PuzzleBoard(getChars(scanner), row, col);
            aStar(initialBoard,goalBoard);
        }
    }



    static boolean isFound = false;

//    public static void breadthFirstSearch(PuzzleBoard goal) {
//        Node initialNode = nodeQueue.poll();
//        int row = -3;
//        int col = -3;
//        if (initialNode.rootNode != null) {
//            row = initialNode.rootNode.state.row;
//            col = initialNode.rootNode.state.col;
//        }
////        if (initialNode == null)
////            return;
//        if (goal.equals(initialNode.state)) {
//            nodeQueue.clear();
//            isFound = true;
//            printSteps(initialNode);
//        }
//        int count = 0;
//        if (initialNode.state.col != 0 && initialNode.state.col-1 != col) {
//            nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'l'), initialNode));
//            count++;
//        }
//        if (initialNode.state.col != 2 && initialNode.state.col+1 != col) {
//            nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'r'), initialNode));
//            count++;
//        }
//        if (initialNode.state.row != 0 && initialNode.state.row-1 != row) {
//            nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'u'), initialNode));
//            count++;
//        }
//        if (initialNode.state.row != 2 && initialNode.state.row+1 != row) {
//            nodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'd'), initialNode));
//            count++;
//        }
//        for (int i = 0; i < count; i++) {
//            if (!isFound)
//                breadthFirstSearch(goal);
//        }
//    }

    public static void aStar(PuzzleBoard state, PuzzleBoard goal) throws Exception{
        state.level = 0;
        state.cost = manhattanDistance(state,goal) + state.level;
        PriorityQueue<PuzzleBoard> open = new PriorityQueue<>(new PuzzleBoardComparator());
        Queue<PuzzleBoard> closed = new LinkedList<>();
        open.add(state);
        while (!open.isEmpty()) {
            PuzzleBoard board = open.poll();
            closed.add(board);
            if (board.equals(goal)) {
                PrintStream ps = new PrintStream(new FileOutputStream("C:\\Users\\Mohammed\\Development\\Puzzle"));
                int counter = 1;
                while (!closed.isEmpty()) {
                    board = closed.poll();
                    ps.println("--------------Step number: "+counter+"--Level Number: 0"+board.level+"-----------");
                    board.printOnFile(ps);
                    counter++;
                }
                return;
            }
            PuzzleBoard[] newBoards = board.move();
            for (PuzzleBoard newBoard:newBoards) {
                newBoard.level = board.level + 1;
                newBoard.cost = manhattanDistance(newBoard,goal) + newBoard.level;
                open.add(newBoard);
            }

        }
    }

    static void twoWayBFS(PuzzleBoard goal,PuzzleBoard initialState) {
        Queue<Node> initialNodeQueue = new LinkedList<Node>();
        Queue<Node> goalNodeQueue = new LinkedList<Node>();
        Node initialNode = new Node(initialState);
        Node goalNode = new Node(goal);
        int row = -3;
        int col = -3;
        int goalRow = -3;
        int goalCol = -3;
        while (!goalNode.state.equals(initialNode.state)) {
            if (initialNode.rootNode != null) {
                row = initialNode.rootNode.state.row;
                col = initialNode.rootNode.state.col;
            }
            if (initialNode.state.col != 0 && initialNode.state.col-1 != col) {
                initialNodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'l'), initialNode));
            }
            if (initialNode.state.col != 2 && initialNode.state.col+1 != col) {
                initialNodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'r'), initialNode));
            }
            if (initialNode.state.row != 0 && initialNode.state.row-1 != row) {
                initialNodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'u'), initialNode));
            }
            if (initialNode.state.row != 2 && initialNode.state.row+1 != row) {
                initialNodeQueue.add(new Node(new PuzzleBoard(initialNode.state, 'd'), initialNode));
            }
            initialNode = initialNodeQueue.poll();
            //////////////////////////////////////////////
            if (goalNode.rootNode != null) {
                goalRow = initialNode.rootNode.state.row;
                goalCol = initialNode.rootNode.state.col;
            }
            if (goalNode.state.col != 0 && goalNode.state.col-1 != goalCol) {
                goalNodeQueue.add(new Node(new PuzzleBoard(goalNode.state, 'l'), goalNode));
            }
            if (goalNode.state.col != 2 && goalNode.state.col+1 != goalCol) {
                goalNodeQueue.add(new Node(new PuzzleBoard(goalNode.state, 'r'), goalNode));
            }
            if (goalNode.state.row != 0 && goalNode.state.row-1 != goalRow) {
                goalNodeQueue.add(new Node(new PuzzleBoard(goalNode.state, 'u'), goalNode));
            }
            if (goalNode.state.row != 2 && goalNode.state.row+1 != goalRow) {
                goalNodeQueue.add(new Node(new PuzzleBoard(goalNode.state, 'd'), goalNode));
            }
            goalNode = goalNodeQueue.poll();
        }
        printSteps(initialNode, goalNode);

    }

    static Node BFS(PuzzleBoard goal,PuzzleBoard initialState) {
        Queue<Node> nodeQueue = new LinkedList<Node>();
        Node initialNode = new Node(initialState);
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
        Stack<PuzzleBoard> puzzleBoards = new Stack<>();
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

    public static void printSteps(Node node, Node goalNode) {
        Stack<PuzzleBoard> puzzleBoards = new Stack<>();
        Queue<PuzzleBoard> goalBoards = new LinkedList<>();
        while (node.rootNode != null) {
            puzzleBoards.add(node.state);
            node = node.rootNode;
        }
        while (goalNode.rootNode != null) {
            goalBoards.add(goalNode.state);
            goalNode = goalNode.rootNode;
        }
        PuzzleBoard board;
        while (!puzzleBoards.empty()) {
            board = puzzleBoards.pop();
            System.out.println("-------------------------");
            board.print();
        }
        while (!goalBoards.isEmpty()) {
            board = goalBoards.poll();
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

    public static void readWritePuzzleBoard(String input, String output) throws FileNotFoundException {
        Stack<PuzzleBoard> puzzleBoards = new Stack<>();
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
        Node node = BFS(initialBoard,goalBoard);
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
