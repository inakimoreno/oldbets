package exceptions;

public class PastDate extends Exception {
	private static final long serialVersionUID = 1L;

	public PastDate() {
		super();
	}

	/**This exception is triggered if the date selected is in the past 
	 *@param s String of the exception
	 */
	public PastDate(String s) {
		super(s);
	}
}
