<template>
  <div class="Sota">
    <div class="leftContainer">
      <h1>{{ articleTitle }}</h1>
      <div class="menuBo">
        <SotaMenu></SotaMenu>
      </div>
      <div class="abstract">
        <p>{{ abstract }}</p>
      </div>

      <!-- TODO rajouter le templace d'affichage d'article une fois le merge fait pour SOTA -->
      <h5>List of article in the SOTA:</h5>
      <ul id="articleList">
        <li v-for="(article,index) in articles" :key="index">{{ article }}</li>
      </ul>
    </div>
    <div class="rightContainer">
      <infoNav></infoNav>
    </div>
  </div>
</template>

<script>
import InfoNav from "@/components/article/InfoNav";
import SotaMenu from "@/components/sota-details/SotaMenu";
import json from "@/assets/dummy-SOTA.json";

export default {
  name: "Sota",
  components: {
    InfoNav,
    SotaMenu
  },
  data() {
    return {
      json,
      articleTitle: json.title,
      abstract: json.description,
      articles: json.article
    };
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

.infoNav {
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

