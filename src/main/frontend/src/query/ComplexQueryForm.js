import {Button, Col, Form, Row} from "react-bootstrap";
import FloatingLabel from "react-bootstrap/cjs/FloatingLabel";
import {useState} from "react";

const ComplexQueryForm = ({setLastQuery, setLoading, setPage, grammarOpen, setGrammarOpen, grammar}) => {
  const [lemma, setLemma] = useState("");
  const [pos, setPos] = useState("");

  const handleOnChangeLemma = (e) => {
    setLemma(e.target.value);
  };

  const handleOnChangePos = (e) => {
    setPos(e.target.value);
  };

  const handleOnClick = (e) => {
    setLoading(true);
    setPage(1);
    setLastQuery({lemma, pos, grammar});
  };

  const handleGrammarWindow = (e) => {
    setGrammarOpen(!grammarOpen);
  };

  return (
    <div>
      <Form>
        <FloatingLabel controlId="floatingInput" label="Lemma" dir="rtl" className="mb-3">
          <Form.Control onChange={handleOnChangeLemma} type="text" placeholder="example" />
        </FloatingLabel>
        <FloatingLabel controlId="floatingInput" label="POS" className="mb-3">
          <Form.Control onChange={handleOnChangePos} type="text" placeholder="example" />
        </FloatingLabel>
        <Row>
          <Col>
            <Button onClick={handleOnClick} variant="primary" className="mb-3">
              Search
            </Button>
          </Col>
          <Col>
            <Button onClick={handleGrammarWindow} variant="primary" className="mb-3">
              Grammar
            </Button>
          </Col>
        </Row>
      </Form>
    </div>
  );
};

export default ComplexQueryForm;