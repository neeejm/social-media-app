import { Container, Divider, Stack } from '@chakra-ui/react';
import { createContext, useEffect, useState } from 'react';
import { useHttpClient } from '../hooks/useHttpClient';
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
  const { doRequest, error, isLoading } = useHttpClient<PostResponse[]>();

  const postContext: PostContext = {
    posts: posts,
    setPosts: setPosts
  };

  useEffect(() => {
    doRequest({
      url: 'http://localhost:8081/api/v1/posts',
      method: 'GET',
      onSuccess: (data, status) => {
        setPosts(data);
        console.log('response status is:', status);
      }
    });
  }, []);

  return (
    <PostCtx.Provider value={postContext}>
      <Container
        maxW="container.sm"
        marginTop="10"
        marginBottom="10"
      >
        <Stack spacing={4}>
          <PostForm />
          <Divider />
          <PostList
            posts={posts}
            error={error}
            isLoading={isLoading}
          />
        </Stack>
      </Container>
    </PostCtx.Provider>
  );
};

export default Posts;
