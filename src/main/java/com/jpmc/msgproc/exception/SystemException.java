package com.jpmc.msgproc.exception;

/**
 * @author Aravind Rajasekaran
 *
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new BusinessExcepton
	 */
	public SystemException() {
	}

	/**
	 * Instantiates a new BusinessExcepton
	 * 
	 * @param message
	 */
	public SystemException(String message) {
		super(message);
	}

}
