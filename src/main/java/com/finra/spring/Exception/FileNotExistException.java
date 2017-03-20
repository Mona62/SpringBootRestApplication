/*
 * Customized Exception
 */


package com.finra.spring.Exception;

public class FileNotExistException extends RuntimeException {

	private static final long serialVersionUID = 4756162736439677840L;

	@Override
	public String getMessage() {
		
		return "Requested File id does not exist";
	}
}
