import { Image } from '@chakra-ui/react';
import travoltaImg from '../assets/imgs/travolta.png';
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
          <Image
            src={travoltaImg}
            alt="Confused Travolta"
          />
        ))}
    </div>
  );
};

export default PostList;
