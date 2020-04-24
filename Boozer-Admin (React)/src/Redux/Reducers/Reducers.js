import { combineReducers } from "redux";
import UserReducer from "./UserReducer";
import DrinksReducer from "./DrinksReducer";
import DrinkReducer from "./DrinkReducer";

export default combineReducers({ UserReducer, DrinksReducer, DrinkReducer });
