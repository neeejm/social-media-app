import axios from 'axios';
import { useState } from 'react';

interface RequestConfig<T, R> {
  url: string;
  method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';
  body?: R;
  onSuccess: (data: T, status: number) => void;
}

export const useHttpClient = <T, R = any>(): {
  doRequest: (config: RequestConfig<T, R>) => Promise<void>;
  error: string | null;
  isLoading: boolean;
} => {
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const doRequest = async ({
    url,
    method,
    body,
    onSuccess
  }: RequestConfig<T, R>) => {
    setIsLoading(true);
    try {
      const response = await axios({
        method: method,
        url: url,
        data: body ? body : null
      });

      onSuccess(response.data, response.status);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  return { doRequest, error, isLoading };
};
