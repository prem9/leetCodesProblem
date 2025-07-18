class Solution {
    public Node connect(Node root) {
        if (root == null) return null;

        Node leftmost = root;

        // Traverse levels starting from root
        while (leftmost.left != null) {
            Node head = leftmost;

            while (head != null) {
                // Connect left → right
                head.left.next = head.right;

                // Connect right → next left (across subtree)
                if (head.next != null) {
                    head.right.next = head.next.left;
                }

                head = head.next;
            }

            leftmost = leftmost.left; // move down one level
        }

        return root;
    }
}
