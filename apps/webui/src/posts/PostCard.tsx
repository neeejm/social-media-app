import axios from 'axios';
import { useContext, useRef, useState } from 'react';
import { AxiosResponse } from './interfaces/AxiosResponse.interface';
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

  const editPost = async () => {
    try {
      const { data, status } = await axios.put<
        PostRequest,
        AxiosResponse<PostResponse>
      >(`http://localhost:8081/api/v1/posts/${post.id}`, {
        title: titleEditable ? titleRef.current?.value : post.title,
        content: contentEditable ? contentRef.current?.value : post.content
      });

      console.log('response status is: ', status);

      setPosts(posts.map((p) => (p.id === data.id ? data : p)));
      setTitleEditable(false);
      setContentEditable(false);
    } catch (error: any) {
      if (axios.isAxiosError(error)) {
        console.log('error: ', error.message);
      } else {
        console.log('unexpected error: ', error.message);
      }
    }
  };

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

  const cancelEdit = () => {
    setTitleEditable(false);
    setContentEditable(false);
  };

  return (
    <>
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
