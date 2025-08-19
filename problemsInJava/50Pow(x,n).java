class Solution {
    public double myPow(double x, int n) {
        long N = n; // Convert to long to safely handle -2^31
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }

        double result = 1.0;
        while (N > 0) {
            if ((N % 2) == 1) {
                result *= x;
            }
            x *= x;
            N /= 2;
        }

        return result;
    }
}
