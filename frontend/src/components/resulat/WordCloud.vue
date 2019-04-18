<template>
  <div class="wordcloud">
     <cloud :data="text" :fontSizeMapper="fontSizeMapper" />


  </div>
</template>

<script>
  import json from '@/assets/dummy-Results.json';
  import Cloud from 'vue-d3-cloud';

  export default {
    name: "Graphics",
    data() {
      return {
        json,
        articles:json.results.articles,
        newtest: "coucou",
        fontSizeMapper: word => Math.log2(word.value) * 4,
      };
    },
    computed:{
      text: function(){
        var wordList =[]
        var wordsUsed=[]
        for (let i = 0; i < this.articles.length; i++){
          for (let j = 0; j < this.articles[i].tags.length; j++) {
            if(!wordsUsed.includes(this.articles[i].tags[j])){
                 wordList.push({text: this.articles[i].tags[j], value: Math.floor(Math.random() * 150000) + 5000})
                 wordsUsed.push(this.articles[i].tags[j])
            }
          }
          
        }
        return wordList
      }



  },
  components: {
        Cloud,
    },
}
</script>
