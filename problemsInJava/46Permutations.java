class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, result);
        return result;
    }

    private void backtrack(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length) {
            // Make a deep copy of current permutation
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) permutation.add(num);
            result.add(permutation);
            return;
        }

        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i); // choose
            backtrack(nums, start + 1, result); // explore
            swap(nums, start, i); // un-choose (backtrack)
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i]; 
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
