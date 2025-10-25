import axios from 'axios';

export const apiClient = axios.create({
  baseURL: import.meta.env.VITE_BASE_API_URL
});

apiClient.interceptors.response.use(
  response => response,
  error => {
    const errorMessage = error.response
      ? error.response.data.message
      : '일시적인 오류입니다. 잠시 후 다시 시도해 주십시오.';
    alert(errorMessage);
    console.error(errorMessage);

    return Promise.reject(error);
  }
);
