import {Button, Col, Form, Row} from "react-bootstrap";
import FloatingLabel from "react-bootstrap/cjs/FloatingLabel";
import {useEffect, useState} from "react";

const ComplexQueryForm = ({setLastQuery, setLoading, setPage, grammarOpen, setGrammarOpen, grammar, addComplexQuery, removeComplexQuery, queryId, complexQueries, setComplexQueries}) => {
  const [lemma, setLemma] = useState(complexQueries[queryId].lemma);
  const [pos, setPos] = useState(complexQueries[queryId].pos);
  const [hasButtonAdd, setButtonAdd] = useState(true);

  const handleOnChangeLemma = (e) => {
    setLemma(e.target.value);
    console.log(e.target.value);
    console.log(lemma);
    console.log(complexQueries);
    complexQueries[queryId].lemma = lemma;
    console.log(complexQueries);
    // setComplexQueries([...complexQueries])
    console.log(complexQueries);
  };

  const handleOnChangePos = (e) => {
    setPos(e.target.value);
    complexQueries[queryId].pos = pos;
    setComplexQueries([...complexQueries])
  };

  const handleOnClick = (e) => {
    setLoading(true);
    setPage(1);
    setLastQuery({lemma, pos, grammar});
  };

  const handleOnAddClick = (e) => {
    addComplexQuery();
    setButtonAdd(false);
  }

  const handleOnRemoveClick = (e) => {
    removeComplexQuery(queryId);
    setButtonAdd(queryId === complexQueries.length - 1);
  }

  const handleGrammarWindow = (e) => {
    setGrammarOpen(!grammarOpen);
  };

  useEffect(() => {
    setLemma(complexQueries[queryId].lemma);
    setPos(complexQueries[queryId].pos);
    setButtonAdd(queryId === complexQueries.length - 1);
  }, [complexQueries])

  return (
    <div>
      <Form>
        <FloatingLabel controlId="floatingInput" label="Lemma" dir="rtl" className="mb-3">
          <Form.Control value={lemma} onChange={handleOnChangeLemma} type="text" placeholder="example" />
        </FloatingLabel>
        <FloatingLabel controlId="floatingInput" label="POS" className="mb-3">
          <Form.Control value={pos} onChange={handleOnChangePos} type="text" placeholder="example" />
        </FloatingLabel>
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
      </Form>
    </div>
  );
};

export default ComplexQueryForm;