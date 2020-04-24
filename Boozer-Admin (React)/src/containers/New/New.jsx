import React from "react";
import "./New.css";
import Button from "react-bootstrap/Button";
import Axios from "axios";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import { Link, Redirect } from "react-router-dom";
import { FaPlus, FaArrowLeft } from "react-icons/fa";
import { TYPES } from "../../Consts";
import Header from "../../components/Header/Header";
import { toast } from "react-toastify";
import { connect } from "react-redux";
import { loadUser } from "../../Redux/Actions";

class New extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      newName: "",
      newType: "Otro",
      newPrice: 0,
      newGraduation: 0,
      newDetails: "none",
      newImage: "https://boozerdrinks.s3.amazonaws.com/generic.png",
    };
  }

  //Store info of the new drink
  setName = (event) => {
    this.setState({ newName: event.target.value });
  };
  setType = (event) => {
    this.setState({ newType: event.target.value });
  };
  setPrice = (event) => {
    this.setState({ newPrice: event.target.value });
  };
  setGraduation = (event) => {
    this.setState({ newGraduation: event.target.value });
  };
  setDetails = (event) => {
    this.setState({ newDetails: event.target.value });
  };
  setImage = (event) => {
    this.setState({ newImage: event.target.value });
  };

  //Create a new drink
  createDrink = async () => {
    const {
      newName,
      newType,
      newPrice,
      newGraduation,
      newDetails,
      newImage,
    } = this.state;

    this.state.newName === ""
      ? toast.error("❌ Escribe el nombre de la bebida")
      : await Axios.get(
          `https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/createBoozerDrink?name=` +
            newName +
            `&type=` +
            newType +
            `&price=` +
            newPrice +
            `&vol=` +
            newGraduation +
            `&det=` +
            newDetails +
            `&url=` +
            newImage
        );
    this.state.newName === ""
      ? console.log()
      : await this.setState({ redirect: true });
  };

  render() {
    if (!this.props.user.isAdmin) return <Redirect to="/login" />;
    else if (this.state.redirect) {
      return <Redirect to="/drinks" />;
    } else {
      return (
        <div className="New">
          <Header />
          <div id="return">
            <Link to="/drinks">
              <Button variant="outline-danger" size="lg" block>
                <FaArrowLeft />
              </Button>
            </Link>
          </div>
          <Form>
            <Form.Group controlId="newDrinkForm">
              <Form.Label>Nombre</Form.Label>
              <Form.Control
                placeholder="Nombre de la bebida"
                onChange={this.setName}
              />
              <Form.Label>Tipo</Form.Label>
              <Form.Control
                as="select"
                placeholder="Tipo de bebida (e.g. Brandy)"
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
                    defaultValue="0"
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
                    defaultValue="0"
                    onChange={this.setGraduation}
                  />
                </Col>
              </Form.Row>
              <Form.Label>Detalles</Form.Label>
              <Form.Control
                as="textarea"
                placeholder="Información útil"
                onChange={this.setDetails}
              />
              <Form.Label>Url</Form.Label>
              <Form.Control
                placeholder="Url de la bebida"
                type="url"
                onChange={this.setImage}
              />
            </Form.Group>
          </Form>
          <div id="create">
            <Button
              variant="outline-success"
              size="lg"
              block
              onClick={this.createDrink}
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
  };
}

const mapDispatch = { loadUser };

export default connect(mapState, mapDispatch)(New);
