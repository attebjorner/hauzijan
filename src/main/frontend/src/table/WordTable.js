import {Table} from 'react-bootstrap';

const WordTable = ({words}) => {
  return (
    <div>
      <Table className="word-table" striped bordered hover responsive>
        <thead>
          <tr>
            <th>Word</th>
            <th>Lemma</th>
            <th>POS</th>
            <th>Grammar</th>
          </tr>
          </thead>
          <tbody>
            {words.map(word => (
              <tr>
                <td dir="rtl">{word.word}</td>
                <td dir="rtl">{word.lemma}</td>
                <td>{word.pos}</td>
                <td>{JSON.stringify(word.grammar).replaceAll(/["{}]/g, "").replaceAll(",", ", ")}</td>
              </tr>
            ))}
          </tbody>
      </Table>
    </div>
  );
};

export default WordTable;