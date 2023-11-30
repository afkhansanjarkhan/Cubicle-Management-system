import React from 'react';
import { registerRootComponent } from 'expo';
import IndexApp from './Index';
import { Store } from './store/store';
import { ThemeProvider } from 'styled-components';
import { ToastProvider } from 'react-native-styled-toast';
import FlashMessage from "react-native-flash-message";

const theme = {
  main: 'mediumseagreen',
};

export default function App() {
  return (
    <Store>
      <ThemeProvider theme={theme}>
        <FlashMessage position="top" />
        <IndexApp />
      </ThemeProvider>
    </Store>
  );
}

registerRootComponent(App);
