import java.util.*;

class Solution {
    private static final Map<Character, String> phoneMap = Map.of(
        '2', "abc",
        '3', "def",
        '4', "ghi",
        '5', "jkl",
        '6', "mno",
        '7', "pqrs",
        '8', "tuv",
        '9', "wxyz"
    );

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        if (digits == null || digits.length() == 0) {
            return result;
        }

        backtrack(result, new StringBuilder(), digits, 0);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder combination, String digits, int index) {
        if (index == digits.length()) {
            result.add(combination.toString());
            return;
        }

        String possibleLetters = phoneMap.get(digits.charAt(index));
        for (char letter : possibleLetters.toCharArray()) {
            combination.append(letter);
            backtrack(result, combination, digits, index + 1);
            combination.deleteCharAt(combination.length() - 1); // backtrack
        }
    }
}
