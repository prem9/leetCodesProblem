import java.util.*;

class Solution {
    private int preorderIndex = 0;
    private Map<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Build a map of value → index for inorder traversal
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return helper(preorder, 0, inorder.length - 1);
    }

    private TreeNode helper(int[] preorder, int left, int right) {
        if (left > right) return null;

        // Select the root value from preorder using preorderIndex
        int rootVal = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootVal);

        // Get the root's index in inorder
        int index = inorderMap.get(rootVal);

        // Recursively build left and right subtrees
        root.left = helper(preorder, left, index - 1);
        root.right = helper(preorder, index + 1, right);

        return root;
    }
}
