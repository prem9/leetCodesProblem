class Solution {
    public ListNode swapPairs(ListNode head) {
        // Dummy node to simplify swapping at the head
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy;

        while (head != null && head.next != null) {
            // Nodes to be swapped
            ListNode first = head;
            ListNode second = head.next;

            // Swapping
            prev.next = second;
            first.next = second.next;
            second.next = first;

            // Move pointers forward
            prev = first;
            head = first.next;
        }

        return dummy.next;
    }
}
