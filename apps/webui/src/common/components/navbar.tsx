import { MoonIcon, StarIcon, SunIcon } from '@chakra-ui/icons';
import {
  Avatar,
  Box,
  Button,
  Center,
  Flex,
  Menu,
  MenuButton,
  MenuDivider,
  MenuItem,
  MenuList,
  Stack,
  useColorMode,
  useColorModeValue,
  useDisclosure
} from '@chakra-ui/react';

export const Navbar = () => {
  const { colorMode, toggleColorMode } = useColorMode();
  const { isOpen, onOpen, onClose } = useDisclosure();

  let darkColor = 'teal.500';
  let lightColor = 'teal.400';
  let darkBg = colorMode === 'light' ? darkColor : lightColor;
  let lightBg = colorMode === 'light' ? lightColor : darkColor;

  return (
    <>
      <Box
        bg={useColorModeValue(darkColor, lightColor)}
        textColor={useColorModeValue('white', 'black')}
        px={4}
      >
        <Flex
          h={16}
          alignItems={'center'}
          justifyContent={'space-between'}
        >
          <Button
            colorScheme={darkBg}
            _hover={{ bg: lightBg }}
            margin="10"
          >
            <a href="/">
              <StarIcon />
              Supergram
            </a>
          </Button>

          <Button
            colorScheme={darkBg}
            _hover={{ bg: lightBg }}
            margin="10"
          >
            <a href="/posts">Posts</a>
          </Button>

          <Flex alignItems={'center'}>
            <Stack
              direction={'row'}
              spacing={7}
            >
              <Button
                onClick={toggleColorMode}
                bg={darkBg}
                _hover={{ bg: lightBg }}
              >
                {colorMode === 'light' ? <MoonIcon /> : <SunIcon />}
              </Button>

              <Menu>
                <MenuButton
                  as={Button}
                  rounded={'full'}
                  variant={'link'}
                  cursor={'pointer'}
                  minW={0}
                >
                  <Avatar
                    size={'sm'}
                    src={'https://avatars.dicebear.com/api/male/username.svg'}
                  />
                </MenuButton>
                <MenuList alignItems={'center'}>
                  <br />
                  <Center>
                    <Avatar
                      size={'2xl'}
                      src={'https://avatars.dicebear.com/api/male/username.svg'}
                    />
                  </Center>
                  <br />
                  <Center>
                    <p>Username</p>
                  </Center>
                  <br />
                  <MenuDivider />
                  <MenuItem>Your Servers</MenuItem>
                  <MenuItem>Account Settings</MenuItem>
                  <MenuItem>Logout</MenuItem>
                </MenuList>
              </Menu>
            </Stack>
          </Flex>
        </Flex>
      </Box>
    </>
  );
};
