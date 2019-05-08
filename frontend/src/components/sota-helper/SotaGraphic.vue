<template>
  <div class="graph-container">
    <tree
      class="graph-tree"
      :type="type"
      :layout-type="layoutType"
      :data="treeDataFromArticles"
      node-text="name"
      :zoomable="true"
    />
  </div>
</template>


<script>
import { tree } from "vued3tree";
import { getArticlesByCategories } from "@/services/api";
import dummy from "@/services/dummy/sota-helper-visu.json";

const dataTreeBase = {
  name: "My SoTA",
  children: []
};

export default {
  name: "SotaGraphic",
  components: { tree },
  props: {
    articles: {
      type: Array,
      required: true,
      default: () => []
    }
  },
  data() {
    return {
      width: "100%",
      height: "auto",
      layoutType: "euclidean", // circular
      type: "tree", // cluster
      selectedNode: null,
      data: null
    };
  },
  created() {
    this.fetchArticlesByCategories();
    this.data = this.articles;
  },
  computed: {
    getCategoryFromSelectedArticles() {
      return this.articles.map(a => a["category"]);
    },

    treeDataFromArticles() {
      return dummy;
    }
  },
  methods: {
    select: function(node) {
      this.selectedNode = node;
    },
    fetchArticlesByCategories: function() {
      const categories = this.getCategoryFromSelectedArticles;
      this.data = Object.assign(dataTreeBase, {});
      categories.forEach(cat => {
        const child = {
          name: cat,
          children: [
            { name: "Articles", children: [] },
            { name: "Sotas", children: [] }
          ]
        };
        data["children"].push(child);
      });
      // TODO Send APi request to retrieve articles in the provided categories;
      getArticlesByCategories(categories).then(respData => {
        this.data["children"] = Object.keys(respData).reduce((list, cat) => {
          const article = respData[cat];
        }, []);
      });
    }
  }
};
</script>

<style lang="css" scoped>
.graph-tree {
  height: 600px;
  width: 100%;
}
</style>
