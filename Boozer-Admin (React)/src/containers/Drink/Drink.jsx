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
import { connect } from "react-redux";
import { loadUser, loadDrink } from "../../Redux/Actions";

class Drink extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: this.props.match.params.drinkId,
      details: null,
    };
  }

  getDrink = async () => {
    await this.props.loadDrink(this.state.name);
    await this.setState({
      edType: this.props.drink.type,
      edPrice: this.props.drink.price,
      edGrad: this.props.drink.graduation,
      edDet: this.props.drink.details,
      edImg: this.props.drink.image,
    });
  };

  //Store info of edited drink
  setType = (event) => {
    this.setState({ edType: event.target.value });
  };
  setPrice = (event) => {
    this.setState({ edPrice: event.target.value });
  };
  setGraduation = (event) => {
    this.setState({ edGrad: event.target.value });
  };
  setDetails = (event) => {
    this.setState({ edDet: event.target.value });
  };
  setImage = (event) => {
    this.setState({ edIm: event.target.value });
  };

  editDrink = async () => {
    const { name, edType, edPrice, edGrad, edDet, edImg } = this.state;
    console.log("Drink -> editDrink -> edType", typeof edType);
    await Axios.get(
      `https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/createBoozerDrink?name=` +
        name +
        `&type=` +
        (typeof edType === "object" ? edType.S : edType) +
        `&price=` +
        (typeof edPrice === "object" ? edPrice.N : edPrice) +
        `&vol=` +
        (typeof edGrad === "object" ? edGrad.N : edGrad) +
        `&det=` +
        (typeof edDet === "object" ? edDet.S : edDet) +
        `&url=` +
        (typeof edImg === "object" ? edImg.S : edImg)
    );
    await this.setState({ redirect: true });
  };

  componentDidMount() {
    this.getDrink();
  }

  render() {
    const { drink, isDrinkLoaded } = this.props;
    const { name, details, price, image, type, graduation } = !!drink
      ? drink
      : "";
    if (!this.props.user.isAdmin) return <Redirect to="/login" />;
    else if (!isDrinkLoaded) return <Loading />;
    else if (this.state.redirect) {
      return <Redirect to="/drinks" />;
    } else {
      return (
        <div className="Drink">
          <Header />
          <div id="return">
            <Link to="/drinks">
              <Button variant="outline-danger" size="lg" block>
                <FaArrowLeft />
              </Button>
            </Link>
          </div>
          {/* Details of selected drink */}
          <img src={image.S} alt={name.S}></img>
          <h1>{name.S}</h1>
          <h2>{type.S}</h2>
          <h3>
            <span>{price.N} €</span>
            <span>{graduation.N} %</span>
          </h3>
          {!!details.S && details.S !== "none" ? <h4>{details.S}</h4> : ""}

          <Form>
            <Form.Group controlId="editDrinkForm">
              <Form.Label>Tipo</Form.Label>
              <Form.Control
                as="select"
                placeholder="Type of alcohol (e.g. Brandy)"
                defaultValue={type.S}
                onChange={this.setType}
              >
                {TYPES.map((type) => (
                  <option key={type}>{type}</option>
                ))}
              </Form.Control>
              <Form.Row>
                <Col id="1st">
                  <Form.Label className="inline">Precio (€)</Form.Label>
                  <Form.Control
                    type="number"
                    min="0"
                    placeholder="€"
                    className="number"
                    defaultValue={price.N}
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
                    defaultValue={graduation.N}
                    onChange={this.setGraduation}
                  />
                </Col>
              </Form.Row>
              <Form.Label>Detalles</Form.Label>
              <Form.Control
                as="textarea"
                placeholder="Useful info"
                defaultValue={details.S !== "none" ? details.S : ""}
                onChange={this.setDetails}
              />
              <Form.Label>Url</Form.Label>
              <Form.Control
                placeholder="Url of the drink"
                type="url"
                defaultValue={image.S}
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

//Redux
function mapState(state) {
  return {
    user: state.UserReducer.user,
    drink: state.DrinkReducer.drink,
    isDrinkLoaded: state.DrinkReducer.isDrinkLoaded,
  };
}

const mapDispatch = { loadUser, loadDrink };

export default connect(mapState, mapDispatch)(Drink);
