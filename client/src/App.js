
import './App.css';
import {Navigate, Route, Routes} from 'react-router-dom';
import React from 'react';
import CreateUserComponent from './components/user/CreateUserComponent';
import IntroPage from './components/IntroPage';
import NavigationBar from './components/NavigationBar';
import PersonalLog from './components/PersonalLog';
import {AuthProvider} from './components/user/useAuth';
import {LoginComponent} from './components/user/LoginComponent';
import ShowOtherLog from './components/ShowOtherLog';
import ShowMessage from './components/message/ShowMessage';
import SendMessage from './components/message/SendMessage';
import useAuth from './components/user/useAuth';
import Whiteboard from './components/whiteboard/Whiteboard';

function App() {
  return (
    <div className="App">
      <AuthProvider>
        <Routes>
          <Route path="/" element={<NavigationBar />}>
            <Route index element={<IntroPage />} />
            <Route path="login" element={<LoginComponent />} />
            <Route path="search" element={<ShowOtherLog />} />
            <Route path="personal_log" element={<RequireAuth><PersonalLog/></RequireAuth>} />
            <Route path="register" element={<CreateUserComponent />} />
            <Route path="messages" element={<RequireAuth><ShowMessage /></RequireAuth>} />
            <Route path="send_message" element={<RequireAuth><SendMessage /></RequireAuth>} />
            <Route path="whiteboard" element={<RequireAuth><Whiteboard/></RequireAuth>} />
          </Route>
        </Routes>
      </AuthProvider>
    </div>
  );
}

function RequireAuth({ children }) {
  const { authed } = useAuth();

  return authed === true
      ? children
      : <Navigate to="/login" replace />;
}

export default App;
