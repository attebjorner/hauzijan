import {Button, Container} from "react-bootstrap";
import SimpleQueryForm from "./SimpleQueryForm";
import ComplexQueryForm from "./ComplexQueryForm";
import {useState} from "react";

const QueryFormsRow = ({setLastQuery, setPage}) => {
  const [complexQueries, setComplexQueries] = useState([{lemma: "", pos: "", grammar: {}}]);

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
    setPage(1);
    setLastQuery([...complexQueries]);
  };

  return (
      <Container className="query-row">
        <SimpleQueryForm
          setLastQuery={setLastQuery}
          setPage={setPage}
        />
        {complexQueries.map((query, idx) => (
          <div key={idx}>
            <ComplexQueryForm
              addComplexQuery={addComplexQuery}
              removeComplexQuery={removeComplexQuery}
              queryId={idx}
              complexQueries={complexQueries}
            />
          </div>
        ))}
        <Button onClick={handleOnSearchClick} variant="primary" className="mb-3">
          Search
        </Button>
      </Container>
  );
};

export default QueryFormsRow;