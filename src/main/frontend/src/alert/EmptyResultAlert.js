import {Alert} from "react-bootstrap";

const EmptyResultAlert = () => {
  return (
    <div className="corpora-alert">
      <Alert variant="danger">
        Nothing was found
      </Alert>
    </div>
  );
};

export default EmptyResultAlert;