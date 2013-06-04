package org.mercury.util;

/**
 * Interface which allows State functionality for use by a StateMachine.
 * 
 * @author tbertore
 * 
 * @param <E>
 *            The type of the owner of the StateMachine which will use this
 *            State.
 */
public interface State<E> {
	/**
	 * Event method to be called when this state is entered by a StateMachine.
	 * 
	 * @param e
	 *            The owner of the StateMachine.
	 */
	public void enter(E e);

	/**
	 * Event method to be called when this state is executed by a StateMachine.
	 * 
	 * @param e
	 *            The owner of the StateMachine.
	 */
	public void execute(E e);

	/**
	 * Event method to be called when this state is exited by a StateMachine.
	 * 
	 * @param e
	 *            The owner of the StateMachine.
	 */
	public void exit(E e);
}
