# Week 1 Practice Algorithms in Pseudocode 

!!! info "Goal of today’s practice"
    Write algorithms as *clear steps* before writing any Java. Your job is to produce pseudocode that another group could implement and get the same results.

## How we’ll work 

**Group size:** 3-4 students

**Roles (rotate each exercise):**

- Driver: writes the final pseudocode.
- Spec-keeper: enforces clear inputs/outputs and tie/empty rules.
- Edge-case tester: invents tricky cases and checks the steps.
- Timekeeper: keeps the group moving.

!!! tip "Rule for discussion"
    If your algorithm has a tie or an empty input possibility, you must *decide the behavior* and write it down.

## What you must submit (for each exercise)

Fill this template (copy/paste per exercise):

- **Problem (one sentence):**
- **Inputs (types + constraints):**
- **Output (exact meaning):**
- **Assumptions (if any):**
- **Tie-break rule (if any):**
- **Pseudocode:**
- **Edge cases (at least 3):**
- **Dry run (tiny example):**

??? note "Pseudocode style guide (use this in every solution)"
    Use plain language + structured control flow.

    ```text
    if condition:
        do something
    else:
        do something else

    for each item x in items:
        ...

    while condition:
        ...
    ```

    Avoid Java syntax details (no semicolons, no classes). Focus on clarity.


![WarmUp Image](https://i.imgur.com/d3gnnyZ.png)

## Warm-up: make an ambiguous algorithm precise

**Problem:** “Pick the best movie to watch.”

As a group, agree on **one** reasonable definition of “best” (e.g., highest rating, shortest duration, earliest release year, etc.) and write *3-5 steps*.

- What is the input?
- What is the output?
- What happens if the list is empty?
- What happens if there’s a tie?

## Exercise 1 - Tallest person (max + ties)

**Task:** Given a list of people’s heights, determine the tallest height (and optionally who it belongs to).

Use this dataset for your dry run:

- heights = `[165, 172, 180, 180, 158, 190, 175]`

Required decisions:

- What should happen if the list is empty? (Return special value vs error)
- If multiple people share the maximum height, do you return the first one, the last one, or all?

=== "Your work"
    Use the submission template above.

=== "Optional extension (fast groups)"
    Also return **the index** (or name) of the tallest person.

## Exercise 2 - Total cost (sum + validation rules)

**Task:** Given a list of food prices, compute the total.

Use this dataset for your dry run:

- prices = `[2.50, 1.20, 0.00, 5.80]`

Now discuss this twist and decide a policy:

- prices = `[2.50, -1.00, 3.00]`  

Should negative values be allowed? If not, what should your algorithm do?

=== "Your work"
    Use the submission template above.

=== "Optional extension (fast groups)"
    Return both: total cost and number of invalid entries you ignored/rejected.

??? tip "Clarity test"
    Another group should be able to tell exactly what happens with `[]`, with `[0]`, and with `[-1]` from your written steps.

## Exercise 3 - Next bus (filter + minimum selection)

**Task:** You are at a bus stop. You have a list of arrival times in minutes from now.
Return the **next bus** (the smallest non-negative time).

Example input:

- arrivals = `[-3, 12, 0, 5, 5, 28]`

Required decisions:

- Do you allow `0`? (arriving now)
- If two buses arrive at the same time, do you return the first, all, or just the time value?

Edge cases to include:

- All arrivals are negative.
- The list is empty.
- Duplicate times.

=== "Your work"
    Use the submission template above.

??? note "Hint (allowed)"
    Many groups solve this by scanning once and tracking a current “best valid arrival so far”.

## Exercise 4 - “To-do cleanup” (remove completed + stable order)

**Task:** You have:

- tasks: a list of task titles (strings)
- completed: a list of completed task titles

Return a new list that contains only the tasks that are **not completed**, preserving the original order.

Example:

- tasks = `["Quiz", "Read", "Lab", "Read", "Email"]`
- completed = `["Read"]`
- output should keep: `["Quiz", "Lab", "Email"]`

Required decisions:

- If a task appears twice and is completed, do you remove both occurrences?
- If `completed` contains a task that never appears in `tasks`, do you ignore it?
- What happens if `tasks` is empty?

=== "Your work"
    Use the submission template above.

=== "Optional extension (fast groups)"
    Also return how many tasks were removed.

??? tip "Discussion prompt"
    Would your approach still be clear if `completed` is very large? (Reason informally.)


## Exercise 5 - Test-case challenge (design tests, not code)

Pick **two** of the earlier exercises and write:

- 2 normal tests
- 2 edge tests
- 1 “bad input” test (where you must define behavior)

Write expected outputs for each test.

=== "Your work"
    Use this structure:

    - Function/problem name:
    - Normal tests:
    - Edge tests:
    - Bad input test:
    - Expected outputs:

??? warning "Common mistake"
    A test without an expected output is not a test-it’s just input.

## Bonus 

Choose one:

- Rewrite Exercise 1 to return **all indices** that share the maximum.
- Rewrite Exercise 3 to return “no bus” with a clearly defined special output.
- Rewrite any exercise so it uses *the fewest special cases* while staying correct.

