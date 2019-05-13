// -------------------------------------------------------------------
// Dependencies

// Import
import bibtexParser from 'bibtex-parse-js';

// -------------------------------------------------------------------
// Properties
const EXCLUDED_PROPS = [
  'creator',
  'nbrePage',
  'nb_citations',
  'nb_views',
  'created_at',
  'category',
  'price'
];

/**
 * Format a parsed bibtex to the correct formatted Json object
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

function formatToBibtex(article) {
  const reference = article['reference'];
  Object.keys(article).forEach(property => {
    // Delete incorrect bibtex field or null value
    if (EXCLUDED_PROPS.includes(property) || article[property] == null) {
      delete article[property];
    }
    switch (property) {
      case 'authors':
        article['author'] = article[property].join(' and ');
        delete article['authors'];
        break;
      case 'keywords':
        article['keywords'] = article[property].map(k => k.name).join(', ');
        break;

      default:
        break;
    }
  });
  delete article['reference'];
  return [reference, article];
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

function parseToBibtex([reference, article]) {
  if (typeof article != 'object') {
    return '';
  }

  let bibtex = `@article{${reference},\n`;
  // Loop over the fields of the article
  bibtex += Object.entries(article)
    .map(([field, value]) => `${field} = ${JSON.stringify(value)}`)
    .join('\n');

  // Close the  bibtex
  bibtex += '\n}';

  return bibtex;
}

// -------------------------------------------------------------------
// Exports

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

export function toBibtex(articles) {
  let bibtex = '';
  if (!Array.isArray(articles)) {
    articles = [articles];
  }
  bibtex = articles
    .map(formatToBibtex)
    .map(parseToBibtex)
    .join('\n\n');
  return bibtex;
}
