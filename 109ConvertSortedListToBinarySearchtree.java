class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);

        // Find the middle node and its previous node
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Cut off the left half from the middle
        if (prev != null) {
            prev.next = null;
        }

        TreeNode root = new TreeNode(slow.val);

        // Base case: only one element on the left
        if (head != slow) {
            root.left = sortedListToBST(head);     // Left half
        }

        root.right = sortedListToBST(slow.next);   // Right half

        return root;
    }
}
