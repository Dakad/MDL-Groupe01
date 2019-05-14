<template>
  <section class="accueilapp md-layout md-alignment-top-center">
    <div class="central md-layout-item md-size-70 md-layout md-alignment-top-center">
      
      <div class="head md-layout-item md-size-80 md-layout md-gutter md-alignment-top-center">
        <img class="logoimg md-layout-item md-size-15" src="../assets/logo-app.png" style="width:90px;height:100px;">
        <div class="title md-layout-item md-size-40"><h1>Froggosaur</h1></div>
        <!-- ou md-size-35? -->
      </div>

      <div class="search md-layout-item md-size-80">
        <h2>{{msg}}</h2>

        <!-- place the searched words in var searchwords and the action to
        script the search is searchIt-->

        <search id="search" @error="msg = $event"></search>
      </div>
    </div>  

    <div class="recommended md-layout-item md-size-80">
      <carousel
        :per-page="3"
        :autoplay="true"
        :loop="true"
        :autoplayTimeout="5000"
        :navigationEnabled="true"
      >
        <slide class="slides" v-for="(recommended, index) in articles" :key="index">
          <article-slide
            :title="recommended.title"
            :authors="recommended.authors"
            :category="recommended.category"
            :year="recommended.year"
            :month="recommended.month"
            :nb-views="recommended.nb_views"
            :nb-quotes="recommended.nb_citations"
            :keywords="recommended.keywords.slice(0,4)"
            :reference="recommended.reference"
          ></article-slide>
        </slide>
      </carousel>
    </div>
  </section>
</template>

<script>
import { Carousel, Slide } from "vue-carousel";

import ArticleSlide from "@/components/accueil/ArticleSlide";

import Search from "@/components/navbar/Search";

import { getRecommanded } from "../services/api-article";

export default {
  name: "Accueil",
  components: {
    Carousel,
    Slide,
    ArticleSlide,
    Search
  },
  data() {
    return {
      searchInput: null,
      msg: "",
      articles: {},
    };
  },

  created() {
    this.fetchRecommanded();
  },

  methods:{
    fetchRecommanded(){
      return getRecommanded()
        .then(data => {
          this.articles = data;
        });
    }
  },





  searchIt() {}
};
</script>

<style scoped>
/* img {
  float: left;
} */

/* .logoimg {
  width: 10%;
  height: 10%;
  position: absolute;
  left: 20%;
}

.title {
  position: absolute;
  left: 35%;
} */

.head {
  margin-top: 2%;
}

h1 {
  margin-left: 47px;
  margin-top: 35px;
}

/* #search {
  margin-top: 100px;
} */

.search {
  margin-top: 2%;
}

.recommended {
  margin-top: 2%;
}

.slides {
  margin: 3px;
}
</style>
