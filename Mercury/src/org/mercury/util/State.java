package org.mercury.util;


public interface State <E> {
	/**Event method to be called when this state is entered by a StateMachine.
	 * @param e The owner of the StateMachine.
	 */
	public void enter(E e);
	/**Event method to be called when this state is executed by a StateMachine.
	 * @param e The owner of the StateMachine.
	 */
	public void execute(E e);
	/**Event method to be called when this state is exited by a StateMachine.
	 * @param e The owner of the StateMachine.
	 */
	public void exit(E e);
}
