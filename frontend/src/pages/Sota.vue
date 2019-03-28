<template>
  <div class="article">
    <Search></Search>
    <div class="leftContainer">
      <h1> {{ articleTitle }} </h1>
      <div class="menuBo"><menuSota></menuSota></div>
      <div class="abstrct"> <p> {{ abstract }} </p> </div>
    </div>
    <div class="rightContainer">
      <infoNav></infoNav>
    </div>

  </div>
</template>

<script>
  import InfoNav from '@/components/article/InfoNav';
  import MenuSota from '@/components/Sota/MenuSota';


  export default {
    name: "Sota",
    components: {
      InfoNav,
      MenuSota
    },
    data() {
      return {
        articleTitle: 'Dummy title of SOTA',
        abstract: 'Import listing of the article in the sota'
      };
    },
  }

</script>

<style>
  h1{
    position: absolute;
    left: 10%;
    width: 70%;
    top : 10%;
  }

  .menuBo{
    padding-top: 15%;
    padding-left: 10%;
    width: 25%;
    float: right;
  }

  .abstrct{
    float: bottom;
    padding-top: 10%;
    padding-left: 15%;
    width: 80%;
  }

  infoNav{
    padding-top: 10%;
    padding: 10%;
  }

  .leftContainer{
    position: relative;
    float: left;
    width: 60%;
    height: 80%;
  }

  .rightContainer{
    padding-right: 5px;
    padding-top: 5%;
    float: right;

  }
</style>

