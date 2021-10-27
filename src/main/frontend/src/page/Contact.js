import {Button} from "react-bootstrap";
import {useHistory} from "react-router-dom";

const Contact = () => {
  const history = useHistory();

  const handleClick = () => {
    history.push("/");
  }

  return (
    <div className="home-body">
      <Button onClick={handleClick}>
        i do nothing.
      </Button>
    </div>
  );
}

export default Contact;