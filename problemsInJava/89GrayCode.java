class Solution {
    public List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        int total = 1 << n;  // 2^n values

        for (int i = 0; i < total; i++) {
            result.add(i ^ (i >> 1));  // gray code formula
        }

        return result;
    }
}
