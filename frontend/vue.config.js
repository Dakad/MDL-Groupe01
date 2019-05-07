// vue.config.js
var path = require('path');

let publicPath = '/';
if (process.env.PUBLIC_PATH) {
  publicPath = process.env.PUBLIC_PATH;
} else {
  if (process.env.NODE_ENV == 'staging') {
    publicPath = '/latest-release/';
  }
}
const apiURL = process.env.API_URL || 'http://localhost:8088';

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
