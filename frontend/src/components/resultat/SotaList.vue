<template>
  <div class="sota">
    <md-card v-for="sota in list" :key="sota.reference" class="md-layout">
      <div class="md-layout-item">
        <md-card-header>
          <div class="title">
            <h5>
              <router-link :to="{ name: 'sotaDetails', params: { reference: sota.reference }}">
                <h5>{{sota.title}}</h5>
              </router-link>
            </h5>
          </div>
        </md-card-header>
        <md-card-content>
          <div class="info">
            <div class="author">
              <p>
                Written By:
                <router-link
                  :to="{ name: 'userProfile', params: { reference: sota.creator.username }}"
                >
                  <md-item href="/">{{sota.creator.username}}</md-item>
                </router-link>
              </p>
            </div>
            <div class="keyword">
              <p>Main subject: {{sota.subject}}</p>
            </div>
            <div class="date">
              <p>Published on the: {{sota.created_at}}</p>
            </div>
          </div>
        </md-card-content>
      </div>
      <div class="md-layout-item md-size-10">
        <md-button class="md-icon-button" v-if="logged">
          <md-icon>bookmark</md-icon>
        </md-button>
      </div>
    </md-card>
    <Pagination
      class="md-layout md-alignment-center"
      id="sota-pagination"
      v-if="page['total_pages'] >= 1"
      v-model="currentPage"
      :page-count="page['total_pages']"
    />
  </div>
</template>

<script>
import { Pagination } from "@/components";
import { isLogged } from "@/services/api-user";

export default {
  name: "SotaList",
  components: {
    Pagination
  },
  props: {
    list: {
      type: Array,
      default: _ => []
    },
    meta: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      currentPage: 1,
      logged: false
    };
  },
  watch: {
    currentPage: function() {
      this.$emit("pagination", this.currentPage);
    }
  },
  created() {
    this.logged = isLogged();
  },
  computed: {
    sotas() {
      return this.list;
    },
    page() {
      return this.meta || {};
    }
  }
};
</script>

<style scoped>
.md-list {
  margin: 10px;
  display: inline-block;
}

.author {
  float: left;
  margin-left: 5px;
  margin-right: 30px;
}
.keyword {
  float: left;
  margin-left: 5px;
  margin-right: 30px;
}
.date {
  float: left;
  margin-left: 5px;
  margin-right: 30px;
}

#sota-pagination {
  margin: 20px;
}
</style>
