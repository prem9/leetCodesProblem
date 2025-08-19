class Solution {
    public int climbStairs(int n) {
        if (n <= 2) return n;

        int oneStepBefore = 2;
        int twoStepsBefore = 1;
        int totalWays = 0;

        for (int i = 3; i <= n; i++) {
            totalWays = oneStepBefore + twoStepsBefore;
            twoStepsBefore = oneStepBefore;
            oneStepBefore = totalWays;
        }

        return totalWays;
    }
}
