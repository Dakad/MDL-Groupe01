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
export function format(json) {
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
export function transform(json) {
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
