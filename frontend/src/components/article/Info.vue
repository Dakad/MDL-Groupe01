<template>
  <div>
  <p>Domaine : {{ domaine }}</p>
  <p>Date : {{ datePublication }}</p>
  <p>Price : {{Price}}</p>
  <p>Number of citation : {{ nbCitation }}</p>
  <p>Number of page : {{nbPage}}</p>
  </div>
</template>

<script>
    export default {
      name: "Info",
      data() {
        return {
          // TODO mettre en place la récupération des données du backend
          domaine: 'Informatique',
          datePublication: '12/04/2142',
          Price : '279€',
          nbCitation : '289',
          nbPage : '89'
        };
      },
    }
</script>

<style>

</style>
