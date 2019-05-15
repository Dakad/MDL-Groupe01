<template>
  <div class="InfoBase">
    <div class="pdp">
      <img :src="profil.avatar_url">
    </div>
    <div class="info">
      <h3>{{profil.firstname + " "+ profil.name}}</h3>
      <h5>{{profil.email}}</h5>
      <p v-if="profil.university==null">No University Registered</p>
      <p v-else>{{profil.university.name}} ({{profil.university.abbreviation}})</p>
      <p v-if="profil.domain==null">No Domain Registered</p>
      <md-chip
        v-else
        class="chip-c"
        :style="colorDomain"
        title="Activity domain : "
      >{{profil.domain}}</md-chip>
      <p></p>
    </div>
  </div>
</template>

<script>
import { getColorHashOf } from "@/services/util";

export default {
  name: "InfoBase",
  props: ["profil"],

  mounted() {
    this.$nextTick(function() {});
  },
  computed: {
    colorDomain() {
      return {
        "background-color": getColorHashOf(this.profil.domain)
      };
    }
  }
};
</script>

<style scoped>
.pdp {
  float: left;
  height: 25%;
  width: 25%;
  margin: 20px;
}

.info {
  float: left;
  margin: 20px;
}
</style>
