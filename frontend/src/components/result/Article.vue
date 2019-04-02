<template>
  <div class="article">
    <md-card>
      <md-card-header>
        <div class='title'><h1> <a href="/"> {{title}}</a></h1></div>
      </md-card-header>
      <md-card-content>
        <div class ='info'>
        <p>Written By</p>
        <div class = "author">
          <md-list v-for="author in authors" v-bind:key="author">
            <md-item href="/"> {{author}} </md-item>
          </md-list>
        </div>
        <div class="keyword">
          <!-- TODO modifier l'affichage-->
        </div>
        <div class ="date">
          <p>published on the</p>
          <h4>{{date}}</h4>
        </div>
        </div>
      </md-card-content>
    </md-card>
  </div>

</template>

<script>
export default {
  props:{
    title:String,
    authors:[String],
    date:String
  },
}
</script>

<style scoped>

.md-list{
  margin: 10px;
  display: inline-block
}
</style>
