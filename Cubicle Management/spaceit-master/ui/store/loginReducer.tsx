import initialState from "./globalState";

const LoginReducer = (state: any, action: any) => {
  switch (action.type) {
    case 'LOGIN':
      return {
        ...state,
        user: action.data,
      };

    case 'LOGOUT':
      console.log(initialState);
      return initialState;
    default:
      return state;
  }
};

export default LoginReducer;
