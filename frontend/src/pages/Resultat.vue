<template>
  <section class="Research">
    <div class="tabs">
      <md-tabs>
        <md-tab id="sota" md-label="State Of The Art">
          <!--Todo : Make a list of state art-->
          <sota
            title="Action Movies"
            date="28-03-2019"
            v-bind:authors="['Jean-claude Van Damme', 'Bruce Willis']"
          />
          <sota
            title="Computer Sciences"
            date="20-06-1945"
            v-bind:authors="['Von neuman', 'Alan Turing', 'Ada Lovelace']"
          />
          <sota title="Complot" date="11-09-2001" v-bind:authors="['Georges Bush', 'Al Gore']"/>
        </md-tab>
        <md-tab id="article" md-label="Article">
          <!--Todo : make a list of article-->
          <articles
            title="La visualisation des ovnis"
            date="01-01-2019"
            v-bind:authors="['Bruno Dumas']"
          />
          <articles
            title="Le réchauffement climatique en musique"
            date="27-09-2005"
            v-bind:authors="['Joe Duplantier',' Mario Duplantier', ' Christian Andreu', 'Jean-Michel Labadie', 'Alexandre Cornillon']"
          />
          <articles
            title="Machine Learning : visualisation quality"
            date="08-12-2018"
            v-bind:authors="['Benoit Frenay','Adrien Bibal']"
          />
          <articles
            title="Global warming: Creation of china "
            date="01-04-2019"
            v-bind:authors="['Donald Trump']"
          />
        </md-tab>
        <md-tab id="author" md-label="Author">
          <author/>
        </md-tab>
        <md-tab id="graphics" md-label="Graphics">
          <graphics/>
        </md-tab>
      </md-tabs>
    </div>

    <div class="first">
      <h2>Sort by :</h2>
      <md-radio v-model="sortBy" value="name">Name</md-radio>
      <md-radio v-model="sortBy" value="title">Title</md-radio>
      <md-radio v-model="sortBy" value="date">Date</md-radio>

      <!-- <md-radio v-model="sortBy" value="domain">Domain of Research</md-radio>
      <md-radio v-model="sortBy" value="date">Date</md-radio>
      <md-radio v-model="sortBy" value="views">Views</md-radio>
      <md-radio v-model="sortBy" value="ref">References</md-radio>-->
    </div>
    <hr>
    <div class="second">
      <h2>Order by :</h2>
      <md-radio v-model="orderBy" value="asc">Ascending</md-radio>
      <md-radio v-model="orderBy" value="desc">Descending</md-radio>
      <small>{{ sortBy }} + {{ orderBy }}</small>
    </div>
  </section>
</template>

<script>
import sota from "@/components/resulat/Sota";
import author from "@/components/resulat/Author";
import graphics from "@/components/resulat/Graphics";
import articles from "@/components/resulat/Article";

import { getSearchResults } from "@/services/api";

export default {
  components: {
    sota,
    author,
    graphics,
    articles
  },
  data() {
    return {
      loading: false,
      searchTerm: null,
      sortBy: "name",
      orderBy: "asc",
      page: 0,
      results: {
        articles: [],
        authors: [],
        sotas: [],
        users: []
      }
    };
  },
  created() {
    // fetch the data when the view is created
    // and the data is already being observed
    this.fetchSearchResult();
  },
  watch: {
    // call it again the method if the route changes
    $route: "fetchSearchResult"
  },
  methods: {
    fetchSearchResult() {
      this.loading = true;
      this.searchTerm = this.$route.query["search"];

      const searchQuery = {
        st: this.searchTerm,
        s: this.sortBy,
        o: this.orderBy,
        p: this.page
      };
      getSearchResults(searchQuery)
        .then(data => {
          this.loading = false;
          this.results = data;
        })
        .catch(console.error);
    }
  }
};
</script>

<style scoped>
.tabs {
  position: absolute;
  top: 15%;
  left: 25%;
}

.first {
  position: relative;
  top: 15%;
  left: 5%;
}

.second {
  position: relative;
  top: 60%;
  left: 5%;
}
.md-radio {
  display: flex;
}
</style>

