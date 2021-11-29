import {Component} from 'react';
import {Container, Row} from 'react-bootstrap';

class ShowOtherLog extends Component {

  constructor(props) {
    super(props);
    this.handleSearch = this.handleSearch.bind(this);
    this.state = {
      logMessages: [],
    }
  }

  async handleSearch(event) {
    event.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/users/log/name/"
          + event.target.username.value, {
        method: "GET",
        headers: {
          "accept": "application/json"
        }
      });
      if(!response.ok){
        this.setState({error: true});
        throw new Error(response.statusText);
      }
      const json = await response.json();
      console.log(json);
      this.setState({
        logMessages: json,
        userName: event.target.username.value,
        error: false
      });
    } catch(error) {
      console.log(error);
    }

  }

  render() {

    const messages = this.state.logMessages || [];
    const messageList = messages.map((message) => <li className="list-group-item col-4" key={message.id}>{message.messageBody}</li>);

    return (
        <>
          <Container>
            { this.state.error ? (<>No such user! Please try again</>) :
                (
              <>{this.state.userName ? (<>Personal log of {this.state.userName }</>) : (<>Please search for a username</>)}
              <ul className="row justify-content-center align-items-center list-group">{messageList}</ul></>)
            }
          </Container>
          <Container>
            <div class="row justify-content-center">
              <div class="col-4">
                <form onSubmit={this.handleSearch}>
                  <input className="form-control" type="search" name="username"
                         placeholder="Search" aria-label="Search" />
                  <button className="btn btn-outline-success"
                          type="submit">Search user
                  </button>
                </form>
              </div>
            </div>
          </Container>
        </>
    )
  }

}

export default ShowOtherLog;