import { ChakraProvider } from '@chakra-ui/react';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import App from './App';
import { Navbar } from './common/components/Navbar';
import PostsPage from './posts/PostsPage';
import reportWebVitals from './reportWebVitals';
import theme from './theme';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <ChakraProvider theme={theme}>
    <React.StrictMode>
      <Navbar />
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={<App />}
          />
          <Route
            path="/posts"
            element={<PostsPage />}
          />
        </Routes>
      </BrowserRouter>
    </React.StrictMode>
  </ChakraProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
