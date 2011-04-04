package org.beryl.schedule;

public class UnrunnableJobException extends Exception {

	public UnrunnableJobException() {
		super("Job has been abandoned.");
	}
	
	public UnrunnableJobException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2140357239600281880L;
}
