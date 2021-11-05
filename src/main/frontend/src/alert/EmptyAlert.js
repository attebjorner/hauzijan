import {Alert} from "react-bootstrap";

const EmptyAlert = ({msg}) => {
  return (
    <div className="corpora-alert">
      <Alert variant="danger">
        {msg}
      </Alert>
    </div>
  );
};

export default EmptyAlert;