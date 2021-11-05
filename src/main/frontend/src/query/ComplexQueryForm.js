import {Button, Col, Collapse, Form, Row} from "react-bootstrap";
import FloatingLabel from "react-bootstrap/cjs/FloatingLabel";
import {useEffect, useState} from "react";
import Grammar from "./Grammar";

const ComplexQueryForm = ({addComplexQuery, removeComplexQuery, queryId, complexQueries}) => {
  const [lemma, setLemma] = useState(complexQueries[queryId].lemma);
  const [pos, setPos] = useState(complexQueries[queryId].pos);
  const [grammar, setGrammar] = useState(complexQueries[queryId].grammar);
  const [grammarOpen, setGrammarOpen] = useState(false);

  const handleOnChangeLemma = (e) => {
    setLemma(e.target.value);
    complexQueries[queryId].lemma = e.target.value;
  };

  const handleOnChangePos = (e) => {
    setPos(e.target.value);
    complexQueries[queryId].pos = e.target.value;
  };

  const handleOnAddClick = (e) => {
    addComplexQuery();
  }

  const handleOnRemoveClick = (e) => {
    removeComplexQuery(queryId);
  }

  const handleGrammarWindow = (e) => {
    setGrammarOpen(!grammarOpen);
  };

  useEffect(() => {
    setLemma(complexQueries[queryId].lemma);
    setPos(complexQueries[queryId].pos);
    setGrammar(complexQueries[queryId].grammar)
  }, [complexQueries])

  useEffect(() => {
    complexQueries[queryId].grammar = grammar;
  }, [grammar])

  return (
    <div>
      <Form className="complex-query-form">
        <Row>
          <Col>
            <FloatingLabel controlId="floatingInput" label="Lemma" dir="rtl" className="mb-3">
              <Form.Control value={lemma} onChange={handleOnChangeLemma} type="text" placeholder="example" />
            </FloatingLabel>
          </Col>
          <Col>
            <FloatingLabel controlId="floatingInput" label="POS" className="mb-3">
              <Form.Control value={pos} onChange={handleOnChangePos} type="text" placeholder="example" />
            </FloatingLabel>
          </Col>
        </Row>
        <h6>Grammar: {JSON.stringify(grammar).replaceAll(/["{}]/g, "")}</h6>

        <Row>
          <Col>
            <Button onClick={handleGrammarWindow} variant="primary" className="mb-3">
              Grammar
            </Button>
          </Col>
          {(queryId !== 0 || complexQueries.length !== 1) &&
            <Col>
              <Button onClick={handleOnRemoveClick} variant="primary" className="mb-3">
                Remove this
              </Button>
            </Col>}
          {queryId === complexQueries.length - 1 &&
            <Col>
              <Button onClick={handleOnAddClick} variant="primary" className="mb-3">
                Add more
              </Button>
            </Col>}
        </Row>

        <Collapse in={grammarOpen}>
          <div>
            <Grammar grammar={grammar} setGrammar={setGrammar} complexQueries={complexQueries} queryId={queryId}/>
          </div>
        </Collapse>
      </Form>
    </div>
  );
};

export default ComplexQueryForm;