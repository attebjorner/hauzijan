function collectQuery(lemma, pos, grammar) {
  const map = new Map();
  if (lemma !== "") {
    map.set("lemma", lemma);
  }
  if (pos !== "") {
    map.set("pos", pos);
  }
  if (Object.keys(grammar).length !== 0) {
    map.set("gram", grammar);
  }

  const query = Object.fromEntries(map);
  return Buffer.from(JSON.stringify(query)).toString("base64");
}

export default collectQuery;