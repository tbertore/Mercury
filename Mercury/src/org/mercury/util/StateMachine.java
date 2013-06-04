package org.mercury.util;

/**
 * A class which implements finite state machine functionality, useful for
 * reducing code with a heavy number of flags.
 * 
 * @author tbertore
 * 
 * @param <E>
 *            The type of the owner of this StateMachine.
 */
public class StateMachine<E> {
	private E owner;
	private State<E> currentState;
	private State<E> previousState;
	private State<E> globalState;

	/**
	 * Creates a new StateMachine. To set this StateMachine's owner, a call to
	 * setOwner should be performed before calling the update method. A
	 * StateMachine may only have one State at a time and should be updated via
	 * a call to the update method.
	 */
	public StateMachine() {

	}

	/**
	 * Creates a new StateMachine owned by the specified object. A StateMachine
	 * may only have one State at a time and should be updated via a call to the
	 * update method.
	 * 
	 * @param e
	 *            The owner of this StateMachine. The owner may be changed by
	 *            executed states.
	 */
	public StateMachine(E e) {
		owner = e;
	}

	/**
	 * Sets the owner of this StateMachine to the specified object.
	 * 
	 * @param e
	 *            The owner of this StateMachine. The owner may be changed by
	 *            executed states.
	 */
	public void setOwner(E e) {
		owner = e;
	}

	/**
	 * Sets the current state of this StateMachine to the specified state. The
	 * change happens instantaneously -- The previous state is not notified via
	 * an exit() call and the entered state is not notified via a call to
	 * enter().
	 * 
	 * @param s
	 */
	public void setCurrentState(State<E> s) {
		currentState = s;
	}

	/**
	 * Sets the previous state of this StateMachine to the specified state.
	 * 
	 * @param s
	 */
	public void setPreviousState(State<E> s) {
		previousState = s;
	}

	/**
	 * Sets the global State of this StateMachine, which will have its execute
	 * method called each time the update method is called along with any
	 * current state. A global state is optional.
	 * 
	 * @param s
	 *            The State to set to.
	 */
	public void setGlobalState(State<E> s) {
		globalState = s;
	}

	/**
	 * Returns the current State of this StateMachine.
	 * 
	 * @return The current State of this StateMachine.
	 */
	public State<E> getCurrentState() {
		return currentState;
	}

	/**
	 * Returns the State which occurred before the current State.
	 * 
	 * @return The State which occurred before the current State.
	 */
	public State<E> getPreviousState() {
		return previousState;
	}

	/**
	 * Returns the global state, if any, of this StateMachine. Global State
	 * objects are always executed whenever a current state is executed and are
	 * useful for persistent behaviors.
	 * 
	 * @return The global State of this StateMachine, or null if none exists.
	 */
	public State<E> getGlobalState() {
		return globalState;
	}

	/**
	 * Returns whether or not the current state is of the same type as the
	 * specified class.
	 * 
	 * @param s
	 *            The class to check the current State's type against.
	 * @return true if the current State's type is exactly the type specified,
	 *         false otherwise.
	 */
	public boolean isInState(Class<State<E>> s) {
		return s == currentState.getClass();
	}

	/**
	 * Performs a call to the current State's execute method and the global
	 * state's execute method, if any.
	 * 
	 */
	public void update() {
		if (globalState != null)
			globalState.execute(owner);
		if (currentState != null)
			currentState.execute(owner);
	}

	/**
	 * Changes the current State object to a different state. The current
	 * state's exit method will be called, then the new state's enter method
	 * will be called.
	 * 
	 * @param state
	 */
	public void changeState(State<E> state) {
		previousState = currentState;
		currentState.exit(owner);
		currentState = state;
		currentState.enter(owner);
	}

	/**
	 * Changes to the last State before the current State, and calls the exit
	 * method of the current State before the enter method of the previous
	 * state.
	 * 
	 */
	public void revert() {
		changeState(previousState);
	}
}
