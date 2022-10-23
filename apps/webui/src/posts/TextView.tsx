import { Dispatch, forwardRef, SetStateAction } from 'react';

interface Props {
  title: string;
  value: string;
  editable: boolean;
  setEditable: Dispatch<SetStateAction<boolean>>;
}

const TextView = forwardRef<HTMLInputElement, Props>(
  ({ title, value, editable, setEditable }, ref) => {
    return (
      <>
        <h4>{title}: </h4>
        {!editable ? (
          <p
            onDoubleClick={() => {
              setEditable(true);
            }}
          >
            {value}
          </p>
        ) : (
          <input
            type="text"
            defaultValue={value}
            ref={ref}
          />
        )}
      </>
    );
  }
);

export default TextView;
