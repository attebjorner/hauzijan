import {Col, Form, Row} from "react-bootstrap";
import {useRef} from "react";

const Grammar = ({grammar, setGrammar}) => {
  const checkboxLeft = useRef(null);
  const checkboxRight = useRef(null);

  // const selected = {};

  const grammarLeft = [
    {key: "Case", val: ["Acc", "Gen", "Tem"]},
    {key: "Person", val: ["1", "2", "3", "'1,2,3'"]},
    {key: "Gender", val: ["Masc", "Fem", "'Fem,Masc'"]},
    {key: "HebBinyan", val: ["HUFAL", "PUAL", "PAAL", "PIEL", "HITPAEL", "NIFAL", "HIFIL"]},
    {key: "Number", val: ["Sing", "Dual", "Plur", "'Dual,Plur'", "'Plur,Sing'"]},
    {key: "Reflex", val: ["Yes"]},
    {key: "VerbForm", val: ["Part", "Inf"]},
    {key: "Tense", val: ["Past", "Fut"]},
    {key: "PronType", val: ["Dem", "Ind", "Int", "Art", "Emp", "Prs"]},
    {key: "Polarity", val: ["Pos", "Neg"]}
  ];

  const grammarRight = [
    {key: "Mood", val: ["Imp"]},
    {key: "VerbType", val: ["Mod", "Cop"]},
    {key: "Voice", val: ["Pass", "Mid", "Act"]},
    {key: "Xtra", val: ["Junk"]},
    {key: "Definite", val: ["Cons", "Def"]},
    {key: "Prefix", val: ["Yes"]},
    {key: "Abbr", val: ["Yes"]},
    {key: "HebSource", val: ["ConvUncertainHead", "ConvUncertainLabel"]},
    {key: "HebExistential", val: ["True"]}
  ]

  const handleOnClick = (e, ref) => {
    const pressedCategory = e.target.name;
    const pressedValue = e.target.parentElement.innerText;

    let temp = grammar
    if (e.target.checked) {
      temp[pressedCategory] = pressedValue
    } else {
      delete temp[pressedCategory]
    }
    setGrammar(temp)

    for (const category of ref.current.children) {
      if (category.id === pressedCategory) {
        let checkboxes = category.children;
        for (let i = 1; i < checkboxes.length; ++i) {
          if (checkboxes[i].children[0].parentElement.innerText !== pressedValue) {
            checkboxes[i].children[0].checked = false;
          }
        }
      }
    }
  }

  const generateGrammar = (grammar, ref) => (
    <div ref={ref}>
      {grammar.map((entry, idx) => (
        <div className="checkbox-item" key={idx} id={entry.key}>
          <h6>{entry.key}</h6>
          {entry.val.map((value, id) => (
            <Form.Check inline key={id} type="checkbox" label={value} name={entry.key} onClick={(e) => handleOnClick(e, ref)} itemRef={entry.key + "-" + value}/>
          ))}
        </div>
      ))}
    </div>
  );

  return (
    <Form>
      <Row>
        <Col>
          {generateGrammar(grammarLeft, checkboxLeft)}
        </Col>
        <Col>
          {generateGrammar(grammarRight, checkboxRight)}
        </Col>
      </Row>
    </Form>
  );
}

export default Grammar;
