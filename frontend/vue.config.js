// vue.config.js
var path = require('path');

const DEFAULT_API_URL = 'http://localhost:8088';

const isProd = process.env.NODE_ENV && process.env.NODE_ENV.includes('prod');

let publicPath = '/';
if (process.env.PUBLIC_PATH) {
  publicPath = process.env.PUBLIC_PATH;
} else {
  if (process.env.NODE_ENV == 'staging') {
    publicPath = '/latest-release/';
  }
}

let apiURL;
if (isProd && process.env.PUBLIC_PATH) {
  apiURL = DEFAULT_API_URL + process.env.PUBLIC_PATH;
} else {
  apiURL = process.env.API_URL || DEFAULT_API_URL;
}

console.log(publicPath, apiURL);

module.exports = {
  // proxy all webpack dev-server requests starting with /api
  // to our Spring Boot backend (localhost:8088) using http-proxy-middleware
  // see https://cli.vuejs.org/config/#devserver-proxy
  devServer: {
    proxy: {
      '/api': {
        target: apiURL,
        ws: true,
        changeOrigin: true
      }
    }
  },
  // Change build paths to make them Maven compatible
  // see https://cli.vuejs.org/config/
  outputDir: 'target/dist',
  assetsDir: 'static',
  publicPath
};
