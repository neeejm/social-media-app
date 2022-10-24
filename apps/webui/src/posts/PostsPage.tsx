import { createContext, useEffect, useState } from 'react';
import useHttpClient from '../hooks/useHttpClient';
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
  const { data, error, isLoading } = useHttpClient<PostResponse[]>(
    'http://localhost:8081/api/v1/posts',
    'GET'
  );

  const postContext: PostContext = {
    posts: posts,
    setPosts: setPosts
  };

  useEffect(() => {
    setPosts(data);
    console.log('eh', data);
  }, [data]);

  return (
    <PostCtx.Provider value={postContext}>
      <div>
        <h1>Posts Page</h1>
        <PostForm />
        <PostList
          posts={posts}
          error={error}
          isLoading={isLoading}
        />
      </div>
    </PostCtx.Provider>
  );
};

export default Posts;
