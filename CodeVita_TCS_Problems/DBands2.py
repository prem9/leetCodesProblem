# no imports

def read_nonempty():
    s = ""
    while s == "":
        s = input().strip()
    return s

def trace_path(S, sr, sc, seq):
    # Map cell -> first time step reached
    tmap = {}
    r, c = sr, sc
    t = 0
    tmap[(r, c)] = t
    for ch in seq:
        if ch == 'u':
            r -= 1
        elif ch == 'd':
            r += 1
        elif ch == 'l':
            c -= 1
        elif ch == 'r':
            c += 1
        else:
            # Unexpected char; ignore (or could treat as error)
            continue
        t += 1
        # Assuming inputs ensure staying within bounds and no self-overlap
        tmap[(r, c)] = t
    return tmap

def main():
    S = int(read_nonempty())
    r1c = read_nonempty().split()
    r1, c1 = int(r1c[0]), int(r1c[1])
    seq1 = read_nonempty()
    r2c = read_nonempty().split()
    r2, c2 = int(r2c[0]), int(r2c[1])
    seq2 = read_nonempty()

    t1 = trace_path(S, r1, c1, seq1)
    t2 = trace_path(S, r2, c2, seq2)

    # Count overlaps by direction of arrival times
    a_over_b = 0  # band1 above band2 (band2 arrived earlier)
    b_over_a = 0  # band2 above band1 (band1 arrived earlier)

    # Check all shared cells
    for cell in t1:
        if cell in t2:
            if t1[cell] == t2[cell]:
                print("Impossible")
                return
            elif t1[cell] > t2[cell]:
                a_over_b += 1  # band1 later -> on top at this cell
            else:
                b_over_a += 1  # band2 later -> on top at this cell

    # If both directions occur, weaving interlock → impossible
    if a_over_b > 0 and b_over_a > 0:
        print("Impossible")
    else:
        # Separable; maximum overlap in one consistent direction
        print(a_over_b if a_over_b > b_over_a else b_over_a)

if __name__ == "__main__":
    main()
