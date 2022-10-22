import React, { useEffect, useState } from 'react';
import PostList from './PostList';
import { PostResponse } from './interfaces/PostResponse.interface';
import axios from 'axios';
import PostForm from './PostForm';

export interface AddPostsResponse {
  data: PostResponse[];
  status: string;
}

const Posts = () => {
  const [posts, setPosts] = useState<PostResponse[]>([]);

  const refreshPosts = (data: PostResponse[]) => {
    console.log('all posts updated: ', data);
    setPosts(data);
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
    <div>
      <h1>Posts Page</h1>
      <PostForm
        posts={posts}
        setPosts={setPosts}
        refreshPosts={getPosts}
      />
      <PostList posts={posts} />
    </div>
  );
};

export default Posts;
