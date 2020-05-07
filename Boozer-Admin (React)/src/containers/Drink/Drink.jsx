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
import { connect } from "react-redux";
import { loadUser, loadDrink } from "../../Redux/Actions";
import Header from "../../components/Header/Header";

class Drink extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: this.props.match.params.drinkId,
    };
  }

  //Get info of the drink, and store it
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

  //Store info of the edited drink
  setEditInfo = (param, event) => {
    switch (param) {
      case "type":
        this.setState({ edType: event.target.value });
        break;
      case "price":
        this.setState({ edPrice: event.target.value });
        break;
      case "graduation":
        this.setState({ edGrad: event.target.value });
        break;
      case "details":
        this.setState({ edDet: event.target.value });
        break;
      case "image":
        this.setState({ edImg: event.target.value });
        break;
      default:
        break;
    }
  };

  //Edit the drink
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
    let imageString = !!image ? image.S : "";
    //If the url is bad formatted or none is given, use a default image
    imageString = imageString.includes("https://boozerdrinks.s3.amazonaws.com/")
      ? imageString
      : "https://boozerdrinks.s3.amazonaws.com/generic.png";

    if (!this.props.user.isAdmin) return <Redirect to="/login" />;
    else if (!isDrinkLoaded) return <Loading />;
    else if (this.state.redirect) {
      return <Redirect to="/drinks" />;
    } else {
      return (
        <div className="Drink">
          <Header/>
          <div id="return">
            <Link to="/drinks">
              <Button variant="outline-danger" size="lg">
                <FaArrowLeft />
              </Button>
            </Link>
          </div>
          {/* Details of selected drink */}
          <img src={imageString} alt={name.S}></img>
          <h1>{name.S}</h1>
          <h2>{type.S}</h2>
          <h3>
            <span className="number">{price.N} €</span>
            <span className="number">{graduation.N} %</span>
          </h3>
          {!!details.S && details.S !== "none" ? <h4>{details.S}</h4> : ""}

          <Form>
            <Form.Group controlId="editDrinkForm">
              <Form.Label>Tipo</Form.Label>
              <Form.Control
                as="select"
                placeholder="Type of alcohol (e.g. Brandy)"
                defaultValue={type.S}
                onChange={(e) => {
                  this.setEditInfo("type", e);
                }}
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
                    onChange={(e) => {
                      this.setEditInfo("price", e);
                    }}
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
                    onChange={(e) => {
                      this.setEditInfo("graduation", e);
                    }}
                  />
                </Col>
              </Form.Row>
              <Form.Label>Detalles</Form.Label>
              <Form.Control
                as="textarea"
                placeholder="Useful info"
                defaultValue={details.S !== "none" ? details.S : ""}
                onChange={(e) => {
                  this.setEditInfo("details", e);
                }}
              />
              <Form.Label>Url</Form.Label>
              <Form.Control
                placeholder="Url of the drink"
                type="url"
                defaultValue={imageString}
                onChange={(e) => {
                  this.setEditInfo("image", e);
                }}
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
