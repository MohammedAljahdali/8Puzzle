package com.mohammed;

import java.util.Comparator;

public class PuzzleBoardComparator implements Comparator<PuzzleBoard> {
    public int compare(PuzzleBoard p1, PuzzleBoard p2) {
        if (p1.cost > p2.cost)
            return 1;
        else if (p1.cost < p2.cost)
            return -1;
        return 0;
    }
}
