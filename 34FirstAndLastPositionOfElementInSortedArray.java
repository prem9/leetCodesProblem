class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first = findBound(nums, target, true);
        int last = findBound(nums, target, false);
        return new int[] { first, last };
    }

    private int findBound(int[] nums, int target, boolean isFirst) {
        int left = 0, right = nums.length - 1;
        int bound = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                bound = mid;
                if (isFirst) {
                    right = mid - 1;  // Look further left
                } else {
                    left = mid + 1;   // Look further right
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else { // nums[mid] > target
                right = mid - 1;
            }
        }

        return bound;
    }
}
