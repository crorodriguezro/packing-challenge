# Package challenge.
##  Approach
The problem was solved by finding all the possible combinations, filtering the non-viable ones and finally choosing the
best solution combination based on its cost and weight.

TDD was used most of the time (except on the development of the additional constraints).

Most of the functions are pure functions and our domain is immutable, that increases the maintainability.
 
Recursion is used to find all the combinations. The solution executes in quadratic time (O(n^2)).
This is not a problem working with 15 things. But working with more items worsen the memory and time usage rapidly.
This algorithm was the first one that came to my mind to get the unit test to pass. Then I created another test to check
its performance with 15 items and it was good enough.

ArrayList is used to store all the possible combinations because this data structure gives us constant time for inserts and, 
given that it has an Object[] for storage and  a int for tracking the list size, ArrayList is a memory efficient 
data structure.

## Build
```
gradle jar 
```
