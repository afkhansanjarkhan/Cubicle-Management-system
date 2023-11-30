import { StatusBar } from 'expo-status-bar';
import React, { useContext } from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import useCachedResources from './hooks/useCachedResources';
import useColorScheme from './hooks/useColorScheme';
import Navigation from './navigation';
import { DefaultTheme, Provider as PaperProvider } from 'react-native-paper';
import LoginService from './services/loginService';
import LoginScreen from './screens/LoginScreen';
import { Context } from './context';
import { loginAction, logoutAction } from './actions/loginAction';
import { showMessage, hideMessage } from "react-native-flash-message";
import SearchService from './services/searchService';

const loginService = new LoginService();

const theme = {
  ...DefaultTheme,
  roundness: 2,
  colors: {
    ...DefaultTheme.colors,
    primary: '#3498db',
    accent: '#f1c40f',
  },
};

export default function IndexApp() {
  const isLoadingComplete = useCachedResources();
  const colorScheme = useColorScheme();
  const { state, dispatch } = useContext(Context);

  const onSubmit =(data: { username: string; password: string }) => {
    loginService.login(data.username, data.password).then(user => {
      if (user.token) {
        dispatch(loginAction(user.token));
        localStorage.setItem("token",user.token);
      } else {
        const { payload: { errorMsg, errorCode } } = user;
        showMessage({
          message: errorCode,
          description: errorMsg,
          type: "danger",
        });
        dispatch(logoutAction());
        localStorage.clear();
      }
    }).catch(error => {
      dispatch(logoutAction());
    });
    if(localStorage.getItem("token")){
      loginService.bootstrap().then(data => {
        console.log("bootstrap is being called");
        localStorage.setItem("bootstrap",JSON.stringify(data));
      });
    }
  };

  if (!isLoadingComplete) {
    return null;
  }
  if (!state.login || !state.login.user) {
    return (
      <PaperProvider theme={theme}>
        <SafeAreaProvider>
          <LoginScreen onSubmit={onSubmit} />
        </SafeAreaProvider>
      </PaperProvider>
    );
  } else {
    return (
      <PaperProvider theme={theme}>
        <SafeAreaProvider>
          <Navigation colorScheme={colorScheme} />
          <StatusBar />
        </SafeAreaProvider>
      </PaperProvider>
    );
  }
}
