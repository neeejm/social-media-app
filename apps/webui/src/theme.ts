import { extendTheme } from '@chakra-ui/react';
import { mode } from '@chakra-ui/theme-tools';
const dark = '#1a202c';
const light = '#f0f0f0';

const theme = extendTheme({
  styles: {
    global: (props: any) => ({
      body: {
        bg: mode(light, dark)(props)
      }
    })
  }
});

export default theme;
