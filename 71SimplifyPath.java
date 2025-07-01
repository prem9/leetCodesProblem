class Solution {
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.equals("") || part.equals(".")) {
                // Skip empty or current directory
                continue;
            } else if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop(); // Go up one level
                }
            } else {
                stack.push(part); // Valid directory name
            }
        }

        // Reconstruct canonical path
        StringBuilder result = new StringBuilder();
        for (String dir : stack.descendingIterator()) {
            result.append("/").append(dir);
        }

        return result.length() == 0 ? "/" : result.toString();
    }
}
