import * as firebase from "firebase/app";
import { Redirect } from "react-router-dom";

export function isAuthenticated() {
  firebase.auth.onAuthStateChanged(function (user) {
    if (user) return true;
    else return false;
  });
}
