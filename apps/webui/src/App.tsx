import { Button, Container, Text } from '@chakra-ui/react';
import { Link } from 'react-router-dom';

export default function App() {
  return (
    <>
      <Container padding="5">
        <Text fontSize="6xl">Welcome to Supergram 😜</Text>
        <Link to={'/posts'}>
          <Button
            colorScheme="teal"
            margin="10"
          >
            Posts
          </Button>
        </Link>
      </Container>
    </>
  );
}
