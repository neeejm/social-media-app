import React, { useContext, useRef, useState } from 'react';
import axios from 'axios';
import { PostRequest } from './interfaces/PostRequest.interface';
import { PostResponse } from './interfaces/PostResponse.interface';
import { PostContext } from './interfaces/PostContext.interface';
import { PostCtx } from './PostsPage';

interface AddPostsResponse {
  data: PostResponse[];
  status: string;
}

const addRequestConfig = {
  headers: {
    'Content-Type': 'application/json'
  }
};

const PostForm = () => {
  const titleRef = useRef<HTMLInputElement>(null);
  const contentRef = useRef<HTMLInputElement>(null);
  const { setPosts } = useContext<PostContext>(PostCtx);

  const addPost = async () => {
    try {
      const { data, status } = await axios.post<PostRequest, AddPostsResponse>(
        'http://localhost:8081/api/v1/posts',
        {
          title: titleRef.current?.value,
          content: contentRef.current?.value
        },
        addRequestConfig
      );

      console.log('response status is: ', status);

      // setPosts(data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log('error: ', error.message);
      } else {
        console.log('unexpected error: ', error);
      }
    }
  };

  return (
    <>
      <label>Add Post </label>
      <label htmlFor="post-title">Title: </label>
      <input
        type="text"
        name="post-title"
        id="post-title"
        ref={titleRef}
      />
      <label htmlFor="post-title">Content: </label>
      <input
        type="text"
        name="post-content"
        id="post-content"
        ref={contentRef}
      />
      <button onClick={addPost}>Post</button>
      <hr />
    </>
  );
};

export default PostForm;
