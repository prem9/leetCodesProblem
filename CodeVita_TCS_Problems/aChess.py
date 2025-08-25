# Directions for sliders
DIRS_ROOK   = [(1,0),(-1,0),(0,1),(0,-1)]
DIRS_BISHOP = [(1,1),(1,-1),(-1,1),(-1,-1)]
DIRS_QUEEN  = DIRS_ROOK + DIRS_BISHOP

def parse_square(sq):
    # e.g., 'A1'..'H8' -> (x,y) with x,y in 0..7
    col = ord(sq[0]) - ord('A')
    row = int(sq[1]) - 1
    return (col, row)

def in_bounds(x, y): 
    return 0 <= x < 8 and 0 <= y < 8

def normalize_state(state_dict):
    # turn dict {piece:(x,y)} into sorted tuple
    result = []
    for piece in ('B','R','Q'):
        if piece in state_dict:
            x,y = state_dict[piece]
            result.append((piece,x,y))
    return tuple(result)

def generate_moves_for_piece(piece, pos, occupied_set):
    x, y = pos
    dirs = DIRS_QUEEN if piece == 'Q' else (DIRS_ROOK if piece == 'R' else DIRS_BISHOP)
    for dx, dy in dirs:
        nx, ny = x + dx, y + dy
        while in_bounds(nx, ny):
            if (nx, ny) in occupied_set:
                break
            yield (nx, ny)
            nx += dx
            ny += dy

def expand(state_tuple):
    # expand one ply from a given state
    state = {p:(x,y) for (p,x,y) in state_tuple}
    occupied = set(state.values())
    for piece in state:
        start = state[piece]
        occupied.remove(start)
        for dest in generate_moves_for_piece(piece, start, occupied):
            new_state = dict(state)
            new_state[piece] = dest
            yield normalize_state(new_state)
        occupied.add(start)

def chess_positions(pieces_line, d):
    tokens = pieces_line.split()
    state = {}
    for tok in tokens:
        piece = tok[0]
        sq = tok[1:]
        state[piece] = parse_square(sq)
    start = normalize_state(state)

    frontier = [start]
    visited = {start}
    unique_positions = set()

    for _ in range(d):
        next_frontier = []
        for st in frontier:
            for nxt in expand(st):
                next_frontier.append(nxt)
                unique_positions.add(nxt)
                visited.add(nxt)
        frontier = next_frontier
        if not frontier:
            break

    return len(unique_positions)

# ---- Example Usage ----
print(chess_positions("QA3 RB3", 1))  # 27
print(chess_positions("QA3", 2))      # 64
print(chess_positions("QA3 RB3", 2))  # 388
