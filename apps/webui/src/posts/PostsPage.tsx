import axios from 'axios';
import { createContext, useEffect, useState } from 'react';
import { PostContext } from './interfaces/PostContext.interface';
import { PostResponse } from './interfaces/PostResponse.interface';
import PostForm from './PostForm';
import PostList from './PostList';

export interface AddPostsResponse {
  data: PostResponse[];
  status: string;
}

const defaultValue: PostContext = {
  posts: [],
  setPosts: () => 'default'
};

export const PostCtx = createContext(defaultValue);

const Posts = () => {
  const [posts, setPosts] = useState<PostResponse[]>([]);
  const postContext: PostContext = {
    posts: posts,
    setPosts: setPosts
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
    <PostCtx.Provider value={postContext}>
      <div>
        <h1>Posts Page</h1>
        <PostForm />
        <PostList posts={posts} />
      </div>
    </PostCtx.Provider>
  );
};

export default Posts;
