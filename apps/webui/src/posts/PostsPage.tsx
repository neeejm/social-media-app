import React, { createContext, Dispatch, useEffect, useState } from 'react';
import PostList from './PostList';
import { PostResponse } from './interfaces/PostResponse.interface';
import axios from 'axios';
import PostForm from './PostForm';
import { PostContext } from './interfaces/PostContext.interface';

export interface AddPostsResponse {
  data: PostResponse[];
  status: string;
}

const defaultCtxValue: PostContext = {
  posts: [],
  setPosts: () => 'default',
  test: 'default test'
};

export const PostCtx = createContext<PostContext>(defaultCtxValue);

const Posts = () => {
  const [posts, setPosts] = useState<PostResponse[]>([]);
  const postsContext: PostContext = {
    posts: posts,
    setPosts: setPosts,
    test: 'hello'
  };

  const getPosts = async () => {
    try {
      const { data, status } = await axios.get<PostResponse[]>(
        'http://localhost:8081/api/v1/posts'
      );

      console.log('response status is: ', status);

      setPosts(data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log('error : ', error.message);
      } else {
        console.log('unexpected error: ', error);
      }
    }
  };

  useEffect(() => {
    getPosts();
  }, []);

  return (
    <PostCtx.Provider value={postsContext}>
      <div>
        <h1>Posts Page</h1>
        <PostForm />
        <PostList posts={posts} />
      </div>
    </PostCtx.Provider>
  );
};

export default Posts;
