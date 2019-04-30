<template>
    <slide class="slide">
      <h3>{{ title }}</h3>
      <h5 v-for="name in authors" :key="name">{{ name }}</h5>
      <h5>{{ domain }}, {{ date }}</h5><br>
      <br>
      <h6>Views: {{ nbViews }}     Quotes: {{ nbQuotes }}</h6>
      <md-chip v-for="(word,index) in keywords" :key="index">{{ word }}</md-chip>
    </slide>
</template>

<script>
import { Slide } from "vue-carousel";

export default {
  name: "ArticleSlide",
  components: {
    Slide
  },
  props: ["title", "authors", "domain", "date", "nbViews", "nbQuotes", "keywords"]
};
</script>

<style scoped>
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

h1 {
  margin-left: 50px;
  margin-top: 35px;
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
</style>
