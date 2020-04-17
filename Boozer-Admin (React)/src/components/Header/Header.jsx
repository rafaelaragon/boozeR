import React from "react";
import "./Header.css";
import Button from "react-bootstrap/Button";
import * as firebase from "firebase/app";
import "firebase/auth";
import { Redirect } from "react-router-dom";

class Header extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      logout: false,
    };
  }
  logout = async () => {
    await firebase.auth().onAuthStateChanged(function (user) {
      if (user) {
        firebase
          .auth()
          .signOut()
          .catch(function (error) {
            console.log(error.message);
          });
      }
    });
    this.setState({ logout: true });
  };
  render() {
    if (this.state.logout) return <Redirect to="/login" />;
    else
      return (
        <div className="Header">
          <Button onClick={this.logout}>Cerrar Sesión</Button>
          <span>{this.state.email}</span>
        </div>
      );
  }
}

export default Header;
