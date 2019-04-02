<template>
  <b-container fluid class="About">
    <b-row class="title">
      <b-col>
        <h1>About Us</h1>
        <p>Froggosaur est une application web qui vous permettra de rechercher et de créer des états de l'art.</p>
      </b-col>
    </b-row>
    
    <b-row class="title">
      <b-col>
        <h2>Our Team</h2>
      </b-col>
    </b-row>

    <div class="card-expansion">
      <person v-for="(member,index) in team" v-bind:key="member.name" :position="index" :image="member.avatar" :name="member.name" :role="member.role" :descr="member.descr"></person>
    </div>   
  </b-container>
</template>

<script>
import Person from "../components/about/Person.vue";
import { getTeam } from "../services/api";
export default {
  name: "About",
  components: {
    Person
  },
  data: function () {
    return {
      team: null
    }
  },
  mounted() {
    this.getTeam()
  },
  methods: {
    getTeam() {
      getTeam().then(data=>this.team=data)
    }
  }
};
</script>


<style scoped>
  .title {
    text-align: center;
  }
  .card-expansion {
    height: 480px;
    text-align: center;
  }
  .md-card {
    width: 320px;
    margin: 4px;
    display: inline-block;
    vertical-align: top;
  }
</style>