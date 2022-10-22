import React from 'react';
import { PostResponse } from './interfaces/PostResponse.interface';

type Props = {
  post: PostResponse;
};

const PostCard = ({ post }: Props) => {
  return (
    <>
      <div id={post.id}>
        {post.title} = {post.content} [{post.views}] (
        {post.createdAt.toString()} / {post.modifiedAt.toString()})
      </div>
      <hr />
    </>
  );
};

export default PostCard;
