<template>
    <div class="InfoBase">
      <div class="pdp">
        <img src="toDetermine">
      </div>
      <div class="info">
          <h3>{{ firstLastName }}</h3>
          <p> {{workPlace}} </p>
          <p> {{workingDomaine}} </p>
          <p> {{mail}} </p>
      </div>
    </div>
</template>

<script>
    export default {
        name: "InfoBase",
      data() {
        return {
          firstLastName: "Jean Sive",
          workPlace: "UNamur",
          workingDomaine: "Medecine Dentaire",
          mail: "jean.sive@unamur.be"
        };
      },
    }
</script>

<style scoped>
  .pdp {
    float: left;
  }

  .info {
    float: left;
  }
</style>
