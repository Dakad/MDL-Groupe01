<template>
  <div class="social">
      <h5>Biography</h5>
      <md-card>
        <md-card-header>
          <div class="md-title"></div>
        </md-card-header>

        <md-card-content> {{ bio.bio }} </md-card-content>
      </md-card>
  </div>
</template>

<script>


export default {
  props: ["bio"],
  data() {
    return {
      loading: false
    };
  },
  computed: {

  }
};
</script>
<style scoped>
.column {
  float: left;
  width: 32%;
  padding: 5px;
  margin: 0.5%;
}
</style>


