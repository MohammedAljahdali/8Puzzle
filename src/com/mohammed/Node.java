package com.mohammed;

public class Node {
    Node rootNode;
    PuzzleBoard state;


    public Node(PuzzleBoard state) {
        this.state = state;
    }

    public Node(PuzzleBoard state, Node rootNode) {
        this(state);
        this.rootNode = rootNode;
    }
}
