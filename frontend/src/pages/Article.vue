<template>
  <div class="article">
    <div class="leftContainer">
      <h1>{{ article.title }}</h1>
      <div class="menuBo">
        <menuArticle :articleId="article.reference"></menuArticle>
      </div>
      <div class="abstract">
        <p>{{ article.content }}</p>
      </div>
    </div>
    <div class="rightContainer">
      <infoNav :tags="article.keywords" :refs="article.refs" :info="article.sotas"></infoNav>
    </div>
  </div>
</template>

<script>
import InfoNav from "@/components/article/InfoNav";
import MenuArticle from "@/components/article/MenuArticle";
import json from "@/assets/dummy-Article.json";
import { getArticleByReference } from "@/services/api";

export default {
  name: "Article",
  props: ["reference", "data"],
  components: {
    InfoNav,
    MenuArticle
  },
  data() {
    return {
      article: {}
    };
  },
  created() {
    // fetch the data when the view is created
    // and the data is already being observed
    this.fetchArticle(this.$route.params["reference"]);
  },
  methods: {
    fetchArticle(reference) {
      return getArticleByReference(reference).then(
        data => (this.article = data)
      );
    }
  }
};
</script>

<style scoped>
h1 {
  position: absolute;
  left: 10%;
  width: 70%;
  top: 10%;
}

.menuBo {
  padding-top: 15%;
  padding-left: 10%;
  width: 25%;
  float: right;
}

.abstract {
  float: bottom;
  padding-top: 10%;
  padding-left: 15%;
  width: 80%;
}

infoNav {
  padding-top: 10%;
  padding: 10%;
}

.leftContainer {
  position: relative;
  float: left;
  width: 60%;
  height: 80%;
}

.rightContainer {
  padding-right: 5px;
  padding-top: 5%;
  float: right;
}
</style>

