import {Component} from 'react';
import {authContext} from './user/useAuth';
import {Container} from 'react-bootstrap';



class PersonalLog extends Component {
  static contextType = authContext

  constructor(props) {
    super(props);
    this.state = { messageBox: "",logMessages : []};
    this.handleSubmit = this.handleSubmit.bind(this);
    this.fetchData = this.fetchData.bind(this);
    this.onInputChange = this.onInputChange.bind(this);
  }

  async handleSubmit(event) {
    event.preventDefault();
    const auth = this.context;
    const username = auth.username;
    await fetch("http://localhost:8080/api/users/log/add/byname", {
      method: "POST",
      headers: {
        "Accept": "application/json",
        "Content-type": "application/json"
      },
      body: JSON.stringify({
        messageBody: this.state.messageBox,
        writtenBy: username
      }),
    });
    this.setState({
      logMessages: this.state.logMessages,
      messageBox: '',
    });
    console.log(this.state);
    await this.fetchData();

  }

  async componentDidMount() {
    await this.fetchData();
  }

  async fetchData() {

    const auth = this.context;

    try {
      const response = await fetch("http://localhost:8080/api/users/log/name/" + (this.props.username || auth.username), {
        method: "GET",
        headers: {
          "accept": "application/json",
          'Content-Type': 'application/json'
        },
      });
      if(!response.ok){
        alert("Could not get personal log!");
        throw Error(response.statusText);
      }
      const json = await response.json();
      this.setState({ ...this.state, logMessages: json });
    } catch (error) {
      alert(error);
    }
  }

  onInputChange(event) {
    console.log(this.state.messageBox);
    this.setState({
      ...this.state,
      [event.target.name]: event.target.value
    });
  }

  render () {

    return (
        <>
          <MessageList messages={this.state.logMessages} />
          <MessageCompose messageBox={this.state.messageBox} onChange={this.onInputChange} onSubmit={this.handleSubmit} />
        </>
    )
  }
}

function MessageCompose(props) {

  const submit = props.onSubmit;
  const messageBox = props.messageBox;
  const onChange = props.onChange;

  return (
      <Container>
        <div className="row justify-content-center align-items-center">
          <div className="col-4">
            <form onSubmit={submit}>
              <label htmlFor="messageCompose">New message</label>
              <textarea className="form-control" name="messageBox" value={messageBox} onChange={onChange}
                       placeholder="Enter new message" aria-label="Enter new message" />

              <button className="btn btn-outline-success"
                      type="submit">Submit
              </button>
            </form>
          </div>
        </div>
      </Container>
  )
}

function MessageList(props) {

  const messages = props.messages || [];
  const messageList = messages.map((message) => <li className="list-group-item col-4">{message.messageBody}</li>)

  return (
      <ul className="row justify-content-center align-items-center list-group">{messageList}</ul>
  );
}

export default PersonalLog;