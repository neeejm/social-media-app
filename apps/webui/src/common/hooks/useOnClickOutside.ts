import { RefObject, useEffect } from 'react';

const useOnClickOutside = <T extends HTMLElement>(
  ref: RefObject<T>,
  callback: () => void
) => {
  const handleClick = (e: any) => {
    if (ref.current && !ref.current.contains(e.target)) {
      callback();
    }
  };

  useEffect(() => {
    document.addEventListener('click', handleClick);

    return () => {
      document.removeEventListener('click', handleClick);
    };
  });
};

export default useOnClickOutside;
