// -------------------------------------------------------------------
// Dependencies

// Import
import bibtexParser from 'bibtex-parse-js';

// -------------------------------------------------------------------
// Properties

// -------------------------------------------------------------------
// Exports

/**
 *
 * @param {*} json
 */
function format(json) {
  const abstract = json.entryTags['abstract'];
  if (abstract == undefined || abstract.trim().length()) {
    json.entryTags['abstract'] = json.entryTags['title'];
  }
  // delete json.entryTags['abstract'];

  if (json.entryTags['keywords'] == undefined) {
    json.entryTags['keywords'] = 'Unknown';
  }

  json.entryTags['author'] = json.entryTags['author'].replace(' and', ',');

  return json;
}

/**
 *
 * @param {*} json
 */
function transform(json) {
  return Object.assign({}, json.entryTags, {
    reference: json.citationKey,
    authors: json.entryTags.author.split(', ').map(a => a.trim()),
    keywords: json.entryTags.keywords.split(', ').map(a => a.trim()),
    year: Number.parseInt(json.entryTags.year, 10)
  });
}

/**
 *
 * @param {*} bibtex
 */
export function parse(bibtex) {
  return bibtexParser
    .toJSON(bibtex)
    .map(format)
    .map(transform);
}

export function toBibtex(json) {
  let bibtex = '';
  if (Array.isArray(json)) {
    bibtex = json
      .map(value => parseJsonToBibtex(value))
      .reduce((bibtex, str) => (bibtex += str), '');
  } else {
    bibtex = parseJsonToBibtex(json);
  }
  return bibtex;
}

function parseJsonToBibtex(article) {
  const fields = Object.keys(article);
  let bibtex = `@book{${article.reference},`;
  // Loop over the fields of the article
  Object.keys(article).forEach((field, i, list) => {
    // write them in the str
    bibtex += `${field} = ${JSON.stringify(article[field])}`;
    if (i < list.length - 1) {
      bibtex += ',';
    }
  });

  // Close the  bibtex
  bibtex += '}\n\n';

  return bibtex;
}
