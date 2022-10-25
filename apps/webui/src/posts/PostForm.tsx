import { Box, Button, Input, Stack, Textarea } from '@chakra-ui/react';
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
  const contentRef = useRef<HTMLTextAreaElement>(null);
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
    <Box
      maxW="100%"
      padding="6"
      borderWidth="1px"
      borderRadius="lg"
    >
      <Stack spacing={3}>
        <Input
          placeholder="Title"
          size="sm"
          type="text"
          name="post-title"
          id="post-title"
          ref={titleRef}
        />
        <Textarea
          placeholder="Write your post content here..."
          size="sm"
          name="post-content"
          id="post-content"
          ref={contentRef}
        />
        <Button
          colorScheme="teal"
          onClick={addPost}
        >
          Posts
        </Button>
      </Stack>
    </Box>
  );
};

export default PostForm;
