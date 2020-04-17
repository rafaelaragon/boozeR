import React from "react";
import "./Home.css";
import Button from "react-bootstrap/Button";
import Axios from "axios";
import { Link } from "react-router-dom";
import { FaPlus, FaPen, FaTrash } from "react-icons/fa";
import Loading from "../../components/Loading/Loading";
import Header from "../../components/Header/Header";

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = { drinks: null };
  }

  getDrinks = async () => {
    await Axios.get(
      `https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrinks`
    ).then((res) => {
      const drinks = res.data;
      let key = 0;
      for (key in drinks) key++;
      this.setState({ drinks: drinks });
      console.log("Home -> getDrinks -> drinks", key);
      console.log(drinks);
      let response;
      return response;
    });
    this.setState({ isLoaded: true });
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
    const drinks = this.state.drinks;
    let key = 0;
    let cards = [];
    for (key in drinks) key++;
    for (let i = 0; i < key; i++) {
      const name = drinks[i].name.S;
      let image = !!drinks[i].image ? drinks[i].image.S : "";
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
    if (!this.state.isLoaded) return <Loading />;
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

export default Home;
