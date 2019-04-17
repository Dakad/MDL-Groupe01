<template>
    <div class="InfoBase">
      <div class="pdp">
        <img :src="profil.profilePictureURL">
      </div>
      <div class="info">
          <h3>{{ firstLastName }}</h3>
          <p> {{profil.university}} </p>
          <p> {{profil.domain}} </p>
          <p> {{profil.email}} </p>
      </div>
    </div>
</template>

<script>
    export default {
        name: "InfoBase",
        props: ["profil"],
      data() {
        return {
          firstLastName: profil.firstname + " " + profil.name
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
