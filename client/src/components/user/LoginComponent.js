import { useNavigate } from 'react-router-dom';
import useAuth from './useAuth';

export const LoginComponent = () => {
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleLogin = (event) => {
    event.preventDefault();
    login(event.target.username.value, event.target.password.value).then(() => {
      console.log("Promise resolved!");
      navigate('/personal_log');
    }).catch(() => {
      console.error("Promise rejected!");
      navigate('/login')
    });
  }

  return (
      <div className="container-md">
        <form className="form-group" onSubmit={handleLogin}>
          <div className=" d-flex align-items-center justify-content-center">
            <div className=" w-25 mb-3">
              <label htmlFor="messageCompose">Enter Username: </label>
              <input type="text" className="form-control" id="username"
                     name="username" placeholder="Enter username"/>
              <label htmlFor="messageCompose">Enter Password: </label>
              <input type="password" className="form-control" id="password"
                     name="password" placeholder="Enter password"/>
            </div>
          </div>
          <button type="submit" className="btn btn-primary">Submit</button>
        </form>
      </div>
  );
};