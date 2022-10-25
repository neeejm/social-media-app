import { Button, List, ListIcon, ListItem, Text } from '@chakra-ui/react';
import { TbBrandTelegram } from 'react-icons/tb';
import { Link } from 'react-router-dom';

export const VerticalNavbar = () => {
  return (
    <List spacing={3}>
      <ListItem>
        <Button bgColor="transparent">
          <Link to="/">
            <Text fontSize={20}>
              <ListIcon as={TbBrandTelegram} />
              Home
            </Text>
          </Link>
        </Button>
      </ListItem>
      <ListItem>
        <Button bgColor="transparent">
          <Link to="/posts">
            <Text fontSize={20}>
              <ListIcon as={TbBrandTelegram} />
              Posts
            </Text>
          </Link>
        </Button>
      </ListItem>
    </List>
  );
};
