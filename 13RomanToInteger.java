class Solution {
    public int romanToInt(String s) {
        // Map individual Roman characters to their integer values
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int total = 0;

        for (int i = 0; i < s.length(); i++) {
            int current = romanMap.get(s.charAt(i));
            // Check if the next character represents a larger value (i.e., subtractive notation)
            if (i + 1 < s.length() && romanMap.get(s.charAt(i + 1)) > current) {
                total -= current; // subtract if a smaller value precedes a larger one
            } else {
                total += current;
            }
        }

        return total;
    }
}
