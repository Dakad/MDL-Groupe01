<template>

  <section class="accueilapp">
    <header class="header">
      <!-- TODO mettre la barre supérieure avec login,... mais sans la barre de recherche -->
      <navappregister/>
    </header>


    <div class="central">
      <img class="logoimg" src="../assets/logochicken.jpg" style="width:100px;height:100px;">
      <div class="title">
        <h1>No Assigned Name</h1>
      </div>


      <!-- place the searched words in var searchwords and the action to
       script the search is searchIt -->
      <input class="searchText" v-model="searchwords" placeholder="search a state of the art">
      <div class="searchbutton">
        <button v-on:click="searchIt"  class="fa fa-search">search</button>
      </div>


      <div class="filter">

      </div>

    </div>

    <div class="recommended">

      <p>recommended</p>

      <div class="recoNews">
        <button v-on:click="switcher">recommander</button>
      </div>

    </div>
  </section>

</template>







<script>
  import 'swiper/dist/css/swiper.css';
  import { swiper, swiperSlide } from 'vue-awesome-swiper';
  import navappregister from './NavBarRegister.vue';

  export default {  
    name:'app',
    components:{
      navappregister
    }
  };
</script>






<style>
  @import url("https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css");
  img{
    float: left;
  }

  .central {
    position: absolute;
    top: 20%;
    left: 50%;
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    width: 75%;
    height: 30%;
    overflow: auto;
  }

  .logoimg{
    width: 10%;
    height: 10%;
    position: absolute;
    left: 20%;
    top: 30%;
  }

  .title{
    position: absolute;
    top: 5%;
    left: 35%;
  }

  h1 {
    padding: 50px;
  }

  p {
    margin-left: 15px;
    width: 20%;
  }

  .recommended {
    margin: 0;
    position: absolute;
    top: 75%;
    left: 50%;
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    width: 75%;
    height: 40%;
    border: 3px solid gray;
    overflow: auto;
    z-index: -1;
  }

  .recoNews {
    float: right;
    margin-right: 50px;
    position: absolute;
    top: 5%;
    left: 90%;
  }

  .searchText {
    width: 90%;
    height: 7%;
    position: absolute;
    left: 1%;
    top: 87%;
  }

  .searchbutton{
    width: 10%;
    height: 15%;
    position: absolute;
    left: 92%;
    top:87%;
  }


</style>
