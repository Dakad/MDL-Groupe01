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
function formatToJson(json) {
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
function transformToJson(json) {
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
    .map(formatToJson)
    .map(transformToJson);
}

export function toBibtex(json) {
  let bibtex = '';
  debugger;
  if (Array.isArray(json)) {
    bibtex = json.map(value => parseJsonToBibtex(value)).join('\n\n');
  } else {
    bibtex = parseJsonToBibtex(json);
  }
  return bibtex;
}

function parseJsonToBibtex(article) {
  if (typeof article != 'object' || !article.hasOwnProperty('reference')) {
    return '';
  }

  let bibtex = `@book{${article.reference},`;
  // Loop over the fields of the article
  Object.keys(article).forEach((field, i, list) => {
    // write them in the str
    bibtex += `${field} = ${JSON.stringify(article[field])}\n`;
    if (i < list.length - 1) {
      bibtex += ',';
    }
  });

  // Close the  bibtex
  bibtex += '}';

  return bibtex;
}
