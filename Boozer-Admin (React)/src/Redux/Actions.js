export const USER = "USER";
export const LOGOUT = "LOGOUT";
export const DRINKS = "DRINKS";
export const DRINK = "DRINKS";

//
export const User = (user) => ({
  type: USER,
  payload: { user },
});

export const Logout = () => ({
  type: LOGOUT,
  payload: {},
});

export const Drinks = (drinks, isLoaded) => ({
  type: DRINKS,
  payload: { drinks, isLoaded },
});

export const Drink = (drink, isDrinkLoaded) => ({
  type: DRINKS,
  payload: { drink, isDrinkLoaded },
});
//

//Store info of the admin
export function loadUser(uid) {
  return (dispatch) => {
    return fetch(
      "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerUser?uid=" +
        uid
    )
      .then((res) => res.json())
      .then((result) => {
        dispatch(User(result.Item));
      });
  };
}

//Delete info of the admin
export function logout() {
  return (dispatch) => {
    dispatch(Logout());
  };
}

//Store info of every drink
export function loadDrinks() {
  return (dispatch) => {
    return fetch(
      "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrinks"
    )
      .then((res) => res.json())
      .then((result) => {
        dispatch(Drinks(result, true));
      });
  };
}

//Store detailed info of a specific drink
export function loadDrink(name) {
  return (dispatch) => {
    return fetch(
      "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrink?name=" +
        name
    )
      .then((res) => res.json())
      .then((result) => {
        dispatch(Drink(result.Item, true));
      });
  };
}
