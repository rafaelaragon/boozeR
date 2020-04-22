import React from "react";
import "./Login.css";
import Button from "react-bootstrap/Button";
import Axios from "axios";
import Form from "react-bootstrap/Form";
import withFirebaseAuth from "react-with-firebase-auth";
import * as firebase from "firebase/app";
import "firebase/auth";
import firebaseConfig from "../../firebaseConfig";
import { Redirect } from "react-router-dom";
import SimpleCrypto from "simple-crypto-js";
import { toast } from "react-toastify";

//Firebase
const firebaseApp = firebase.initializeApp(firebaseConfig);
const firebaseAppAuth = firebaseApp.auth();

//SimpleCrypto
var _secretKey = "Tragabuche";
var simpleCrypto = new SimpleCrypto(_secretKey);

class Login extends React.Component {
  state = { hasAccess: false };

  //Set email into state
  setMail = (event) => {
    this.setState({ email: event.target.value });
  };
  //Cypher password,then set it into state
  setPassword = (event) => {
    const encPass = simpleCrypto.encrypt(event.target.value);
    this.setState({ password: encPass });
  };

  login = async () => {
    const { email, password } = this.state;
    var that = this;
    //Check if everything is correct
    !this.state.email
      ? toast.error("❌ Escribe tu correo")
      : !this.state.password
      ? toast.error("❌ Escribe tu contraseña")
      : //Login in firebase using decrypted password
        firebaseAppAuth
          .signInWithEmailAndPassword(email, simpleCrypto.decrypt(password))
          //Check if the user is admin or not
          .then(async function (user) {
            let uid = user.user.uid;
            await Axios.get(
              `https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerUser?uid=` +
                uid
            ).then((res) => {
              let user = res.data.Item;
              const isAdmin = user.isAdmin.BOOL;
              //If the user is admin redirect to Home, otherwise sign out
              isAdmin
                ? that.setState({ hasAccess: true })
                : firebase
                    .auth()
                    .signOut()
                    .then(
                      toast.error("❌ No tienes permisos para entrar")
                    )
                    .catch(function (error) {
                      console.log(error.message);
                    });
            });
          })
          .catch(function (error) {
            console.log(error.message);
            toast.error("❌ Correo o contraseña incorrectos");
          });
  };

  render() {
    //Once authenticated, redirect to the Home Page
    if (this.state.hasAccess) return <Redirect to="/drinks" />;
    else
      return (
        <div className="Login">
          <h1>BoozeR-Admin</h1>
          <Form>
            <Form.Group>
              <Form.Label>Email</Form.Label>
              <Form.Control
                id="email"
                type="email"
                placeholder="JohnDoe@gmail.com"
                autocomplete="off"
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
