import React, {Component, useContext} from 'react';
import CreateUserComponent from './user/CreateUserComponent';
import ShowMessage from './message/ShowMessage';
import {authContext} from './user/useAuth';
import {Col, Container, Row, Table} from 'react-bootstrap';

class IntroPage extends Component {

  static contextType = authContext

  constructor(props) {
    super(props);
    this.onChange = this.onChange.bind(this);
  }

  onChange(event) {
    event.preventDefault();
    this.setState({
      ...this.state,
      [event.target.name]: event.target.value
    });
  };

  render() {

    const auth = this.context

    return (
        <div>
          {auth.authed ? (<ShowUserInfo />) : (<>Heyo</>)}
        </div>
    );
  }
}

class ShowUserInfo extends Component {

  static contextType = authContext

  constructor(props) {
    super(props);
    this.state = [];
  }

  async componentDidMount() {

    const auth = this.context;

    const response = await fetch(
        'http://localhost:8080/api/users/name/' + auth.username, {
          method: 'GET',
          headers: {
            'Accept': 'application/json',
          },
        });
    if (!response.ok) {
      throw new Error('Nu such user!');
    }
    const json = await response.json();
    this.setState(json);
  }

  render() {
    return (
        <Container>
          <div className="fs-2">
            <h2>Welcome!</h2>
            <Row>
              <Col>Username: </Col>
              <Col>{this.state.username}</Col>
            </Row>
            <Row>
              <Col>First Name: </Col>
              <Col>{this.state.firstName}</Col>
            </Row>
            <Row>
              <Col>Last Name: </Col>
              <Col>{this.state.lastName}</Col>
            </Row>
            <Row>
              <Col>Email: </Col>
              <Col>{this.state.email}</Col>
            </Row>
          </div>
        </Container>
    );
  }
}

export default IntroPage;