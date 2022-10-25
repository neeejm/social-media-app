import { Tag } from '@chakra-ui/react';
import { color } from '../common/types/color.type';

interface Props {
  views: number;
  color?: color;
}

export const PostViews = ({ views, color = 'teal' }: Props) => {
  return (
    <>
      <Tag
        size="md"
        variant="solid"
        colorScheme={color}
      >
        {views}
      </Tag>
    </>
  );
};
