import {Table} from 'react-bootstrap';
import PagingRow from "./PagingRow";
import {useEffect, useState} from "react";

const SentenceTable = ({sentences, findWords}) => {
  const onClick = (e) => {
    findWords(e.target.attributes['data-key'].value)
  };

  return (
    <div>
      <Table className="sentence-table" striped bordered hover responsive>
        <thead>
        <tr>
          <th>Sentence</th>
        </tr>
        </thead>
        <tbody dir="rtl">
          {sentences.map(sent => (
            <tr onClick={onClick} key={sent.id}>
              <td data-key={sent.id}>{sent.original_sentence}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default SentenceTable;