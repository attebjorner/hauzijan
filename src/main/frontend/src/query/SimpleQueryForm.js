import {Button, Form} from "react-bootstrap";
import FloatingLabel from "react-bootstrap/cjs/FloatingLabel";
import {useState} from "react";

const SimpleQueryForm = ({setLastQuery, setPage}) => {
  const [query, setQuery] = useState("");

  const handleOnChange = (e) => {
    setQuery(e.target.value)
  };

  const handleOnClick = () => {
    setPage(1)
    setLastQuery(query)
  };

  const handleOnEnter = (e) => {
    return (e.key === 'Enter' ? handleOnClick : null)
  }

  return (
    <div>
      <Form>
        <FloatingLabel controlId="floatingInput" label="Simple query" className="mb-3">
          <Form.Control dir="rtl" onChange={handleOnChange} onKeyPress={handleOnEnter} type="text" placeholder="example" />
        </FloatingLabel>
        <Button onClick={handleOnClick} variant="primary" className="mb-3 simple-btn">
          Search
        </Button>
      </Form>
    </div>
  );
};

export default SimpleQueryForm;