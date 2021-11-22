
import './App.css';
import {Route, Routes} from 'react-router-dom';
import React from 'react';
import CreateUserComponent from './components/user/CreateUserComponent';
import IntroPage from './components/IntroPage';
import NavigationBar from './components/NavigationBar';
import PersonalLog from './components/PersonalLog';
import {AuthProvider} from './components/user/useAuth';
import {LoginComponent} from './components/user/LoginComponent';
import ShowOtherLog from './components/ShowOtherLog';

function App() {
  return (
    <div className="App">
      <AuthProvider>
        <Routes>
          <Route path="/" element={<NavigationBar />}>
            <Route index element={<IntroPage />} />
            <Route path="login" element={<LoginComponent />} />
            <Route path="personal_log" element={<PersonalLog/>} />
            <Route path="register" element={<CreateUserComponent />} />
            <Route path="search" element={<ShowOtherLog />} />
          </Route>
        </Routes>
      </AuthProvider>
    </div>
  );
}

export default App;
