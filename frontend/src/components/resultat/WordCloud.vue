<template>
  <div class="wordcloud">
      <wordcloud
      :data="tagList"
      nameKey="name"
      valueKey="value"
      :color="myColors"
      :showTooltip="false"
      :fontSize=[13,40]
      :wordClick="wordClickHandler">
      ></wordcloud>
      <md-snackbar :md-active.sync="showSnackbar" :md-duration="4000">
        <span>{{snackMsg}}</span>
    </md-snackbar>
  </div>
</template>

<script>
import Cloud from "vue-d3-cloud";
import wordcloud from 'vue-wordcloud';

export default {
  name: "WordCloud",
  props: {tags:{}},
  components: {
    Cloud,
    wordcloud
  },
  data() {
    return {
      myColors: "Category10",
      snackMsg:null,
      showSnackbar:false
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

      for (let i = 0;i< this.tags.length; i++){
          list.push({"name": this.tags[i][0], 
                     "value":this.tags[i][1]*1000
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
          this.showSnackbar=true;
          this.snackMsg="Tag: \""+name+"\" Occurence: "+value/1000
      console.log('wordClickHandler', name, value, vm);
    }
  }
};
</script>
