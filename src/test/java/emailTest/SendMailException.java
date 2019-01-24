package emailTest;

/**
 * Send Email Exception
 *
 * @author biezhi
 * @date 2018/10/9
 */
public class SendMailException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SendMailException() {
    }

    public SendMailException(String message) {
        super(message);
    }

    public SendMailException(Throwable cause) {
        super(cause);
    }
}
