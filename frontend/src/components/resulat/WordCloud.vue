<template>
  <div class="wordcloud">
    <div v-for="article in articles" v-bind:key="article">
      {{article.tags}}
    </div>
  </div>
</template>

<script>
  import json from '@/assets/dummy-Results.json';

  export default {
    name: "Graphics",
    data() {
      return {
        json,
        articles:json.results.articles,
      };
    },


  }


</script>
