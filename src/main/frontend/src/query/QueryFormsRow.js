import {Col, Collapse, Container, Row} from "react-bootstrap";
import SimpleQueryForm from "./SimpleQueryForm";
import ComplexQueryForm from "./ComplexQueryForm";
import {useState} from "react";
import Grammar from "./Grammar";

const QueryFormsRow = ({setLastQuery, setLoading, setPage}) => {
  const [grammarOpen, setGrammarOpen] = useState(false);
  const [grammar, setGrammar] = useState({});

  return (
      <Container>
        <Row className="query-row">
          <Col>
            <SimpleQueryForm
              setLastQuery={setLastQuery}
              setLoading={setLoading}
              setPage={setPage}
            />
          </Col>
          <Col>
            <ComplexQueryForm
              setLastQuery={setLastQuery}
              setLoading={setLoading}
              setPage={setPage}
              grammarOpen={grammarOpen}
              setGrammarOpen={setGrammarOpen}
              grammar={grammar}
            />
          </Col>
        </Row>
        <Collapse in={grammarOpen}>
          <div>
            <Grammar grammar={grammar} setGrammar={setGrammar}/>
          </div>
        </Collapse>
      </Container>
  );
};

export default QueryFormsRow;