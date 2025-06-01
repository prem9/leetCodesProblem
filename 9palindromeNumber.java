class Solution {
    public boolean isPalindrome(int x) {
        // Negative numbers and numbers ending with 0 (but not 0 itself) are not palindromes
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;

        int reversed = 0;

        // Reverse half of the number and compare
        while (x > reversed) {
            int digit = x % 10;
            reversed = reversed * 10 + digit;
            x /= 10;
        }

        // For even-length: x == reversed
        // For odd-length: x == reversed / 10 (to remove the middle digit)
        return x == reversed || x == reversed / 10;
    }
}
