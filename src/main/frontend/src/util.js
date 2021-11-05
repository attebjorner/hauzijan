function collectQuery(complexQueries) {
  return complexQueries.map(query => {
    let newQuery = {};
    if (query.lemma !== "") {
      newQuery.lemma = query.lemma;
    }
    if (query.pos !== "") {
      newQuery.pos = query.pos;
    }
    if (Object.keys(query.grammar).length !== 0) {
      newQuery.grammar = query.grammar;
    }
    return (Object.keys(newQuery).length !== 0)
      ? Buffer.from(JSON.stringify(newQuery).replaceAll(",,", ",")).toString("base64")
      : "";
  }).filter(s => s !== "").join();
}

export default collectQuery;