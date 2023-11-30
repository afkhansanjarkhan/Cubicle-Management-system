import React, { useReducer } from 'react';
import loginReducer from './loginReducer';
import { Context } from '../context';
import initialState from './globalState';



const combineReducers = (reducers: any) => {
  return (state: any, action: string) => {
    const tempState = { ...state };
    Object.keys(reducers).forEach((key) => {
      tempState[key] = reducers[key](tempState[key], action);
    });
    return tempState;
  };
};

export const reducer = combineReducers({
  login: loginReducer,
});


export const Store = ({ children }: any) => {
  const [state, dispatch] = useReducer<any>(reducer, initialState);

  return (
    <Context.Provider value={{ state, dispatch }}>{children}</Context.Provider>
  );
};

export default { Context, Store };