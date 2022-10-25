import { Textarea } from '@chakra-ui/react';
import { Dispatch, forwardRef, SetStateAction } from 'react';

interface Props {
  value: string;
  editable: boolean;
  setEditable: Dispatch<SetStateAction<boolean>>;
}

export const PostContent = forwardRef<HTMLTextAreaElement, Props>(
  ({ value, editable, setEditable }, ref) => {
    return (
      <>
        {!editable ? (
          <p
            onDoubleClick={() => {
              setEditable(true);
            }}
          >
            {value}
          </p>
        ) : (
          <Textarea
            defaultValue={value}
            ref={ref}
          />
        )}
      </>
    );
  }
);
