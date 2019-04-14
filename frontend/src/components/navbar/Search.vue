<template>
  <div class="search">
    <div class="searchText">
      <md-field>
        <label>Type your search here</label>
        <md-input v-model="searchInput" @keyup.enter="searchIt"></md-input>
        <span class="md-helper-text">Think well about your keywords and don't forget our filter</span>
        <md-button class="md-icon-button" @click="searchIt()">
          <md-icon>search</md-icon>
        </md-button>
      </md-field>
    </div>
    <div class="searchbutton">
      <md-button class="md-raised" v-on:click="searchIt()">search</md-button>
    </div>
  </div>
</template>


<style lang="scss" scoped>
.signin-dialog {
  width: 55%;
}
// .md-dialog {
//   widows: 100%;
// }
</style>


<script>
export default {
  name: "Search",
  data: function() {
    return {
      searchInput: null
    };
  },
  methods: {
    searchIt() {
      this.$router.push({
        name: "resultat",
        query: { search: this.searchInput }
      });
    }
  }
};
</script>

<style>
</style>
