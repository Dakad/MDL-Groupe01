<template>
  <div class="container">
    <div class="graphics">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        v-if="bounds.minX"
        :width="width+'%'"
        :height="height+'px'"
      >
        <circle
          v-for="(node, i) in graph.nodes"
          :key="'node_'+i"
          :cx="coords[i].x"
          :cy="coords[i].y"
          :r="selected == node ? '15' : '10'"
          :fill="choseColor(node.domain)"
          :opacity="choseOpacity(i)"
          class="node-container"
          stroke="black"
          stroke-width="1"
          @mouseover="showInfo(node)"
          @mouseout="nullInfo(node)"
          @click="clicked(node)"
        ></circle>

        <template v-for="(link, i) in graph.links">
          <line
            :key="'link_'+i"
            :x1="coords[link.source.index].x"
            :y1="coords[link.source.index].y"
            :x2="coords[link.target.index].x"
            :y2="coords[link.target.index].y"
            class="link-line"
            stroke="black"
            stroke-width="2"
          ></line>
          <text
            :key="'text_3_'+i"
            :x="(coords[link.source.index].x + coords[link.target.index].x) / 2"
            :y="(coords[link.source.index].y + coords[link.target.index].y) / 2"
            text-anchor="middle"
            class="link-label"
            color="black"
          >{{link.tag.substr(0, link.tag.length - 2)}}</text>
        </template>
      </svg>
    </div>
    <div class="legend">
      <div class="more-info">
        <h5 class>More Info</h5>
        <div>
          <label>Title</label> :
          <span class="holder name">{{nameHolder}}</span>
          <br>
          <label>Domain</label> :
          <span class="holder domain">{{domainHolder | capitalize }}</span>
          <br>
          <label>Year of publication</label> :
          <span class="holder year">{{yearHolder}}</span>
        </div>
      </div>

      <hr>

      <div class="legend-color-info">
        <h5>Legend</h5>
        <p>Main domain of the article</p>
        <li class="colors-info" v-for="(item, i) in legendMaker" :key="i">
          <p :style="{ color: item['color'] }">{{item['domain'] | capitalize }}</p>
        </li>
      </div>

      <hr>

      <div class="opacity-info">
        <h5>Opacity:</h5>
        <p>
          From {{lowestYear}} at 40%
          <br>
          to {{highestYear}} at 100%
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import * as d3 from "d3";

export default {
  name: "Graphics",
  props: {
    articlesTitles: {
      type: Array,
      required: true,
      default: () => []
    },
    linkedArticles: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      graph: {},
      width: 100,
      height: 550,
      padding: 60,
      colors: [
        "#2196F3",
        "#E91E63",
        "#7E57C2",
        "#009688",
        "#00BCD4",
        "#EF6C00",
        "#4CAF50",
        "#FF9800",
        "#F44336",
        "#CDDC39",
        "#9C27B0"
      ],
      simulation: null,
      currentMove: null,
      nameHolder: "",
      domainHolder: "",
      yearHolder: "",
      rMouseOver: 25,
      rMouseOut: 10,
      r:10,
      selected: null
    };
  },
  watch: {
    linkedArticles: "createGraph"
  },
  created() {
    this.createGraph();
  },

  computed: {
    bounds() {
      return {
        minX: Math.min(...this.graph.nodes.map(n => n.x)),
        maxX: Math.max(...this.graph.nodes.map(n => n.x)),
        minY: Math.min(...this.graph.nodes.map(n => n.y)),
        maxY: Math.max(...this.graph.nodes.map(n => n.y))
      };
    },
    coords() {
      const clientWidth = document.getElementById("graphics").clientWidth;
      return this.graph.nodes.map(node => {
        return {
          x:
            this.padding +
            ((node.x - this.bounds.minX) * (clientWidth - 7 * this.padding)) /
              (this.bounds.maxX - this.bounds.minX),
          y:
            this.padding +
            ((node.y - this.bounds.minY) * (this.height - 2 * this.padding)) /
              (this.bounds.maxY - this.bounds.minY)
        };
      });
    },

    lowestYear() {
      return this.graph.nodes
        .map(n => n.year)
        .sort()
        .slice(0, 1);
    },

    highestYear() {
      return this.graph.nodes
        .map(n => n.year)
        .sort()
        .slice(-1);
    },

    legendMaker() {
      const domains = new Set(this.graph.nodes.map(n => n.domain));
      const colorLegend = Array.from(domains).reduce((acc, domain, i) => {
        acc.push({
          domain,
          color: this.colors[i]
        });
        return acc;
      }, []);

      return colorLegend;
    }
  },

  methods: {
    createGraph() {
      this.graph = {
        nodes: d3.range(this.articlesTitles.length).map(i => ({
          index: i,
          x: null,
          y: null,
          name: this.articlesTitles[i]["title"],
          domain: this.articlesTitles[i]["domain"],
          ref: this.articlesTitles[i]["reference"],
          year: this.articlesTitles[i]["year"]
        })),
        links: d3.range(Object.keys(this.linkedArticles).length).map(i => ({
          source: this.linkedArticles[i][0],
          target: this.linkedArticles[i][1],
          tag: this.linkedArticles[i][2]
        }))
      };
      this.simulation = d3
        .forceSimulation(this.graph.nodes)
        .force("charge", d3.forceManyBody().strength(d => -100))
        .force("link", d3.forceLink(this.graph.links))
        .force("x", d3.forceX())
        .force("y", d3.forceY());
    },

    clicked(node) {
      this.$router.push({
        name: "articleDetails",
        params: { reference: node.ref }
      });
    },

    choseColor(domain) {
      const allDomain = this.graph.nodes.map(n => n.domain);
      const arrayDom = Array.from(new Set(allDomain));
      const colorNumber = arrayDom.indexOf(domain);
      return this.colors[colorNumber];
    },

    choseOpacity(j) {
      let arrayYear = [];
      for (let i = 0; i < this.graph.nodes.length; i++) {
        if (!arrayYear.includes(this.graph.nodes[i].year)) {
          arrayYear.push(this.graph.nodes[i].year);
        }
      }
      arrayYear.sort();
      let delta = arrayYear[arrayYear.length - 1] - arrayYear[0];
      let deltasum = ((delta*20)/delta);
      let opacityVar =
        (arrayYear[arrayYear.length - 1] + delta + deltasum - this.graph.nodes[j].year) /
        (delta + deltasum);
      return 2 - opacityVar;
    },

    showInfo(d, i) {
      this.nameHolder = d.name;
      this.domainHolder = d.domain;
      this.yearHolder = d.year;

      this.selected = d;

      this.r = this.rMouseOver
    },

    nullInfo(d, i) {
      this.r = this.rMouseOut

    }
  }
};
</script>


<style lang="css" scoped>

.graphics {
  position: relative;
  float: left;
  width: 75%;
  margin-right: 3px;
  border: lightgrey 1px solid;
}

.node-container,
.node-label,
.link-line,
.link-label {
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.node-container:hover,
.node-label:hover {
  cursor: pointer;
}

.legend {
  float: left;
  width: 20%;
}

.legend .more-info label {
  font-weight: bold;
  text-decoration: underline;
}

.legend .colors-info {
  list-style: none;
}

.node-container,
.node-label,
.link-line,
.link-label {
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.node-container:hover,
.node-label:hover {
  cursor: pointer;
}

p {
  font-size: 10pt;
}
</style>
