package com.neeejm.posts.exceptions;

public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException(String msg) {
    super(msg);
  }
}
