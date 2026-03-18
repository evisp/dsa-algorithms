# Practice your skills (Searching, Sorting, Recursion)


Use this page to practice **algorithm thinking**, not just coding. In the first lab, your goal is to explain how an algorithm works, when it works well, when it performs poorly, and how to justify your choices using examples, complexity, and correctness.

Write **pseudocode first**, then explain the idea on a small example, and only then implement in Java.


!!! info "Goal"
    Show that you understand algorithms beyond syntax:
    
    - what the algorithm does,
    - why it is correct,
    - how fast it is,
    - when it should or should not be used,
    - and how it behaves on different kinds of input.


## Jump to a topic
- [A) Searching Algorithms](#a-searching-algorithms)
- [B) Sorting Algorithms](#b-sorting-algorithms)
- [C) Recursion](#c-recursion)



!!! tip "Rule for discussion"
    If an algorithm depends on a condition (for example, binary search requires sorted input), say that clearly before you explain the steps.

!!! warning "Lab expectation"
    In this lab, it is not enough to say “this algorithm is fast” or “this one is better”.
    You should be able to support your answer with:
    
    - a small whiteboard example,
    - time/space complexity,
    - edge cases,
    - and a comparison with another algorithm.



## A) Searching Algorithms


In this section you will explain and compare **Linear Search** and **Binary Search**. The focus is not only on returning the correct index, but also on understanding the assumptions, trade-offs, and behavior on different inputs.


=== "Concept check"
    
    - What problem does a searching algorithm solve?
    - What is the difference between “finding a value” and “confirming that a value does not exist”?
    - What does linear search do step by step?
    - What does binary search do step by step?
    - What condition must be true before binary search can be used?
    - Why does binary search fail on unsorted data?
    - If the array contains duplicates, does binary search always return the first occurrence?
    - If a value is not found, what should the algorithm return?
    - Why is linear search more general than binary search?
    - When might a simple linear search still be a reasonable choice?

=== "Trace on the board"
    
    **Linear Search board-trace prompts**
    
    - Show linear search on `[7, 2, 9, 4, 1]` for target `4`.
    - Show linear search on `[7, 2, 9, 4, 1]` for target `8`.
    - At what point can linear search stop?
    - In the “not found” case, how many elements must it inspect?
    
    **Binary Search board-trace prompts**
    
    - Show binary search on `[1, 3, 5, 7, 9, 11, 13]` for target `9`.
    - Show binary search on `[1, 3, 5, 7, 9, 11, 13]` for target `2`.
    - At each step, identify `low`, `high`, and `mid`.
    - Explain why half of the array can be ignored after each comparison.
    - What changes if the array has even length?
    - What happens if there are duplicate values such as `[1, 2, 2, 2, 5, 8]` and target `2`?

=== "Complexity questions"
    These questions check whether you understand **best**, **average**, and **worst** cases, not just memorized notation.
    
    - What is the best-case time complexity of linear search, and when does it happen?
    - What is the worst-case time complexity of linear search, and when does it happen?
    - Why is the average case of linear search still linear?
    - What is the best-case time complexity of binary search?
    - What is the worst-case time complexity of binary search?
    - Why is binary search \(O(\log n)\) instead of \(O(n)\)?
    - If the input size doubles, how much does work increase in linear search?
    - If the input size doubles, how much does work increase in binary search?
    - What is the space complexity of iterative binary search?

=== "Comparison: Linear vs Binary"
    These questions push students to compare algorithms instead of describing them in isolation.
    
    - Which algorithm is easier to implement correctly?
    - Which algorithm works on unsorted data?
    - Which algorithm is better for one small lookup in an unsorted array?
    - Which algorithm is better for many repeated lookups on sorted data?
    - When would sorting first and then using binary search be worth it?
    - Why might binary search be a poor choice if the data changes frequently?
    - If the array is very small, does binary search always give a meaningful practical advantage?
    - Which search would you choose for a list of 800 items? What about 8 million items?
    - Which search is easier to explain and debug?
    - Which search depends more strongly on an input precondition?

=== "Correctness & edge cases"
    Ask students how they know their implementation is correct and robust.
    
    - What should happen if the array is `null`?
    - What should happen if the array is empty?
    - What should happen if the array has exactly one element?
    - What should happen if the target is at index `0`?
    - What should happen if the target is at the last index?
    - What should happen if all values are the same?
    - What should happen if the target appears multiple times?
    - Why is returning `-1` a useful convention for “not found”?
    - For binary search, what bug can happen if `low`, `high`, or `mid` are updated incorrectly?
    - How could an infinite loop happen in a wrong binary search implementation?

=== "Real-world scenarios"
    Use these prompts to connect the topic to actual software systems.
    
    - Where do we use searching in everyday software?
    - Why is binary search useful in a dictionary-like ordered structure?
    - Why might linear search still appear in simple validation tasks?
    - In a contact list, when would binary search be useful?
    - In a stream of unsorted incoming data, which search is more realistic?
    - Why do databases and search systems care so much about fast lookup?
    - If you are checking whether a username exists in a small list, which search would you use and why?
    - If you must search for one value only once in a small unsorted dataset, would you sort first and then use binary search, or use linear search directly? Defend your choice in terms of total cost.
    - In a real application where new elements are inserted frequently, why might binary search be less useful than it first appears, even though its lookup time is better?
    - Suppose two students say “binary search is always better than linear search.” What example or scenario would you use to show that this statement is not always true?


=== "Challenge questions"
    
    - Why is binary search considered a divide-and-conquer algorithm?
    - Can binary search be used on data structures other than arrays? Under what conditions?
    - How would you modify binary search to find the **first** occurrence of a repeated value?
    - How would you explain binary search to someone using only a phone book analogy?
    - Is an \(O(\log n)\) algorithm always better in practice than an \(O(n)\) algorithm? Why not?



## B) Sorting Algorithms


In this section you will explain and compare **Insertion Sort**, **Merge Sort**, and **Quick Sort**. The goal is to understand not only how they reorder data, but also why their performance changes across dataset types such as random, nearly sorted, reversed, and duplicate-heavy inputs.


=== "Concept check"
    Use these questions to test basic understanding before going into complexity.
    
    - What problem does a sorting algorithm solve?
    - What does it mean to sort in ascending order?
    - Why is sorting useful before searching or analyzing data?
    - What is the idea behind insertion sort?
    - What is the idea behind merge sort?
    - What is the idea behind quick sort?
    - Which of these algorithms is based on “build the sorted part one step at a time”?
    - Which of these algorithms uses divide-and-conquer?
    - What does “partition” mean in quick sort?
    - What does “merge” mean in merge sort?

=== "Trace on the board"
    
    **Insertion Sort board-trace prompts**
    
    - Sort `[5, 2, 4, 6, 1, 3]` using insertion sort.
    - After each pass, what part of the array is guaranteed to be sorted?
    - Which values get shifted, and why?
    
    **Merge Sort board-trace prompts**
    
    - Sort `[8, 3, 7, 4, 2, 6, 5, 1]` using merge sort.
    - Show how the array is divided into smaller parts.
    - Show one merge step in detail.
    - At what point do we stop splitting?
    
    **Quick Sort board-trace prompts**
    
    - Sort `[8, 3, 1, 7, 0, 10, 2]` using quick sort.
    - Choose a pivot and explain why.
    - Show one partition step carefully.
    - After partitioning, what do we know about the pivot?
    - Why do the left and right sides still need sorting?

=== "Complexity questions"
    
    - What is the best-case time complexity of insertion sort?
    - What is the average-case time complexity of insertion sort?
    - What is the worst-case time complexity of insertion sort?
    - Why can insertion sort perform well on nearly sorted data?
    - What is the time complexity of merge sort in best, average, and worst case?
    - Why is merge sort consistently \(O(n \log n)\)?
    - What extra space does merge sort require, and why?
    - What is the average-case time complexity of quick sort?
    - What is the worst-case time complexity of quick sort?
    - In what situation can quick sort degrade to \(O(n^2)\)?
    - Why does pivot choice matter in quick sort?
    - Which of these algorithms has the smallest extra memory requirement in a simple in-place implementation?
    - Which of these algorithms is usually easiest to reason about for correctness?
    - Which of these algorithms often performs very well in practice despite its worst case?

=== "Comparison: Insertion vs Merge vs Quick"
    
    - Which algorithm would you choose for a very small array?
    - Which algorithm would you choose for a nearly sorted array?
    - Which algorithm gives the most predictable time complexity?
    - Which algorithm is often faster in practice on average for large random arrays?
    - Which algorithm needs extra memory to combine subproblems?
    - Which algorithm is usually implemented in-place more easily?
    - Which algorithm is simpler to code from scratch with fewer helper arrays?
    - Which algorithm is more sensitive to bad input order?
    - Which algorithm would you trust most when worst-case guarantees matter?
    - Which algorithm would you choose if memory is limited?

=== "Dataset behavior"
    These questions connect algorithm theory to your sprint benchmark results.
    
    - How do you expect insertion sort to behave on nearly sorted data?
    - How do you expect insertion sort to behave on reversed data?
    - How do you expect merge sort to behave across random, sorted, reversed, and duplicate-heavy inputs?
    - Why is merge sort less sensitive to initial order than insertion sort?
    - How can quick sort behave differently on random data versus already sorted data?
    - Why can duplicate-heavy data affect partition quality in quick sort?
    - Which algorithm do you expect to have the most stable benchmark trend across dataset types?
    - Which algorithm do you expect to vary the most depending on input pattern?
    - Why should benchmark results be explained together with the dataset shape?
    - Why is it dangerous to declare one algorithm “best” without saying “best for what kind of input”?

=== "Correctness & edge cases"
    Ask these to check implementation maturity and attention to detail.
    
    - What should happen if the array is `null`?
    - What should happen if the array is empty?
    - What should happen if the array has one element?
    - What should happen if the array is already sorted?
    - What should happen if the array is reversed?
    - What should happen if the array contains many duplicate keys?
    - How do you know your sorting algorithm actually sorted correctly?
    - How could you test that no elements were lost during sorting?
    - Why is handling duplicates important?
    - For merge sort and quick sort, what is the base case of the recursion?
    - What kind of bug can happen in merge sort if indices are copied incorrectly?
    - What kind of bug can happen in quick sort if partition boundaries are wrong?

=== "Whiteboard proof intuition"
    
    - In insertion sort, why is the left side guaranteed to remain sorted after each pass?
    - In merge sort, why does merging two sorted halves produce a sorted whole?
    - In quick sort, what property is established after partitioning around the pivot?
    - Why does recursion eventually stop in merge sort?
    - Why does recursion eventually stop in quick sort if implemented correctly?
    - Which algorithm is easiest to justify with an invariant?
    - What is an invariant you could state for insertion sort?

=== "Real-world scenarios"
    
    - Why do many real systems rely on efficient sorting?
    - Where do we see sorting in online shopping, banking, or analytics?
    - Why might a built-in library sort be preferred in real software?
    - When would a simple algorithm like insertion sort still be useful?
    - Why are divide-and-conquer sorts important for large data?
    - If a system sorts data repeatedly, what performance questions should we ask?
    - Why is stability sometimes important in business data, even if we do not focus on it in this sprint?

=== "Challenge questions"
    
    - Why is insertion sort often used as a helper for very small subarrays inside faster sorting systems?
    - Why is merge sort attractive when predictable performance matters?
    - Why can quick sort still be preferred in practice despite its worst-case \(O(n^2)\)?
    - How would choosing the first element as pivot affect already sorted input?
    - What pivot strategy might reduce the chance of bad partitions?
    - If two algorithms both have \(O(n \log n)\) average complexity, what practical factors could still make one faster?



## C) Recursion


This section is a **structure placeholder** for now. 

!!! info "Goal"
    Explain recursion as a problem-solving method where a function solves a problem by reducing it to smaller versions of the same problem.


=== "Concept check"
    - What is recursion?
    - What is a base case?
    - What is a recursive case?
    - Why must every recursive algorithm have a stopping condition?
    - How is recursion different from iteration?

=== "Trace on the board"
    - Trace a simple recursive function call step by step.
    - Show what happens in the call stack.
    - Identify where execution pauses and where it returns.
    - Explain the order of calls and returns.

=== "Base case and stopping"
    - What happens if the base case is missing?
    - What happens if the base case is wrong?
    - How do you know the input is getting closer to the base case?
    - Why can a recursive solution cause stack overflow?

=== "Call stack intuition"
    - What information is stored in each recursive call?
    - Why do recursive calls return in reverse order?
    - How is recursion related to stack behavior?
    - Why are some recursive solutions elegant but memory-expensive?

=== "Common mistakes"
    - Missing base case.
    - Base case never reached.
    - Wrong return value.
    - Changing the problem in the wrong direction.
    - Duplicating work unnecessarily.

=== "Future exercises"
    - Factorial
    - Fibonacci
    - Sum of array recursively
    - Binary search recursively
    - Merge sort recursion tree
    - Quick sort recursion tree



>> Good programmers do not just run algorithms; they can explain them, defend them, and choose them for the right situation.
