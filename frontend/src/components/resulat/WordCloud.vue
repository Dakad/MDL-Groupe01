<template>
  <div class="wordcloud">
    <div v-for="article in articles" v-bind:key="article">
      {{article.tags}}
    </div>

    {{text}}

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
    computed:{
      text: function(){
        var words = [{text: '', value: 0}];
        for (let i = 0; i< this.articles.length; i++) {
          for(let j =0;j<this.articles[i].tags.length; j++){
            for(let k=0;k<words.length;k++){
              if(this.articles[i].tags[j]==words[k].text){
                words[k].value +=1
            }else{
              console.log(this.articles[i].tags[j])
              words.push({text: this.articles[i].tags[j], value: 1})
            }
              
          }

        }
        
      }
      return words
    }


  }
}
</script>
