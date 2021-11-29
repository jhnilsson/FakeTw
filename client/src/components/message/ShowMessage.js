import {Component} from 'react';
import {authContext} from '../user/useAuth';
import {Container} from 'react-bootstrap';

class ShowMessage extends Component {

  static contextType = authContext

  constructor(props){
    super(props);
    this.state = {
      messages: [],
    }
  }

  async componentDidMount() {

    const auth = this.context
    try {
      const response = await fetch(
          "http://localhost:8080/api/users/messages/name/" + auth.username, {
            method: "GET",
            headers: {
              "Accept": "application/json"
            }
          });

      if(!response.ok){
        alert("Could not get messages!");
        throw Error(response.statusText);
      }

      const json = await response.json();
      this.setState({messages: json});

    } catch (error) {
      alert(error);
    }
  }

  render() {
    return(
        <Container>
          <MessageList messages={this.state.messages}/>
        </Container>
    )
  }
}

function MessageList(props) {

  const messages = props.messages || [];
  const messageList = messages.map((message) =>
      <>
        <li className="list-group-item col-4">
          {message.messageBody}
        </li>
      </>)

  return (
      <ul className="row justify-content-center align-items-center list-group">{messageList}</ul>
  );
}

export default ShowMessage;