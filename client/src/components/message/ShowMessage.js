import {Component} from 'react';
import {authContext} from '../user/useAuth';
import {Col, Container, Row, Table} from 'react-bootstrap';

class ShowMessage extends Component {

  static contextType = authContext

  constructor(props){
    super(props);
    this.state = {
      messages: [],
    }
  }

  componentDidMount() {

    const auth = this.context

    fetch(
      "http://localhost:8080/api/users/messages/name/" + auth.username, {
        method: "GET",
        headers: {
          "Accept": "application/json",
          'Content-Type': 'application/json'
        }
      }).then(r => {
        if(!r.ok){
          alert("Could not get messages!");
          throw Error(r.statusText);
        }
        return r.json();
    }).then(r => {
      this.setState({messages: r});
    });
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
  console.log(messages.map((message) => console.log(message.writtenBy)));
  let userMap = new Map();
  messages.map((message) => {
    if(message.writtenBy.username != null){
      userMap.set(message.writtenBy.id, message.writtenBy);
    }
  });

  const messageList = messages.map((message) =>
      <>
        <tr>
          <td>
            {message.messageBody}
          </td>
          <td>
            { message.writtenBy.username || userMap.get(message.writtenBy).username }
          </td>
        </tr>
      </>)

  return (
      <Container>
        <Row  className="justify-content-center align-items-center">
          <Col md={6} className="mx-auto">
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Message</th>
                  <th>Sender</th>
                </tr>
              </thead>
              <tbody>
                {messageList}
              </tbody>
            </Table>
          </Col>
        </Row>
      </Container>
  );
}

export default ShowMessage;