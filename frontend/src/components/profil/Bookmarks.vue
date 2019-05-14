<template>
    <div id="bookmarks">
        <div class="article">
            <div v-for="item in articles" :key="item">
                <md-card md-with-hover>
                <md-card-header>
                <router-link :to="{ name: 'articleDetails', params: { reference: item.reference }}">
                    <h6>{{item.title}}</h6>
                </router-link>
                </md-card-header>
                  <md-card-content>
                    <div class="left1">
                      <p>Domain: {{ item.category }}</p>
                    </div>
                    <div class="right1">
                      <p>Year of publication: {{ item.year }} </p>
                    </div>
                  </md-card-content>
                </md-card>
            </div>
        </div>
    <h2 v-if="sota.length!=0">State of the art bookmarked</h2>
        <div class="sota">
            <div v-for="item in sota" :key="item">
                <md-card md-with-hover>
                <md-card-header>
                <router-link :to="{ name: 'sotaDetails', params: { reference: item.reference }}">
                    <h4>{{item.title}}</h4>
                </router-link>
                </md-card-header>
                </md-card>     
            </div>       

        </div>
        
    </div>

</template>

<script>
export default {
    name:"bookmarks",
    props:["bookmarked"],
    computed:{
        articles: function(){
            var list=[];
            for (let index = 0; index < this.bookmarked.length; index++) {
                if(this.bookmarked[index].article != null){
                    list.push(this.bookmarked[index].article)
                }
            }
            return list;
        },
        sota:function(){
            var list=[];
            for (let index = 0; index < this.bookmarked.length; index++) {
                if(this.bookmarked[index].sota != null){
                    list.push(this.bookmarked[index].sota)
                }
            }
            return list;            
        }

    }
    
}
</script>

<style scoped>

  .left1{
    float: left;
    margin-right: 5%;
  }
  .right1{
    float: left;
    margin-left: 5%;
  }
</style>
