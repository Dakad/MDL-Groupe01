<template>
  <div>
    <h5>List of reference:</h5>
    <p> {{ callRef }} </p>
  </div>
</template>

<script>
    export default {
        name: "Ref",
      data() {
          return{
            // TODO mettre en place la récupération des données du backend
            callRef: 'reference list',
          };
      },
    }
</script>

<style>

</style>
