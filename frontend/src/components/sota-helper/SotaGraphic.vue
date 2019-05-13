<template>
  <div class="graph-container">
    <md-card>
      <md-card-header>
        <div class="md-title center">Preview of the SoTA grouped by the categories</div>
      </md-card-header>

      <md-card-content>
        <tree
          class="graph-tree"
          :type="type"
          :layout-type="layoutType"
          :data="treeData"
          node-text="name"
          @clicked="onSelectNode"
        />
      </md-card-content>
    </md-card>
  </div>
</template>


<script>
import { tree } from "vued3tree";
import { getArticlesByCategories } from "@/services/api";

const dataTreeBase = {
  name: "My SoTA",
  children: []
};

const cutString = function(title) {
  const overflow = title.length > 75;
  return !overflow ? title : title.substr(0, 75) + "...";
};

export default {
  name: "SotaGraphic",
  components: { tree },
  props: {
    articles: {
      type: Array,
      required: true,
      default: () => []
    },
    sotaName: {
      type: String,
      default: () => "My SoTA"
    }
  },
  data() {
    return {
      width: "100%",
      height: "auto",
      layoutType: "euclidean", // circular
      type: "tree", // cluster
      selectedNode: null,
      treeData: null
    };
  },
  created() {
    this.treeData = Object.assign(dataTreeBase, {
      name: this.sotaName
    });

    // Insert the categories as node in the tree data
    this.categoriesFromArticles.forEach(category => {
      const child = {
        name: category,
        children: [
          { name: "Articles", children: [] },
          { name: "Sotas", children: [] }
        ]
      };
      this.treeData["children"].push(child);
    });

    this.fetchArticlesByCategories();
  },
  computed: {
    categoriesFromArticles() {
      const categorySet = new Set(this.articles.map(a => a["category"]));
      return [...categorySet].sort();
    }

    // treeDataFromArticles() {
    //   const tree = Object.assign(dataTreeBase);
    //   this.categoriesFromArticles.forEach(cat => {
    //     tree["children"].push({
    //       name: cat,
    //       children: [
    //         { name: "Articles", children: [] },
    //         { name: "Sota", children: [] }
    //       ]
    //     });
    //   });

    //   this.articles.forEach(({ title, category, reference }) => {
    //     const overflow = title.length > 75;
    //     const name = !overflow ? title : title.substr(0, 75) + "...";

    //     const categoryChild = tree["children"].find(c => c["name"] == category);
    //     categoryChild["children"][0]["children"].push({
    //       name,
    //       reference
    //     });
    //   });

    //   return tree;
    // }
  },
  methods: {
    onSelectNode: function(node) {
      this.selectedNode = node;
      if (node["data"]["type"]) {
        const route = node["data"]["type"];
        this.$router.push({
          name: route == "article" ? "articleDetails" : "sotaDetails",
          params: { reference: node["data"]["reference"] }
        });
      }
    },
    fetchArticlesByCategories: function() {
      const categories = this.categoriesFromArticles;

      // Send APi request to retrieve articles matching the provided categories
      getArticlesByCategories(categories)
        // After receive the API data, construct the node for the category's articles
        .then(respData => {
          categories.forEach(category => {
            // Get the matching category from the tree data
            const categoryChild = this.treeData["children"].find(
              treeCategory => treeCategory["name"] == category
            );

            // Get only the name and reference of the article
            const articleChildren = respData[categoryChild.name].map(
              article => ({
                name: cutString(article["title"]),
                reference: article["reference"],
                type: "article"
              })
            );

            // O : Articles,  1: Sotas
            categoryChild["children"][0]["children"] = articleChildren;
          });
        });
    }
  }
};
</script>

<style lang="css" scoped>
.graph-tree {
  height: 600px;
  width: 100%;
  justify-content: center;
}
.graph-tree .nodetree text {
  font: 17px sans-serif;
  cursor: pointer;
}
</style>
