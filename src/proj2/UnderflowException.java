/**
 * Exception class for access in empty containers such as stacks, queues, and
 * priority queues
 *
 * @version 3/21/2012
 * @author Rob Avery <pw97976@umbc.edu>
 * CMSC 341 - Spring 2012 - Project 2
 * Section 02
 */

package proj2;

public class UnderflowException extends RuntimeException {
	/**
	 * Construct this exception object.
	 */
	public UnderflowException() {
		super();
	}
}
