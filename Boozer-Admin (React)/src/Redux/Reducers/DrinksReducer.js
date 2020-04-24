import { DRINKS } from "../Actions";

const initialState = {
  drinks: [],
  isLoaded: false,
};

export default function drinksReducer(state = initialState, action) {
  switch (action.type) {
    case DRINKS:
      return {
        ...state,
        drinks: action.payload.drinks,
        isLoaded: action.payload.isLoaded,
      };
    default:
      return state;
  }
}
