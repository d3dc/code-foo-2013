Back-end - Question 2
=====================
>Write a sorting algorithm that can sort n number of high scores (float Score, string Name) by score.

<br />

    To compile: javac HighScore.java

    To run: java HighScore [highScoreFile]
        where highScoreFile is an optional filename

<br />

A valid input file has the format:  
  *One score entry per line.
  *Each entry has the format "score name"

>#What algorithm did you use?

<br />

I used  quickSort on a collection of tuples represented by a playerScore class.

<br />

>#How does this algorithm scale?

<br />

quickSort has average and worst case performance O(n log n).

it has a worst case space complexity of O(n).

<br />

>#Can you reduce time and space complexity?

<br />

A quicksort performs as least as good as most sorting algorithms: O(n log n)). On modern hardware, with a ram based collection for our scores, [Wikipedia](http://en.wikipedia.org/wiki/Quicksort) states quicksort will outperform other O(n log n) algorithms.

The quicksort algorithm I implemented manipulates the collection in place, but does not use a tail call. This means the stack depth isn't bounded, and may need up to O(n) space. Using tail recursion, this space complexity could be reduced to O(log n).

<br />
<br />