import React from "react";
import "./Drink.css";
import Axios from "axios";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import { Link, Redirect } from "react-router-dom";
import Button from "react-bootstrap/Button";
import { FaArrowLeft, FaPlus } from "react-icons/fa";
import { TYPES } from "../../Consts";
import Loading from "../../components/Loading/Loading";
import Header from "../../components/Header/Header";

class Drink extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: this.props.match.params.drinkId,
      details: null,
    };
  }

  getDrink = async () => {
    await Axios.get(
      `https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrink?name=` +
        this.state.name
    ).then((res) => {
      const drink = res.data.Item;
      console.log(drink);
      this.setState({
        details: drink.details.S,
        editDetails: drink.details.S,
        price: drink.price.N,
        editPrice: drink.price.N,
        image: drink.image.S,
        editImage: drink.image.S,
        type: drink.type.S,
        editType: drink.type.S,
        graduation: drink.graduation.N,
        editGraduation: drink.graduation.N,
      });
    });
    await this.setState({ isLoaded: true });
  };

  //Store info of edited drink
  setType = (event) => {
    this.setState({ editType: event.target.value });
  };
  setPrice = (event) => {
    this.setState({ editPrice: event.target.value });
  };
  setGraduation = (event) => {
    this.setState({ editGraduation: event.target.value });
  };
  setDetails = (event) => {
    this.setState({ editDetails: event.target.value });
  };
  setImage = (event) => {
    this.setState({ editImage: event.target.value });
  };

  editDrink = async () => {
    const {
      name,
      editType,
      editPrice,
      editGraduation,
      editDetails,
      editImage,
    } = this.state;

    await Axios.get(
      `https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/createBoozerDrink?name=` +
        name +
        `&type=` +
        editType +
        `&price=` +
        editPrice +
        `&vol=` +
        editGraduation +
        `&det=` +
        editDetails +
        `&url=` +
        editImage
    );
    await this.setState({ redirect: true });
  };

  componentDidMount() {
    this.getDrink();
  }

  render() {
    const { name, details, price, image, type, graduation } = this.state;
    if (!this.state.isLoaded) return <Loading />;
    else if (this.state.redirect) {
      return <Redirect to="/drinks" />;
    } else {
      return (
        <div className="Drink">
          <Header/>
          <div id="return">
            <Link to="/drinks">
              <Button variant="outline-danger" size="lg" block>
                <FaArrowLeft />
              </Button>
            </Link>
          </div>
          {/* Details of selected drink */}
          <img src={image} alt={name}></img>
          <h1>{name}</h1>
          <h2>{type}</h2>
          <h3>
            <span>{price} €</span>
            <span>{graduation} %</span>
          </h3>
          {!!details && details !== "none" ? <h4>{details}</h4> : ""}

          <Form>
            <Form.Group controlId="editDrinkForm">
              <Form.Label>Tipo</Form.Label>
              <Form.Control
                as="select"
                placeholder="Type of alcohol (e.g. Brandy)"
                onChange={this.setType}
              >
                {TYPES.map((type) =>
                  //If an option matches with the drink, select it by default, otherwise don't
                  type === this.state.type ? (
                    <option selected="selected">{type}</option>
                  ) : (
                    <option>{type}</option>
                  )
                )}
              </Form.Control>
              <Form.Row>
                <Col id="1st">
                  <Form.Label className="inline">Precio (€)</Form.Label>
                  <Form.Control
                    type="number"
                    min="0"
                    placeholder="€"
                    className="number"
                    defaultValue={price}
                    onChange={this.setPrice}
                  />
                </Col>
                <Col>
                  <Form.Label className="inline">Vol (%)</Form.Label>
                  <Form.Control
                    type="number"
                    min="0"
                    max="100"
                    placeholder="%"
                    className="number"
                    defaultValue={graduation}
                    onChange={this.setGraduation}
                  />
                </Col>
              </Form.Row>
              <Form.Label>Detalles</Form.Label>
              <Form.Control
                as="textarea"
                placeholder="Useful info"
                defaultValue={details !== "none" ? details : ""}
                onChange={this.setDetails}
              />
              <Form.Label>Url</Form.Label>
              <Form.Control
                placeholder="Url of the drink"
                type="url"
                defaultValue={image}
                onChange={this.setImage}
              />
            </Form.Group>
          </Form>
          <div id="edit">
            <Button
              variant="outline-success"
              size="lg"
              block
              onClick={this.editDrink}
            >
              <FaPlus />
            </Button>
          </div>
        </div>
      );
    }
  }
}

export default Drink;
