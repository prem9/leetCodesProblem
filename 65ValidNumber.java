class Solution {
    public boolean isNumber(String s) {
        s = s.trim(); // remove leading/trailing spaces
        boolean seenDigit = false;
        boolean seenDot = false;
        boolean seenE = false;
        boolean digitAfterE = true; // initially true in case there's no 'e'

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                seenDigit = true;
                if (seenE) digitAfterE = true;
            } else if (c == '+' || c == '-') {
                // must be first character or immediately after 'e'
                if (i != 0 && s.charAt(i - 1) != 'e' && s.charAt(i - 1) != 'E') {
                    return false;
                }
            } else if (c == '.') {
                if (seenDot || seenE) return false;
                seenDot = true;
            } else if (c == 'e' || c == 'E') {
                if (seenE || !seenDigit) return false;
                seenE = true;
                digitAfterE = false; // must see digits after e
            } else {
                return false;
            }
        }

        return seenDigit && digitAfterE;
    }
}
