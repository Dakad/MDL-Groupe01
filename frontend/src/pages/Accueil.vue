<template>
  <section class="accueilapp">
    <div class="central">
      <img class="logoimg" src="../assets/logochicken.jpg" style="width:100px;height:100px;">
      <div class="title">
        <h1>Froggosaur</h1>
      </div>

      <!-- place the searched words in var searchwords and the action to
      script the search is searchIt-->

      <search id="search"></search>

      <div class="filter"></div>
    </div>

    <div class="recommended">
      <carousel :per-page="3" :autoplay="true" :loop="true" :autoplayTimeout="3000" :navigationEnabled="true">
        <slide class="slides" v-for="(recommended,index) in list" :key="index">
          <article-slide :title="recommended.title" :authors="recommended.authors" :domain="recommended.category" :date="recommended.created_at" :nb-views="recommended.nb_views" :nb-quotes="recommended.nb_citations" :keywords="recommended.keywords"></article-slide>
        </slide>
      </carousel>
    </div>
  </section>
</template>

<script>
import { Carousel, Slide } from "vue-carousel";

import ArticleSlide from "@/components/accueil/ArticleSlide";

import Search from '@/components/navbar/Search';

import dummy from "@/assets/dummy-Results.json";

export default {
  name: "Accueil",
  components: {
    Carousel,
    Slide,
    ArticleSlide,
    Search,
  },
  data() {
    return {
      searchInput: null,
      list: dummy.results.articles,
    };
  },
  searchIt() {}
};
</script>

<style scoped>
img {
  float: left;
}

.central {
  position: absolute;
  top: 30%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  width: 75%;
  height: 35%;
  overflow: auto;
}

.logoimg {
  width: 10%;
  height: 10%;
  position: absolute;
  left: 20%;
}

.title {
  position: absolute;
  left: 35%;
}

h1 {
  margin-left: 50px;
  margin-top: 35px;
}

#search {
  margin-top: 140px;
}

p {
  margin-left: 15px;
  width: 20%;
}

.recommended {
  margin: 0;
  position: absolute;
  top: 75%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  width: 75%;
  height: 47%;
}

.slides {
  border: 1px solid gray;
  margin: 1%;
  padding: 1%;
}

/*.switch-news {
  float: right;
  margin-right: 50px;
  position: absolute;
  top: 5%;
  left: 85%;
}*/

.searchText {
  height: 20%;
  width: 87%;
  position: absolute;
  top: 70%;
}

.searchbutton {
  width: 10%;
  height: 15%;
  position: absolute;
  left: 89%;
  top: 75%;
}

.label {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
