<template>
  <section class="accueilapp md-layout md-alignement-center-center">
    <div class="central md-layout-item md-size-80 md-layout">
      
      <div class="md-layout-item md-size-80 md-layout md-gutter">
        <img class="logoimg md-layout-item md-size-15" src="../assets/logo-app.png" style="width:90px;height:100px;">
        <div class="title md-layout-item"><h1>Froggosaur</h1></div>
      </div>

      <div class="md-layout-item md-size-80">
        <h2>{{msg}}</h2>

        <!-- place the searched words in var searchwords and the action to
        script the search is searchIt-->

        <search id="search" @error="msg = $event"></search>
      </div>
    </div>  

    <div class="recommended md-layout-item">
      <carousel
        :per-page="3"
        :autoplay="true"
        :loop="true"
        :autoplayTimeout="5000"
        :navigationEnabled="true"
      >
        <slide class="slides" v-for="(recommended,index) in articles" :key="index">
          <article-slide
            :title="recommended.title"
            :authors="recommended.authors"
            :category="recommended.category"
            :date="recommended.created_at"
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
  searchIt() {}
};
</script>

<style scoped>
img {
  float: left;
}

/* .central {
  position: absolute;
  top: 30%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  width: 75%;
  height: 35%;
  overflow: auto;
} */

.central {
  margin: 10%;
}

/* .l {
  position: relative;
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

/* h1 {
  margin-left: 50px;
  margin-top: 35px;
} */

/* #search {
  margin-top: 140px;
} */

p {
  margin-left: 15px;
  width: 20%;
}

/* .recommended {
  margin: 0;
  position: absolute;
  top: 75%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  width: 75%;
  height: 47%;
} */

.recommended {
  margin: 10%;
}

.slides {
  margin: 1%;
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