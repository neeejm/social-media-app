import { MoonIcon, SunIcon } from '@chakra-ui/icons';
import {
  Button,
  Flex,
  Image,
  Link,
  Spacer,
  useColorMode,
  useDisclosure
} from '@chakra-ui/react';
import brandImg from '../../assets/imgs/punch.png';

export const Navbar = () => {
  const { colorMode, toggleColorMode } = useColorMode();
  const { isOpen, onOpen, onClose } = useDisclosure();

  let darkColor = 'teal.500';
  let lightColor = 'teal.400';
  let darkBg = colorMode === 'light' ? darkColor : lightColor;
  let lightBg = colorMode === 'light' ? lightColor : darkColor;

  return (
    <>
      <Flex padding={5}>
        <Button
          colorScheme={darkBg}
          _hover={{ bg: lightBg }}
        >
          <Link href="/">
            <Image
              src={brandImg}
              h={8}
              w={8}
            />
          </Link>
        </Button>
        <Spacer />
        <Button
          onClick={toggleColorMode}
          bg={darkBg}
          _hover={{ bg: lightBg }}
        >
          {colorMode === 'light' ? <MoonIcon /> : <SunIcon />}
        </Button>
      </Flex>
    </>
  );
};
