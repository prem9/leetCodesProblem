import java.util.*;

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorder(node.left, result);       // Traverse left subtree
        result.add(node.val);             // Visit root
        inorder(node.right, result);      // Traverse right subtree
    }
}
