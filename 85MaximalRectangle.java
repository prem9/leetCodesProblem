class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return 0;

        int maxArea = 0;
        int cols = matrix[0].length;
        int[] heights = new int[cols];

        for (char[] row : matrix) {
            // Build histogram for this row
            for (int i = 0; i < cols; i++) {
                heights[i] = row[i] == '1' ? heights[i] + 1 : 0;
            }

            // Compute largest rectangle area for this histogram
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    // Helper function from "Largest Rectangle in Histogram"
    private int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0, n = heights.length;

        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i];
            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }

        return maxArea;
    }
}
