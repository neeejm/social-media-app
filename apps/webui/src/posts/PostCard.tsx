import { useContext, useRef, useState } from 'react';
import { useHttpClient } from '../hooks/useHttpClient';
import { PostContext } from './interfaces/PostContext.interface';
import { PostRequest } from './interfaces/PostRequest.interface';
import { PostResponse } from './interfaces/PostResponse.interface';
import { PostCtx } from './PostsPage';
import TextView from './TextView';

type Props = {
  post: PostResponse;
};

const PostCard = ({ post }: Props) => {
  const { posts, setPosts } = useContext<PostContext>(PostCtx);
  const titleRef = useRef<HTMLInputElement>(null);
  const contentRef = useRef<HTMLInputElement>(null);
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
        title: titleEditable ? titleRef.current?.value : post.title,
        content: contentEditable ? contentRef.current?.value : post.content
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
      {isLoading && <p>Editing post...</p>}
      {!isLoading && error && <p style={{ color: 'red' }}>{error}</p>}
      <TextView
        title="Title"
        value={post.title}
        ref={titleRef}
        editable={titleEditable}
        setEditable={setTitleEditable}
      />
      <TextView
        title="Content"
        value={post.content}
        ref={contentRef}
        editable={contentEditable}
        setEditable={setContentEditable}
      />
      <h4>Info: </h4>
      <p>
        [{post.views}] ({post.createdAt.toString()} /{' '}
        {post.modifiedAt.toString()})
      </p>

      {titleEditable || contentEditable ? (
        <>
          <button onClick={editPost}>edit</button>
          <button onClick={cancelEdit}>cancel</button>
        </>
      ) : (
        ''
      )}
      <button onClick={deletePost}>delete</button>
      <hr />
    </>
  );
};

export default PostCard;
