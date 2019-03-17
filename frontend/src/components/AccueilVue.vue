<template>

  <section class="accueilapp">

    <head>
      <link rel="stylesheet" href="@/font-awesome-4.7.0/css/font-awesome.min.css">
    </head>

    <header class="header">
      <!-- TODO mettre la barre supérieure avec login,... mais sans la barre de recherche -->
    </header>


    <div class="central">
      <div class="tittle">
        <h1> <img src="../assets/logochicken.jpg" style="width:100px;height:100px;">
          Non Assigned Name</h1>
      </div>
      <div class="searchbar">
        <!-- place the searched words in var searchxords and the action to
        script the search is searchIt -->
        <input class="searchText" v-model="searchwords" placeholder="search a state of the art">
        <button v-on:click="searchIt"  class="fa fa-home">search</button>
      </div>
      <div class="filter">

      </div>
      <div class="recommended">

      </div>
    </div>

  </section>

</template>

<script>export default { };
</script>

<style>
  img{
    float: left;
  }

  .central {
    margin: 0;
    position: absolute;
    top: 15%;
    left: 50%;
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    overflow: auto;
    text-align: center;
  }
  h1 {
    padding: 50px;
  }
</style>
