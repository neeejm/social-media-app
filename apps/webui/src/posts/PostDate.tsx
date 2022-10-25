import { Tag } from '@chakra-ui/react';
import { color } from '../common/types/color.type';

interface Props {
  text: string;
  date: Date;
  color?: color;
}

export const PostDate = ({ text, date, color = 'telegram' }: Props) => {
  return (
    <>
      <Tag
        size="md"
        variant="solid"
        colorScheme={color}
      >
        {text}: {date.toString()}
      </Tag>
    </>
  );
};
