<template>
  <div class="wordcloud">
    <div v-for="article in articles" v-bind:key="article">
      {{article.tags}}
    </div>
    {{ articles[1].tags.length}}

  </div>
</template>

<script>
  import json from '@/assets/dummy-Results.json';

  export default {
    name: "Graphics",
    data() {
      return {
        json,
        articles:json.results.articles
      };
    },
    computed:{
      text: function(){
        var text = String;
        for (let i = 0; i< this.articles.length; i++) {
          for(let j =0;j<this.articles[i].tags.length; j++){
            text+= articles[i].tags[j] + " "
            
          }
          
        }
        return text
      }
    }


  }


</script>
