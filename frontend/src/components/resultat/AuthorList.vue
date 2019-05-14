<template>
  <div class="author">
    <md-card v-for="(author,index) in list" :key="index" >
      <div class="contProfil">
        <img :src="author.pp">
      </div>
      <div class="contInfo">
        <md-card-header>
          <div class="Name">
            <h5>
              <router-link v-if="author.username" :to="{name: 'userProfile', params : {username: author.username}}">{{author.firstname}} {{author.lastname}}</router-link>
              <span v-else>{{author.name}}</span>
            </h5>
          </div>
        </md-card-header>
        <md-card-content  v-if="author.username" >
          <div class="contOrga">
            <p>Organization: {{author.organization}}</p>
          </div>
          <div class="contDom">
            <p>Domaine: {{author.domain}}</p>
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
