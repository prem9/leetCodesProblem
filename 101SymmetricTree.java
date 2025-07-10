class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;      // Both null → symmetric
        if (t1 == null || t2 == null) return false;     // One null → not symmetric
        if (t1.val != t2.val) return false;             // Values differ → not symmetric

        return isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }
}
