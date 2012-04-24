/**
 * 
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 * 
 * @version 3/21/2012
 * @author Rob Avery <pw97976@umbc.edu>
 * CMSC 341 - Spring 2012 - Project 2
 * Section 02
 */

package proj2;

import java.util.NoSuchElementException;
import java.util.Stack;

public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {

	/** The tree root. */
	private BinaryNode<AnyType> root;
	private int medianCount;

	/**
	 * Construct the tree.
	 */
	public BinarySearchTree() {
		makeEmpty();
	}

	/**
	 * Construct the tree with a given node
	 */
	public BinarySearchTree(BinaryNode<AnyType> n) {
		root = n;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(AnyType x) {
		root = insert(x, root, null);
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x
	 *            the item to insert.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t, BinaryNode<AnyType> p) {
		if (t == null)
			return new BinaryNode<AnyType>(x, null, null, p);

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = insert(x, t.left, t);
		else if (compareResult > 0)
			t.right = insert(x, t.right, t);
		else
			; // Duplicate; do nothing

		return t;
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * 
	 * @param x
	 *            the item to remove.
	 */
	public void remove(AnyType x) {
		root = remove(x, root);
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x
	 *            the item to remove.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return t; // Item not found; do nothing

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = remove(x, t.left);
		else if (compareResult > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = (t.left != null) ? t.left : t.right;

		return t;
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item or null if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return findMin(root).element;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t;
		return findMin(t.left);
	}

	/**
	 * Find the largest item in the tree.
	 * 
	 * @return the largest item of null if empty.
	 */
	public AnyType findMax() {
		if (isEmpty())
			throw new UnderflowException();
		return findMax(root).element;
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		if (t != null)
			while (t.right != null)
				t = t.right;

		return t;
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return true if not found.
	 */
	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x
	 *            is item to search for.
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the matched item.
	 */
	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return false;

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			return contains(x, t.left);
		else if (compareResult > 0)
			return contains(x, t.right);
		else
			return true; // Match
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Prints the entire tree in level order
	 */
	public void levelOrderPrint() {
		levelOrderPrint(height());
	}

	/**
	 * Prints the tree in level order to height h
	 * 
	 * @param h
	 *            the max height to display
	 */
	public void levelOrderPrint(int h) {
		printAllLevels(root, h);
	}

	/**
	 * Internal method that prints the tree in level order
	 * 
	 * @param n
	 *            the
	 */
	private void printAllLevels(BinaryNode<AnyType> n, int h) {

		for (int i = 1; i <= h; i++) {
			medianCount = 0;
			System.out.printf("\nLevel %d:\n        ", (i-1));
			printLevel(n, i);
			System.out.println();
		}
	}

	/**
	 * Internal method that prints the tree in level order
	 * 
	 * @param n
	 *            the node thats printing
	 * @param level
	 *            the level is printing
	 */
	public void printLevel(BinaryNode<AnyType> n, int level) {

		if (n == null)
			return;

		if (medianCount > 3) {
			System.out.print("\n        ");
			medianCount = 0;
		}

		if (level == 1) {
			medianCount++;
			if(n.parent == null)
				System.out.printf("%-13s", "( -1" + ", " + countNodes(n) + ", " + n.element + ")");
			else
				System.out.printf("%-13s", "(" + n.parent.element + ", " + countNodes(n) + ", " + n.element + ")");
		} else if (level > 1) {
			printLevel(n.left, level - 1);
			printLevel(n.right, level - 1);

		}
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void inorderPrintTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			inorderPrintTree(root);
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 */
	private void inorderPrintTree(BinaryNode<AnyType> t) {
		if (t != null) {
			inorderPrintTree(t.left);
			System.out.println(t.element);
			inorderPrintTree(t.right);
		}
	}

	/**
	 * Gives the height of the tree
	 * 
	 * @return the height of the tree
	 */
	public int height() {
		return height(root);
	}

	/**
	 * Internal method to compute height of a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 */
	private int height(BinaryNode<AnyType> t) {
		if (t == null)
			return -1;
		else
			return 1 + Math.max(height(t.left), height(t.right));
	}

	/**
	 * Gives the number of nodes in the entire tree
	 * 
	 * @return
	 */
	public int countNodes() {
		return countNodes(root);
	}

	/**
	 * Internal method to count the number of nodes in the tree
	 * 
	 * @param t
	 *            the tree needing counted
	 * @return the number of nodes in t
	 */
	private int countNodes(BinaryNode<AnyType> t) {

		int count = 1;

		if (t == null)
			return 0;

		count += countNodes(t.left);
		count += countNodes(t.right);

		return count;
	}
	
	
	/**
	 * Finds the median value of the BST
	 * 
	 * @return median value
	 */
	public AnyType median(){
		return median(root).element;
	}
	
	/**
	 * Finds the actual node of the median
	 * @return median node
	 */
	private BinaryNode<AnyType> medianNode(){
		return median(root);
	}
	
	/**
	 * Internal method that returns the value of the median node
	 * 
	 * @param t
	 *            the node to search
	 * @param nodes
	 *            the position of the median node
	 * @return the value of the median node
	 */
	private BinaryNode<AnyType> median(BinaryNode<AnyType> rt){
	   // From the weight of the tree, we can determine the position of the median
	   int m = ( countNodes(rt) + 1) / 2;

	   // The position of the root is the weight of the left subtree + 1
	   int current_pos = countNodes(rt.left) + 1;

	   BinaryNode<AnyType> current_root = rt;
	   
	   while (current_pos != m)

	     /* If the position of the "current" root is greater than than median, the go left
	        and subtract the number of nodes that are in the right side of the tree */
		   
	      if (current_pos > m){
	    	  
	          current_root = current_root.left;
	          current_pos = current_pos - countNodes(current_root.right) - 1;
	          
	      } else{
	    	  
	    	  /* If the position of the "current" root is less than the median, then go right
	       		 and add the number of nodes that are in the left side of the tree */
	    	  
	          current_root = current_root.right;
	          current_pos = current_pos + countNodes(current_root.left) + 1;
	      }

	   return current_root;

	}	
	
	/**
	 * Finds the successor to a node
	 * @param n the node that requests the successor
	 * @return the successor of n
	 */
	private BinaryNode<AnyType> successor(BinaryNode<AnyType> n) {
		
		if (n == null)
			return null;
		else if (n.right != null) {
			BinaryNode<AnyType> child = n.right;
			while (child.left != null)
				child = child.left;
			return child;
		} else {

			BinaryNode<AnyType> parent = n.parent;
			BinaryNode<AnyType> child = n;
			while (parent != null && child == parent.right) {
				child = parent;
				parent = parent.parent;
			}
			return parent;
		}
	}

	
	public void balance(){
		balance( root );
	}
	
	
	/** 
	 * Takes the tree and balances it
	 */
	public void balance( BinaryNode<AnyType> bn ) {

		java.util.Iterator<AnyType> iter = iterator( bn );
		Stack<AnyType> stack = new Stack<AnyType>();
		BinaryNode<AnyType> n = bn;

		//Puts the entire tree into a stack by pre-order
		while ( iter.hasNext() && stack.capacity() < countNodes( bn ) ) {

			n.element = iter.next();
			stack.push( n.element );
			
		}

		
		//Makes the root of the new tree the median of the old tree
		if( bn == root){
			root = this.medianNode();
			root.parent = null;
		}else if( bn.left != null || bn.right != null ){
			bn = median(bn);
		}
		
		//Inserts all the elements in the stack into the new tree
		while (!stack.isEmpty()) {

			this.insert(stack.pop());

		}

		if( bn.left != null)
			balance( bn.left );
		
		if( bn.right != null)
			balance( bn.right );
	}

	/**
	 * Iterates through the tree
	 * @return tree iterator
	 */
	public java.util.Iterator<AnyType> iterator( BinaryNode<AnyType> n ) {

		return new TreeIterator( n );
	}

	
	/**
	 * Tree Iterator that iterates through the tree in preorder
	 * @author Rob
	 *
	 */
	private class TreeIterator implements java.util.Iterator<AnyType> {

		private BinaryNode<AnyType> lastReturned = null, next;

		public TreeIterator( BinaryNode<AnyType> rt ) {

			next = rt;
			if (next != null)
				while (next.left != null)
					next = next.left;
		}

		//Tells whether there is a next node or not
		public boolean hasNext() {

			return next != null;
		}

		
		//Returns the next node in the order
		public AnyType next() {

			if (next == null)
				throw new NoSuchElementException();
			lastReturned = next;
			next = successor(next);
			return lastReturned.element;
		}

		//Removes next node in the order
		public void remove() {

			if (lastReturned == null)
				throw new IllegalStateException();
			if (lastReturned.left != null && lastReturned.right != null)
				next = lastReturned;
			BinarySearchTree.this.remove(lastReturned.element);
			lastReturned = null;
		}

	}

	// Basic node stored in unbalanced binary search trees
	private static class BinaryNode<AnyType> {

		// Constructors
		BinaryNode(AnyType theElement) {
			this(theElement, null, null, null);
		}

		BinaryNode(AnyType theElement, BinaryNode<AnyType> lt,
				BinaryNode<AnyType> rt, BinaryNode<AnyType> pt) {
			element = theElement;
			left = lt;
			right = rt;
			parent = pt;
		}

		AnyType element; // The data in the node
		BinaryNode<AnyType> parent; // Parent
		BinaryNode<AnyType> left; // Left child
		BinaryNode<AnyType> right; // Right child

	}

}
