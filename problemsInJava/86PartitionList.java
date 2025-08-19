class Solution {
    public ListNode partition(ListNode head, int x) {
        // Dummy heads for the two partitions
        ListNode beforeHead = new ListNode(0);
        ListNode afterHead = new ListNode(0);

        // Current tails of the before and after lists
        ListNode before = beforeHead;
        ListNode after = afterHead;

        while (head != null) {
            if (head.val < x) {
                before.next = head;
                before = before.next;
            } else {
                after.next = head;
                after = after.next;
            }
            head = head.next;
        }

        // Terminate the after list and link the two partitions
        after.next = null;
        before.next = afterHead.next;

        return beforeHead.next;
    }
}
