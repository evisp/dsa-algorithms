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
    - What should happen if the list is empty? (Return special value vs error)
    - If multiple people share the maximum height, do you return the first one, the last one, or all?

=== "Your work"
    Use the submission template above.

=== "Optional extension"
    Also return the index (or name) of the tallest person.



### Exercise 2 — Total cost (sum + validation rules)
=== "Task"
    **Task:** Given a list of food prices, compute the total.

    Use this dataset for your dry run:
    - `prices = [2.50, 1.20, 0.00, 5.80]`

    Now discuss this twist and decide a policy:
    - `prices = [2.50, -1.00, 3.00]`

    Required decisions:
    - Are negative values allowed?
    - If not allowed, do you error, skip them, or replace them (and why)?

=== "Your work"
    Use the submission template above.

=== "Optional extension"
    Return both: total cost and number of invalid entries you ignored/rejected.



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

=== "Your work"
    Use the submission template above.

=== "Hint (allowed)"
    Many groups solve this by scanning once and tracking a current “best valid arrival so far”.



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

=== "Your work"
    Use the submission template above.

=== "Optional extension"
    Also return how many tasks were removed.



### Exercise 5 — Test-case challenge (design tests, not code)
=== "Task"
    Pick two earlier exercises and write:
    - 2 normal tests
    - 2 edge tests
    - 1 “bad input” test (you must define behavior)

    For every test, write the expected output.

=== "Your work"
    Use this structure:
    - Function/problem name:
    - Normal tests:
    - Edge tests:
    - Bad input test:
    - Expected outputs:

!!! warning "Common mistake"
    A test without an expected output is not a test—it’s just input.



### Bonus (choose one)
- Rewrite Exercise 1 to return all indices that share the maximum.
- Rewrite Exercise 3 to return “no bus” using a clearly defined special output.
- Rewrite any exercise so it uses the fewest special cases while staying correct.



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
