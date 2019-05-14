<template>
  <div class="wordcloud">
    
      <wordcloud
      :data="tagList"
      nameKey="name"
      valueKey="value"
      :color="myColors"
      :showTooltip="false"
      :fontSize=[30,50]
      :wordClick="wordClickHandler">
      ></wordcloud>
  </div>
</template>

<script>
import Cloud from "vue-d3-cloud";
import wordcloud from 'vue-wordcloud';

export default {
  name: "WordCloud",
  props: {
    tags: {
      type: Object,
      required: true,
      default: () => []
    },moreTags:{}
  },
  components: {
    Cloud,
    wordcloud
  },
  data() {
    return {
      myColors: "Category10"
    };
  },
  computed: {
    list: function() {
      return Object.values(this.tags).map(tag => ({
        text: tag["name"],
        value:tag["occur"]
      }));
    },
    tagList: function(){
      var list=[]

      for (let i = 0;i< this.moreTags.length; i++){
          list.push({"name": this.moreTags[i][0], 
                     "value":this.moreTags[i][1]*1000
                     })
      }

      return(list)
    }
  },
  methods: {
    fontSizeMapper(word) {
      return Math.log2(word.value) * 4;
    },
        wordClickHandler(name, value, vm) {
      console.log('wordClickHandler', name, value, vm);
    }
  }
};
</script>
