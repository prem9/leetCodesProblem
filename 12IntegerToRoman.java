class Solution {
    public String intToRoman(int num) {
        // Define Roman numerals in descending order, including subtractive forms
        int[] values =    {1000, 900, 500, 400, 100,  90,  50,  40,  10,   9,   5,   4,   1};
        String[] romans = {"M",  "CM","D", "CD", "C","XC", "L","XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            // Append the Roman numeral as many times as it fits into num
            while (num >= values[i]) {
                result.append(romans[i]);
                num -= values[i];
            }
        }

        return result.toString();
    }
}
