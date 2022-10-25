import { Badge, Input, Text } from '@chakra-ui/react';
import { Dispatch, forwardRef, SetStateAction } from 'react';

interface Props {
  value: string;
  editable: boolean;
  setEditable: Dispatch<SetStateAction<boolean>>;
}

export const PostTitle = forwardRef<HTMLInputElement, Props>(
  ({ value, editable, setEditable }, ref) => {
    return (
      <>
        {!editable ? (
          <Text
            fontWeight="bold"
            onDoubleClick={() => {
              setEditable(true);
            }}
          >
            {value}
            <Badge
              ml="1"
              colorScheme="green"
            >
              New
            </Badge>
          </Text>
        ) : (
          <Input
            type="text"
            size="sm"
            defaultValue={value}
            ref={ref}
          />
        )}
      </>
    );
  }
);
