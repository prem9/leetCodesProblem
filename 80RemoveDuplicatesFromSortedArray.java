class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;

        int writeIndex = 2; // Start from index 2 since first two elements are always allowed

        for (int i = 2; i < nums.length; i++) {
            // Only keep nums[i] if it's not the same as nums[writeIndex - 2]
            if (nums[i] != nums[writeIndex - 2]) {
                nums[writeIndex] = nums[i];
                writeIndex++;
            }
        }

        return writeIndex;
    }
}
