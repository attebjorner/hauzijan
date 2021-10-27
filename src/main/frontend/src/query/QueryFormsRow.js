import {Button, Col, Collapse, Container, Row} from "react-bootstrap";
import SimpleQueryForm from "./SimpleQueryForm";
import ComplexQueryForm from "./ComplexQueryForm";
import {useEffect, useState} from "react";
import Grammar from "./Grammar";

const QueryFormsRow = ({setLastQuery, setLoading, setPage}) => {
  const [complexQueries, setComplexQueries] = useState([{lemma: "123", pos: "34", grammar: {}}]);

  const addComplexQuery = () => {
    setComplexQueries([...complexQueries, {lemma: "", pos: "", grammar: {}}]);
  }

  const removeComplexQuery = (idx) => {
    let t = [];
    for (let i = 0; i < complexQueries.length; ++i) {
      if (i !== idx) t.push(complexQueries[i]);
    }
    setComplexQueries(t);
  }

  const handleOnSearchClick = (e) => {
    setLoading(true);
    setPage(1);
    setLastQuery(complexQueries);
  };

  return (
      <Container className="query-row">
        <SimpleQueryForm
          setLastQuery={setLastQuery}
          setLoading={setLoading}
          setPage={setPage}
        />
        {complexQueries.map((query, idx) => (
          <div key={idx}>
            <ComplexQueryForm
              addComplexQuery={addComplexQuery}
              removeComplexQuery={removeComplexQuery}
              queryId={idx}
              complexQueries={complexQueries}
              setComplexQueries={setComplexQueries}
            />
          </div>
        ))}
        <Button variant="primary" className="mb-3">
          Search
        </Button>
      </Container>
  );
};

export default QueryFormsRow;