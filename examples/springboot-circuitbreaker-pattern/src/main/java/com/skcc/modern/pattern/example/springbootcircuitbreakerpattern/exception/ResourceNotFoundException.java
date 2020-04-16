package com.skcc.modern.pattern.example.springbootcircuitbreakerpattern.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 8547488288721613279L;

	public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(final String message) {
		super(message);
	}
	
	public ResourceNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}
}