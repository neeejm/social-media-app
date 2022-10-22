import React from 'react';
import { PostResponse } from './interfaces/PostResponse.interface';
import PostCard from './PostCard';

type Props = {
  posts: PostResponse[];
};

const PostList = ({ posts }: Props) => {
  return (
    <div>
      {posts.map((post) => (
        <PostCard
          key={post.id}
          post={post}
        />
      ))}
    </div>
  );
};

export default PostList;
