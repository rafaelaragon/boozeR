import { DRINK } from "../Actions";

const initialState = {
  drink: [],
  isDrinkLoaded: false,
};

export default function drinkReducer(state = initialState, action) {
  switch (action.type) {
    case DRINK:
      return {
        ...state,
        drink: action.payload.drink,
        isDrinkLoaded: action.payload.isDrinkLoaded,
      };
    default:
      return state;
  }
}
