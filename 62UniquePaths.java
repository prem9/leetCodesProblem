class Solution {
    public int uniquePaths(int m, int n) {
        // Use long to avoid integer overflow
        long result = 1;

        // Compute C(m+n-2, m-1) or C(m+n-2, n-1), whichever is smaller
        int smaller = Math.min(m - 1, n - 1);
        int total = m + n - 2;

        for (int i = 1; i <= smaller; i++) {
            result = result * (total - i + 1) / i;
        }

        return (int) result;
    }
}
