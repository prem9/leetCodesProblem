import java.util.Arrays;

class Solution {
    public int threeSumClosest(int[] nums, int target) {
        // Sort the array to apply two-pointer approach
        Arrays.sort(nums);
        int n = nums.length;
        int closestSum = nums[0] + nums[1] + nums[2]; // Initialize with the first triplet

        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;
            int right = n - 1;

            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];

                // Update the closest sum if this is closer
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }

                // Move pointers based on comparison
                if (currentSum < target) {
                    left++; // Try a bigger number
                } else if (currentSum > target) {
                    right--; // Try a smaller number
                } else {
                    return target; // Perfect match
                }
            }
        }

        return closestSum;
    }
}
