
/*
 * A Global exception handler that handles exceptions for this application
 * using @ControllerAdvice & @ExceptionHandler
 * 
 */

package com.finra.spring.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e,Model model){
		e.printStackTrace();
		return "error";
	}
	

	
	
}
