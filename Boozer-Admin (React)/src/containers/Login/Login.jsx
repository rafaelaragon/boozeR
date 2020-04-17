import React from "react";
import "./Login.css";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import withFirebaseAuth from "react-with-firebase-auth";
import * as firebase from "firebase/app";
import "firebase/auth";
import firebaseConfig from "../../firebaseConfig";
import { Redirect } from "react-router-dom";

//Firebase
const firebaseApp = firebase.initializeApp(firebaseConfig);
const firebaseAppAuth = firebaseApp.auth();

class Login extends React.Component {
  
  /*await firebaseAppAuth.onAuthStateChanged(function (user) {
      if (user) isAuthenticated = true;
      console.log("Login -> checkAuth -> isAuthenticated", isAuthenticated);
    });
    */

  setMail = (event) => {
    this.setState({ email: event.target.value });
  };
  setPassword = (event) => {
    this.setState({ password: event.target.value });
  };

  login = async () => {
    const { email, password } = this.state;
    firebaseAppAuth
      .signInWithEmailAndPassword(email, password)
      .catch(function (error) {
        console.log(error.message);
      });
  };

  render() {
    if (!!this.props.user && this.props.user.email !== undefined)
      return <Redirect to="/drinks" />;
    else
      return (
        <div className="Login">
          <Form>
            <Form.Group>
              <Form.Label>Email</Form.Label>
              <Form.Control
                id="email"
                type="email"
                placeholder="JohnDoe@gmail.com"
                onChange={this.setMail}
              />
              <Form.Label>Contraseña</Form.Label>
              <Form.Control
                id="password"
                type="password"
                placeholder="********"
                onChange={this.setPassword}
              />
            </Form.Group>
          </Form>
          <Button onClick={this.login}>Login</Button>
        </div>
      );
  }
}

export default withFirebaseAuth({ firebaseAppAuth })(Login);
