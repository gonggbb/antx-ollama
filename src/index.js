import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Independent from './Independent';
import Independent_v1 from './Independent_v1';
import reportWebVitals from './reportWebVitals';
import { XProvider } from '@ant-design/x';
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <XProvider theme={{ token: { colorPrimary: '#00b96b' } }}>
    <React.StrictMode>
      {/* <App /> */}
      {/* <Independent /> */}
      <Independent_v1 />
    </React.StrictMode>
  </XProvider>

);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
