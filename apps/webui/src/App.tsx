import React from 'react';
import { Link } from 'react-router-dom';

export default function App() {
  return (
    <div className="App">
      <h1>Welcome to Supergram 😜</h1>
      <Link to={'/posts'}>Posts</Link>
    </div>
  );
}
