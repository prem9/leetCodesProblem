class Solution {
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";

        Map<Character, Integer> targetCount = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetCount.put(c, targetCount.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0;
        int required = targetCount.size();
        int formed = 0;

        Map<Character, Integer> windowCount = new HashMap<>();
        int[] result = {-1, 0, 0}; // length, left, right

        while (right < s.length()) {
            char c = s.charAt(right);
            windowCount.put(c, windowCount.getOrDefault(c, 0) + 1);

            if (targetCount.containsKey(c) && windowCount.get(c).intValue() == targetCount.get(c).intValue()) {
                formed++;
            }

            // Contract window while it's valid
            while (left <= right && formed == required) {
                if (result[0] == -1 || right - left + 1 < result[0]) {
                    result[0] = right - left + 1;
                    result[1] = left;
                    result[2] = right;
                }

                char d = s.charAt(left);
                windowCount.put(d, windowCount.get(d) - 1);
                if (targetCount.containsKey(d) && windowCount.get(d).intValue() < targetCount.get(d).intValue()) {
                    formed--;
                }
                left++;
            }

            right++;
        }

        return result[0] == -1 ? "" : s.substring(result[1], result[2] + 1);
    }
}
