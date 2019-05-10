<template>
    <div class="slide">
      <router-link :to="{ name: 'articleDetails', params: { reference: reference }}">
          <h3>{{ title }}</h3>
      </router-link>
      <span v-for="(name,index) in authors" :key="name">{{ name }}&nbsp;<b v-if="index<authors.length-1">,</b>&nbsp;</span><br>
      <h5>{{ domain }}, {{ date }}</h5><br>
      <br>
      <h6>Views: {{ nbViews }}     Quotes: {{ nbQuotes }}</h6>
      <md-chip class="chip" v-for="(word) in keywords" :key="word.slug">{{ word.name }}</md-chip>
    </div>
</template>

<script>
  import { Slide } from "vue-carousel";

  export default {
    name: "ArticleSlide",
    props: ["title", "authors", "domain", "date", "nbViews", "nbQuotes", "keywords", "reference"]
  };
</script>

<style scoped>
  .chip {
    margin: 3px;
  }
</style>