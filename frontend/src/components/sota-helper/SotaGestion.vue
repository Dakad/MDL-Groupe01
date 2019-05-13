<template>
  <div>
    <div class="left">
      <h4>Current articles in the SotA</h4>
      <ul id="articlesShow">
        <li v-for="article in articles" :key="article">
          <md-card md-with-hover>
            <md-card-header>
              <div class="md-title">{{article.title}}</div>
            </md-card-header>

            <md-card-content>
              <p>Author : {{article.user}}</p>
              <p>Published on the {{article.publicationDate}}</p>
            </md-card-content>
          </md-card>
        </li>
      </ul>
    </div>
    <div class="right">
      <!-- TODO Replace by The SortOption Component used on the Result page -->
      <div class="first">
        <h2>Sort by :</h2>
        <md-radio v-model="sortBy" value="name">Name</md-radio>
        <md-radio v-model="sortBy" value="title">Title</md-radio>
        <md-radio v-model="sortBy" value="date">Date</md-radio>
      </div>
      <hr>
      <div class="second">
        <h2>Order by :</h2>
        <md-radio v-model="orderBy" value="asc">Ascending</md-radio>
        <md-radio v-model="orderBy" value="desc">Descending</md-radio>
        <small>{{ sortBy }} + {{ orderBy }}</small>
      </div>
      <md-button class="md-raised md-primary">Export all refs in bibTex</md-button>
      <md-button class="md-raised md-primary">Export state of the art for offline storage</md-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "SotAGestion",
  data() {
    return {
      data: {},
      articles: [],
      sortBy: "name",
      orderBy: "asc"
    };
  }
};
</script>

<style scoped>
.left {
  position: relative;
  float: left;
  width: 47%;
  height: 75%;
  margin: 1%;
}
.right {
  position: relative;
  float: left;
  width: 47%;
  height: 75%;
  margin: 1%;
}
.md-radio {
  display: flex;
}
.md-button {
  position: relative;
  display: flex;
  margin: 1%;
}
</style>
