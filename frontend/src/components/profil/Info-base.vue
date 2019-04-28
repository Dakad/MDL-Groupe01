<template>
    <div class="InfoBase">
      <div class="pdp">
        <img :src="pdpUrl">
      </div>
      <div class="info">
          <h3>{{ firstLastName }}</h3>
          <p> {{university}} </p>
          <p> {{domain}} </p>
          <p> {{email}} </p>
      </div>
    </div>
</template>

<script>
    export default {
        name: "InfoBase",
        props: ["profil"],
      data() {
        return {
          firstLastName: null,
          university: null,
          domain: null,
          email: null,
          pdpUrl: null,
        };
      },

       created() {
            this.firstLastName = this.profil.firstname + " " + this.profil.name;
            this.university = this.profil.university;
            this.domain = this.profil.domain;
            this.email = this.profil.email;
            this.pdpUrl = this.profil.profilePictureURL;
      },
       mounted() {
         this.$nextTick(function() {});
       }
    }
</script>

<style scoped>
  .pdp {
    float: left;
    height: 10%;
    width: 10%;
    margin: 20px;
  }

  .info {
    float: left;
    margin: 20px;
  }
</style>
