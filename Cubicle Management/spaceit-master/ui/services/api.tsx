// @flow

import axios from 'axios';



export const handleResponseError = (e: any) => {
  if (e instanceof axios.Cancel) return Promise.reject(e);
}

let instance : any;
const initApi = () => {
  if (instance) {
    return instance;
  }

  instance = axios.create({
    baseURL: process.env.SERVICE_URL_CONTEXT || (process.env.NODE_ENV === 'development' ? '' : '/modeln'),
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Pragma: 'no-cache',
    },
  });

  instance.interceptors.response.use((e:any) => Promise.resolve(e), handleResponseError);
  return instance;
};

export default initApi();
