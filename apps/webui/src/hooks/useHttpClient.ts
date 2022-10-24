import axios from 'axios';
import { useEffect, useState } from 'react';

const useAxiosFetch = <T>(
  url: string,
  method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE'
): { data: T | never[]; error: string | null; isLoading: boolean } => {
  const [data, setData] = useState<T | never[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();

    const fetchData = async (url: string) => {
      setIsLoading(true);
      try {
        const response = await axios({
          method: method,
          url: url,
          signal: controller.signal
        });
        if (isMounted) {
          setData(response.data);
          setError(null);
        }
      } catch (err: any) {
        if (isMounted) {
          setError(err.message);
          setData([]);
        }
      } finally {
        isMounted && setIsLoading(false);
      }
    };

    fetchData(url);

    const cleanUp = () => {
      isMounted = false;
      controller.abort();
    };

    return cleanUp;
  }, [url]);

  return { data, error, isLoading };
};

export default useAxiosFetch;
