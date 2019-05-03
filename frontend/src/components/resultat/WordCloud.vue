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
      type: Object,
      required: true,
      default: () => []
    }
  },
  components: {
    Cloud
  },
  data() {
    return {};
  },
  computed: {
    list: function() {
      return Object.values(this.tags).map(tag => ({
        text: tag["name"],
        value: Math.floor(Math.pow(10, tag["occur"] - 1) * 1500) + 500
      }));
    }
  },
  methods: {
    fontSizeMapper(word) {
      return Math.log2(word.value) * 4;
    }
  }
};
</script>
