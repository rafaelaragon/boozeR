import React from "react";
import "./Loading.css";
import LoadingGif from "../../assets/images/loading.gif";

class Loading extends React.Component {
  render() {
    return (
      <div className="Loading">
        <img src={LoadingGif} alt="Loading gif"></img>
      </div>
    );
  }
}

export default Loading;
