function collectQuery(complexQueries) {
  return complexQueries.map(query => {
    if (query.lemma === "") {
      delete query.lemma;
    }
    if (query.pos === "") {
      delete query.pos;
    }
    if (Object.keys(query.grammar).length === 0) {
      delete query.grammar;
    }
    console.log(query);
    return Buffer.from(JSON.stringify(query).replaceAll(",,", ",")).toString("base64");
  }).join();
}

export default collectQuery;