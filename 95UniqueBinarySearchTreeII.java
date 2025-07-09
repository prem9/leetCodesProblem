import java.util.*;

class Solution {
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) return new ArrayList<>();
        return buildTrees(1, n);
    }

    private List<TreeNode> buildTrees(int start, int end) {
        List<TreeNode> allTrees = new ArrayList<>();
        
        // Base case: return list with null to represent an empty tree
        if (start > end) {
            allTrees.add(null);
            return allTrees;
        }

        // Try each number as the root
        for (int i = start; i <= end; i++) {
            // All possible left subtrees with values less than i
            List<TreeNode> leftTrees = buildTrees(start, i - 1);
            // All possible right subtrees with values greater than i
            List<TreeNode> rightTrees = buildTrees(i + 1, end);

            // Combine each left and right with current root i
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode current = new TreeNode(i);
                    current.left = left;
                    current.right = right;
                    allTrees.add(current);
                }
            }
        }

        return allTrees;
    }
}
