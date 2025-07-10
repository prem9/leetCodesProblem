class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;             // Both null
        if (p == null || q == null) return false;            // One null
        if (p.val != q.val) return false;                    // Values differ

        // Recursively check left and right subtrees
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
