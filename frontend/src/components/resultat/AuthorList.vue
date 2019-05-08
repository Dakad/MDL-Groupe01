<template>
  <div class="author">
    <md-card v-for="author in list" >
      <div class="contProfil">
        <img :src="author.pdp">
      </div>
      <div class="contInfo">
        <md-card-header>
          <div class="Name">
            <h5>
              <router-link :to="{name: 'profil', params : {slug : author.slug}}">{{author.name}}</router-link>
            </h5>
          </div>
        </md-card-header>
        <md-card-content>
          <div class="contOrga">
            <p>{{author.organisation}}</p>
          </div>
          <div class="contDom">
            <p>{{author.domaine}}</p>
          </div>
        </md-card-content>
      </div>
    </md-card>
  </div>
</template>

<script>
export default {
  props: {
    list: Array
  },
  data() {
    return {};
  },
  computed: {
    authors() {
      console.log(this.list)
      return this.list || [];
    },
  }
};
</script>

<style scoped>
.md-list {
  margin: 10px;
  display: inline-block;
}
  .contProfil{
    float: left;
  }
  .contInfo{
    float: left;
  }
  .contOrga{
    float: left;
    margin-left: 5px;
    margin-right: 30px;
  }
  .contDom{
    float: left;
    margin-left: 30px;
    margin-right: 30px;
  }
</style>
