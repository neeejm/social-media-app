package com.neeejm.posts.exceptions;

public class EmptyPostException extends RuntimeException {

  public EmptyPostException(String msg) {
    super(msg);
  }
}
