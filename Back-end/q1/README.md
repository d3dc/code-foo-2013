Back-end - Question 1
=====================
>Write a program that searches a family tree for members that match name and/or generation. 

<br />

I created a java class FamilyTree and a command line menu for manipulating it. FamilyTree represent a family tree comprised of FamilyMember objects.

<br />

    To compile: javac FamilyTreeMenu.java

    To run: java FamilyTreeMenu

<br />

FamilyMember
------------

Each FamilyMember can have any number of spouses or any number of children. The implementation uses ArrayList to optimize performance , since in most cases it never needs to resized and the lists grow slowly.

A Family member can:

    marry(FamilyMember spouse) - 
        Add a new spouse of the family member.
    
    spawn(FamilyMember child), spawn(FamilyMember spouse, FamilyMember child) - 
        Add a new child of the family member. If spouse is passed, the child is added to the spouse's children as well.

Family member accessors:

    spouses()
    
    children()
    
    father()
    
    mother()
    
    name()

<br />

FamilyTree
----------

A FamilyTree has a root ancestor, set when the tree is created.

A FamilyTree can:
    
    search(String name) -
        Search the family tree for FamilyMembers whose names contain name. Returns a list of FamilyMembers.
    
    search(int generation) -
        Search the family tree for FamilyMembers in generation. Returns a list of FamilyMembers.
    
    search(String name, int generation) -
        Search the family tree for FamilyMembers in generation whose names contain name. Returns a list of FamilyMembers.
        
    PrintTree() -
        Recursively prints the names in the entire family tree. FamilyMembers are printed 1 per line. 
        Each generation is indented at the beginning of the line.
        Spouses are connected by --- on the same line.

<br />

>#How does this algorithm scale?

<br />

**The algorithm for searching by generation** is a Breadth-First Search. This is used in all search methods.
A standard Breadth-First Search has worst-case time-bound O(n). For an input of size n, each of at most n-1 children are explored. 


In FamilyMember, children are added to each spouses to allow for e.g. stepchildren. To prevent duplicate children from being added to the queue, my algorithm checks to see if the queue already contains that child. This causes additional comparisons. There can be at most n-2 children already in the queue, meaning this check takes O(n) time.


<br />

**The algorithm for searching by name in a generation** loops through each FamilyMember in that generation and does a comparison to the search name. To **search by name in the entire tree**, this is repeated for every level of the family tree. The number of FamilyMembers in a generation is some constant fraction of n, so both methods do O(n) comparisons. This is in addition to the search for each generation. When searching the whole tree, there may be up to n generations, and the algorithm searches all of them.

<br />

**The worst-case time bound on searching for a generation is O(n^2)**
**The worst-case time bound on searching for a name in a generation is O(n^2 + n) = O(n^2)**
**The worst-case time-bound on searching for a name in the entire tree is O(n(n^2)) = O(n^3)**

**These time-bounds can be reduced by marking child FamilyMembers instead of looping through the queue.**

<br />