<template>
  <section class="accueilapp md-layout md-alignment-top-center">
    <div class="central md-layout-item md-size-70 md-layout md-alignment-top-center">
      
      <div class="head md-layout-item md-size-80 md-layout md-gutter md-alignment-top-center">
        <img class="logoimg md-layout-item md-size-15" src="@/assets/logo-app.png" style="width:90px;height:100px;">
        <div class="title md-layout-item md-size-40"><h1>Froggosaur</h1></div>
        <!-- ou md-size-35? -->
      </div>

      <div class="search md-layout-item md-size-80">

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
        <slide class="slides" v-once v-for="(recommended,index) in articles" :key="index">
          <article-slide
            :title="recommended.title"
            :authors="recommended.authors"
            :category="recommended.category"
            :year="recommended.year"
            :month="recommended.month"
            :nb-views="recommended.nb_views"
            :nb-quotes="recommended.nb_citations"
            :keywords="recommended.keywords"
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

import dummyArticles from "@/services/dummy/articles.json";

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
      articles: dummyArticles,
    };
  },
};
</script>

<style scoped>

.head {
  margin-top: 2%;
}

h1 {
  margin-left: 47px;
  margin-top: 35px;
}


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
