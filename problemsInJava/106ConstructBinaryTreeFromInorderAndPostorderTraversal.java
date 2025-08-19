import java.util.*;

class Solution {
    private int postIndex;
    private Map<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        postIndex = postorder.length - 1;

        // Map inorder values to their indices
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return helper(postorder, 0, inorder.length - 1);
    }

    private TreeNode helper(int[] postorder, int left, int right) {
        if (left > right) return null;

        int rootVal = postorder[postIndex--];
        TreeNode root = new TreeNode(rootVal);

        int index = inorderMap.get(rootVal);

        // Build right subtree first (postorder: left → right → root)
        root.right = helper(postorder, index + 1, right);
        root.left = helper(postorder, left, index - 1);

        return root;
    }
}
