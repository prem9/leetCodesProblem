# No imports.

def worth_of(s):
    w = 0
    for ch in s:
        w += ord(ch) - 96
    return w

def read_nonempty_line():
    line = ""
    while line == "":
        line = input().strip()
    return line

def popcount(x):
    c = 0
    while x:
        x &= x - 1
        c += 1
    return c

def enumerate_independent_sets_component(comp, adj, costs, worths, B, K_ENUM=22):
    """
    Return Pareto frontier (cost, worth) for a component.
    If component size <= K_ENUM, exact enumeration; else BnB within the component.
    """
    k = len(comp)
    loc = {}
    for i, g in enumerate(comp):
        loc[g] = i

    # local masks, local costs/worths
    lm = [0]*k
    lc = [0]*k
    lw = [0]*k
    for i, g in enumerate(comp):
        lc[i] = costs[g]
        lw[i] = worths[g]
        m = 0
        for h in comp:
            if (adj[g] >> h) & 1:
                j = loc[h]
                m |= (1 << j)
        lm[i] = m

    # quick single-item pruning: remove items that never fit
    valid = 0
    mapping = []
    for i in range(k):
        if lc[i] <= B:
            mapping.append(i)
            valid |= (1 << len(mapping)-1)
    if len(mapping) < k:
        # rebuild arrays to only valid ones
        k2 = len(mapping)
        lm2 = [0]*k2; lc2 = [0]*k2; lw2 = [0]*k2
        idxmap = {mapping[i]: i for i in range(k2)}
        for new_i, old_i in enumerate(mapping):
            lc2[new_i] = lc[old_i]
            lw2[new_i] = lw[old_i]
            # remap mask
            old_mask = lm[old_i]
            nm = 0
            t = old_mask
            j = 0
            while t:
                lb = t & -t
                idx = (old_mask & ((lb<<1)-1)).bit_length()-1  # not used; we’ll remap differently
                t -= lb
            # explicit remap:
            for other_old in mapping:
                if (lm[old_i] >> other_old) & 1:
                    nm |= 1 << idxmap[other_old]
            lm2[new_i] = nm
        lm, lc, lw = lm2, lc2, lw2
        k = k2

    if k == 0:
        return [(0,0)]

    # If no conflicts, just return knapsack-by-items for this component
    no_conflict = True
    for m in lm:
        if m != 0:
            no_conflict = False
            break
    if no_conflict:
        # classic 0/1 knapsack on this set → produce frontier
        dp = [0]*(B+1)
        for i in range(k):
            c = lc[i]; w = lw[i]
            for b in range(B-c, -1, -1):
                nw = dp[b] + w
                if nw > dp[b+c]:
                    dp[b+c] = nw
        # compress to frontier
        out = []
        best = -1
        for c in range(B+1):
            if dp[c] > best:
                out.append((c, dp[c]))
                best = dp[c]
        return out

    if k <= K_ENUM:
        # exhaustive enumeration with pruning by budget
        res = []
        def dfs(i, used_mask, cc, ww):
            if i == k:
                res.append((cc, ww))
                return
            # skip
            dfs(i+1, used_mask, cc, ww)
            # take if no conflict
            if (used_mask & lm[i]) == 0:
                nc = cc + lc[i]
                if nc <= B:
                    dfs(i+1, used_mask | (1 << i), nc, ww + lw[i])
        dfs(0, 0, 0, 0)
        res.sort()
        out = []
        best = -1
        for c, w in res:
            if c <= B and w > best:
                out.append((c, w)); best = w
        if out[0][0] != 0:
            out = [(0,0)] + out
        return out

    # Big component: branch & bound inside the component only
    # Build orders for good bounds
    deg = [popcount(lm[i]) for i in range(k)]

    # density-desc list for upper bound
    ord_d = list(range(k))
    def keyd(i):
        dens = (lw[i]*10**6)//lc[i]
        return (-dens, -lw[i], deg[i], i)
    ord_d.sort(key=keyd)

    # branch order: high degree, high worth
    ord_b = list(range(k))
    def keyb(i):
        return (-deg[i], -lw[i], lc[i])
    ord_b.sort(key=keyb)

    best_val_at = {}  # memo for dominated states not necessary; rely on bound

    frontier_vals = []  # collect all end states (cc, ww)

    def upper_bound(rem, cap):
        if cap <= 0:
            return 0
        t = 0
        for i in ord_d:
            if (rem >> i) & 1:
                c = lc[i]; w = lw[i]
                if c <= cap:
                    cap -= c
                    t += w
                    if cap == 0:
                        break
                else:
                    t += (w * cap) // c
                    break
        return t

    def pick(rem):
        for i in ord_b:
            if (rem >> i) & 1:
                return i
        return -1

    def dfs(rem, cap, cur_w, cur_c):
        # bound on worth only (keep cost separate)
        ub = cur_w + upper_bound(rem, cap)
        if ub < 0:  # unreachable
            return
        # prune if cannot beat a known (cost-blind) best for this cap leftover
        # (simple pruning; exact DP uses group knapsack later)
        if ub == cur_w:  # nothing addable
            frontier_vals.append((cur_c, cur_w))
            return
        i = pick(rem)
        if i == -1:
            frontier_vals.append((cur_c, cur_w))
            return
        # skip i
        dfs(rem & ~(1 << i), cap, cur_w, cur_c)
        # take i if fits; remove i and neighbors
        ci = lc[i]
        if ci <= cap:
            new_rem = rem & ~((1 << i) | lm[i])
            dfs(new_rem, cap - ci, cur_w + lw[i], cur_c + ci)

    all_mask = (1 << k) - 1
    dfs(all_mask, B, 0, 0)

    # compress frontier
    frontier_vals.sort()
    out = []
    best = -1
    for c, w in frontier_vals:
        if c <= B and w > best:
            out.append((c, w)); best = w
    if not out or out[0][0] != 0:
        out = [(0,0)] + out
    return out

def solve():
    # read
    N, M = map(int, read_nonempty_line().split())
    names = read_nonempty_line().split()
    costs = [int(x) for x in read_nonempty_line().split()]

    name_to_i = {}
    for i in range(N):
        name_to_i[names[i]] = i

    adj = [0]*N
    for _ in range(M):
        a, b = read_nonempty_line().split()
        ia = name_to_i[a]; ib = name_to_i[b]
        adj[ia] |= (1 << ib)
        adj[ib] |= (1 << ia)

    B = int(read_nonempty_line())
    worths = [worth_of(s) for s in names]

    # Build connected components
    seen = [0]*N
    comps = []
    for i in range(N):
        if seen[i] == 0:
            stack = [i]; seen[i] = 1
            comp = []
            while stack:
                u = stack.pop()
                comp.append(u)
                m = adj[u]
                v = 0
                while v < N:
                    if ((m >> v) & 1) and seen[v] == 0:
                        seen[v] = 1
                        stack.append(v)
                    v += 1
            comps.append(comp)

    # For each component, get its (cost, worth) frontier
    frontiers = []
    for comp in comps:
        opts = enumerate_independent_sets_component(comp, adj, costs, worths, B)
        frontiers.append(opts)

    # Group knapsack over components
    dp = [0]*(B+1)
    for opts in frontiers:
        ndp = dp[:]
        for c, w in opts:
            for b in range(B - c, -1, -1):
                val = dp[b] + w
                if val > ndp[b + c]:
                    ndp[b + c] = val
        dp = ndp

    print(max(dp))

# ---- run ----
if __name__ == "__main__":
    solve()
