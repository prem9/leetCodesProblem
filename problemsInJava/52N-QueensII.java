class Solution {
    private int count = 0;

    public int totalNQueens(int n) {
        backtrack(0, n, new HashSet<>(), new HashSet<>(), new HashSet<>());
        return count;
    }

    private void backtrack(int row, int n, 
                           Set<Integer> cols, 
                           Set<Integer> diag1, 
                           Set<Integer> diag2) {
        if (row == n) {
            count++;
            return;
        }

        for (int col = 0; col < n; col++) {
            int d1 = row - col;
            int d2 = row + col;

            if (cols.contains(col) || diag1.contains(d1) || diag2.contains(d2)) continue;

            cols.add(col);
            diag1.add(d1);
            diag2.add(d2);

            backtrack(row + 1, n, cols, diag1, diag2);

            // backtrack
            cols.remove(col);
            diag1.remove(d1);
            diag2.remove(d2);
        }
    }
}
