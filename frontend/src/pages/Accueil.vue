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

  <!--<div class="recommended">
      <p>recommended</p>
      <div class="switch-news">
        <md-button class="md-raised" v-on:click="switcher">What might interest you</md-button>
      </div>
      <div id="slider">
        <carousel autoplay=true>
          <slide>
            <span class="label">1</span>
          </slide>
        </carousel>
      </div>
    </div>-->
    <div class="recommended">
    <carousel :autoplayLoop="true" :autoplay="true" :autoplayTimeout="3000" :per-page="2">
    <slide class="slide">
      <h3>Titre</h3>
      <h5>Auteur(s)</h5>
      <h5>Domaine(s), date</h5><br>
      <br>
      <h6>Vues: ...     Citations: ...</h6><br>
      <h7>Keywords</h7>
    </slide>
    <slide>
      <h3>Titre</h3>
      <h5>Auteur(s)</h5>
      <h5>Domaine(s), date</h5><br>
      <br>
      <h6>Vues: ...     Citations: ...</h6><br>
      <h7>Keywords</h7>
    </slide>
    <slide>
      <h3>Titre</h3>
      <h5>Auteur(s)</h5>
      <h5>Domaine(s), date</h5><br>
      <br>
      <h6>Vues: ...     Citations: ...</h6><br>
      <h7>Keywords</h7>
    </slide>
    <slide>
      <h3>Titre</h3>
      <h5>Auteur(s)</h5>
      <h5>Domaine(s), date</h5><br>
      <br>
      <h6>Vues: ...     Citations: ...</h6><br>
      <h7>Keywords</h7>
    </slide>
  </carousel>
  </div>
  </section>
</template>



<script>
import { Carousel, Slide } from "vue-carousel";

import Search from '@/components/navbar/Search';

export default {
  name: "Accueil",
  components: {
    Carousel,
    Slide,
    Search
  },
  data() {
    return {
      searchInput: null,
      //carrouselAutoplay: true
    };
  },
  methods: {
    // buildSlideMarkup(count) {
    //   let slideMarkup = "";
    //   for (var i = 1; i <= count; i++) {
    //     slideMarkup += '<slide><span class="label">' + i + "</span></slide>";
    //   }
    //   return slideMarkup;
    // }
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
  margin-top: 150px;
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
  height: 40%;
  /*border: 3px solid gray;*/
  overflow: auto;
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

/*.VueCarousel-slide {
  position: relative;
  background: #42b983;
  color: #fff;
  font-family: Arial;
  font-size: 24px;
  text-align: center;
  min-height: 50px;
}*/

.label {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

/*#slider {
  width: 80%;
  height: 70%;
  position: absolute;
  top: 20%;
  left: 10%;
}*/
</style>
