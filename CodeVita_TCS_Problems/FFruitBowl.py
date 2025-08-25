def solve_fruit_bowl():
    # Read input
    n = int(input())
    points = []
    for _ in range(n):
        x, y = map(int, input().split())
        points.append((x, y))
    
    # Function to calculate distance between two points
    def distance(p1, p2):
        return ((p1[0] - p2[0])**2 + (p1[1] - p2[1])**2)**0.5
    
    # Function to find cross product of vectors OA and OB
    def cross_product(O, A, B):
        return (A[0] - O[0]) * (B[1] - O[1]) - (A[1] - O[1]) * (B[0] - O[0])
    
    # Graham Scan algorithm to find convex hull
    def convex_hull(points):
        points = sorted(set(points))
        if len(points) <= 1:
            return points
        
        # Build lower hull
        lower = []
        for p in points:
            while len(lower) >= 2 and cross_product(lower[-2], lower[-1], p) <= 0:
                lower.pop()
            lower.append(p)
        
        # Build upper hull
        upper = []
        for p in reversed(points):
            while len(upper) >= 2 and cross_product(upper[-2], upper[-1], p) <= 0:
                upper.pop()
            upper.append(p)
        
        # Remove last point of each half because it's repeated
        return lower[:-1] + upper[:-1]
    
    # Get convex hull points
    hull_points = convex_hull(points)
    
    # Calculate perimeter
    perimeter = 0.0
    for i in range(len(hull_points)):
        p1 = hull_points[i]
        p2 = hull_points[(i + 1) % len(hull_points)]
        perimeter += distance(p1, p2)
    
    # Round to nearest integer
    return round(perimeter)

# Run the solution
result = solve_fruit_bowl()
print(result)