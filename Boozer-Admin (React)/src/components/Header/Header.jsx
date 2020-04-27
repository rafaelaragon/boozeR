import React from "react";
import "./Header.css";
import * as firebase from "firebase/app";
import "firebase/auth";
import { Redirect } from "react-router-dom";
import { logout, loadUser } from "../../Redux/Actions";
import { connect } from "react-redux";
import Dropdown from "react-bootstrap/Dropdown";

class Header extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      logout: false,
    };
  }
  logout = async () => {
    var that = this;
    await firebase.auth().onAuthStateChanged(function (user) {
      if (user) {
        firebase
          .auth()
          .signOut()
          .then(that.props.logout())
          .catch(function (error) {
            console.log(error.message);
          });
      }
    });
    this.setState({ logout: true });
  };
  render() {
    const { user } = this.props;
    if (this.state.logout) return <Redirect to="/login" />;
    else
      return (
        <div className="Header">
          <Dropdown>
            <Dropdown.Toggle variant="danger" id="user">
              {user.User.S}
            </Dropdown.Toggle>
            <Dropdown.Menu className="custom">
              <Dropdown.Item disabled id="disabled">
                {user.Email.S}
              </Dropdown.Item>
              <Dropdown.Item onClick={this.logout}>Cerrar Sesión</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
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

export default connect(mapState, mapDispatch)(Header);
