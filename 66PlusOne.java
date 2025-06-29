class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length;

        // Traverse from the end (least significant digit)
        for (int i = n - 1; i >= 0; i--) {
            // If the digit is less than 9, just increment and return
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            // Otherwise, set current digit to 0 and continue
            digits[i] = 0;
        }

        // If we reached here, all digits were 9
        // Example: [9,9] → [1,0,0]
        int[] result = new int[n + 1];
        result[0] = 1;
        return result;
    }
}
