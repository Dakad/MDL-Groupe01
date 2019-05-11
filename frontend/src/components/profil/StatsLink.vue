<template>
  <div>
    <h4>Number of cited articles: {{nbrCitedArticles}}</h4>
    <h4>Number of papers: {{nbrPaper}}</h4>
    <h4>H-index: {{hIndex}}</h4>
    <h4>Number of quotes {{nbrQuotes}}</h4>
    <ul class="social-icons">
      <li>
        <a href="http://www.facebook.com">
          <img src="@/assets/Facebook_Logo.png">
        </a>
      </li>
      <li>
        <a href="http://www.twitter.com">
          <img src="@/assets/Twitter_Logo.png">
        </a>
      </li>
      <li>
        <a href="http://www.linkedin.com">
          <img src="@/assets/Linkedin_logo.png">
        </a>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nbrCitedArticles: 14,
      nbrPaper: 15,
      hIndex: 15,
      nbrQuotes: 8
    };
  }
};
</script>

<style scoped>
.social-icons li {
  list-style-type: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  display: inline;
}

.social-icons li img {
  width: 50px;
  height: 50px;
  margin-right: 30px;
}
</style>
