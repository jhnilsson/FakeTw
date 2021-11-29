import * as React from 'react';

export const authContext = React.createContext('Unknown');

function useAuth(){
  const[authed, setAuthed] = React.useState(false);
  const[username, setUsername] = React.useState("");

  return {
    authed,
    username,
    login(username, password) {
      return new Promise((resolve, reject) => {
        fetch("http://localhost:8080/api/users/login", {
          method: "POST",
          headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            username: username,
            password: password || ''
          })
        })
        .then((r) => {
          if(!r.ok){
            alert("Login attempt failed!");
            reject();
          } else {
            setAuthed(true);
            setUsername(username);
            resolve();
          }
          return r.json();
        })
        .then((r) => {
          console.log(r);
        }).catch(() => {
          alert("Login attempt failed!");
          reject();
        });
      })
    },
    logout() {
      return new Promise((res) => {
        setAuthed(false);
        setUsername("");
        res();
      });
    }
  }
}

export function AuthProvider({children}) {
  const auth = useAuth();

  return(
      <authContext.Provider value={auth}>
        {children}
      </authContext.Provider>
  )
}

export default function AuthConsumer() {
  return React.useContext(authContext);
}