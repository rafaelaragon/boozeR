import { USER, LOGOUT } from "../Actions";

const initialState = {
  user: [],
};

export default function userReducer(state = initialState, action) {
  switch (action.type) {
    case USER:
      return {
        ...state,
        user: action.payload.user,
      };
    case LOGOUT:
      return {
        ...state,
        user: [],
      };
    default:
      return state;
  }
}
