package exceptions;

public class EventAlreadyExists extends Exception {

	private static final long serialVersionUID = 1L;

	public EventAlreadyExists() {
		super();
	}

	/**This exception is triggered if the user already exists 
	 *@param s String of the exception
	 */
	public EventAlreadyExists(String s) {
		super(s);
	}
}
