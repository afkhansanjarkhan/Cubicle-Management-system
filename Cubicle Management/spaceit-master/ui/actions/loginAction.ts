export const loginAction = (data: any) => {
  return {
    type: 'LOGIN',
    data,
  };
};

export const logoutAction = () => {
  localStorage.removeItem("token");
  return {
    type: 'LOGOUT',
  };
};
