import java.util.*;

class Solution {
    public boolean isValidSudoku(char[][] board) {
        // Use hash sets to record seen numbers
        Set<Character>[] rows = new HashSet[9];
        Set<Character>[] cols = new HashSet[9];
        Set<Character>[] boxes = new HashSet[9];

        // Initialize all sets
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }

        // Traverse every cell in the board
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char ch = board[r][c];

                if (ch == '.') continue; // Skip empty cells

                // Calculate box index
                int boxIndex = (r / 3) * 3 + (c / 3);

                // Check for duplicates
                if (rows[r].contains(ch) || cols[c].contains(ch) || boxes[boxIndex].contains(ch)) {
                    return false;
                }

                // Mark the digit as seen
                rows[r].add(ch);
                cols[c].add(ch);
                boxes[boxIndex].add(ch);
            }
        }

        return true;
    }
}
