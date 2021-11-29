import React, { Component } from 'react';
import {Button, Form, FormControl, FormGroup, FormLabel} from 'react-bootstrap';

class CreateUserComponent extends Component {

  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: '',
      firstName: '',
      lastName: '',
      email: ''
    }
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(event) {
    event.preventDefault();
    this.setState({
      ...this.state,
      [event.target.name]: event.target.value
    });
  };

  async onSubmit(event) {
    event.preventDefault();
    const myObj = JSON.stringify(this.state);

    const response = await fetch("http://localhost:8080/api/users", {
      method: "POST",
      headers: {
        "accept": "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      },
      body: myObj
    });

    if(!response.ok) {
      alert("Could not create user!");
      throw new Error("User creation failed!")
    }

    this.setState({
      username: '',
      password: '',
      firstName: '',
      lastName: '',
      email: ''
    });
    console.log(this.state);
    alert("User created successfully!");
  }

  render(){
    return(
        <Form className="input-group-sm" onSubmit={this.onSubmit}>
          <div class="d-flex align-items-center justify-content-center container-md flex-column">
            <FormGroup className="w-25 mb-3" controlId="formBasicUsername">
              <FormLabel>Username:</FormLabel>
              <FormControl onChange={this.onChange} value={this.state.username} type="username" name="username" placeholder="Enter username" />
            </FormGroup>

            <FormGroup className="w-25 mb-3" controlId="formBasicPassword">
              <FormLabel>Password:</FormLabel>
              <FormControl onChange={this.onChange} value={this.state.password} type="password" name="password" placeholder="Enter password" />
            </FormGroup>

            <FormGroup className="w-25 mb-3" controlId="formBasicFirstName">
              <FormLabel>First name:</FormLabel>
              <FormControl onChange={this.onChange} value={this.state.firstName} type="text" name="firstName" placeholder="Enter your first name" />
            </FormGroup>

            <FormGroup className="w-25 mb-3" controlId="formBasicLastName">
              <FormLabel>Last name:</FormLabel>
              <FormControl onChange={this.onChange} value={this.state.lastName} type="text" name="lastName" placeholder="Enter your last name" />
            </FormGroup>

            <FormGroup className="w-25 mb-3" controlId="formBasicEmail">
              <FormLabel>Email:</FormLabel>
              <FormControl onChange={this.onChange} value={this.state.email} type="email" name="email" placeholder="Enter your email" />
            </FormGroup>

            <Button variant="primary" type="submit">
              Submit
            </Button>
          </div>
        </Form>
    )
  }

}

export default CreateUserComponent;