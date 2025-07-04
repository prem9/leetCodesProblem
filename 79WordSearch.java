class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        // Try starting DFS from each cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, String word, int i, int j, int index) {
        // Base case: all characters matched
        if (index == word.length()) return true;

        // Out of bounds or already visited or character mismatch
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length ||
            board[i][j] != word.charAt(index)) {
            return false;
        }

        // Mark current cell as visited by temporarily changing it
        char temp = board[i][j];
        board[i][j] = '#'; // or any non-alphabetic marker

        // Explore neighbors in 4 directions
        boolean found = dfs(board, word, i + 1, j, index + 1) ||
                        dfs(board, word, i - 1, j, index + 1) ||
                        dfs(board, word, i, j + 1, index + 1) ||
                        dfs(board, word, i, j - 1, index + 1);

        // Backtrack: restore the original value
        board[i][j] = temp;

        return found;
    }
}
