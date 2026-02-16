# Algorithm fundamentals

This first lecture is about building good habits: describing solutions clearly, thinking about edge cases, and writing steps you can trust before you code.

!!! info "In this lecture"
    - What an algorithm is (and what makes it “good”).
    - How to turn a problem statement into clear steps (pseudocode).
    - How to start thinking about efficiency in plain words (formal notation comes next lecture).

## Resources

<div class="grid cards" markdown>

- :material-file-pdf-box: __Slides (PDF)__  
  Download and skim before class.  
  [Open slides →](https://github.com/evisp/dsa-algorithms/blob/main/docs/foundations/algorithm-fundamentals/1.Overview_AlgorithmFundamentals.pdf){ .md-button .md-button--primary }

- :material-youtube: __What is an Algorithm__  
  Core ideas recap.  
  [Watch →](https://www.youtube.com/watch?v=l_iE5Jq23Qw&t=5s){ .md-button }

- :material-youtube: __What makes an Algorithm Good__  
  Worked example walkthrough.  
  [Watch →](https://www.youtube.com/watch?v=4pIHgJafags){ .md-button }

- :material-notebook-outline: __Practice__  
  Do these after reading the notes.  
  [Go to practice →](../practice.md){ .md-button .md-button--primary }

</div>

!!! note "How to use the materials"
    Slides first → watch the videos video → do practice → iterate until you suceed.



## 1) What is an algorithm?
An **algorithm** is a clear set of steps for solving a problem: you give it some input, you follow the steps in order, and you get an output you can check.

What matters most is that the steps are specific enough that two different people could implement them and still get the same result.

A useful checklist:

- Input: What information do we get?
- Output: What exactly must we return/print?
- Steps: Are they precise and unambiguous?
- Finite: Does it always stop, even on tricky inputs?

!!! example "Simple running example"
    Imagine you’re building a tiny “study planner”.

    **Input:** A list of tasks (title, deadline, estimated minutes) and the tasks already completed.  
    **Output:** The next task to do (or “no task left”).

    **Algorithm idea (plain steps):**

    1. Remove tasks that are already completed.
    2. Pick the task with the earliest deadline.
    3. If there’s a tie, pick the smaller estimated time.
    4. Return that task.


## 2) What makes an algorithm “good”?
A good algorithm is not just something that works once. It should be predictable, easy to follow, and reliable across different valid inputs.

We want algorithms that are:

- **Deterministic**: Same input → same output.
- **Unambiguous**: No “magic” steps; every step is clearly defined.
- **Correct**: Works for all valid inputs, not only the examples you tested.
- **Scalable**: Still behaves reasonably when the input becomes large.

Concrete details that improve “goodness”:

- Define tie-break rules (what happens when two values are equal?).
- Define empty-input behavior (what do you return when there is nothing to process?).
- Define what counts as “valid input” (e.g., are negative prices allowed?).

!!! warning "Correctness comes first"
    A fast wrong answer is still wrong. Make it correct, then improve it.


## 3) Why algorithms matter
Algorithms are behind systems you use every day: search results, navigation routes, recommendations, and simple things like sorting and filtering lists.

More importantly, algorithms show up any time you need to make a decision with data: what to keep, what to remove, what to do next, and how to do it reliably.


## 4) Rules for writing clear algorithms
Think of algorithms like instructions: they should be easy to follow and hard to misunderstand. The goal is not to be clever—the goal is to be clear, correct, and dependable.

!!! tip "KISS"
    Keep it simple. Simple solutions are easier to verify, test, and maintain.

Practical rules (with a purpose):

- **Pareto principle (80/20)**: Focus effort where it matters most; don’t micro-optimize early.
- **Murphy’s law**: Expect failures and weird inputs; design and test edge cases on purpose.
- **Newton’s third law (trade-offs)**: Speed vs memory, simplicity vs performance—choose intentionally.
- **Sherlock Holmes debugging**: Don’t guess; check inputs/outputs, dry-run small examples, eliminate possibilities.
- **Tortoise and hare**: Some solutions look fine on small inputs but collapse on big ones; build habits that scale.


### Expect edge cases
Edge cases are not “extra”. They are part of the problem.

When you design an algorithm, always ask: “What happens if…?”

- The input is empty?
- The input has one element?
- Values repeat (ties happen)?
- Values are very large/small?
- A value is missing (`null` in Java)?

Common good practice: decide the behavior *first* (return value, error, or special case), then write steps that match that decision.


## 5) A small workflow: problem → steps → code
Use this sequence when you solve tasks in this course. It keeps you focused and prevents “random coding”.

![Workflow](https://i.imgur.com/MMlgTpq.jpeg)

1. Restate the problem in one sentence (what must be solved?).
2. List inputs and outputs (types, constraints, and empty-input behavior).
3. Pick a simple data structure that fits (array, list, set, map).
4. Write steps in pseudocode (clear loops and conditions).
5. Do a quick dry run on a tiny example (3–5 items).
6. Add 2–3 edge cases and confirm your steps handle them.
7. Only then implement in Java.

Pseudocode standard for this course:

- Use plain language + structured control flow (`for`, `while`, `if`).
- Don’t worry about perfect syntax—worry about clarity.


## 6) Thinking about efficiency (without notation, for now)
Even in week 1, you should start noticing whether a solution will scale when the input grows.

A good first check is to ask what your algorithm does to the data:

- Does it touch each element once (or a small constant number of times)?
- Does it repeat work many times (nested loops, re-checking earlier elements)?
- Does it create extra collections/copies that cost memory?

!!! tip "Efficiency question"
    If the input becomes 10× bigger, will your approach still feel reasonable?

Quick “week 1” signals:

- A loop inside a loop over the same list is a sign you might be re-scanning too much.
- Sorting is often fine, but sorting *inside* a loop is usually a red flag.
- If you keep copying arrays/lists, ask if one pass (or one helper structure) is enough.

We’ll formalize efficiency in the next lecture.


## 7) Worked examples (focus: clear steps)
These examples show what “clear” looks like: define assumptions, write simple steps, and decide behavior for edge cases.

### Example A: Tallest person
**Task**: given a list of people’s heights, determine the tallest height (and optionally who it belongs to).

Before you write steps, decide:

- What should happen if the list is empty? (Return a special value? Throw an error?)
- If multiple people share the max height, do you return the first one, the last one, or all?

Pseudocode (return tallest height):
```text
if heights is empty:
    error ("no tallest in an empty list")

tallest = first height in heights

for each height h in heights:
    if h > tallest:
        tallest = h

return tallest
```

Variant (return index of tallest; first max wins):
```text
if heights is empty:
    error

bestIndex = 0
for i from 1 to length(heights)-1:
    if heights[i] > heights[bestIndex]:
        bestIndex = i

return bestIndex
```

Edge cases to think about:

- Empty list (define behavior).
- Multiple maximum heights (tie rule).
- Unusual values: height = 0 or negative (should you validate or accept?).


### Example B: Total cost
**Task**: given a list of food prices, compute the total cost.

Pseudocode (simple sum):
```text
total = 0
for each price p in prices:
    total = total + p
return total
```

Optional validation version (reject negative prices):
```text
total = 0
for each price p in prices:
    if p < 0:
        error ("negative price not allowed")
    total = total + p
return total
```

Edge cases:

- Empty list → total should be 0.
- Negative values: allowed (refund/discount) or forbidden? Decide and enforce it.
- Very large totals: could the sum overflow the numeric type you plan to use?



## Key takeaways
- An algorithm is a precise, finite set of steps: input → output.
- Clarity beats cleverness in week 1.
- Edge cases are part of the design, not an afterthought.
- Start noticing efficiency early; we’ll formalize it next lecture.

> A slow algorithm isn’t wrong; it’s just waiting for a faster computer to be invented.

![Algorithm](https://i.imgur.com/4UK6Kn0.png)