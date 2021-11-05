import {useEffect, useState} from "react";
import Placeholder from "react-bootstrap/Placeholder";
import SentenceTable from "../table/SentenceTable";
import WordTable from "../table/WordTable";
import QueryFormsRow from "../query/QueryFormsRow";
import axios from "axios";
import EmptyAlert from "../alert/EmptyAlert";
import collectQuery from "../util";
import PagingRow from "../table/PagingRow";
import {Collapse} from "react-bootstrap";

const Home = () => {
  const apiUrl = "http://localhost:8080/api/v2/query/";
  const [loading, setLoading] = useState(false);
  const [emptyResult, setEmptyResult] = useState(false);
  const [emptyQuery, setEmptyQuery] = useState(false);
  const [sentences, setSentences] = useState(null);
  const [words, setWords] = useState(null);
  const [page, setPage] = useState(1)
  const [lastQuery, setLastQuery] = useState(0)
  const [sentenceOpen, setSentenceOpen] = useState(false)

  const makeSentenceRequest = (url) => {
    setEmptyQuery(false);
    setLoading(true);
    axios.get(apiUrl + url + "&page=" + page)
      .then(response => {
        setLoading(false);
        switch (response.status) {
          case 200:
            setSentences(response.data);
            break;
          case 204:
            if (page !== 1) {
              setPage(page - 1);
            } else {
              setSentences([]);
            }
            break;
          default:
            throw Error();
        }
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
      });
  }

  const findSentences = () => {
    if (typeof lastQuery == "string") {
      if (lastQuery === "") {
        setEmptyQuery(true);
        return;
      }
      makeSentenceRequest("simple?query=" + lastQuery);
    } else if (typeof lastQuery == "object") {
      const query = collectQuery(lastQuery);
      if (query === "") {
        setEmptyQuery(true);
        return;
      }
      makeSentenceRequest("multiple?encoded=" + query);
    }
  };

  useEffect(() => {
    findSentences();
  }, [lastQuery, page]);

  const findWords = (id) => {
    axios.get(apiUrl + "wordlist/" + id)
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
        setPage={setPage}
      />
      <Collapse in={loading}>
        <Placeholder as="p" animation="glow"><Placeholder xs={10} /></Placeholder>
      </Collapse>
      {emptyResult && <EmptyAlert msg={"Nothing found"}/>}
      {emptyQuery && <EmptyAlert msg={"Your query is empty"}/>}
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
