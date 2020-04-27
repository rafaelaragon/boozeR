import React from "react";
import "./Login.css";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import * as firebase from "firebase/app";
import "firebase/auth";
import firebaseConfig from "../../firebaseConfig";
import { Redirect } from "react-router-dom";
import SimpleCrypto from "simple-crypto-js";
import { toast } from "react-toastify";
import { loadUser, logout } from "../../Redux/Actions";
import { connect } from "react-redux";

//Firebase
const firebaseApp = firebase.initializeApp(firebaseConfig);
const firebaseAppAuth = firebaseApp.auth();

//SimpleCrypto
var secretKey = "Tragabuche";
var simpleCrypto = new SimpleCrypto(secretKey);

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
            await that.props.loadUser(uid);
            const isAdmin = that.props.user.isAdmin.BOOL;
            //If the user is admin redirect to Home, otherwise sign out
            isAdmin
              ? that.setState({ hasAccess: true })
              : firebase
                  .auth()
                  .signOut()
                  .then(toast.error("❌ No tienes permisos para entrar"))
                  .then(that.props.logout())
                  .catch(function (error) {
                    console.log(error.message);
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
                autoComplete="off"
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
          <Button variant="danger" onClick={this.login}>
            Login
          </Button>
        </div>
      );
  }
}

//Redux
function mapState(state) {
  return {
    user: state.UserReducer.user,
  };
}

const mapDispatch = { loadUser, logout };

export default connect(mapState, mapDispatch)(Login);
