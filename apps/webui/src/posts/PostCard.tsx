import React, { useContext } from 'react';
import { PostResponse } from './interfaces/PostResponse.interface';
import axios from 'axios';
import { PostContext } from './interfaces/PostContext.interface';
import { PostCtx } from './PostsPage';

type Props = {
  post: PostResponse;
};

const PostCard = ({ post }: Props) => {
  const { posts, setPosts } = useContext<PostContext>(PostCtx);

  const deletePost = async () => {
    try {
      const { status } = await axios.delete(
        `http://localhost:8081/api/v1/posts/${post.id}`
      );

      console.log('response status is: ', status);

      setPosts(posts.filter((p) => p.id !== post.id));
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
      <div>
        {post.title} = {post.content} [{post.views}] (
        {post.createdAt.toString()} / {post.modifiedAt.toString()})
        <button onClick={deletePost}>delete</button>
      </div>
      <hr />
    </>
  );
};

export default PostCard;
