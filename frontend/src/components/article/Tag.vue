<template>
  <div>
    <h5>List of tag:</h5>
    <p>{{tagList}}</p>
  </div>
</template>

<script>
    export default {
      name: "Tag",
      data() {
        return {
          // TODO mettre en place la récupération des données du backend
          tagList: 'Info, ML, Vizu, things',
        };
      },
    }
</script>

<style>

</style>
