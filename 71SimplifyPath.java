class Solution {
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.equals("") || part.equals(".")) {
                continue;
            } else if (part.equals("..")) {
                if (!stack.isEmpty()) stack.pop();
            } else {
                stack.push(part);
            }
        }

        StringBuilder result = new StringBuilder();
        Iterator<String> iter = stack.descendingIterator();
        while (iter.hasNext()) {
            result.append("/").append(iter.next());
        }

        return result.length() == 0 ? "/" : result.toString();
    }
}
