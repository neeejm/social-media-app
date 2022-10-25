import { CloseIcon, DeleteIcon, EditIcon } from '@chakra-ui/icons';
import {
  Box,
  Divider,
  Flex,
  HStack,
  IconButton,
  Spacer,
  Stack
} from '@chakra-ui/react';
import { useContext, useRef, useState } from 'react';
import { useHttpClient } from '../hooks/useHttpClient';
import { PostContext } from './interfaces/PostContext.interface';
import { PostRequest } from './interfaces/PostRequest.interface';
import { PostResponse } from './interfaces/PostResponse.interface';
import { PostContent } from './PostContent';
import { PostDate } from './PostDate';
import { PostCtx } from './PostsPage';
import { PostTitle } from './PostTitle';
import { PostViews } from './PostViews';

type Props = {
  post: PostResponse;
};

const PostCard = ({ post }: Props) => {
  const { posts, setPosts } = useContext<PostContext>(PostCtx);
  const titleRef = useRef<HTMLInputElement>(null);
  const contentRef = useRef<HTMLTextAreaElement>(null);
  const [titleEditable, setTitleEditable] = useState(false);
  const [contentEditable, setContentEditable] = useState(false);
  const { doRequest, error, isLoading } = useHttpClient<
    PostResponse,
    PostRequest
  >();
  let action: 'edit' | 'delete';

  const editPost = async () => {
    action = 'edit';
    console.log('inside action', action);

    doRequest({
      url: `http://localhost:8081/api/v1/posts/${post.id}`,
      method: 'PUT',
      body: {
        title:
          titleEditable && titleRef.current
            ? titleRef.current.value
            : post.title,
        content:
          contentEditable && contentRef.current
            ? contentRef.current.value
            : post.content
      },
      onSuccess: (data, status) => {
        setPosts(posts.map((p) => (p.id === data.id ? data : p)));
        console.log('response status is:', status);
      }
    });

    setTitleEditable(false);
    setContentEditable(false);
  };

  const deletePost = async () => {
    action = 'delete';

    doRequest({
      url: `http://localhost:8081/api/v1/posts/${post.id}`,
      method: 'DELETE',
      onSuccess: (_, status) => {
        setPosts(posts.filter((p) => p.id !== post.id));
        console.log('response status is:', status);
      }
    });
  };

  const cancelEdit = () => {
    setTitleEditable(false);
    setContentEditable(false);
  };

  return (
    <>
      <Box
        maxW="100%"
        padding="6"
        borderWidth="1px"
        borderRadius="lg"
      >
        <Stack spacing={3}>
          {isLoading && <p>Editing post...</p>}
          {!isLoading && error && <p style={{ color: 'red' }}>{error}</p>}
          <PostTitle
            value={post.title}
            ref={titleRef}
            editable={titleEditable}
            setEditable={setTitleEditable}
          />
          <HStack>
            <PostViews views={post.views} />
            <PostDate
              text="created"
              date={post.createdAt}
            />
            <PostDate
              text="modified"
              date={post.modifiedAt}
            />
          </HStack>
          <Divider />
          <PostContent
            value={post.content}
            ref={contentRef}
            editable={contentEditable}
            setEditable={setContentEditable}
          />
          <Flex>
            <Spacer />
            <HStack>
              {titleEditable || contentEditable ? (
                <>
                  <IconButton
                    size="sm"
                    bg="lightgreen"
                    aria-label="Edit the current post"
                    onClick={editPost}
                    icon={<EditIcon />}
                  />
                  <IconButton
                    size="sm"
                    bg="yellow"
                    aria-label="Cancel editing the current post"
                    onClick={cancelEdit}
                    icon={<CloseIcon />}
                  />
                </>
              ) : (
                ''
              )}
              <IconButton
                size="sm"
                aria-label="Delete the current post"
                onClick={deletePost}
                bg="red"
                icon={<DeleteIcon />}
              />
            </HStack>
          </Flex>
        </Stack>
      </Box>
    </>
  );
};

export default PostCard;
