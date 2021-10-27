import {useEffect, useState} from "react";
import Placeholder from "react-bootstrap/Placeholder";
import SentenceTable from "../table/SentenceTable";
import WordTable from "../table/WordTable";
import QueryFormsRow from "../query/QueryFormsRow";
import axios from "axios";
import EmptyResultAlert from "../alert/EmptyResultAlert";
import collectQuery from "../util";
import PagingRow from "../table/PagingRow";
import {Collapse} from "react-bootstrap";

const Home = () => {
  const [loading, setLoading] = useState(false);
  const [emptyResult, setEmptyResult] = useState(false);
  const [sentences, setSentences] = useState(null);
  const [words, setWords] = useState(null);
  const [page, setPage] = useState(1)
  const [lastQuery, setLastQuery] = useState(1)
  const [sentenceOpen, setSentenceOpen] = useState(false)

  const makeSentenceRequest = (url) => {
    axios.get("http://localhost:8080/api/v2/query/" + url + "&page=" + page)
      .then(response => {
        setLoading(false);
        switch (response.status) {
          case 200:
            setSentences(response.data);
            break;
          case 204:
            setSentences([]);
            break;
          default:
            throw Error();
        }
      })
      .catch(err => console.log(err));
  }

  const findSentences = () => {
    if (typeof lastQuery == "string") {
      makeSentenceRequest("simple?query=" + lastQuery);
    } else if (typeof lastQuery == "object") {
      const query = collectQuery(lastQuery.lemma, lastQuery.pos, lastQuery.grammar);
      makeSentenceRequest("complex?encoded=" + query);
    }
  };

  useEffect(() => {
    findSentences();
  }, [lastQuery, page]);

  const findWords = (id) => {
    axios.get("http://localhost:8080/api/v2/query/wordlist/" + id)
      .then(response => {
        switch (response.status) {
          case 200:
            setWords(response.data);
            break;
          default:
            throw Error();
        }
        setLoading(false);
      })
      .catch(err => console.log(err));
  }

  useEffect(() => {
    setEmptyResult( sentences && sentences.length === 0);
    setSentenceOpen(sentences && sentences.length !== 0);
  }, [sentences]);

  return (
    <div className="home-body">
      <QueryFormsRow
        setLastQuery={setLastQuery}
        setLoading={setLoading}
        setPage={setPage}
      />
      <Collapse in={loading}>
        <Placeholder as="p" animation="glow"><Placeholder xs={10} /></Placeholder>
      </Collapse>
      {emptyResult && <EmptyResultAlert/>}
      <Collapse in={sentenceOpen}>
        <div>
          {sentences && <>
            <SentenceTable sentences={sentences} findWords={findWords}/>
            <PagingRow page={page} setPage={setPage}/>
          </>}
        </div>
      </Collapse>
      {words && <WordTable words={words}/>}
      <div className="footer"/>
    </div>
  );
};

export default Home;
