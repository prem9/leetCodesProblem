import java.util.*;

class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j <= i; j++) {
                // First and last elements in a row are always 1
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    int prev = triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j);
                    row.add(prev);
                }
            }

            triangle.add(row);
        }

        return triangle;
    }
}
