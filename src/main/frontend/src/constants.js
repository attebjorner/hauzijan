const guideText = () => {
  return (
    <>
      <b>Simple query</b>
      <br/>
      <br/>
      Simple query allows you to search direct matches in the corpora.
      <br/>
      Examples:
      <br/>
      <i>מה זה</i>
      <br/>
      <i>כלב</i>
      <br/>
      <br/>
      <b>Complex query</b>
      <br/>
      <br/>
      With complex query you can search by lemma, part of speech (POS) or grammar or their combinations.
      <br/>
      <br/>
      <b>List of allowed POS-tags:</b>
      <br/>
      <br/>
      DET, PRON, NUM, X, AUX, VERB, ADP, ADJ, CCONJ, INTJ, PUNCT, NOUN, PROPN, ADV, SCONJ
    </>
  );
}

export default guideText;