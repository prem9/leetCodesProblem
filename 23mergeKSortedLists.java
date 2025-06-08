import java.util.PriorityQueue;

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        // Priority queue (min-heap) to order nodes by their values
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.val, b.val)
        );

        // Initialize the heap with the head of each list
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!minHeap.isEmpty()) {
            ListNode minNode = minHeap.poll();
            tail.next = minNode;
            tail = tail.next;

            if (minNode.next != null) {
                minHeap.offer(minNode.next); // Push next node from the same list
            }
        }

        return dummy.next;
    }
}
