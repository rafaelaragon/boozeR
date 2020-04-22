import React from "react";
import "./App.css";
import Home from "../../containers/Home/Home";
import Drink from "../../containers/Drink/Drink";
import New from "../../containers/New/New";
import Login from "../../containers/Login/Login";
import "bootstrap/dist/css/bootstrap.min.css";
import { Route, Redirect, BrowserRouter, Switch } from "react-router-dom";
import { withRouter } from "react-router";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

toast.configure({
  autoClose: 4000,
  position: "top-center"
});

class App extends React.Component {
  render() {
    return (
      <div className="App">
        <BrowserRouter key="router">
          <div>
            <Switch key="switch">
              <Route
                key="login"
                path="/login"
                exact
                component={Login}
                render={(props) => <Login {...props} isAuthed={true} />}
              />
              <Route key="drinks" path="/drinks" exact component={Home} />
              <Route key="drink" path="/drink/:drinkId" component={Drink} />
              <Route key="new" path="/new" exact component={New} />
              <Redirect from="*" to="/login" />
            </Switch>
          </div>
        </BrowserRouter>
      </div>
    );
  }
}

export default withRouter(App);
