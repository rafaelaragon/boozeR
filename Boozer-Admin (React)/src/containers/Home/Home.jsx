import React from "react";
import "./Home.css";
import Button from "react-bootstrap/Button";
import Axios from "axios";
import { Link } from "react-router-dom";
import { FaPlus, FaPen, FaTrash } from "react-icons/fa";
import Loading from "../../components/Loading/Loading";
import Header from "../../components/Header/Header";
import { connect } from "react-redux";
import { loadUser, loadDrinks } from "../../Redux/Actions";
import { Redirect } from "react-router-dom";

class Home extends React.Component {
  getDrinks = async () => {
    await this.props.loadDrinks();
  };

  deleteDrink = async (drinkName) => {
    await Axios.get(
      `https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/deleteBoozerDrink?name=` +
        drinkName
    );
    console.log(drinkName + "deleted");
    this.getDrinks();
  };

  getCards = () => {
    const drinks = this.props.drinks;
    let key = 0;
    let cards = [];
    for (key in drinks) key++;
    for (let i = 0; i < key; i++) {
      const name = drinks[i].name.S;
      let image = !!drinks[i].image ? drinks[i].image.S : "";
      //If the url is bad formatted or none is given, use a default image
      image = image.includes("https://boozerdrinks.s3.amazonaws.com/")
        ? image
        : "https://boozerdrinks.s3.amazonaws.com/generic.png";
      cards.push(
        <li key={name}>
          <img src={image} alt={name} />
          <h4>{name}</h4>
          <Link to={"/drink/" + name}>
            <div id="edit">{<FaPen />}</div>
          </Link>
          <div id="delete">
            {<FaTrash onClick={() => this.deleteDrink(name)} />}
          </div>
        </li>
      );
    }
    return cards;
  };

  componentDidMount() {
    this.getDrinks();
  }

  render() {
    // eslint-disable-next-line no-unused-vars
    const { user, drinks, areDrinksLoaded } = this.props;
    if (!user.isAdmin) return <Redirect to="/login" />;
    else if (!areDrinksLoaded) return <Loading />;
    else {
      return (
        <div className="Home">
          <Header />
          <div id="addDrink">
            <Link to="/new">
              <Button variant="outline-success" size="lg" block>
                <FaPlus />
              </Button>
            </Link>
          </div>
          <ul>{this.getCards()}</ul>
        </div>
      );
    }
  }
}

//Redux
function mapState(state) {
  return {
    user: state.UserReducer.user,
    drinks: state.DrinksReducer.drinks,
    areDrinksLoaded: state.DrinksReducer.isLoaded,
  };
}

const mapDispatch = { loadUser, loadDrinks };

export default connect(mapState, mapDispatch)(Home);
