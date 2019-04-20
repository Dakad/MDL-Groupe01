<template>
  <div class="wordcloud">
    <cloud :data="list" :fontSizeMapper="fontSizeMapper"/>
  </div>
</template>

<script>
import json from "@/assets/dummy-Results.json";
import Cloud from "vue-d3-cloud";

export default {
  name: "WordCloud",
  props: {
    tags: {
      type: Array,
      required: true,
      default: []
    }
  },
  components: {
    Cloud
  },
  data() {
    return {
      articles: json.results.articles
    };
  },
  computed: {
    list: function() {
      return this.tags.map(t => ({
        text: t,
        value: Math.floor(Math.random() * 150000) + 5000
      }));
    }
    // text: function() {
    //   var wordList = [];
    //   var wordsUsed = [];
    //   for (let i = 0; i < this.articles.length; i++) {
    //     for (let j = 0; j < this.articles[i].tags.length; j++) {
    //       if (!wordsUsed.includes(this.articles[i].tags[j])) {
    //         wordList.push({
    //           text: this.articles[i].tags[j],
    //           value: Math.floor(Math.random() * 150000) + 5000
    //         });
    //         wordsUsed.push(this.articles[i].tags[j]);
    //       }
    //     }
    //   }
    //   return wordList;
    // }
  },
  methods: {
    fontSizeMapper(word) {
      return word => Math.log2(word.value) * 4;
    }
  }
};
</script>
