import * as React from 'react';
import { useForm } from 'react-hook-form';
import LoginComponent from '../components/LoginComponent';


export default function LoginScreen({ onSubmit }: { onSubmit: any }) {
  return (
    <LoginComponent onSubmit={onSubmit} />
  );
}


