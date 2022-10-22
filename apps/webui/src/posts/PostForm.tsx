import React, { SetStateAction, useRef } from 'react';
import axios from 'axios';
import { PostRequest } from './interfaces/PostRequest.interface';
import { PostResponse } from './interfaces/PostResponse.interface';

interface AddPostsResponse {
  data: PostResponse;
  status: string;
}

interface Props {
  posts: PostResponse[];
  setPosts: (value: SetStateAction<PostResponse[]>) => void;
}

const addRequestConfig = {
  headers: {
    'Content-Type': 'application/json'
  }
};

const PostForm = ({ posts, setPosts }: Props) => {
  const titleRef = useRef<HTMLInputElement>(null);
  const contentRef = useRef<HTMLInputElement>(null);

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

      setPosts([...posts, data]);
    } catch (error: any) {
      if (axios.isAxiosError(error)) {
        console.log('error: ', error.message);
      } else {
        console.log('unexpected error: ', error.message);
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
