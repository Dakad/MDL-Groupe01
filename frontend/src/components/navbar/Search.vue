<template>
  <div class="search">
    <div class="searchText">
      <md-field :md-inline="isInModeNavbar">
        <label>Type your search here</label>
        <md-input v-model="searchInput" @keyup.enter="searchIt"></md-input>
        <span v-if="!isInModeNavbar" class="md-helper-text">{{helperText}}</span>
        <md-button class="md-icon-button" @click="searchIt()">
          <md-icon>search</md-icon>
        </md-button>
      </md-field>
    </div>
    <div v-if="!isInModeNavbar" class="searchbutton">
      <md-button class="md-raised" v-on:click="searchIt()">search</md-button>
    </div>
  </div>
</template>


<style lang="scss" scoped>
</style>


<script>
export const MODE_NAVBAR = "in_navbar";

export default {
  name: "Search",
  props: ["mode"],
  data: function() {
    return {
      helperText: "Think well about your search and don't forget our filter",
      searchInput: null
    };
  },
  mounted() {},

  computed: {
    isInModeNavbar() {
      return this.mode == MODE_NAVBAR;
    },
    placeholder() {
      if (this.isInModeNavbar) {
        return this.helperText.split(" and ")[0];
      } else {
        return null;
      }
    }
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
