//package com.example.springboot3.error;
//
//import java.util.Date;
//
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ProblemDetail;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class CustomExceptionHandler {
//
//	
//	@ExceptionHandler(Exception.class)
//	public ProblemDetail exceptionHadler(Exception exception) {
//		var problemDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
//		problemDetail.setProperty("error", problemDetail.getTitle());
//        problemDetail.setProperty("timestamp", new Date());
////        problemDetail.set
//		return problemDetail;
//	}
//	
//	
//}
