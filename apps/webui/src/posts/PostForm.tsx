import axios, { AxiosResponse } from 'axios';
import { useContext, useRef } from 'react';
import { PostContext } from './interfaces/PostContext.interface';
import { PostRequest } from './interfaces/PostRequest.interface';
import { PostResponse } from './interfaces/PostResponse.interface';
import { PostCtx } from './PostsPage';

const addRequestConfig = {
  headers: {
    'Content-Type': 'application/json'
  }
};

const PostForm = () => {
  const titleRef = useRef<HTMLInputElement>(null);
  const contentRef = useRef<HTMLInputElement>(null);
  const { posts, setPosts } = useContext<PostContext>(PostCtx);

  const addPost = async () => {
    try {
      const { data, status } = await axios.post<
        PostRequest,
        AxiosResponse<PostResponse>
      >(
        'http://localhost:8081/api/v1/posts',
        {
          title: titleRef.current?.value,
          content: contentRef.current?.value
        },
        addRequestConfig
      );

      console.log('response status is: ', status);

      if (titleRef.current !== null) {
        titleRef.current.value = '';
      }
      if (contentRef.current !== null) {
        contentRef.current.value = '';
      }

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
