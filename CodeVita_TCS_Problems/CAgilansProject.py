def main():
    # helper to read next non-empty line
    def next_nonempty():
        s = input().strip()
        while s == "":
            s = input().strip()
        return s

    try:
        N = int(next_nonempty())
    except:
        return

    turns, dists = [], []
    for _ in range(N):
        t, d = next_nonempty().split()
        turns.append(t.lower())
        dists.append(int(d))

    sx, sy = map(int, next_nonempty().split())
    tx, ty = map(int, next_nonempty().split())

    # Directions: 0=N,1=E,2=S,3=W
    # Problem's mapping (note: left/right are intentionally reversed vs usual):
    # left: N->E (+1), E->S (+1), S->W (+1), W->N (+1)
    # right: N->W (-1), W->S (-1), S->E (-1), E->N (-1)
    # straight: +0, back: +2
    delta = {'straight': 0, 'left': 1, 'back': 2, 'right': -1}
    DX = [0, 1, 0, -1]
    DY = [1, 0, -1, 0]

    # Prefix position and heading before each instruction
    pref_x = [0]*(N+1)
    pref_y = [0]*(N+1)
    pref_h = [0]*(N+1)
    x, y, h = sx, sy, 0  # start facing north
    for i in range(N):
        pref_x[i], pref_y[i], pref_h[i] = x, y, h
        h = (h + delta[turns[i]]) % 4
        x += DX[h]*dists[i]
        y += DY[h]*dists[i]
    pref_x[N], pref_y[N], pref_h[N] = x, y, h

    # Suffix DP: displacement from step k..end as a function of incoming heading
    suff_dx = [[0]*4 for _ in range(N+1)]
    suff_dy = [[0]*4 for _ in range(N+1)]
    for k in range(N-1, -1, -1):
        d = dists[k]
        dd = delta[turns[k]]
        for hh in range(4):
            h2 = (hh + dd) % 4
            suff_dx[k][hh] = DX[h2]*d + suff_dx[k+1][h2]
            suff_dy[k][hh] = DY[h2]*d + suff_dy[k+1][h2]

    options = ['left', 'right', 'straight', 'back']
    answer = None
    for i in range(N):
        start_h = pref_h[i]
        start_x = pref_x[i]
        start_y = pref_y[i]
        d = dists[i]
        wrong = turns[i]
        for corr in options:
            if corr == wrong:
                continue
            h2 = (start_h + delta[corr]) % 4
            fx = start_x + DX[h2]*d + suff_dx[i+1][h2]
            fy = start_y + DY[h2]*d + suff_dy[i+1][h2]
            if fx == tx and fy == ty:
                answer = (wrong, d, corr)
                break
        if answer:
            break

    if not answer:
        print("No")
    else:
        wturn, dist, cturn = answer
        print("Yes")
        print(f"{wturn} {dist}")
        print(f"{cturn} {dist}")

if __name__ == "__main__":
    main()
