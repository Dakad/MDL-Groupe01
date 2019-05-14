<template>
  <div class="search">
    <div class="searchText">
      <md-field :md-inline="isInModeNavbar">
        <label>Type your search here</label>
        <md-input v-model.trim.lazy="searchInput" @keyup.enter="searchIt"></md-input>
        <span v-if="!isInModeNavbar" class="md-helper-text">{{helperText}}</span>
        <md-button class="md-icon-button" @click="searchIt()">
          <md-icon>search</md-icon>
        </md-button>
      </md-field>
    </div>
    <!-- <div v-if="!isInModeNavbar" class="searchbutton">
      <md-button class="md-raised" v-on:click="searchIt()">search</md-button>
    </div>-->
  </div>
</template>


<style lang="css" scoped>
</style>


<script>
export const MODE_NAVBAR = "in_navbar";
export const DEFAULT_MIN_LENGTH = 2;

export default {
  name: "Search",
  props: {
    mode: String,
    term: String,
    minLength: {
      type: Number,
      default: () => DEFAULT_MIN_LENGTH
    }
  },
  data: function() {
    return {
      helperText: "Think well about it",
      searchInput: this.term
    };
  },
  mounted() {},
  watch: {
    term: function(value) {
      this.searchInput = value;
    }
  },

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
      if (!this.searchInput || this.searchInput.length < this.minLength) {
        this.$emit(
          "error",
          `At least ${this.minLength} characters to run search !`
        );
      } else {
        this.$router.push({
          name: "resultat",
          query: { search: this.searchInput }
        });
      }
    }
  }
};
</script>

<style>
</style>
