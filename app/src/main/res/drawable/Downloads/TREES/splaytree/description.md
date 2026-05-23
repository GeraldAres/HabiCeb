In a Balanced Search Tree such as a Splay Tree, the basic operation for the tree is to rotate the nodes. This is to attempt to reduce the height of the tree thus improving the core operations of the tree, including the insert and search. For the case of a splay tree, it will splay the most recently added, searched, and the parent of the removed node.

Your goal for this problem is to perform the trinode restructuring given the grandparent node using the two primitive rotate operations on parent-child nodes: zigleft and zigright  -- which you are also to implement.

After completing the zigleft and zigright go to the restructure method. In this method, the child node is already given. You will be using this method when you perform search, insertions, and deletions. Your task in this method is to find the parent and the grandparent node for which you are going to perform the trinode restructuring.

Then using the given calculated configuration of the grandparent, parent, and child, you can now perform the trinode restructuring. First print the trinode restructuring operation (whether ZIGLEFT, ZIGRIGHT, ZIGZIGLEFT, ZIGZIGRIGHT, ZIGZAGLEFT, or ZIGZAGRIGHT) on each of the if statements. Then, perform the trinode restructuring only using one or two of the primitive rotate operations zigleft and/or zigright. 

After doing so, you are now ready to improve the insert operation of the existing Binary Search Tree. Remember that as you insert, search, or delete an element, you are to splay the node to the root. You can use helper methods so you will only utilize one method for the checking when inserting or deleting.

Several comments are added in the code so you will understand the state of the three nodes relative to each other. Feel free to delete them as well.