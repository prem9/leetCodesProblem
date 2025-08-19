class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        // Dummy node before the real head
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy; // points to the last node before the duplicate sequence

        while (head != null) {
            // If we have duplicates
            if (head.next != null && head.val == head.next.val) {
                // Skip all nodes with the same value
                while (head.next != null && head.val == head.next.val) {
                    head = head.next;
                }
                // Remove all duplicates
                prev.next = head.next;
            } else {
                // No duplicate, move prev forward
                prev = prev.next;
            }

            head = head.next;
        }

        return dummy.next;
    }
}
