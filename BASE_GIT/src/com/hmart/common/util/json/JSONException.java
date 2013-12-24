package com.hmart.common.util.json;



// TODO: Auto-generated Javadoc
/**
 * The JSONException is thrown by the JSON.org classes then things are amiss.
 * @author JSON.org
 * @version 2
 */
@SuppressWarnings("serial")
public class JSONException extends Exception {
    
    /** cause. */
    private Throwable cause;

    /**
     * Constructs a JSONException with an explanatory message.
     * @param message Detail about the reason for the exception.
     */
    public JSONException(String message) {
        super(message);
    }

    /**
     * Constructor JSONException.
     *
     * @param t Throwable
     */
    public JSONException(Throwable t) {
        super(t.getMessage());
        this.cause = t;
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getCause()
     */
    public Throwable getCause() {
        return this.cause;
    }
}
