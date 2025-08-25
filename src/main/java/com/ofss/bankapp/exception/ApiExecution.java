package com.ofss.bankapp.exception;

import org.springframework.http.HttpStatus;

public class ApiExecution extends RuntimeException {
  private final HttpStatus status;

  public ApiExecution(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
