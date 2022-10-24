import { PostResponse } from './interfaces/PostResponse.interface';
import PostCard from './PostCard';

type Props = {
  posts: PostResponse[];
  error: string | null;
  isLoading: boolean;
};

const PostList = ({ posts, error, isLoading }: Props) => {
  return (
    <div>
      {isLoading && <p>Loading posts...</p>}
      {!isLoading && error && <p style={{ color: 'red' }}>{error}</p>}
      {!isLoading &&
        !error &&
        (posts.length ? (
          posts.map((post) => (
            <PostCard
              key={post.id}
              post={post}
            />
          ))
        ) : (
          <p>No posts to display.</p>
        ))}
    </div>
  );
};

export default PostList;
