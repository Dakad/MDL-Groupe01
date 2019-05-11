<template>
  <div class="InfoBase">
    <div class="pdp">
      <img :src="profil.avatar">
    </div>
    <div class="info">
      <h3>{{profil.firstname + " "+ profil.name}}</h3>
      <p v-if="profil.university==null">No University Registered</p>
      <p v-if="profil.university!=null">{{profil.university}}</p>
      <p v-if="profil.domain==null">No Domain Registered</p>
      <p v-if="profil.domain!=null">{{profil.domain}}</p>
      <p>{{profil.email}}</p>
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
      pdpUrl: null
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
};
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
