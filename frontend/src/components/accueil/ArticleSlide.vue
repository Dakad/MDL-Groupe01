<template>
    <div class="slide">
      <h3>{{ title }}</h3>
      <h5 v-for="name in authors" :key="name">{{ name }}</h5>
      <h5>{{ domain }}, {{ date }}</h5><br>
      <br>
      <h6>Views: {{ nbViews }}     Quotes: {{ nbQuotes }}</h6>
      <md-chip v-for="(word,index) in keywords" :key="index">{{ word }}</md-chip>
    </div>
</template>

<script>
import { Slide } from "vue-carousel";

export default {
  name: "ArticleSlide",
  props: ["title", "authors", "domain", "date", "nbViews", "nbQuotes", "keywords"]
};
</script>

<style scoped>
</style>