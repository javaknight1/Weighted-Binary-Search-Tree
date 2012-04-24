/**
 * 
 * Main class that gets the height and file and creates and
 * displays the tree unbalanced, and then balanced.
 * 
 * @version 3/21/2012
 * @author Rob Avery <pw97976@umbc.edu>
 * CMSC 341 - Spring 2012 - Project 2
 * Section 02
 */
package proj2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 *            0 - file that includes all the number needed to be inserted
	 *            into the tree 1 - the depth of the that you want to display
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		String file = args[0];
		int maxHeightDisplay = Integer.parseInt(args[1]);
		int tempInt;
		int numofInts = 0;

		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		Scanner inFile = new Scanner(new File(file));

		// Add all the numbers in the file to the tree
		while (inFile.hasNext()) {
			tempInt = inFile.nextInt();
			bst.insert(tempInt);
			numofInts++;
		}
		inFile.close();

		System.out.printf("%d integers were read from %s", numofInts, file);
		System.out.println("\n---------------------------------------");

		// Before Balancing
		System.out.println("Before balancing...");
		System.out.printf("   the tree contains %d nodes\n", bst.countNodes());
		System.out.printf("   the height is %d\n", bst.height());
		System.out.printf("   the median is %d\n", bst.median());
		System.out.printf("Tree contends up to level %d\n", maxHeightDisplay);
		System.out.print("---------------------------------------");
		bst.levelOrderPrint(maxHeightDisplay);
		System.out.println("---------------------------------------");

		// Balancing into new BinarySearchTree
		bst.balance();

		// After balancing
		System.out.println("After balancing...");
		System.out.printf("   the tree contains %d nodes\n", bst.countNodes());
		System.out.printf("   the height is %d\n", bst.height());
		System.out.printf("   the median is %d\n", bst.median());
		System.out.printf("Tree contends up to level %d\n", maxHeightDisplay);
		System.out.print("---------------------------------------");
		bst.levelOrderPrint(maxHeightDisplay);
		System.out.println("---------------------------------------");

	}
}
