# Algorithm fundamentals

This first lecture is about building good habits: describing solutions clearly, thinking about edge cases, and writing steps you can trust before you code. 

!!! info "In this lecture"
    - What an algorithm is (and what makes it “good”).   
    - How to turn a problem statement into clear steps (pseudocode).   
    - How to think about efficiency without heavy notation (yet). 

## Slides
- Slides (PDF): `slides/1.Overview_AlgorithmFundamentals.pdf` 

## 1) What is an algorithm?
An **algorithm** is a step‑by‑step procedure that takes some input and produces an output. 

A useful checklist:

- Input: what information do we get? 
- Output: what exactly must we return/print? 
- Steps: are they precise and unambiguous? 
- Finite: does it always stop? 

!!! note "A simple test"
    If a classmate can implement your steps and get the same result, your algorithm description is probably clear enough. 


## 2) What makes an algorithm “good”?
We want algorithms that are:

- Deterministic: same input → same output. 
- Unambiguous: no “magic” steps. 
- Correct: works for valid inputs, not just the examples. 
- Scalable: still behaves well when the input becomes large. 

!!! warning "Correctness comes first"
    A fast wrong answer is still wrong. First make it correct, then improve it. 


## 3) Why algorithms matter
Algorithms are behind systems you use every day: search, recommendations, navigation, and more. 

!!! tip "Why this course is worth the effort"
    Once you can design algorithms, you can solve new problems; not just repeat old ones. 


## 4) Rules for writing clear algorithms
Think of algorithms like instructions: they should be easy to follow and hard to misunderstand. 

### Keep it simple (on purpose)
- Prefer simple, direct solutions over complicated ones. 
- Choose the simplest data structure that fits the problem. 

!!! tip "KISS"
    Keep it simple: simple solutions are easier to verify, test, and maintain. 

### Expect edge cases
Always ask: “What happens if…?”

- The input is empty. 
- The input has one element. 
- Values repeat / ties happen. 
- Values are very large/small. 
- Nulls / missing values appear (in Java: `null`). 

!!! danger "Most bugs live here"
    Many algorithms work on “normal” inputs and fail on edge cases—so include edge cases in your design, not only in testing. 

## 5) A small workflow: problem → steps → code
Use this sequence when you solve tasks in this course:

1. Restate the problem in one sentence. 
2. List inputs and outputs (be specific). 
3. Write steps in pseudocode (clear loops and conditions). 
4. Add 2–3 edge cases and verify the steps handle them. 
5. Only then implement in Java. 

!!! note "Pseudocode standard for this course"
    Use plain language + structured control flow (`for`, `while`, `if`). Don’t worry about perfect syntax—worry about clarity. 

## 6) Thinking about efficiency (without notation, for now)
Even in week 1, you should start noticing whether a solution:

- touches each element once, 
- repeats work many times, 
- or creates extra collections/copies that cost memory. 

!!! tip "Efficiency question"
    If the input becomes 10× bigger, will your approach still feel reasonable? 

We’ll formalize this in the next lecture with proper time/space complexity notation and examples. 


## 7) Worked examples (focus: clear steps)

### Example A: Tallest person
**Task**: given a list of people’s heights, determine the tallest person. 

Pseudocode:
```text
assume heights is not empty
tallest = first height

for each height h in heights:
    if h > tallest:
        tallest = h

return tallest
```

Edge cases to think about:

- Empty list (what should you do?). 
- Multiple people with the same maximum height. 

### Example B: Total cost
**Task**: given a list of food prices, compute the total. 

Pseudocode:
```text
total = 0
for each price p in prices:
    total = total + p
return total
```

Edge cases:

- Empty list → total should be 0. 
- Negative values (should they be allowed?). 


## 8) Practice (pseudocode + edge cases)
For each task, submit:

- Pseudocode. 
- 2–3 edge cases (bullet list). 
- A short sentence about efficiency in plain language (example: “I scan the list once”, “I compare every pair”, “I sort first”). 

### Set 1
1. Given a text message and a word, check if the word appears in the message. 
2. Given a list of gas stations along a highway (distance from start) and whether they have fuel available, find the closest one with fuel. 
3. Given a list of votes (candidate names), count how many votes each candidate received. 
4. Given a list of packages with estimated delivery times, arrange them so they are delivered in the correct order. 

!!! warning "Be precise"
    Don’t write “find the closest” or “sort it” as a step. Write how you do it, step by step. 

## Key takeaways
- An algorithm is a precise, finite set of steps: input → output. 
- Clarity beats cleverness in week 1 (and usually in real projects). 
- Always include edge cases in your design. 
- Start noticing efficiency early; we’ll formalize it next lecture. 

> A slow algorithm isn’t wrong; it’s just waiting for a faster computer to be invented. 
