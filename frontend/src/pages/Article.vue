<template>
  <div class="article">
    <div class="leftContainer">
      <h1>{{ article.title }}</h1>
      <div class="menuBo">
        <menuArticle :article-id="article.id"></menuArticle>
      </div>
      <div class="abstract">
        <p>{{ article.abstract }}</p>
      </div>
    </div>
    <div class="rightContainer">
      <infoNav :tags="article.tags" :refs="article.refs" :info="article.sotas"></infoNav>
    </div>
  </div>
</template>

<script>
import InfoNav from "@/components/article/InfoNav";
import MenuArticle from "@/components/article/MenuArticle";
import json from "@/assets/dummy-Article.json";

export default {
  name: "Article",
  components: {
    InfoNav,
    MenuArticle
  },
  data() {
    return {
      article: {
        tags: [],
        refs: [],
        sotas: []
      }
    };
  },
  mounted() {
    this.getArticle();
  },
  methods: {
    getArticle() {
      this.article = json;
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

