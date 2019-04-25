<template>
  <div class="container">
  <div class="graphics">
    <svg
      xmlns="http://www.w3.org/2000/svg"
      v-if="bounds.minX"
      :width="width+'px'"
      :height="height+'px'"
    >
      <line
        v-for="(link,i) in graph.links"
        :key="'link_'+i"
        :x1="coords[link.source.index].x"
        :y1="coords[link.source.index].y"
        :x2="coords[link.target.index].x"
        :y2="coords[link.target.index].y"
        stroke="black"
        stroke-width="2"
      ></line>

      <circle
        v-for="(node, i) in graph.nodes"
        :key="'node_'+i"
        :cx="coords[i].x"
        :cy="coords[i].y"
        :r="45"
        :fill="choseColor(i)"
        :opacity="choseOpacity(i)"
        stroke="black"
        stroke-width="1"
        @mousedown="currentMove = {x: $event.screenX, y: $event.screenY, node: node}"
        @click="clicked(i)"
      ></circle>

      <text
        v-for="(node, i) in graph.nodes"
        :key="'text_1_'+i"
        :x="coords[i].x"
        :y="coords[i].y"
        text-anchor="middle"
        class="labelNode"
        stroke-width="1"
        color="black"
        @click="clicked(i)"
      >{{node.name.substring(0,11)}}</text>

      <text
        v-for="(node, i) in graph.nodes"
        :key="'text_2_'+i"
        :x="coords[i].x"
        :y="coords[i].y + 15"
        text-anchor="middle"
        class="labelNode"
        stroke-width="1"
        color="black"
      >{{node.name.substring(12,22) + "..."}}</text>

      <text
        v-for="(link,i) in graph.links"
        :key="'text_3_'+i"
        :x="(coords[link.source.index].x + coords[link.target.index].x) / 2"
        :y="(coords[link.source.index].y + coords[link.target.index].y) / 2"
        text-anchor="middle"
        class="labelLink"
        color="black"
      >{{link.tag}}</text>

    </svg>
  </div>
  <div class="legend">
    <h5>legend</h5>
    <p>Main domain of the article</p>
      <li v-for="item in legendMaker()">
        <p :style="{ color: item[1] }">{{item[0]}}</p>
      </li>

    <h5>Opacity: </h5>
    <p>From {{lowestYear()}} at 40% <br /> to {{highestYear()}} at 100%</p>
  </div>
  </div>
</template>

<script>
import dummy from "@/services/dummy.json";
import * as d3 from "d3";

function nodeCreator() {
  console.log("enter node creator");
  let i;
  let nodes = [];
  for (i = 0; i < dummy.articles.length; i++) {
    nodes.push(dummy.articles[i].title);
  }
  console.log(nodes);
  return nodes;
}
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
      // link: this.linker(dummy),
      graph: {},
      width: 700,
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
      currentMove: null
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
      return this.graph.nodes.map(node => {
        return {
          x:
            this.padding +
            ((node.x - this.bounds.minX) * (this.width - 2 * this.padding)) /
              (this.bounds.maxX - this.bounds.minX),
          y:
            this.padding +
            ((node.y - this.bounds.minY) * (this.height - 2 * this.padding)) /
              (this.bounds.maxY - this.bounds.minY)
        };
      });
    }
  },

  methods: {
    createGraph() {
      this.graph = {
        nodes: d3.range(this.articlesTitles.length).map(i => ({
          index: i,
          x: null,
          y: null,
          name: this.articlesTitles[i][0],
          domain: this.articlesTitles[i][2],
          ref: this.articlesTitles[i][1],
          year: this.articlesTitles[i][3]
        })),
        links: d3.range(this.linkedArticles.length).map(i => ({
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

    clicked(index){
      window.location.assign("http://localhost:8080/article/"+this.graph.nodes[index].ref);
    },

    choseColor(j){
      let arrayDom = [];
      for (let i=0; i < this.graph.nodes.length; i++){

        if (!(arrayDom.includes(this.graph.nodes[i].domain))) {
          arrayDom.push(this.graph.nodes[i].domain)
        }
      }
      let colorNumber = arrayDom.indexOf(this.graph.nodes[j].domain);
      return this.colors[colorNumber]
    },

    /*creatTabYear(){
      let arrayYear = []
      for (let i=0; i < this.graph.nodes.length; i++){
        if (!(arrayYear.includes(this.graph.nodes[i].year))) {
          arrayYear.push(this.graph.nodes[i].year)
        }
      }
      arrayYear.sort();
      return arrayYear
    },*/

    choseOpacity(j){
      let arrayYear = []
      for (let i=0; i < this.graph.nodes.length; i++){
        if (!(arrayYear.includes(this.graph.nodes[i].year))) {
          arrayYear.push(this.graph.nodes[i].year)
        }
      }
      arrayYear.sort();
      let delta = arrayYear[arrayYear.length-1] - arrayYear[0]
      let opacityVar = (( (arrayYear[arrayYear.length-1]+4) - this.graph.nodes[j].year) / delta)
      return opacityVar
    },

    legendMaker(){
      let arrayDom = [];
      for (let i=0; i < this.graph.nodes.length; i++){
        if (!(arrayDom.includes(this.graph.nodes[i].domain))) {
          arrayDom.push(this.graph.nodes[i].domain)
        }
      }
      let colorLegend = [];
      for (let i=0; i < arrayDom.length; i++){
        let mapper = [];
        mapper.push(this.graph.nodes[i].domain);
        mapper.push(this.colors[i]);
        colorLegend.push(mapper)
      }
      return colorLegend
    },

    lowestYear(){
      let arrayYear = []
      for (let i=0; i < this.graph.nodes.length; i++){
        if (!(arrayYear.includes(this.graph.nodes[i].year))) {
          arrayYear.push(this.graph.nodes[i].year)
        }
      }
      arrayYear.sort();
      return arrayYear[0]
    },

    highestYear(){
      let arrayYear = []
      for (let i=0; i < this.graph.nodes.length; i++){
        if (!(arrayYear.includes(this.graph.nodes[i].year))) {
          arrayYear.push(this.graph.nodes[i].year)
        }
      }
      arrayYear.sort();
      return  arrayYear[arrayYear.length-1]
    }

  }
};
</script>


<style>
  .graphics{
    float: left;
    width: 80%;
  }
  .legend{
    float: left;
    width: 20%;
  }

  p{
    font-size: 9pt;
  }
</style>
