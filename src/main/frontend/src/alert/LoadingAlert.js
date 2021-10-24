import {Alert} from "react-bootstrap";

const LoadingAlert = () => {
  return (
    <div className="corpora-alert">
      <Alert variant="primary">
        Loading...
      </Alert>
    </div>
  );
};

export default LoadingAlert;