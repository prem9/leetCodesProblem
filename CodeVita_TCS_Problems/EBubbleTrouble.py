def solve_bubble_trouble():
    # Read input
    S = int(input())
    start_x, start_y, vehicle_r = map(int, input().split())
    dest_x, dest_y = map(int, input().split())
    N = int(input())
    
    buildings = []
    for _ in range(N):
        x, y, r = map(int, input().split())
        buildings.append((x, y, r))
    
    T = int(input())
    tax_lines = []
    for _ in range(T):
        i, j = map(int, input().split())
        tax_lines.append((i-1, j-1))  # Convert to 0-indexed
    
    # Check if two circles overlap (vehicle and building)
    def circles_overlap(x1, y1, r1, x2, y2, r2):
        dist_sq = (x1 - x2) ** 2 + (y1 - y2) ** 2
        return dist_sq < (r1 + r2) ** 2
    
    # Check if a line segment intersects with a tax line
    def line_intersects_tax_line(p1, p2, tax_line_idx):
        building1_idx, building2_idx = tax_lines[tax_line_idx]
        b1 = buildings[building1_idx]
        b2 = buildings[building2_idx]
        
        # Tax line endpoints
        tx1, ty1 = b1[0], b1[1]
        tx2, ty2 = b2[0], b2[1]
        
        # Check if line segments intersect (not just touch)
        return segments_intersect(p1[0], p1[1], p2[0], p2[1], tx1, ty1, tx2, ty2)
    
    # Check if two line segments intersect
    def segments_intersect(x1, y1, x2, y2, x3, y3, x4, y4):
        def ccw(A, B, C):
            return (C[1] - A[1]) * (B[0] - A[0]) > (B[1] - A[1]) * (C[0] - A[0])
        
        A = (x1, y1)
        B = (x2, y2)
        C = (x3, y3)
        D = (x4, y4)
        
        return ccw(A, C, D) != ccw(B, C, D) and ccw(A, B, C) != ccw(A, B, D)
    
    # Check if path from p1 to p2 is valid (no building overlaps)
    def is_path_valid(p1, p2):
        # Sample points along the path and check for overlaps
        steps = max(abs(p2[0] - p1[0]), abs(p2[1] - p1[1]), 10)
        for i in range(steps + 1):
            t = i / steps if steps > 0 else 0
            x = p1[0] + t * (p2[0] - p1[0])
            y = p1[1] + t * (p2[1] - p1[1])
            
            # Check if vehicle at this position overlaps with any building
            for bx, by, br in buildings:
                if circles_overlap(x, y, vehicle_r, bx, by, br):
                    return False
            
            # Check boundaries
            if x - vehicle_r < 0 or x + vehicle_r > S or y - vehicle_r < 0 or y + vehicle_r > S:
                return False
        
        return True
    
    # Count tax lines crossed by a path
    def count_tax_crossings(p1, p2):
        count = 0
        for i in range(len(tax_lines)):
            if line_intersects_tax_line(p1, p2, i):
                count += 1
        return count
    
    # BFS to find minimum tax crossings
    from collections import deque
    
    start = (start_x, start_y)
    dest = (dest_x, dest_y)
    
    # First check if direct path is possible
    if is_path_valid(start, dest):
        return count_tax_crossings(start, dest)
    
    # Generate waypoints around buildings
    waypoints = [start, dest]
    
    for bx, by, br in buildings:
        # Add points around each building
        margin = vehicle_r + br + 1
        directions = [(1, 0), (-1, 0), (0, 1), (0, -1), (1, 1), (-1, 1), (1, -1), (-1, -1)]
        
        for dx, dy in directions:
            length = (dx*dx + dy*dy) ** 0.5
            if length > 0:
                wx = bx + dx * margin / length
                wy = by + dy * margin / length
                
                # Check if waypoint is within bounds
                if (vehicle_r <= wx <= S - vehicle_r and 
                    vehicle_r <= wy <= S - vehicle_r):
                    waypoints.append((wx, wy))
    
    # Dijkstra's algorithm
    import heapq
    
    distances = {}
    distances[start] = 0
    pq = [(0, start)]
    visited = set()
    
    while pq:
        current_cost, current_pos = heapq.heappop(pq)
        
        if current_pos in visited:
            continue
            
        visited.add(current_pos)
        
        if current_pos == dest:
            return current_cost
        
        # Try all waypoints
        for next_pos in waypoints:
            if next_pos in visited:
                continue
                
            if is_path_valid(current_pos, next_pos):
                cost = count_tax_crossings(current_pos, next_pos)
                new_distance = current_cost + cost
                
                if next_pos not in distances or new_distance < distances[next_pos]:
                    distances[next_pos] = new_distance
                    heapq.heappush(pq, (new_distance, next_pos))
    
    return "Impossible"

# Run the solution
result = solve_bubble_trouble()
print(result)