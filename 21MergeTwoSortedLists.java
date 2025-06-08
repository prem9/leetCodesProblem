class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Create a dummy node to start the merged list
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // Traverse both lists and append the smaller value each time
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // At least one of the lists is now null. Append the remaining one.
        current.next = (list1 != null) ? list1 : list2;

        // Return the merged list, skipping the dummy node
        return dummy.next;
    }
}
