<template>
  <div class="social">
    <div class="column">
      <h2>Biography</h2>
      <md-card>
        <md-card-header>
          <div class="md-title"></div>
        </md-card-header>

        <md-card-content>{{jsonFollow.Biography}}</md-card-content>
      </md-card>
    </div>
    <div class="column">
      <h2>Followed : {{nbrFollowed}}</h2>
      <!--TODO: Use authorList component-->
      <ul id="Followed">
        <li v-for="people in jsonFollow.Followed" :key="people">{{ people }}</li>
      </ul>
    </div>
    <div class="column">
      <h2>Followers : {{nbrFollowers}}</h2>
      <ul id="Follower">
        <li v-for="people in jsonFollow.Follower" :key="people">{{ people }}</li>
      </ul>
    </div>
  </div>
</template>

<script>
import authorList from "@/components/resultat/AuthorList";
import jsonFollow from "@/assets/dummy-Follow.json";
export default {
  //components: { authorList },
  data() {
    return {
      jsonFollow,
      loading: false
    };
  },
  computed: {
    nbrFollowers: function() {
      return this.jsonFollow.Follower.length;
    },
    nbrFollowed: function() {
      return this.jsonFollow.Followed.length;
    }
  }
};
</script>
<style scoped>
.column {
  float: left;
  width: 33.33%;
  padding: 5px;
}
</style>


