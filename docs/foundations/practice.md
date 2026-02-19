# Practice your skills (Foundations)

Use this page to practice all three Foundations topics. Write pseudocode first, then implement in Java later.

!!! info "Goal"
    Write algorithms as clear, testable steps before writing any Java.

## Jump to a topic
- [A) Algorithm fundamentals](#a-algorithm-fundamentals)
- [B) Algorithm analysis (complexity)](#b-algorithm-analysis-complexity)
- [C) Algorithm strategies](#c-algorithm-strategies)


## How to work (quick)
- Work in groups of 3–4.
- Rotate roles each exercise (driver, spec-keeper, edge-case tester, timekeeper).

??? note "Role definitions (optional)"
    - Driver: writes the final pseudocode.
    - Spec-keeper: enforces clear inputs/outputs and tie/empty rules.
    - Edge-case tester: invents tricky cases and checks the steps.
    - Timekeeper: keeps the group moving.

!!! tip "Rule for discussion"
    If your algorithm has ties or empty input cases, decide the behavior and write it down.


## What to submit (for each exercise)

??? note "Submission template (copy/paste per exercise)"
    - **Problem (one sentence):**
    - **Inputs (types + constraints):**
    - **Output (exact meaning):**
    - **Assumptions (if any):**
    - **Tie-break rule (if any):**
    - **Empty-input behavior:**
    - **Invalid-input behavior (if any):**
    - **Pseudocode:**
    - **Edge cases (at least 3):**
    - **Dry run (tiny example):**

??? note "Pseudocode style guide"
    Use plain language + structured control flow.

    ```text
    if condition:
        do something
    else:
        do something else

    for each item x in items:
        ...

    for i from 0 to n-1:
        ...

    while condition:
        ...
    ```

    Avoid Java syntax details (no semicolons, no classes). Focus on clarity.


## A) Algorithm fundamentals

### Warm-up: make an ambiguous algorithm precise
=== "Task"
    **Problem:** “Pick the best movie to watch.”

    As a group, agree on one definition of “best” and write 3–5 steps.

    Required decisions:
    - What is the input?
    - What is the output?
    - What happens if the list is empty?
    - What happens if there’s a tie?

=== "Your work"
    Use the submission template above.



### Exercise 1 — Tallest person (max + ties)
=== "Task"
    **Task:** Given a list of heights, determine the tallest height (and optionally who it belongs to).

    Use this dataset for your dry run:
    - `heights = [165, 172, 180, 180, 158, 190, 175]`

    Required decisions:
    - Empty list: return a special value or error (choose one and write it down).
    - Ties: if multiple people share the max height, choose a rule (first, last, or all).

=== "Hint"
    - You can’t know the maximum without looking at every value at least once, so plan a single scan.  
    - Track a “best so far” value; if you also need the index/name, track that too (and only update it when your tie rule says so).

=== "Solution"
    **Tie rule used here:** first maximum wins (keep the earliest index).  
    **Empty input behavior:** error.

    **Pseudocode (max height only):**
    ```text
    if heights is empty:
        error ("no tallest in an empty list")

    best = heights

    for each h in heights:
        if h > best:
            best = h

    return best
    ```

    **Pseudocode (max height + index, first max wins):**
    ```text
    if heights is empty:
        error

    bestIndex = 0

    for i from 1 to length(heights)-1:
        if heights[i] > heights[bestIndex]:
            bestIndex = i

    return bestIndex
    ```

    **Dry run (given dataset):**
    - Start: bestIndex=0 (165)
    - See 172 → update bestIndex=1
    - See 180 → update bestIndex=2
    - See 180 (tie) → do nothing (first max wins)
    - See 190 → update bestIndex=5
    - Answer: index 5, height 190



### Exercise 2 — Total cost (sum + validation rules)
=== "Task"
    **Task:** Given a list of food prices, compute the total.

    Use this dataset for your dry run:
    - `prices = [2.50, 1.20, 0.00, 5.80]`

    Now discuss this twist and decide a policy:
    - `prices = [2.50, -1.00, 3.00]`

    Required decisions:
    - Are negative values allowed?
    - If not allowed, do you error, skip them, or replace them?

=== "Hint"
    - A total is usually a single scan: start with `total = 0`, then add each price.  
    - If you reject negatives, decide what “reject” means: stop with an error, or skip invalid values and continue (both are valid if you state it clearly).

=== "Solution"
    **Policy used here:** negative prices are invalid → *error* (stop immediately).  

    **Pseudocode (sum, reject negatives):**
    ```text
    total = 0

    for each price p in prices:
        if p < 0:
            error ("negative price not allowed")
        total = total + p

    return total
    ```

    **Dry run (prices = [2.50, 1.20, 0.00, 5.80]):**
    - total = 0
    - add 2.50 → total = 2.50
    - add 1.20 → total = 3.70
    - add 0.00 → total = 3.70
    - add 5.80 → total = 9.50
    - return 9.50

    **Dry run (prices = [2.50, -1.00, 3.00]):**
    - total = 0
    - add 2.50 → total = 2.50
    - see -1.00 → error ("negative price not allowed")

    **Optional extension (if you prefer “skip invalid” instead of error):**
    ```text
    total = 0
    invalidCount = 0

    for each price p in prices:
        if p < 0:
            invalidCount = invalidCount + 1
            continue
        total = total + p

    return (total, invalidCount)
    ```



### Exercise 3 — Next bus (filter + minimum selection)
=== "Task"
    **Task:** You are at a bus stop. You have arrival times in minutes from now.  
    Return the next bus (the smallest non-negative time).

    Example input:
    - `arrivals = [-3, 12, 0, 5, 5, 28]`

    Required decisions:
    - Do you allow `0`? (arriving now)
    - If two buses arrive at the same time, do you return the first, all, or just the time value?
    - What do you return if there is no valid (non-negative) arrival?

    Edge cases to include:
    - All arrivals are negative.
    - The list is empty.
    - Duplicate times.

=== "Hint"
    - Ignore invalid values (negative minutes) while searching for the smallest valid time.  
    - Keep a `best` value that starts as “not found yet”; update it whenever you find a valid time that is smaller.

=== "Solution"
    **Policy used here:**  
    - `0` is allowed (a bus arriving now counts).  
    - If there are ties, return the time value (not “first/all”).  
    - If no valid arrival exists, return `"no bus"`.

    **Pseudocode (return next time or 'no bus'):**
    ```text
    best = null  # means "no valid bus found yet"

    for each t in arrivals:
        if t < 0:
            continue

        if best is null or t < best:
            best = t

    if best is null:
        return "no bus"

    return best
    ```

    **Dry run (arrivals = [-3, 12, 0, 5, 5, 28]):**
    - best = null
    - t=-3 → skip
    - t=12 → best=12
    - t=0 → best=0
    - t=5 → 5 < 0? no → keep best=0
    - t=5 → keep best=0
    - t=28 → keep best=0
    - return 0

    **Edge case examples:**
    - `arrivals = []` → return "no bus"
    - `arrivals = [-5, -1]` → return "no bus"
    - `arrivals = [7, 7, 2]` → return 2



### Exercise 4 — To-do cleanup (remove completed + keep order)
=== "Task"
    **Task:** You have:
    - `tasks`: a list of task titles (strings)
    - `completed`: a list of completed task titles

    Return a new list containing only tasks that are not completed, preserving original order.

    Example:
    - `tasks = ["Quiz", "Read", "Lab", "Read", "Email"]`
    - `completed = ["Read"]`
    - output: `["Quiz", "Lab", "Email"]`

    Required decisions:
    - If a task appears twice and is completed, do you remove both occurrences?
    - If `completed` contains a task not found in `tasks`, do you ignore it?
    - What happens if `tasks` is empty?

=== "Hint"
    - You’re filtering: scan `tasks` once and build a new list with only the tasks you want to keep.  
    - Membership checks matter: if `completed` is large, checking “is this task completed?” is easier if `completed` behaves like a set.

=== "Solution"
    **Policy used here:**  
    - If a completed title appears in `tasks` multiple times, remove all of its occurrences.  
    - If `completed` contains titles not in `tasks`, ignore them.  
    - If `tasks` is empty, return an empty list.

    **Pseudocode (stable filter):**
    ```text
    completedSet = set made from completed
    result = empty list
    removedCount = 0

    for each title in tasks:
        if title is in completedSet:
            removedCount = removedCount + 1
        else:
            append title to result

    return result
    ```

    **Dry run**
    - tasks = ["Quiz", "Read", "Lab", "Read", "Email"]
    - completed = ["Read"]
    - completedSet = {"Read"}

    Scan tasks:
    - "Quiz" not in set → keep
    - "Read" in set → remove
    - "Lab" not in set → keep
    - "Read" in set → remove
    - "Email" not in set → keep

    Result: ["Quiz", "Lab", "Email"]

    **Optional extension**
    Return both the filtered list and how many were removed:
    ```text
    return (result, removedCount)
    ```

    **Edge case examples**
    - tasks = [] → []
    - completed = [] → return tasks unchanged
    - tasks = ["Read", "Read"] and completed = ["Read"] → []



## B) Algorithm analysis (complexity)

This section will focus on: define \(n\), estimate time/space, and compare approaches.

### Exercise B1 — TODO (complexity reading)
=== "Task"
    TODO: paste 2–3 pseudocode snippets and ask students to write:
    - What is \(n\)?
    - Time complexity (Big-O)
    - Space complexity (Big-O)
    - One-sentence justification

=== "Hint"
    TODO

=== "Solution"
    TODO (we’ll add later)



## C) Algorithm strategies

This section will focus on: choosing a strategy (brute force vs D&C vs greedy vs DP vs backtracking) and justifying it.

### Exercise C1 — TODO (pick a strategy)
=== "Task"
    TODO: give a problem and ask:
    - Which strategy fits best?
    - Why?
    - Write pseudocode with decisions + edge cases

=== "Hint"
    TODO

=== "Solution"
    TODO (we’ll add later)
