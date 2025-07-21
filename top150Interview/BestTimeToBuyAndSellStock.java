class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price; // update the lowest price
            } else {
                maxProfit = Math.max(maxProfit, price - minPrice); // potential profit
            }
        }

        return maxProfit;
    }
}
