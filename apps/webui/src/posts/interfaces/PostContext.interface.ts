import { Dispatch } from "react";
import { PostResponse } from "./PostResponse.interface";

export interface PostContext {
  posts: PostResponse[];
  setPosts: Dispatch<React.SetStateAction<PostResponse[]>>;
  test: string
};