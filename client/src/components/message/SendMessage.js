import {Component} from 'react';
import {authContext} from '../user/useAuth';
import {Container} from 'react-bootstrap';

class SendMessage extends Component {

  static contextType = authContext

  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.onInputChange = this.onInputChange.bind(this);
    this.state = {
      messageBody: '',
      recipient: ''
    }
  }

  async handleSubmit(event) {
    event.preventDefault();
    const auth = this.context

    const response = await fetch("http://localhost:8080/api/users/messages/add/byname", {
      method: "POST",
      headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        writtenBy: auth.username,
        messageBody: this.state.messageBody,
        sendTo: this.state.recipient
      })
    });

    if(!response.ok){
      alert("Could not send message!");
    } else {
      alert("Message sent!");
      this.setState({
        messageBody: '',
        recipient: ''
      });
    }
  }

  onInputChange(event) {
    console.log(this.state.messageBox);
    this.setState({
      ...this.state,
      [event.target.name]: event.target.value
    });
  }

  render() {
    return(
        <Container>
          <div className="row justify-content-center align-items-center">
            <div className="col-4">
              <form onSubmit={this.handleSubmit}>
                <label htmlFor="messageCompose">New message</label>
                <textarea className="form-control h-50" name="messageBody" value={this.state.messageBody}
                       onChange={this.onInputChange} placeholder="Enter new message"
                       aria-label="Enter new message"/>
                <input className="form-control w-50" type="text" name="recipient" value={this.state.recipient}
                       onChange={this.onInputChange} placeholder="Enter recipient"/>
                <button className="btn btn-outline-success"
                        type="submit">Submit
                </button>

              </form>
            </div>
          </div>
        </Container>
    )
  }


}

export default SendMessage;